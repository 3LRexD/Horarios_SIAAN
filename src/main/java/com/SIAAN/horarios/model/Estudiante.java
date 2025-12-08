package com.SIAAN.horarios.model;

import jakarta.persistence.*; // Si usas Spring Boot 3
// import javax.persistence.*; // Si usas Spring Boot 2 (usa este si el de arriba da error)

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Column(name = "nombre") // Asumo que tienes una columna nombre, si se llama diferente cámbialo aquí
    private String nombre;
    
    // Constructor vacío obligatorio
    public Estudiante() {
    }

    // Getters y Setters
    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}