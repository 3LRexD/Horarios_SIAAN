package com.SIAAN.horarios.service;

import com.SIAAN.horarios.engine.HorarioGeneratorEngine;
import com.SIAAN.horarios.model.Materia;
import com.SIAAN.horarios.model.Paralelo;
import com.SIAAN.horarios.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneradorHorariosServiceImpl implements GeneradorHorariosService {

    private final MateriaRepository materiaRepository;
    private final HorarioGeneratorEngine generadorEngine;

    // Conectamos las piezas (Repositorio y Motor)
   
    public GeneradorHorariosServiceImpl(MateriaRepository materiaRepository, 
                                        HorarioGeneratorEngine generadorEngine) {
        this.materiaRepository = materiaRepository;
        this.generadorEngine = generadorEngine;
    }

    @Override
    public List<List<Paralelo>> generarOpciones(List<Long> idsMaterias) {
        // de aca le saco los ids desde la base de daytos uwu
        List<Materia> materiasSolicitadas = materiaRepository.findAllById(idsMaterias);

        if (materiasSolicitadas.isEmpty()) {
            throw new RuntimeException("No se encontraron materias con esos IDs.");
        }

        // aca le paso pa q lo use
        System.out.println("Enviando " + materiasSolicitadas.size() + " materias al motor...");
        
        return generadorEngine.generarOpciones(materiasSolicitadas);
    }
}