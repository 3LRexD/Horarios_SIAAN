package com.SIAAN.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SIAAN.horarios.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    // acá podés agregar consultas personalizadas si las necesitas, por ejemplo:
    Materia findByCodigo(String codigo);
}
