package com.SIAAN.horarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long idMateria;

    private String nombre;
    private String sigla;

    @Column(name = "creditos_academicos", nullable = false)
    private int creditosAcademicos;


    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private Tipo tipo;

    public Materia() {
    }

    public Materia(Long idMateria, String nombre, String sigla, int creditosAcademicos, Tipo tipo) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.sigla = sigla;
        this.creditosAcademicos = creditosAcademicos;
        this.tipo = tipo;
    }
   // getters y setters
    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getCreditosAcademicos() {
        return creditosAcademicos;
    }

    public void setCreditosAcademicos(int creditosAcademicos) {
        this.creditosAcademicos = creditosAcademicos;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
