package com.SIAAN.horarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paralelo")
public class Paralelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    public Paralelo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }
}
    