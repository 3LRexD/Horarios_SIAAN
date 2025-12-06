package com.SIAAN.horarios.export;

import com.SIAAN.horarios.model.Horario;
import com.SIAAN.horarios.model.Paralelo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class ExcelVisualExporter {

    private static final List<String[]> BLOQUES_TIEMPO = Arrays.asList(
            new String[]{"07:15", "08:00"},
            new String[]{"08:00", "08:45"},
            new String[]{"09:00", "09:45"},
            new String[]{"09:45", "10:30"},
            new String[]{"10:45", "11:30"},
            new String[]{"11:30", "12:15"},
            new String[]{"12:30", "13:15"},
            new String[]{"13:15", "14:00"},
            new String[]{"14:15", "15:00"},
            new String[]{"15:00", "15:45"},
            new String[]{"16:00", "16:45"},
            new String[]{"16:45", "17:30"},
            new String[]{"17:45", "18:30"},
            new String[]{"18:30", "19:15"},
            new String[]{"19:30", "20:15"},
            new String[]{"20:15", "21:00"},
            new String[]{"21:00", "21:30"}
    );

    // Ancho de la tabla (1 hora + 6 días) + Espacio (3 columnas vacías)
    private static final int ANCHO_TABLA = 7; 
    private static final int ESPACIO_ENTRE_TABLAS = 2;
    private static final int DESPLAZAMIENTO = ANCHO_TABLA + ESPACIO_ENTRE_TABLAS;

    public static ByteArrayInputStream generarExcelVisual(List<List<Paralelo>> opciones) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Estilos
            CellStyle baseStyle = workbook.createCellStyle();
            baseStyle.setAlignment(HorizontalAlignment.CENTER);
            baseStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            baseStyle.setBorderBottom(BorderStyle.THIN);
            baseStyle.setBorderTop(BorderStyle.THIN);
            baseStyle.setBorderLeft(BorderStyle.THIN);
            baseStyle.setBorderRight(BorderStyle.THIN);
            baseStyle.setWrapText(true);

            // Estilo para el título de la opción (Negrita y Grande)
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.cloneStyleFrom(baseStyle);
            Font fontTitulo = workbook.createFont();
            fontTitulo.setBold(true);
            fontTitulo.setFontHeightInPoints((short) 14);
            tituloStyle.setFont(fontTitulo);
            tituloStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            tituloStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // CREAMOS UNA SOLA HOJA PARA TODO
            Sheet sheet = workbook.createSheet("Mis Horarios");
            
            // Mapa de colores global (para que Matemáticas sea siempre azul en todas las opciones)
            Map<String, CellStyle> coloresMaterias = new HashMap<>();

            int columnaInicio = 0;
            int contadorOpcion = 1;

            for (List<Paralelo> opcion : opciones) {
                
                // 1. Dibujar el Título "OPCIÓN X" encima de la tabla
                Row rowTitulo = sheet.getRow(0); // Usamos la fila 0 para títulos
                if (rowTitulo == null) rowTitulo = sheet.createRow(0);
                
                Cell cellTitulo = rowTitulo.createCell(columnaInicio);
                cellTitulo.setCellValue("OPCIÓN " + contadorOpcion);
                cellTitulo.setCellStyle(tituloStyle);
                
                // Fusionar celdas del título para que abarque toda la tabla
                sheet.addMergedRegion(new CellRangeAddress(0, 0, columnaInicio, columnaInicio + 6));

                // 2. DIBUJAR LA ESTRUCTURA (A partir de la columnaInicio)
                dibujarEsqueleto(sheet, baseStyle, columnaInicio);

                // 3. LLENAR LOS DATOS
                for (Paralelo p : opcion) {
                    CellStyle estiloMateria = obtenerEstiloColor(workbook, p.getMateria().getNombre(), coloresMaterias, baseStyle);
                    for (Horario h : p.getListaHorarios()) {
                        pintarBloque(sheet, h, p.getMateria().getNombre(), estiloMateria, columnaInicio);
                    }
                }

                // 4. AJUSTAR ANCHO DE COLUMNAS
                // Ajustamos las 7 columnas de ESTA tabla actual
                sheet.setColumnWidth(columnaInicio, 4000); // Columna Hora
                for (int i = 1; i < 7; i++) {
                    sheet.setColumnWidth(columnaInicio + i, 5000); // Columnas Días
                }

                // Calcular dónde empieza la siguiente tabla
                columnaInicio += DESPLAZAMIENTO;
                contadorOpcion++;
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Error al generar Excel visual: " + e.getMessage());
        }
    }

    private static void dibujarEsqueleto(Sheet sheet, CellStyle style, int colStart) {
        // Cabecera de Días (Fila 1 ahora, porque la 0 es el título gigante)
        Row header = sheet.getRow(1);
        if (header == null) header = sheet.createRow(1);

        String[] dias = {"HORA", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SÁBADO"};
        
        for (int i = 0; i < dias.length; i++) {
            Cell cell = header.createCell(colStart + i);
            cell.setCellValue(dias[i]);
            cell.setCellStyle(style);
        }

        // Columna de Horas
        int rowIdx = 2; // Empezamos en fila 2
        for (String[] bloque : BLOQUES_TIEMPO) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) row = sheet.createRow(rowIdx);

            Cell cellHora = row.createCell(colStart);
            cellHora.setCellValue(bloque[0] + " - " + bloque[1]);
            cellHora.setCellStyle(style);
            
            // Celdas vacías con borde
            for(int k=1; k<=6; k++) {
                Cell c = row.createCell(colStart + k);
                c.setCellStyle(style);
            }
            rowIdx++;
        }
    }

    private static void pintarBloque(Sheet sheet, Horario h, String nombreMateria, CellStyle style, int colStart) {
        // La columna depende del día + el desplazamiento de la tabla actual
        int columnaDia = colStart + mapDiaToColumna(h.getDiaSemana().getNombre());
        
        int startRow = -1;
        int endRow = -1;
        LocalTime inicioClase = h.getHoraInicio();
        LocalTime finClase = h.getHoraFin();

        for (int i = 0; i < BLOQUES_TIEMPO.size(); i++) {
            LocalTime inicioBloque = LocalTime.parse(BLOQUES_TIEMPO.get(i)[0]);
            if (!inicioBloque.isBefore(inicioClase) && inicioBloque.isBefore(finClase)) {
                if (startRow == -1) startRow = i + 2; // +2 por Título y Cabecera
                endRow = i + 2;
            }
        }

        if (startRow != -1 && endRow != -1) {
            Row row = sheet.getRow(startRow);
            if (row == null) row = sheet.createRow(startRow);
            
            Cell cell = row.getCell(columnaDia);
            if (cell == null) cell = row.createCell(columnaDia);

            cell.setCellValue(nombreMateria);
            cell.setCellStyle(style);

            if (endRow > startRow) {
                CellRangeAddress region = new CellRangeAddress(startRow, endRow, columnaDia, columnaDia);
                // Validar que no se solape 
                boolean alreadyMerged = false;
                for(int i=0; i<sheet.getNumMergedRegions(); i++){
                    if(sheet.getMergedRegion(i).intersects(region)){
                        alreadyMerged = true; 
                        break;
                    }
                }
                if(!alreadyMerged) {
                    sheet.addMergedRegion(region);
                }
            }
        }
    }

    private static final short[] COLORES = {
            IndexedColors.LIGHT_TURQUOISE.getIndex(),
            IndexedColors.LIGHT_GREEN.getIndex(),
            IndexedColors.LIGHT_YELLOW.getIndex(),
            IndexedColors.LIGHT_ORANGE.getIndex(),
            IndexedColors.ROSE.getIndex(),
            IndexedColors.LAVENDER.getIndex(),
            IndexedColors.LEMON_CHIFFON.getIndex(),
            IndexedColors.AQUA.getIndex()
    };

    private static CellStyle obtenerEstiloColor(Workbook wb, String materia, Map<String, CellStyle> mapa, CellStyle base) {
        if (mapa.containsKey(materia)) {
            return mapa.get(materia);
        }
        CellStyle style = wb.createCellStyle();
        style.cloneStyleFrom(base);
        int colorIdx = mapa.size() % COLORES.length;
        style.setFillForegroundColor(COLORES[colorIdx]);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        mapa.put(materia, style);
        return style;
    }

    private static int mapDiaToColumna(String nombreDia) {
        String n = nombreDia.toUpperCase().trim();
        if (n.contains("LUN")) return 1;
        if (n.contains("MAR")) return 2;
        if (n.contains("MIE") || n.contains("MIÉ")) return 3;
        if (n.contains("JUE")) return 4;
        if (n.contains("VIE")) return 5;
        if (n.contains("SAB") || n.contains("SÁB")) return 6;
        return 1;
    }
}