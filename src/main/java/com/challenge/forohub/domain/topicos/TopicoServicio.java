package com.challenge.forohub.domain.topicos;

import com.challenge.forohub.domain.subject.Curso;
import com.challenge.forohub.domain.subject.ICursoRespository;
import com.challenge.forohub.domain.answers.DTO_TopicoRespuestas;
import com.challenge.forohub.domain.usuario.Usuario;
import com.challenge.forohub.domain.usuario.IUsuarioRepository;
import com.challenge.forohub.infra.Error.ErrorDeConsulta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service

public class TopicoServicio {

    @Autowired
    private ICursoRespository cursoRespository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ITopicoRepository topicoRepository;

    public DatosListarTopico agregarTopico(DatosRegistrarTopico datosRegistroTopico) {
        Curso curso;
        Usuario usuario;

        if (!usuarioRepository.existsById(datosRegistroTopico.usuario_id())) {
            throw new ErrorDeConsulta("No se encontró al usuario en el repositorio");
        }

        if (cursoRespository.findByNombre(datosRegistroTopico.nombreCurso()).isPresent()) {
            curso = cursoRespository.findByNombre(datosRegistroTopico.nombreCurso()).get();
        } else {
            throw new ErrorDeConsulta("No se encontró el curso en el repositorio");
        }

        usuario = usuarioRepository.findById(datosRegistroTopico.usuario_id()).get();

        Topico topico = new Topico(
                null,
                datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje(),
                LocalDateTime.now(),
                true,
                usuario,
                curso,
                new ArrayList<>());

        Topico topicoL = topicoRepository.save(topico);
        return new DatosListarTopico(
                topicoL.getId(),
                topicoL.getTitulo(),
                topicoL.getMensaje(),
                topicoL.getFecha());
    }

    public Page<DatosListarTopico> getTopicos(Pageable pageable) {
        return topicoRepository.findAllByStatusTrue(pageable).map(DatosListarTopico::new);
    }


    public DatosListarTopico getTopico(Long id) {
        if (topicoRepository.findByStatusById(id) == null) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        if (!topicoRepository.findByStatusById(id)) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        if (topicoRepository.findById(id).isEmpty()) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        Topico topicoL = topicoRepository.findById(id).get();

        return new DatosListarTopico(
                topicoL.getId(),
                topicoL.getTitulo(),
                topicoL.getMensaje(),
                topicoL.getFecha());
    }


    @Transactional
    public DatosListarTopico actualizaTopico(DatosActualizarTopico datosActualizarTopico) {
        if (!topicoRepository.existsById(datosActualizarTopico.id())) {
            throw new ErrorDeConsulta("No se encontró el topico en el repositorio");
        }
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarTopico(datosActualizarTopico);
        return new DatosListarTopico(topico);
    }


    @Transactional
    public void desactivaTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ErrorDeConsulta("No se encontró el topico en el repositorio");
        }
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivaTopico();
    }

    public Page<DTO_TopicoRespuestas> getRespuestas(Long id, Pageable pageable) {
        if (!topicoRepository.existsById(id) || !topicoRepository.findByStatusById(id)) {
            throw new ErrorDeConsulta("No se encontró el topico en el repositorio");
        }
        Page<DTO_TopicoRespuestas> dto_topicoRespuestas = topicoRepository.findAllByRespuestas(id, pageable).map(DTO_TopicoRespuestas::new);
        return dto_topicoRespuestas;
    }
}
