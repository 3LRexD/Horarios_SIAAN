package com.SIAAN.horarios.controller; // Ajusta tu paquete

import com.SIAAN.horarios.model.Estudiante;
import com.SIAAN.horarios.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "http://localhost:5175") // para el vue poner el port que te salga cuando el vue arranque 
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/buscar")
    public ResponseEntity<?> obtenerEstudiante(@RequestParam String correo) {
        
        Estudiante est = estudianteRepository.findByUsuarioAndContrasena(correo, "123"); 
        
        return ResponseEntity.ok(est);
    }
}