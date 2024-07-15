package com.challenge.forohub.controller;

import com.challenge.forohub.domain.answers.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")

public class RespuestaController {

    @Autowired
    private RespuestaServicio respuestaService;


    @PostMapping
    public ResponseEntity<DatosListarRespuestas> agregarRespuesta(@RequestBody @Valid DatosRegistrarRespuesta datosRegistroRespuesta) {

        DatosListarRespuestas datosListadoRespuestas = respuestaService.agregarRespuesta(datosRegistroRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);

    }


    @PutMapping
    public ResponseEntity<DatosListarRespuestas> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        DatosListarRespuestas datosListadoRespuestas = respuestaService.actualizaRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListarRespuestas>> mostrarRespuestas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(respuestaService.getRespuestas(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosListarRespuestas> mostrarRespuesta(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.getRespuesta(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity borrarRespuesta(@PathVariable Long id) {
        respuestaService.deleteRespuesta(id);
        return ResponseEntity.noContent().build();
    }

}
