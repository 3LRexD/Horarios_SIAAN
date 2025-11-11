package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.service.GeneradorHorariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-generador")
public class GeneradorTestController {

    @Autowired
    private GeneradorHorariosService generador;

    @PostMapping("/ordenar")
    public ResponseEntity<List<Long>> ordenar(@RequestBody List<Long> materiaIds) {
        List<Long> ordenado = generador.ordenarMateriasPorNumeroDeParalelos(materiaIds);
        return ResponseEntity.ok(ordenado);
    }
}
