package com.SIAAN.horarios.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; 
import java.util.List;

@Entity
@Table(name = "tipos")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Long idTipo;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

   
    @OneToMany(mappedBy = "tipo")
    @JsonIgnore 
    private List<Materia> materias;

    public Tipo() {
    }

    public Tipo(Long idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }

    // getters setters
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}