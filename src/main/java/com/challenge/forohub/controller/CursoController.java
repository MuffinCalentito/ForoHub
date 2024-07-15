package com.challenge.forohub.controller;

import com.challenge.forohub.domain.subject.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")

public class CursoController {

    @Autowired
    private ICursoRespository cursoRespository;

    @PostMapping
    public ResponseEntity<DatosListarCurso> agregarCurso(@RequestBody @Valid DatosRegistrarCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroCurso);
        Curso curso = cursoRespository.save(new Curso(datosRegistroCurso));
        DatosListarCurso datosListarCurso = new DatosListarCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
        URI url = uriComponentsBuilder.path("cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(datosListarCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarCurso>> mostrarCursos(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListarCurso> listarCursos = cursoRespository.findAll(pageable).map(DatosListarCurso::new);
        return ResponseEntity.ok(listarCursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListarCurso> muestraCurso(@PathVariable Long id) {
        Curso curso = cursoRespository.getReferenceById(id);
        DatosListarCurso datosListarCurso = new DatosListarCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
        return ResponseEntity.ok(datosListarCurso);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListarCurso> actualizaCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        Curso curso = cursoRespository.getReferenceById(datosActualizarCurso.id());
        curso.actualizarCurso(datosActualizarCurso);
        return ResponseEntity.ok(new DatosListarCurso(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarCurso(@PathVariable Long id) {
        cursoRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
