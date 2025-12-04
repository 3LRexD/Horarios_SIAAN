package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MateriaRepository extends JpaRepository<Materia, Long> {
}