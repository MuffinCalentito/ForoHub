package com.challenge.forohub.domain.answers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosRegistrarRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long topico_id,
        @NotNull
        Long usuario_id) {
}
