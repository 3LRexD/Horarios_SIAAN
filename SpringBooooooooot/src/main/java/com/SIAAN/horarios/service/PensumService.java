package com.SIAAN.horarios.service;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.SIAAN.horarios.model.Materia;
import com.SIAAN.horarios.repository.MateriaRepository;
import java.util.List;
import java.util.Map;

public class PensumService {
@Autowired
    private MateriaRepository materiaRepository;

    public Map<Integer, List<Materia>> getPensumOrganizado() {
        Map<Integer, List<Materia>> pensum = new HashMap<>();

        // SEMESTRE 1
        // Query específica para extraer las materias del 1er semestre usando sus SIGLAS
        List<String> sem1Siglas = Arrays.asList("MAT-123", "MAT-122", "SIS-111", "SIS-123", "FIL-107");
        pensum.put(1, materiaRepository.findBySiglaIn(sem1Siglas));

        // SEMESTRE 2
        List<String> sem2Siglas = Arrays.asList("MAT-132", "MAT-251", "MAT-142", "SIS-112", "SIS-133", "FHC-102");
        pensum.put(2, materiaRepository.findBySiglaIn(sem2Siglas));

        // SEMESTRE 3
        List<String> sem3Siglas = Arrays.asList("MAT-133", "FIS-111", "MAT-143", "SIS-113", "SIS-122", "FIL-207"); // Falta IND-260 en tu archivo de texto original, cuidado
        pensum.put(3, materiaRepository.findBySiglaIn(sem3Siglas));

        // SEMESTRE 4
        List<String> sem4Siglas = Arrays.asList("MAT-252", "SIS-211", "SIS-214", "SIS-221", "SIS-225", "FHC-202");
        pensum.put(4, materiaRepository.findBySiglaIn(sem4Siglas));

        // SEMESTRE 5
        List<String> sem5Siglas = Arrays.asList("MAT-361", "SIS-212", "SIS-215", "SIS-251", "SIS-226", "SIS-231");
        pensum.put(5, materiaRepository.findBySiglaIn(sem5Siglas));

        // SEMESTRE 6
        // Nota: DER-394 no estaba en tu archivo text, así que no saldrá si no la agregas a la BD
        List<String> sem6Siglas = Arrays.asList("SIS-213", "SIS-216", "SIS-252", "SIS-323", "SIS-233"); 
        pensum.put(6, materiaRepository.findBySiglaIn(sem6Siglas));

        // SEMESTRE 7
        List<String> sem7Siglas = Arrays.asList("SIS-311", "SIS-312", "SIS-321", "FHC-302"); // Ajustado segun lo disponible en tu txt
        pensum.put(7, materiaRepository.findBySiglaIn(sem7Siglas));

        // SEMESTRE 8
        List<String> sem8Siglas = Arrays.asList("SIS-322", "SIS-341", "SIS-324", "SIS-352"); // SIS-322 es Gerencia TIC en tu txt
        pensum.put(8, materiaRepository.findBySiglaIn(sem8Siglas));

        // SEMESTRE 9
        List<String> sem9Siglas = Arrays.asList("SIS-353", "SIS-354"); // Talleres de Grado segun tu txt
        pensum.put(9, materiaRepository.findBySiglaIn(sem9Siglas));

        return pensum;
    }
}
