package com.challenge.forohub.domain.profile;

import jakarta.validation.constraints.NotBlank;


public record DatosRegistrarPerfil(
        @NotBlank
        String nombre) {
}
