package com.SIAAN.horarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes", schema = "public")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estudiante;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "correo_institucional")
    private String correoInstitucional;

    public String getUsuario() {
        return usuario;
    }
    public String getContrasena() {
        return contrasena;
        }
    public String getCorreoInstitucional() {
        return correoInstitucional;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }
    
}