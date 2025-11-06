package com.SIAAN.horarios.model;
import jakarta.persistence.*;

@Entity
@Table(name = "paralelos")
public class Paralelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paralelo")
    private Long idParalelo;

    @Column(name = "num_paralelo", nullable = false)
    private int numParalelo;

    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente", nullable = false)
    private Docente docente;

    
    public Paralelo() {
    }

    
    public Paralelo(int numParalelo, Materia materia, Docente docente, Aula aula) {
        this.numParalelo = numParalelo;
        this.materia = materia;
        this.docente = docente;
    }

    // getters y setters
    public Long getIdParalelo() {
        return idParalelo;
    }

    public void setIdParalelo(Long idParalelo) {
        this.idParalelo = idParalelo;
    }

    public int getNumParalelo() {
        return numParalelo;
    }

    public void setNumParalelo(int numParalelo) {
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
