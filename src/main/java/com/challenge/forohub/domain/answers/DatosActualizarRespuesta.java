package com.challenge.forohub.domain.answers;

import lombok.NonNull;


public record DatosActualizarRespuesta(
        @NonNull
        Long id,
        String mensaje,
        Boolean solucion) {
}
