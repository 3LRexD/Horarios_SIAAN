package com.SIAAN.horarios.dto;

public interface HorarioMateriaDTO {
    Integer getIdHorario();
    String getGrupo();
    String getDia();
    String getHoraInicio();
    String getHoraFin();
    String getDocente(); // Nombre del profe
}