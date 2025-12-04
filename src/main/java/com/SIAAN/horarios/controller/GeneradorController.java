package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.service.GeneradorHorariosService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/generador")
@CrossOrigin(origins = "*") // Permite que el frontend (React/Angular) se conecte sin problemas
public class GeneradorController {

    private final GeneradorHorariosService generadorService;

    public GeneradorController(GeneradorHorariosService generadorService) {
        this.generadorService = generadorService;
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generarHorarios(@RequestBody List<Long> idsMaterias) {
        try {
            
            List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);

            if (opciones.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "mensaje", "no hay combinaciones validas :C",
                    "total_opciones", 0
                ));
            }

            //salida pal postman
            return ResponseEntity.ok(Map.of(
                "mensaje", "Horarios generados exitosamente.",
                "total_opciones", opciones.size(),
                "data", opciones 
            ));

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
   

    // metodo antiguo

    /*@PostMapping("/exportar-excel")
    public ResponseEntity<InputStreamResource> exportarExcel(@RequestBody List<Long> idsMaterias) {
        
        List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);

       
        java.io.ByteArrayInputStream in = ExcelExporter.horariosToExcel(opciones);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=horarios_generados.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }*/
   @PostMapping("/exportar-excel")
    public ResponseEntity<InputStreamResource> exportarExcel(@RequestBody List<Long> idsMaterias) {
        // genera opcinoes
        List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);
        // expoortamos
        java.io.ByteArrayInputStream in = com.SIAAN.horarios.export.ExcelVisualExporter.generarExcelVisual(opciones);
        //respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=horarios_visuales.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}