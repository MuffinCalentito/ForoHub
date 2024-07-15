package com.challenge.forohub.domain.topicos;

import java.time.LocalDateTime;

public record DatosListarTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha
) {
    public DatosListarTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha());
    }
}
