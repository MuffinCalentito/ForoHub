package com.challenge.forohub.domain.usuario_profile;

import com.challenge.forohub.domain.profile.Perfil;
import com.challenge.forohub.domain.profile.IPerfilRespository;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.IUsuarioRepository;
import com.challenge.forohub.infra.Error.ErrorDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class Usuario_Perfil_Service {

    @Autowired

    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IUsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private IPerfilRespository perfilRespository;

    public DatosListar_Usuario_Perfil agregarPefil(DatosRegistro_Usuario_Perfil datosRegistroUsuarioPerfil) {

        Usuario usuario;
        Perfil perfil;

        if (usuarioRepository.findById(datosRegistroUsuarioPerfil.usuario_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontró el usuario en el repositorio");
        }
        if (perfilRespository.findById(datosRegistroUsuarioPerfil.perfil_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontró el perfil en el repositorio");
        }

        usuario = usuarioRepository.getReferenceById(datosRegistroUsuarioPerfil.usuario_id());
        perfil = perfilRespository.getReferenceById(datosRegistroUsuarioPerfil.perfil_id());

        Usuario_Perfil usuarioPerfil = new Usuario_Perfil(null, usuario, perfil);

        Usuario_Perfil usuarioPerfil1 = usuarioPerfilRepository.save(usuarioPerfil);

        return new DatosListar_Usuario_Perfil(usuarioPerfil1);
    }

    public List<DatosListar_Usuario_Perfil> mostrarUsuarioPerfil() {
        return usuarioPerfilRepository.findAll().stream().map(DatosListar_Usuario_Perfil::new).toList();
    }

    public void borrarUsuarioPerfil(Long id) {
        usuarioPerfilRepository.deleteById(id);
    }
}
