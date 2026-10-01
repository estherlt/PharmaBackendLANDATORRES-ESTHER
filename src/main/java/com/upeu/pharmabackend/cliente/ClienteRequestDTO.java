package com.upeu.pharmabackend.cliente;

import jakarta.validation.constraints.*;

public record ClienteRequestDTO(
        @NotBlank(message = "El DNI es obligatorio.")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos.")
        String dni,

        @NotBlank(message = "Los nombres son obligatorios.")
        @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres.")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios.")
        @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres.")
        String apellidos,

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "El correo no tiene un formato válido.")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres.")
        String email,

        @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener exactamente 9 dígitos.")
        String telefono,

        @Size(max = 250, message = "La dirección no puede superar los 250 caracteres.")
        String direccion,

        @NotNull(message = "El estado es obligatorio.")
        Boolean estado
) {}
