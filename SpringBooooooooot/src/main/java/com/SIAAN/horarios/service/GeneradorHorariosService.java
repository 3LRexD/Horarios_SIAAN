package com.SIAAN.horarios.service;

import com.SIAAN.horarios.model.Paralelo;
import java.util.List;

public interface GeneradorHorariosService {
    // genera opciones gracias a lkos IDS
    List<List<Paralelo>> generarOpciones(List<Long> idsMaterias);
}