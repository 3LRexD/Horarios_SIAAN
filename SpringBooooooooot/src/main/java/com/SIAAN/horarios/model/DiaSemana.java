package com.SIAAN.horarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dias_semana")
public class DiaSemana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia")
    private Long idDia;

    private String nombre;

    public DiaSemana() {}

    // --- Getters y Setters ---
    public Long getIdDia() {
        return idDia;
    }

    public void setIdDia(Long idDia) {
        this.idDia = idDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
