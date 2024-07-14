package com.alura.api.model.topico;

import jakarta.validation.constraints.NotBlank;

public record TopicoCreacionDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String autor,
        @NotBlank
        String curso
){
}
