package com.SIAAN.horarios.dto;

import java.util.List;

public class OpcionHorarioDTO {
    private int idOpcion; // Identificador: 1, 2, 3...
    private List<ClaseHorarioDTO> clases; // La lista de materias de esta opción

    // Constructor vacío
    public OpcionHorarioDTO() {
    }

    // Constructor completo
    public OpcionHorarioDTO(int idOpcion, List<ClaseHorarioDTO> clases) {
        this.idOpcion = idOpcion;
        this.clases = clases;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public List<ClaseHorarioDTO> getClases() {
        return clases;
    }

    public void setClases(List<ClaseHorarioDTO> clases) {
        this.clases = clases;
    }
}