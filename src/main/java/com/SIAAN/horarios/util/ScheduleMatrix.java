package com.SIAAN.horarios.util;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ScheduleMatrix {

    // configuración: rango horario y resolución en minutos
    private final LocalTime START = LocalTime.of(6, 0);   // 06:00
    private final LocalTime END = LocalTime.of(22, 0);    // 22:00
    private final int SLOT_MINUTES = 30;                  // resolución 30 minutos

    private final int slotsPerDay;
    private final Map<String, boolean[]> matrix = new HashMap<>();

    public ScheduleMatrix() {
        this.slotsPerDay = computeSlotsPerDay();
    }

    private int computeSlotsPerDay() {
        long minutes = java.time.Duration.between(START, END).toMinutes();
        return (int) (minutes / SLOT_MINUTES);
    }

    private int timeToSlotIndex(LocalTime t) {
        if (t.isBefore(START)) return 0;
        if (t.isAfter(END)) return slotsPerDay; // fuera de rango
        long minutes = java.time.Duration.between(START, t).toMinutes();
        return (int) (minutes / SLOT_MINUTES);
    }

    // asegura que el día exista en la matriz
    private boolean[] ensureDay(String dayName) {
        return matrix.computeIfAbsent(dayName, k -> new boolean[slotsPerDay]);
    }

    // comprueba si el intervalo [inicio, fin) cabe libre en ese día
    public boolean isFree(String dayName, LocalTime inicio, LocalTime fin) {
        boolean[] day = ensureDay(dayName);
        int s = timeToSlotIndex(inicio);
        int e = timeToSlotIndex(fin);
        if (s < 0) s = 0;
        if (e > slotsPerDay) e = slotsPerDay;
        for (int i = s; i < e; i++) {
            if (day[i]) return false;
        }
        return true;
    }

    // reserva el intervalo (marca true)
    public void reserve(String dayName, LocalTime inicio, LocalTime fin) {
        boolean[] day = ensureDay(dayName);
        int s = timeToSlotIndex(inicio);
        int e = timeToSlotIndex(fin);
        for (int i = s; i < e; i++) day[i] = true;
    }

    // libera el intervalo (marca false)
    public void release(String dayName, LocalTime inicio, LocalTime fin) {
        boolean[] day = ensureDay(dayName);
        int s = timeToSlotIndex(inicio);
        int e = timeToSlotIndex(fin);
        for (int i = s; i < e; i++) day[i] = false;
    }

    // opcional: limpiar todo
    public void clear() {
        matrix.clear();
    }
}
