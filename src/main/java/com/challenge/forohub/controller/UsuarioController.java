package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuario.*;
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
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")

public class UsuarioController {


    @Autowired
    private IUsuarioRepository usuarioRepository;



    @PostMapping
    public ResponseEntity<DatosListarUsuarios> agregarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroUsuario);
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosListarUsuarios datosListarUsuarios = new DatosListarUsuarios(
                usuario.getId(),
                usuario.getNombre()
        );
        URI url = uriComponentsBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosListarUsuarios);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarUsuarios>> mostrarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListarUsuarios> listaUsuarios = usuarioRepository.findAll(pageable).map(DatosListarUsuarios::new);
        return ResponseEntity.ok(listaUsuarios);
    }



    @GetMapping("/{id}")
    public ResponseEntity<DatosListarUsuarios> muestraUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        DatosListarUsuarios datosListarUsuarios = new DatosListarUsuarios(
                usuario.getId(),
                usuario.getNombre()
        );

        return ResponseEntity.ok(datosListarUsuarios);
    }



    @PutMapping
    @Transactional
    public ResponseEntity<DatosListarUsuarios> actualizaUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {


        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosListarUsuarios(usuario));

    }



    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuario(@PathVariable Long id) {

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
