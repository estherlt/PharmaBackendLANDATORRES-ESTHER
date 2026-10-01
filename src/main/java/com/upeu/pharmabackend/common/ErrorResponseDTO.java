package com.upeu.pharmabackend.common;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    public static ErrorResponseDTO de(int status, String error, String message, String path) {
        return new ErrorResponseDTO(LocalDateTime.now(), status, error, message, path, null);
    }

    public static ErrorResponseDTO deValidacion(String message, String path, Map<String, String> errores) {
        return new ErrorResponseDTO(LocalDateTime.now(), 400, "Bad Request", message, path, errores);
    }
}
