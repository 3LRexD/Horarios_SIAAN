package com.SIAAN.horarios.service;

import com.SIAAN.horarios.model.Horario;
import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.repository.HorarioRepository;
import com.SIAAN.horarios.repository.ParaleloRepository;
import com.SIAAN.horarios.util.ScheduleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeneradorHorariosServiceImpl implements GeneradorHorariosService {

    @Autowired
    private ParaleloRepository paraleloRepository;
    @Autowired
    private HorarioRepository horarioRepository;


    /**
     * Ordena materias por cantidad de paralelos (menor -> mayor).
     * Elimina duplicados de entrada y devuelve lista ordenada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long> ordenarMateriasPorNumeroDeParalelos(List<Long> materiaIds) {
        if (materiaIds == null) return Collections.emptyList();

        // 1) eliminar duplicados y mantener la lista de ids únicos
        List<Long> distinctIds = materiaIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 2) construir mapa id -> count de paralelos (consulta DB por cada id)
        Map<Long, Long> counts = new HashMap<>();
        for (Long mid : distinctIds) {
            long c = paraleloRepository.countByMateria_IdMateria(mid);

            counts.put(mid, c);
        }

        // 3) ordenar por el count (menor -> mayor). En empate, mantiene orden por id asc (o puedes cambiar)
        List<Long> sorted = distinctIds.stream()
                .sorted(Comparator.comparingLong((Long id) -> counts.getOrDefault(id, 0L))
                        .thenComparingLong(Long::longValue))
                .collect(Collectors.toList());

        return sorted;
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<List<Paralelo>> generarHorarioPorDiasYHoras(List<Long> materiaIds) {
        if (materiaIds == null || materiaIds.isEmpty()) return Optional.empty();

        // 1) ordenar materias por número de paralelos (mejora backtracking)
        List<Long> materiasOrdenadas = ordenarMateriasPorNumeroDeParalelos(materiaIds);

        // 2) obtener lista de paralelos por materia, en el mismo orden
        List<List<Paralelo>> opcionesPorMateria = new ArrayList<>();
        for (Long mid : materiasOrdenadas) {
            List<Paralelo> paralelos = paraleloRepository.findByMateria_IdMateria(mid);
            if (paralelos == null || paralelos.isEmpty()) {
                // si alguna materia no tiene paralelos, no podemos generar
                return Optional.empty();
            }
            opcionesPorMateria.add(paralelos);
        }

        // 3) backtracking con ScheduleMatrix (dias + horas)
        ScheduleMatrix matrix = new ScheduleMatrix();
        List<Paralelo> seleccionActual = new ArrayList<>();
        List<List<Paralelo>> soluciones = new ArrayList<>();
        final int SOLUTIONS_LIMIT = 1; // por ahora devolvemos la primera solución

        backtrackDiasHoras(0, opcionesPorMateria, seleccionActual, soluciones, matrix, SOLUTIONS_LIMIT);

        if (soluciones.isEmpty()) return Optional.empty();
        return Optional.of(soluciones.get(0));
    }

    // backtracking que usa ScheduleMatrix para detectar choques (solo dia+hora)
    private void backtrackDiasHoras(int idx,
                                    List<List<Paralelo>> opcionesPorMateria,
                                    List<Paralelo> seleccionActual,
                                    List<List<Paralelo>> soluciones,
                                    ScheduleMatrix matrix,
                                    int solutionsLimit) {
        if (soluciones.size() >= solutionsLimit) return;
        if (idx == opcionesPorMateria.size()) {
            soluciones.add(new ArrayList<>(seleccionActual));
            return;
        }

        for (Paralelo candidato : opcionesPorMateria.get(idx)) {    
            //List<Horario> horariosCandidato = horarioRepository.findByParalelo_IdParalelo(candidato.getIdParalelo());
            List<Horario> horariosCandidato = horarioRepository.buscarPorParalelo(candidato.getIdParalelo());
            boolean conflict = false;

            // comprobar si todos los horarios del candidato caben en la matrix
            for (Horario h : horariosCandidato) {
                String dia = diaKey(h);
                LocalTime hi = h.getHoraInicio();
                LocalTime hf = h.getHoraFin();
                if (!matrix.isFree(dia, hi, hf)) {
                    conflict = true;
                    break;
                }
            }

            if (!conflict) {
                // reservar los intervalos del candidato
                for (Horario h : horariosCandidato) {
                    matrix.reserve(diaKey(h), h.getHoraInicio(), h.getHoraFin());
                }

                seleccionActual.add(candidato);
                backtrackDiasHoras(idx + 1, opcionesPorMateria, seleccionActual, soluciones, matrix, solutionsLimit);

                // backtrack: desreservar y quitar candidato
                seleccionActual.remove(seleccionActual.size() - 1);
                for (Horario h : horariosCandidato) {
                    matrix.release(diaKey(h), h.getHoraInicio(), h.getHoraFin());
                }

                if (soluciones.size() >= solutionsLimit) return;
            }
        }
    }

    // clave de día: preferimos el nombre (lowercase), si no existe usamos el id
    private String diaKey(Horario h) {
        try {
            if (h.getDiaSemana() != null && h.getDiaSemana().getNombre() != null) {
                return h.getDiaSemana().getNombre().toLowerCase();
            } else if (h.getDiaSemana() != null && h.getDiaSemana().getIdDia() != null) {
                return String.valueOf(h.getDiaSemana().getIdDia());
            }
        } catch (Exception e) {
            // ignore
        }
        return "unknown";
    }
}
