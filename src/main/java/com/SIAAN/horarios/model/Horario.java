package com.SIAAN.horarios.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;

    @Column(name = "hora_i", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_f", nullable = false)
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "id_paralelo", referencedColumnName = "id_paralelo", nullable = false)
    private Paralelo paralelo;

    @ManyToOne
    @JoinColumn(name = "id_dia", referencedColumnName = "id_dia", nullable = false)
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "id_aula", referencedColumnName = "id_aula", nullable = false)
    private Aula aula;

    public Horario() {
    }

    public Horario(LocalTime horaInicio, LocalTime horaFin, Paralelo paralelo, DiaSemana diaSemana, Aula aula) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.paralelo = paralelo;
        this.diaSemana = diaSemana;
        this.aula = aula;
    }

    // getters y setters
    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }
}
