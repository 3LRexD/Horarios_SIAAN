package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    // opción 1 (funciona con el nombre del atributo)
    List<Horario> findByParalelo_IdParalelo(Long idParalelo);

    // opción 2 (query explícita, útil si el IDE marca error)
    @Query("SELECT h FROM Horario h WHERE h.paralelo.idParalelo = :idParalelo")
    List<Horario> buscarPorParalelo(@Param("idParalelo") Long idParalelo);
}
