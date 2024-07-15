package com.challenge.forohub.domain.answers;

import com.challenge.forohub.domain.topicos.Topico;
import com.challenge.forohub.domain.topicos.ITopicoRepository;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.IUsuarioRepository;
import com.challenge.forohub.infra.Error.ErrorDeConsulta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class RespuestaServicio {

    @Autowired
    private ITopicoRepository topicoRepository;
    @Autowired
    private IRespuestaRepository respuestaRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    public DatosListarRespuestas agregarRespuesta(DatosRegistrarRespuesta datosRegistroRespuesta) {
        Topico topico;
        Usuario usuario;


        if (topicoRepository.findById(datosRegistroRespuesta.topico_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontro el topico en el repositorio");
        }
        if (!topicoRepository.findByStatusById(datosRegistroRespuesta.topico_id())) {
            throw new ErrorDeConsulta("No se encontro el topico en el repositorio");
        }
        if (usuarioRepository.findById(datosRegistroRespuesta.usuario_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontro al usuario en el repositorio");
        }

        topico = topicoRepository.findById(datosRegistroRespuesta.topico_id()).get();

        usuario = usuarioRepository.findById(datosRegistroRespuesta.usuario_id()).get();

        Respuestas respuestas = new Respuestas(
                null,
                datosRegistroRespuesta.mensaje(),
                topico,
                LocalDateTime.now(),
                usuario,
                false
        );

        topico.agregarRespuesta(respuestas);
        Respuestas respuesta = respuestaRepository.save(respuestas);

        return new DatosListarRespuestas(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFecha(),
                respuesta.getUsuario().getNombre(),
                respuesta.getSolucion()
        );

    }


    public Page<DatosListarRespuestas> getRespuestas(Pageable pageable) {
        return respuestaRepository.findAll(pageable).map(DatosListarRespuestas::new);
    }


    public void deleteRespuesta(Long id) {
        respuestaRepository.deleteById(id);
    }


    public DatosListarRespuestas getRespuesta(Long id) {
        if (respuestaRepository.findById(id).isEmpty()) {
            throw new ErrorDeConsulta("No se encontró la respuesta");
        }
        Respuestas respuesta = respuestaRepository.findById(id).get();
        return new DatosListarRespuestas(respuesta) ;
    }


    @Transactional
    public DatosListarRespuestas actualizaRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        if (respuestaRepository.findById(datosActualizarRespuesta.id()).isEmpty()) {
            throw new ErrorDeConsulta("No se halló la respuesta");
        }
        Respuestas respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actualiza(datosActualizarRespuesta);
        return new DatosListarRespuestas(respuesta);
    }
}
