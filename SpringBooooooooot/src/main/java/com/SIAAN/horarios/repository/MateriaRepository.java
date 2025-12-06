package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findBySiglaIn(List<String> siglas);
}