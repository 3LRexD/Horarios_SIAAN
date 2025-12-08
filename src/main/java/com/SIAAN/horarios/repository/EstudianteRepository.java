package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Estudiante; // <--- CORREGIDO: Antes decía .entity
import com.SIAAN.horarios.dto.MallaItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query(value = """
        SELECT 
            mp.semestre,
            m.nombre AS materia,
            CASE 
                -- 1. Si ya la aprobó -> 'aprobado'
                WHEN me.aprobado = true THEN 'aprobado'
                
                -- 2. Si NO la aprobó, verificamos si tiene requisitos pendientes
                WHEN EXISTS (
                    SELECT 1 
                    FROM prerrequisitos pre
                    -- Buscamos si el estudiante aprobó el requisito (usando TU nombre de columna)
                    LEFT JOIN materias_estudiantes me_req 
                        ON pre.id_materia_requisito = me_req.id_materia 
                        AND me_req.id_estudiante = e.id_estudiante 
                        AND me_req.aprobado = true
                    WHERE pre.id_materia = m.id_materia -- Requisitos de la materia actual
                      AND me_req.id_materia IS NULL       -- ¡Si es NULL, es que NO aprobó el requisito!
                ) THEN 'bloqueado'
                
                -- 3. Si no la aprobó y cumple todo -> 'disponible'
                ELSE 'disponible'
            END AS estado
        FROM 
            estudiantes e
            JOIN carreras_estudiantes ce ON e.id_estudiante = ce.id_estudiante
            JOIN pensums p ON ce.id_carrera = p.id_carrera AND ce.id_pensum = p.id_pensum
            JOIN materias_pensums mp ON p.id_pensum = mp.id_pensum
            JOIN materias m ON mp.id_materia = m.id_materia
            LEFT JOIN materias_estudiantes me 
                ON m.id_materia = me.id_materia 
                AND me.id_estudiante = e.id_estudiante
        WHERE 
            e.id_estudiante = :idEstudiante
        ORDER BY 
            mp.semestre ASC, m.nombre ASC
    """, nativeQuery = true)
    List<MallaItemDTO> obtenerMallaPorEstudiante(@Param("idEstudiante") Integer idEstudiante);
}
