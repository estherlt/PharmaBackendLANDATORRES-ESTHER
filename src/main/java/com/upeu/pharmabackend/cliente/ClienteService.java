package com.upeu.pharmabackend.cliente;

import com.upeu.pharmabackend.common.OperacionInvalidaException;
import com.upeu.pharmabackend.common.PaginaResponseDTO;
import com.upeu.pharmabackend.common.RecursoDuplicadoException;
import com.upeu.pharmabackend.common.RecursoNoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClienteService {

    private static final Set<String> CAMPOS_ORDEN = Set.of("id", "dni", "nombres", "apellidos", "email");

    private final ClienteRepository repositorio;

    public ClienteService(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public PaginaResponseDTO<ClienteResponseDTO> listar(int pagina, int tamanio, String ordenarPor, String direccion) {
        if (!CAMPOS_ORDEN.contains(ordenarPor)) {
            throw new OperacionInvalidaException(
                    "El campo de orden '" + ordenarPor + "' no es válido. Usa: id, dni, nombres, apellidos o email.");
        }
        if (pagina < 0 || tamanio < 1 || tamanio > 100) {
            throw new OperacionInvalidaException("Parámetros de paginación no válidos: pagina >= 0 y tamanio entre 1 y 100.");
        }
        Sort.Direction dir = "desc".equalsIgnoreCase(direccion) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort orden = Sort.by(dir, ordenarPor).and(Sort.by(Sort.Direction.ASC, "id"));
        PageRequest pageRequest = PageRequest.of(pagina, tamanio, orden);

        Page<ClienteResponseDTO> resultado = repositorio.findAll(pageRequest).map(ClienteResponseDTO::desde);
        return PaginaResponseDTO.desde(resultado);
    }

    public ClienteResponseDTO obtener(Long id) {
        return ClienteResponseDTO.desde(buscarOFallar(id));
    }

    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        validarUnicos(dto, null);
        Cliente cliente = new Cliente();
        aplicar(cliente, dto);
        return ClienteResponseDTO.desde(repositorio.save(cliente));
    }

    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = buscarOFallar(id);
        validarUnicos(dto, id);
        aplicar(cliente, dto);
        return ClienteResponseDTO.desde(repositorio.save(cliente));
    }

    public void darDeBaja(Long id) {
        Cliente cliente = buscarOFallar(id);
        if (!cliente.isEstado()) {
            throw new OperacionInvalidaException("El cliente con id " + id + " ya se encuentra inactivo.");
        }
        cliente.setEstado(false);
        repositorio.save(cliente);
    }

    private void validarUnicos(ClienteRequestDTO dto, Long idActual) {
        repositorio.findByDni(dto.dni()).ifPresent(existente -> {
            if (!existente.getId().equals(idActual)) {
                throw new RecursoDuplicadoException("Ya existe un cliente registrado con el DNI " + dto.dni() + ".");
            }
        });
        repositorio.findByEmail(dto.email()).ifPresent(existente -> {
            if (!existente.getId().equals(idActual)) {
                throw new RecursoDuplicadoException("Ya existe un cliente registrado con el correo " + dto.email() + ".");
            }
        });
    }

    private void aplicar(Cliente cliente, ClienteRequestDTO dto) {
        cliente.setDni(dto.dni().trim());
        cliente.setNombres(dto.nombres().trim());
        cliente.setApellidos(dto.apellidos().trim());
        cliente.setEmail(dto.email().trim());
        cliente.setTelefono(dto.telefono() == null || dto.telefono().isBlank() ? null : dto.telefono().trim());
        cliente.setDireccion(dto.direccion() == null || dto.direccion().isBlank() ? null : dto.direccion().trim());
        cliente.setEstado(dto.estado());
    }

    private Cliente buscarOFallar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un cliente con id " + id));
    }
}
