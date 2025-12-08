package com.SIAAN.horarios.engine;

import com.SIAAN.horarios.model.Materia;
import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.util.ScheduleMatrix;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HorarioGeneratorEngine {

    /**
     * Este es el método principal.
     * Recibe: Las materias que el alumno quiere.
     * Devuelve: Una lista de combinaciones (donde cada combinación es una lista de Paralelos).
     */
    public List<List<Paralelo>> generarOpciones(List<Materia> materiasRequeridas) {
        List<List<Paralelo>> resultados = new ArrayList<>();
        
        //lsita vacia
        if (materiasRequeridas == null || materiasRequeridas.isEmpty()) {
            return resultados;
        }

        // ordena materias por prioridad
        List<Materia> materiasOrdenadas = new ArrayList<>(materiasRequeridas);
        materiasOrdenadas.sort((m1, m2) -> 
            Integer.compare(m1.getParalelos().size(), m2.getParalelos().size())
        );

        //prueba y error
        ScheduleMatrix matrizVacia = new ScheduleMatrix();
        backtrack(0, materiasOrdenadas, new ArrayList<>(), matrizVacia, resultados);

        return resultados;
    }

   //recursividad 
    private void backtrack(int indiceMateria, 
                           List<Materia> materias, 
                           List<Paralelo> paralelosActuales, 
                           ScheduleMatrix matrizActual, 
                           List<List<Paralelo>> resultados) {

        // CASO DE ÉXITO: Ya asignamos paralelos para todas las materias
        if (indiceMateria == materias.size()) {
            // se guarda la copia
            resultados.add(new ArrayList<>(paralelosActuales));
            return;
        }

        // CASO NORMAL: Intentar asignar la materia actual
        Materia materiaActual = materias.get(indiceMateria);
        List<Paralelo> opcionesParalelos = materiaActual.getParalelos();

        //se prueba cada paralelo para esa materia
        for (Paralelo paralelo : opcionesParalelos) {
            
            // se hace la prueba en la matriz copia
            ScheduleMatrix intentoMatriz = matrizActual.copy();
            boolean cabe = intentoMatriz.checkAndAdd(paralelo.getListaHorarios());
            
            if (cabe) {
                paralelosActuales.add(paralelo);

                backtrack(indiceMateria + 1, materias, paralelosActuales, intentoMatriz, resultados);
                
                paralelosActuales.remove(paralelosActuales.size() - 1);
            }
            
        }
    }
}