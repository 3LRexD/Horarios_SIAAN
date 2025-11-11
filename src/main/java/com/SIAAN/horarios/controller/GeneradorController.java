package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.service.GeneradorHorariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/generador")
public class GeneradorController {

    @Autowired
    private GeneradorHorariosService generador;

    // [30, 23, 5, 6]postman prueba de sis info, tecweb, dbb2, estructura
    @PostMapping("/generar-dias-horas")
    public ResponseEntity<?> generarPorDiasHoras(@RequestBody List<Long> materiaIds) {
        return generador.generarHorarioPorDiasYHoras(materiaIds)
            .<ResponseEntity<?>>map(sol -> {
                List<Map<String, Object>> resp = sol.stream().map(p -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("idParalelo", p.getIdParalelo());
                    data.put("numParalelo", p.getNumParalelo());
                    data.put("idMateria", p.getMateria() != null ? p.getMateria().getIdMateria() : null);
                    return data;
                }).collect(Collectors.toList());
                return ResponseEntity.ok(resp);
            })
            .orElseGet(() -> ResponseEntity.status(404).body("No se encontró solución"));

    }
}
