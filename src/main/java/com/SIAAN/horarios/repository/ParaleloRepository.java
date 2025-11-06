package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Paralelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaleloRepository extends JpaRepository<Paralelo, Long> {
}
