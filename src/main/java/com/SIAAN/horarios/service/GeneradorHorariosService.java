package com.SIAAN.horarios.service;

import com.SIAAN.horarios.model.Paralelo;
import java.util.List;
import java.util.Optional;
    
public interface GeneradorHorariosService {
    /**
     * Ordena la lista de ids de materias de menor a mayor según la cantidad de paralelos disponibles.
     * @param materiaIds lista de ids de materias (puede contener duplicados)
     * @return lista ordenada sin duplicados (menor cantidad de paralelos primero)
     */
    List<Long> ordenarMateriasPorNumeroDeParalelos(List<Long> materiaIds);

    /**
     * Genera un horario válido seleccionando un paralelo por materia sin choques de días ni horas.
     * @param materiaIds lista de ids de materias a cursar
     * @return lista de paralelos elegidos (una posible solución), o vacío si no hay solución
     */
    Optional<List<Paralelo>> generarHorarioPorDiasYHoras(List<Long> materiaIds);
}
