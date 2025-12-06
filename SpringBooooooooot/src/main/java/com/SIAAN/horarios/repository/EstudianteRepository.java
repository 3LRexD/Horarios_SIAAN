package com.SIAAN.horarios.repository;

import com.SIAAN.horarios.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    // Spring crea automáticamente el SQL para esto:
    // SELECT * FROM estudiantes WHERE usuario = ? AND contrasena = ?
    Estudiante findByUsuarioAndContrasena(String usuario, String contrasena);
    Estudiante findByUsuario(String usuario);
}
