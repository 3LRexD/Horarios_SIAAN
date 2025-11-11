package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Paralelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaleloRepository extends JpaRepository<Paralelo, Long> {
    long countByMateria_IdMateria(Long idMateria);

    List<Paralelo> findByMateria_IdMateria(Long idMateria);
}
