package com.challenge.forohub.domain.subject;

public record DatosListarCurso(
        Long id,
        String nombre,
        String categoria) {

    public DatosListarCurso(Curso curso) {
        this(curso.getId(),
                curso.getNombre(),
                curso.getCategoria());
    }
}
