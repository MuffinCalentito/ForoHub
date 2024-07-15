package com.challenge.forohub.domain.profile;

public record DatosListarPerfil(
        Long id,
        String nombre) {
    public DatosListarPerfil(Perfil perfil) {

        this(perfil.getId(), perfil.getNombre());
    }
}
