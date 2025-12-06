package com.SIAAN.horarios.model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "paralelos")
public class Paralelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paralelo")
    private Long idParalelo;

    @Column(name = "num_paralelo")
    private String numParalelo;

    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente", nullable = false)
    private Docente docente;

    @OneToMany(mappedBy = "paralelo", fetch = FetchType.EAGER)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Horario> listaHorarios;

    public Paralelo() {
    }


    public Paralelo(String numParalelo, Materia materia, Docente docente) {
        this.numParalelo = numParalelo;
        this.materia = materia;
        this.docente = docente; 
    }

    // getters y setters
    public List<Horario> getListaHorarios() {
        return listaHorarios;
    }

    public void setListaHorarios(List<Horario> listaHorarios) {
        this.listaHorarios = listaHorarios;
    }

    public Long getIdParalelo() {
        return idParalelo;
    }

    public void setIdParalelo(Long idParalelo) {
        this.idParalelo = idParalelo;
    }

    public String getNumParalelo() {
        return numParalelo;
    }

    public void setNumParalelo(String numParalelo) {
        this.numParalelo = numParalelo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

}
