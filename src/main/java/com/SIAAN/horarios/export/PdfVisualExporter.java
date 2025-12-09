package com.SIAAN.horarios.export;

import com.SIAAN.horarios.model.Horario;
import com.SIAAN.horarios.model.Paralelo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color; // Ojo: Usar java.awt.Color para conversión
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.util.List;

public class PdfVisualExporter {

    // Los mismos bloques de tiempo que en tu Excel y Vue
    private static final String[] HORAS_TEXTO = {
        "07:15 - 08:00", "08:00 - 08:45", "09:00 - 09:45", "09:45 - 10:30",
        "10:45 - 11:30", "11:30 - 12:15", "12:30 - 13:15", "13:15 - 14:00",
        "14:15 - 15:00", "15:00 - 15:45", "16:00 - 16:45", "16:45 - 17:30",
        "17:45 - 18:30", "18:30 - 19:15", "19:30 - 20:15", "20:15 - 21:00"
    };

    private static final LocalTime[] HORAS_INICIO = {
        LocalTime.parse("07:15"), LocalTime.parse("08:00"), LocalTime.parse("09:00"), LocalTime.parse("09:45"),
        LocalTime.parse("10:45"), LocalTime.parse("11:30"), LocalTime.parse("12:30"), LocalTime.parse("13:15"),
        LocalTime.parse("14:15"), LocalTime.parse("15:00"), LocalTime.parse("16:00"), LocalTime.parse("16:45"),
        LocalTime.parse("17:45"), LocalTime.parse("18:30"), LocalTime.parse("19:30"), LocalTime.parse("20:15")
    };
    
    // Duración aproximada de cada bloque para calcular el fin (45 mins)
    // Esto es simplificado para matching.

    public static ByteArrayInputStream generarPdf(List<Paralelo> opcionSeleccionada) {
        Document document = new Document(PageSize.A4.rotate()); // Hoja horizontal
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Horario Generado - SIAAN", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // 2. Tabla (6 columnas: Hora + 5 días)
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2f, 2f, 2f, 2f, 2f}); // Anchos relativos

            // 3. Cabeceras
            String[] cabeceras = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
            for (String cabecera : cabeceras) {
                PdfPCell cell = new PdfPCell(new Phrase(cabecera, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
                cell.setBackgroundColor(new Color(0, 51, 102)); // Tu azul institucional
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            // 4. Llenado de Filas (Grid)
            // Paleta de colores simple para el PDF (puedes mejorarla)
            Color colorFondoCelda = new Color(245, 245, 245);

            for (int i = 0; i < HORAS_TEXTO.length; i++) {
                String rangoHora = HORAS_TEXTO[i];
                LocalTime horaInicioBloque = HORAS_INICIO[i];
                LocalTime horaFinBloque = horaInicioBloque.plusMinutes(45); // Asumimos 45 min por bloque

                // Celda de Hora
                PdfPCell cellHora = new PdfPCell(new Phrase(rangoHora, FontFactory.getFont(FontFactory.HELVETICA, 9)));
                cellHora.setBackgroundColor(new Color(230, 230, 230));
                cellHora.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellHora.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cellHora);

                // Celdas de Días (Lunes a Viernes)
                String[] dias = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES"};
                
                for (String dia : dias) {
                    Paralelo claseEncontrada = buscarClaseEnBloque(opcionSeleccionada, dia, horaInicioBloque, horaFinBloque);
                    
                    PdfPCell cellDia = new PdfPCell();
                    cellDia.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellDia.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellDia.setPadding(5);

                    if (claseEncontrada != null) {
                        // Texto de la materia
                        String texto = claseEncontrada.getMateria().getNombre() + "\n(" + claseEncontrada.getNumParalelo() + ")";
                        cellDia.setPhrase(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                        
                        // Color de fondo (Amarillito suave para resaltar)
                        cellDia.setBackgroundColor(new Color(255, 250, 205)); 
                    } else {
                        cellDia.setPhrase(new Phrase(""));
                        cellDia.setBackgroundColor(Color.WHITE);
                    }
                    table.addCell(cellDia);
                }
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método auxiliar para buscar coincidencia
    private static Paralelo buscarClaseEnBloque(List<Paralelo> paralelos, String diaColumna, LocalTime inicioBloque, LocalTime finBloque) {
        for (Paralelo p : paralelos) {
            if (p.getListaHorarios() != null) {
                for (Horario h : p.getListaHorarios()) {
                    // Normalizar día
                    String diaClase = h.getDiaSemana().getNombre().toUpperCase().replace("É", "E").replace("Á", "A");
                    
                    if (diaClase.equals(diaColumna)) {
                        // Coincidencia de Hora
                        // Si el bloque empieza justo cuando empieza la clase O está dentro del rango
                        if (!inicioBloque.isBefore(h.getHoraInicio()) && inicioBloque.isBefore(h.getHoraFin())) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }
}