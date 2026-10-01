package com.upeu.pharmabackend.cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String dni,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String direccion,
        boolean estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {
    public static ClienteResponseDTO desde(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(), c.getDni(), c.getNombres(), c.getApellidos(), c.getEmail(),
                c.getTelefono(), c.getDireccion(), c.isEstado(),
                c.getFechaCreacion(), c.getFechaModificacion());
    }
}
