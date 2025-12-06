package com.SIAAN.horarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.SIAAN.horarios.model.Materia;
import com.SIAAN.horarios.service.PensumService;
import java.util.List;
import java.util.Map;

public class PensumController {
@Autowired
    private PensumService pensumService;

    @GetMapping("/pensum")
    public Map<Integer, List<Materia>> getPensum() {
        return pensumService.getPensumOrganizado();
    }
}
