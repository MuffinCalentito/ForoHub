package com.challenge.forohub.domain.usuario;

public record DatosListarUsuarios(
        Long id,
        String nombre)
{

    public DatosListarUsuarios(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNombre());
    }
}
