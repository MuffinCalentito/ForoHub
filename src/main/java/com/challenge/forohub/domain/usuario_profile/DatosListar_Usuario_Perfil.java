package com.challenge.forohub.domain.usuario_profile;

public record DatosListar_Usuario_Perfil(
        String nomrbreUsuario,
        String nombrePerfil) {

    public DatosListar_Usuario_Perfil(Usuario_Perfil usuarioPerfil) {

        this(usuarioPerfil.getUsuario().getNombre(),
                usuarioPerfil.getPerfil().getNombre());
    }
}
