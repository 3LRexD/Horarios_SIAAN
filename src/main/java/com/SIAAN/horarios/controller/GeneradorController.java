package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.service.GeneradorHorariosService;
// Asegúrate de que este import coincida con tu estructura
import com.SIAAN.horarios.export.ExcelVisualExporter; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/generador")
@CrossOrigin(origins = "*") // Importante para que Vue se comunique con Spring
public class GeneradorController {

    private final GeneradorHorariosService generadorService;

    public GeneradorController(GeneradorHorariosService generadorService) {
        this.generadorService = generadorService;
    }

    // Endpoint para ver JSON (Solo datos)
    @PostMapping("/generar")
    public ResponseEntity<?> generarHorarios(@RequestBody List<Long> idsMaterias) {
        try {
            List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);
            if (opciones.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensaje", "No hay combinaciones válidas", "total_opciones", 0));
            }
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

    // Endpoint para descargar EXCEL (El que nos interesa)
    @PostMapping("/exportar-excel")
    public ResponseEntity<?> exportarExcel(@RequestBody List<Long> idsMaterias) {
        try {
            // 1. Validar entrada
            if (idsMaterias == null || idsMaterias.isEmpty()) {
                return ResponseEntity.badRequest().body("Debe seleccionar al menos una materia.");
            }

            // 2. Generar lógica
            List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);

            if (opciones.isEmpty()) {
                return ResponseEntity.badRequest().body("No se encontraron combinaciones de horarios posibles.");
            }

            // 3. Convertir a Excel (Tu exportador actual)
            ByteArrayInputStream in = ExcelVisualExporter.generarExcelVisual(opciones);

            // 4. Preparar cabeceras para descarga
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=horarios_visuales.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al generar el archivo: " + e.getMessage());
        }
    }
}