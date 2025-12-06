package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.model.Estudiante;
import com.SIAAN.horarios.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5175")//aca cambiar el port de acuerdo al vue para que funque
public class AuthController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String password = credenciales.get("password");

        Estudiante estudiante = estudianteRepository.findByUsuarioAndContrasena(usuario, password);

        if (estudiante != null) {
            return ResponseEntity.ok(estudiante); 
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}