package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.dto.MallaItemDTO;
import com.SIAAN.horarios.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Permite que Vue (localhost:5173 o similar) consuma la API
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // URL para probar: http://localhost:8080/api/estudiantes/1/malla
    @GetMapping("/{id}/malla")
    public List<MallaItemDTO> getMallaCurricular(@PathVariable("id") Integer id) {
        return estudianteRepository.obtenerMallaPorEstudiante(id);
    }
}