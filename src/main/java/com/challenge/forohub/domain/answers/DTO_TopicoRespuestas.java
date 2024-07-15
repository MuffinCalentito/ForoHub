package com.challenge.forohub.domain.answers;

import java.time.LocalDateTime;


public record DTO_TopicoRespuestas(
        String mensaje,
        LocalDateTime fecha,
        String nombre,
        Boolean solucion
) {

    public DTO_TopicoRespuestas(Respuestas respuesta) {
        this(respuesta.getMensaje(),
                respuesta.getFecha(),
                respuesta.getUsuario().
                        getNombre(),
                respuesta.getSolucion());
    }
}
