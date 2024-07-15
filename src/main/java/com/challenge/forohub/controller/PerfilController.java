package com.challenge.forohub.controller;

import com.challenge.forohub.domain.profile.*;
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
@RequestMapping("/perfiles")
@SecurityRequirement(name = "bearer-key")

public class PerfilController {

    @Autowired
    private IPerfilRespository perfilRespository;

    @PostMapping
    public ResponseEntity<DatosListarPerfil> agregarPerfil(@RequestBody @Valid DatosRegistrarPerfil datosRegistroPerfil, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroPerfil);
        Perfil perfil = perfilRespository.save(new Perfil(datosRegistroPerfil));
        DatosListarPerfil datosListadoPerfil = new DatosListarPerfil(
                perfil.getId(),
                perfil.getNombre()
        );
        URI url = uriComponentsBuilder.path("perfiles/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoPerfil);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarPerfil>> mostrarPerfil(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListarPerfil> listadoPerfil = perfilRespository.findAll(pageable).map(DatosListarPerfil::new);
        return ResponseEntity.ok(listadoPerfil);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListarPerfil> muestraPerfil(@PathVariable Long id) {
        Perfil perfil = perfilRespository.getReferenceById(id);
        DatosListarPerfil datosListarPerfil = new DatosListarPerfil(
                perfil.getId(),
                perfil.getNombre()
        );
        return ResponseEntity.ok(datosListarPerfil);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListarPerfil> actualizaPerfil(@RequestBody @Valid DatosActualizarPerfil datosActualizarPerfil) {
        Perfil perfil = perfilRespository.getReferenceById(datosActualizarPerfil.id());
        perfil.actualizarPerfil(datosActualizarPerfil);
        return ResponseEntity.ok(new DatosListarPerfil(perfil));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarPerfil(@PathVariable Long id) {
        perfilRespository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
