package com.SIAAN.horarios.dto;

public interface MallaItemDTO {
    Long getIdMateria();         
    String getNombreMateria();   
    String getSigla();           
    Integer getSemestre();       
    String getEstado();          
    
    // ¡Nuevo!
    String getPrerrequisitos(); 
}