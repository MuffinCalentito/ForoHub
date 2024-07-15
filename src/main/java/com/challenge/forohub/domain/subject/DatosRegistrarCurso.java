package com.challenge.forohub.domain.subject;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarCurso(

        @NotBlank
        String nombre,

        @Nullable
        String categoria) {
}
