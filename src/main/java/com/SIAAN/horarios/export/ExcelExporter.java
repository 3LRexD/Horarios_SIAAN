package com.SIAAN.horarios.export;

import com.SIAAN.horarios.model.Horario;
import com.SIAAN.horarios.model.Paralelo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static ByteArrayInputStream horariosToExcel(List<List<Paralelo>> opciones) {
        // 1. Crear el libro de Excel (Workbook)
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Horarios Generados");

            // 2. Crear estilo para la cabecera (Negrita)
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // 3. Crear la Fila de Títulos
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"Opción #", "Materia", "Paralelo", "Docente", "Días y Horas"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // 4. Llenar los datos
            int rowIdx = 1;
            int opcionNumero = 1;

            for (List<Paralelo> opcion : opciones) {
                // Por cada opción, recorremos sus materias
                for (Paralelo p : opcion) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue("Opción " + opcionNumero);
                    row.createCell(1).setCellValue(p.getMateria().getNombre());
                    row.createCell(2).setCellValue(p.getNumParalelo()); // Ej: "P1"
                    
                    // Manejo seguro de docente (por si es nulo)
                    String nombreDocente = (p.getDocente() != null) ? p.getDocente().getNombre() : "Sin asignar";
                    row.createCell(3).setCellValue(nombreDocente);

                    // Formatear el horario bonito (Ej: "LUN 10:45-12:15")
                    row.createCell(4).setCellValue(formatearHorarios(p.getListaHorarios()));
                }
                
                // Agregamos una fila vacía para separar visualmente las opciones
                rowIdx++; 
                opcionNumero++;
            }

            // Ajustar tamaño de columnas automáticamente
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error al crear el archivo Excel: " + e.getMessage());
        }
    }

    // Método auxiliar para convertir la lista de horarios en un texto legible
    private static String formatearHorarios(List<Horario> horarios) {
        if (horarios == null || horarios.isEmpty()) return "Por definir";
        
        StringBuilder sb = new StringBuilder();
        for (Horario h : horarios) {
            sb.append(h.getDiaSemana().getNombre().toUpperCase().substring(0, 3)) // Ej: LUN
              .append(" ")
              .append(h.getHoraInicio())
              .append("-")
              .append(h.getHoraFin())
              .append(" | ");
        }
        return sb.toString();
    }
}