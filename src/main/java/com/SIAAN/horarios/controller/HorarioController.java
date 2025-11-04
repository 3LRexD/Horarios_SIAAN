package com.SIAAN.horarios.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/zapato")
public class HorarioController {


    @GetMapping("/hola")
    public String holaMundo() {
        return "Hola Mundo";
    }

    @GetMapping("/holanombre/{nombre}/{edad}")
    public String holaMundoNombre(@PathVariable String nombre, 
                                  @PathVariable int edad){
        return "Hola Mundoo" + nombre + edad;
    }

}
