package com.challenge.forohub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarTopico(
        @NotNull
        Long usuario_id,
        @NotBlank
        String nombreCurso,
        @NotBlank
        String mensaje,
        @NotBlank
        String titulo) {
}
