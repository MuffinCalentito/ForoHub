package com.challenge.forohub.domain.answers;

import java.time.LocalDateTime;

public record DatosListarRespuestas(
        Long id,
        String mensaje,
        String nombreTopico,
        LocalDateTime fecha,
        String nombreUsuario,
        Boolean solucion
) {
    public DatosListarRespuestas(Respuestas respuesta) {
        this(respuesta.getId() ,
                respuesta.getMensaje(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFecha(),
                respuesta.getUsuario().getNombre(),
                respuesta.getSolucion());
    }
}
