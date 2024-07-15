package com.challenge.forohub.domain.usuario_profile;

import jakarta.validation.constraints.NotNull;


public record DatosRegistro_Usuario_Perfil(
        @NotNull
        Long usuario_id,
        @NotNull
        Long perfil_id) {
}
