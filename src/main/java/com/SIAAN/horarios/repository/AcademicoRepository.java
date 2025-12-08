package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.dto.MallaItemDTO;
import com.SIAAN.horarios.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AcademicoRepository extends JpaRepository<Materia, Long> {

    @Query(value = """
        SELECT 
            m.id_materia AS idMateria,
            m.nombre AS nombreMateria,
            m.sigla AS sigla,
            mp.semestre AS semestre,
            CAST(CASE WHEN me.aprobado IS TRUE THEN 'true' ELSE 'false' END AS VARCHAR) AS estado,
            -- Agregamos esto: Une los IDs de los requisitos en un solo texto separado por comas
            STRING_AGG(CAST(pr.id_materia_requisito AS VARCHAR), ',') AS prerrequisitos
        FROM estudiantes_pensums ep
        JOIN materias_pensums mp ON ep.id_pensum = mp.id_pensum
        JOIN materias m ON mp.id_materia = m.id_materia
        LEFT JOIN materias_estudiantes me ON (m.id_materia = me.id_materia AND me.id_estudiante = ep.id_estudiante)
        -- Unimos con la tabla de pre-requisitos
        LEFT JOIN prerrequisitos pr ON m.id_materia = pr.id_materia
        WHERE ep.id_estudiante = :idEstudiante
        -- Agrupamos para poder usar STRING_AGG
        GROUP BY m.id_materia, m.nombre, m.sigla, mp.semestre, me.aprobado
        ORDER BY mp.semestre ASC, m.nombre ASC
    """, nativeQuery = true)
    List<MallaItemDTO> obtenerMallaPorEstudiante(@Param("idEstudiante") Long idEstudiante);
}