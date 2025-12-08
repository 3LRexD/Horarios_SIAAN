package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.dto.MallaItemDTO;
import com.SIAAN.horarios.repository.AcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/academico")
@CrossOrigin(origins = "*")
public class AcademicoController {

    @Autowired
    private AcademicoRepository academicoRepository;

    // Ejemplo: GET http://localhost:8080/api/academico/malla/1
   @GetMapping("/malla/{idEstudiante}")
    public ResponseEntity<List<MallaItemDTO>> getMalla(@PathVariable Long idEstudiante) {
        
        List<MallaItemDTO> malla = academicoRepository.obtenerMallaPorEstudiante(idEstudiante);
        
        // CAMBIO: Si está vacío, devolvemos OK (200) con lista vacía, NO un error (404).
        if (malla.isEmpty()) {
            return ResponseEntity.ok(java.util.Collections.emptyList());
        }
        
        return ResponseEntity.ok(malla);
    }
}