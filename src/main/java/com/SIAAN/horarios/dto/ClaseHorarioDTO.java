package com.SIAAN.horarios.dto;

public class ClaseHorarioDTO {
    private String nombreMateria;
    private String sigla;      // Ej: SIS-111
    private String grupo;      // Ej: "1" o "A"
    private String dia;        // Ej: "LUNES"
    private String horaInicio; // Ej: "07:15"
    private String horaFin;    // Ej: "09:45"
    private String color;      // Ej: "#FF5733"
    private Long idParalelo;

    // Constructor vacío (Necesario para JSON)
    public ClaseHorarioDTO() {
    }

    // Constructor completo
    public ClaseHorarioDTO(String nombreMateria, String sigla, String grupo, String dia, String horaInicio, String horaFin, String color, Long idParalelo) {
        this.nombreMateria = nombreMateria;
        this.sigla = sigla;
        this.grupo = grupo;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.color = color;
        this.idParalelo = idParalelo;
    }

    // --- GETTERS Y SETTERS ---

    public Long getIdParalelo() { 
        return idParalelo; 
    }

    public void setIdParalelo(Long idParalelo) { 
        this.idParalelo = idParalelo; 
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}