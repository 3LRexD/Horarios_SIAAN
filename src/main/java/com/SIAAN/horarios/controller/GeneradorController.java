package com.SIAAN.horarios.controller;

import com.SIAAN.horarios.dto.ClaseHorarioDTO;
import com.SIAAN.horarios.dto.OpcionHorarioDTO;
import com.SIAAN.horarios.model.Horario; 
import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.service.GeneradorHorariosService;
import com.SIAAN.horarios.export.ExcelVisualExporter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.SIAAN.horarios.repository.ParaleloRepository;
import com.SIAAN.horarios.export.PdfVisualExporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/generador")
@CrossOrigin(origins = "*") 
public class GeneradorController {

    private final GeneradorHorariosService generadorService;
    private final ParaleloRepository paraleloRepository;

    // Paleta de colores pastel
    private final String[] COLORES = {
        "#FFD700", "#FFB347", "#87CEEB", "#90EE90", 
        "#DDA0DD", "#FF6961", "#F0E68C", "#FFA07A"
    };

    public GeneradorController(GeneradorHorariosService generadorService, ParaleloRepository paraleloRepository) {
        this.generadorService = generadorService;
        this.paraleloRepository = paraleloRepository;
    }
    // --- ENDPOINT 1: GENERAR Y VER EN PANTALLA (JSON) ---
    @PostMapping("/generar")
    public ResponseEntity<?> generarHorarios(@RequestBody List<Long> idsMaterias) {
        try {
            List<List<Paralelo>> opcionesCrudas = generadorService.generarOpciones(idsMaterias);

            if (opcionesCrudas.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensaje", "No hay combinaciones válidas", "total_opciones", 0));
            }

            // Aquí sucede la magia de conversión
            List<OpcionHorarioDTO> opcionesVisuales = convertirA_DTOs(opcionesCrudas);

            return ResponseEntity.ok(opcionesVisuales);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- ENDPOINT 2: DESCARGAR EXCEL ---
    @PostMapping("/exportar-excel")
    public ResponseEntity<?> exportarExcel(@RequestBody List<Long> idsMaterias) {
        try {
            if (idsMaterias == null || idsMaterias.isEmpty()) {
                return ResponseEntity.badRequest().body("Debe seleccionar al menos una materia.");
            }

            List<List<Paralelo>> opciones = generadorService.generarOpciones(idsMaterias);

            if (opciones.isEmpty()) {
                return ResponseEntity.badRequest().body("No se encontraron combinaciones.");
            }

            ByteArrayInputStream in = ExcelVisualExporter.generarExcelVisual(opciones);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=horarios_visuales.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/exportar-individual")
    public ResponseEntity<?> exportarIndividual(@RequestBody List<Long> idsParalelos) {
        try {
            // 1. Buscamos los paralelos específicos de esa opción
            List<Paralelo> paralelosEncontrados = paraleloRepository.findAllById(idsParalelos);
            
            // 2. Los envolvemos en una lista de listas (porque el exportador espera varias opciones)
            // Aquí creamos una lista que contiene UNA sola opción.
            List<List<Paralelo>> opcionUnica = List.of(paralelosEncontrados);

            // 3. Generamos el Excel
            ByteArrayInputStream in = ExcelVisualExporter.generarExcelVisual(opcionUnica);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=horario_seleccionado.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/exportar-pdf-individual")
    public ResponseEntity<?> exportarPdfIndividual(@RequestBody List<Long> idsParalelos) {
        try {
            // 1. Buscamos los paralelos
            List<Paralelo> paralelosEncontrados = paraleloRepository.findAllById(idsParalelos);
            
            // 2. Generamos el PDF
            ByteArrayInputStream in = PdfVisualExporter.generarPdf(paralelosEncontrados);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=horario_seleccionado.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF) // Tipo MIME para PDF
                    .body(new InputStreamResource(in));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al generar PDF: " + e.getMessage());
        }
    }

    

    // --- EL MÉTODO QUE CONVIERTE LOS DATOS (CORREGIDO) ---
    private List<OpcionHorarioDTO> convertirA_DTOs(List<List<Paralelo>> opcionesCrudas) {
        
        List<OpcionHorarioDTO> resultado = new ArrayList<>();
        int contadorOpcion = 1;

        for (List<Paralelo> combinacion : opcionesCrudas) {
            List<ClaseHorarioDTO> clasesDeEstaOpcion = new ArrayList<>();
            int colorIndex = 0; 

            for (Paralelo p : combinacion) {
                String colorActual = COLORES[colorIndex % COLORES.length];
                
                // 1. Usamos getListaHorarios() (según tu clase Paralelo)
                if (p.getListaHorarios() != null) {
                    for (Horario h : p.getListaHorarios()) {
                        ClaseHorarioDTO dto = new ClaseHorarioDTO();

                        dto.setIdParalelo(p.getIdParalelo());
                        // Datos de la materia
                        dto.setNombreMateria(p.getMateria().getNombre());
                        dto.setSigla(p.getMateria().getSigla());
                        dto.setGrupo(p.getNumParalelo()); // Usamos getNumParalelo()

                        
                        
                        
                        // 2. Datos del horario (CORREGIDO SEGÚN TUS CLASES)
                        
                        // DÍA: Accedemos al objeto DiaSemana y luego a su nombre
                        if (h.getDiaSemana() != null) {
                            dto.setDia(h.getDiaSemana().getNombre()); 
                        }
                        
                        // HORA INICIO: Convertimos LocalTime a String
                        if (h.getHoraInicio() != null) {
                            dto.setHoraInicio(h.getHoraInicio().toString()); 
                        }

                        // HORA FIN: Convertimos LocalTime a String
                        if (h.getHoraFin() != null) {
                            dto.setHoraFin(h.getHoraFin().toString());
                        }
                        
                        dto.setColor(colorActual);
                        
                        clasesDeEstaOpcion.add(dto);
                    }
                }
                colorIndex++; 
            }
            resultado.add(new OpcionHorarioDTO(contadorOpcion++, clasesDeEstaOpcion));
        }
        return resultado;
    }
}