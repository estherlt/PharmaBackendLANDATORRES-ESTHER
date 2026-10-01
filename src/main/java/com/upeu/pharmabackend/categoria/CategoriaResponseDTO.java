package com.upeu.pharmabackend.categoria;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        boolean estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion
) {
    public static CategoriaResponseDTO desde(Categoria c) {
        return new CategoriaResponseDTO(
                c.getId(), c.getNombre(), c.getDescripcion(), c.isEstado(),
                c.getFechaCreacion(), c.getFechaModificacion());
    }
}
