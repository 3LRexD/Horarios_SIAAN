package com.SIAAN.horarios.util;

import com.SIAAN.horarios.model.Horario;
import java.time.LocalTime;
import java.util.List;

/**
 * Representa la matriz de ocupación.
 * Usa una precisión de minutos (1440 mins/día) para soportar tus bloques exactos
 * (07:15, 08:45, etc.) y los recreos intermedios sin configuraciones complejas.
 */
public class ScheduleMatrix {

    // 7 días (0=Lunes ... 6=Domingo) x 1440 minutos (24 horas)
    private final boolean[][] matrix;

    public ScheduleMatrix() {
        this.matrix = new boolean[7][1440]; 
    }

    // Constructor privado para clonar la matriz
    private ScheduleMatrix(boolean[][] sourceMatrix) {
        this.matrix = new boolean[7][1440];
        for (int i = 0; i < 7; i++) {
            System.arraycopy(sourceMatrix[i], 0, this.matrix[i], 0, 1440);
        }
    }

    
    public boolean checkAndAdd(List<Horario> horarios) {
        // ve si hay espacio
        for (Horario h : horarios) {
            int diaIdx = mapDiaToIndice(h.getDiaSemana().getNombre());
            
            // Convertimos las horas exactas a minutos del día
            // Ej: 07:15 -> 435
            int inicio = toMinutes(h.getHoraInicio());
            int fin = toMinutes(h.getHoraFin());

           
            for (int m = inicio; m < fin; m++) {
                if (m >= 1440) break; // por si es despues de media noche
                if (this.matrix[diaIdx][m]) {
                    return false; // hay choque
                }
            }
        }

        // para marcar como ocupado
        // solo se llega aca si lo anterior fue exitoso para TODOS los horarios del paralelo
        for (Horario h : horarios) {
            int diaIdx = mapDiaToIndice(h.getDiaSemana().getNombre());
            int inicio = toMinutes(h.getHoraInicio());
            int fin = toMinutes(h.getHoraFin());

            for (int m = inicio; m < fin; m++) {
                if (m < 1440) {
                    this.matrix[diaIdx][m] = true; 
                }
            }
        }
        return true; 
    }

    //matriz copia aca u.u
    public ScheduleMatrix copy() {
        return new ScheduleMatrix(this.matrix);
    }

    // --- Auxiliares ---

    private int toMinutes(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    // mapea los dias
    private int mapDiaToIndice(String nombreDia) {
        if (nombreDia == null) return 0;
        String normalized = nombreDia.trim().toUpperCase();
        
                                                    //columnas
        if (normalized.contains("LUN")) return 0; 
        if (normalized.contains("MAR")) return 1; 
        if (normalized.contains("MIE") || normalized.contains("MIÉ")) return 2; 
        if (normalized.contains("JUE")) return 3; 
        if (normalized.contains("VIE")) return 4; 
        if (normalized.contains("SAB") || normalized.contains("SÁB")) return 5; 
        if (normalized.contains("DOM")) return 6; 
        
        return 0; // lunes x defecto
    }
}