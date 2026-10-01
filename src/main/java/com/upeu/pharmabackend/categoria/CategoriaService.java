package com.upeu.pharmabackend.categoria;

import com.upeu.pharmabackend.common.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repositorio;

    public CategoriaService(CategoriaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<CategoriaResponseDTO> listar() {
        return repositorio.findAll().stream()
                .map(CategoriaResponseDTO::desde)
                .toList();
    }

    public CategoriaResponseDTO obtener(Long id) {
        return CategoriaResponseDTO.desde(buscarOFallar(id));
    }

    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        aplicar(categoria, dto);
        return CategoriaResponseDTO.desde(repositorio.save(categoria));
    }

    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = buscarOFallar(id);
        aplicar(categoria, dto);
        return CategoriaResponseDTO.desde(repositorio.save(categoria));
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarOFallar(id);
        repositorio.delete(categoria);
    }

    private void aplicar(Categoria categoria, CategoriaRequestDTO dto) {
        categoria.setNombre(dto.nombre().trim());
        categoria.setDescripcion(dto.descripcion() == null || dto.descripcion().isBlank()
                ? null : dto.descripcion().trim());
        categoria.setEstado(dto.estado());
    }

    private Categoria buscarOFallar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una categoría con id " + id));
    }
}
