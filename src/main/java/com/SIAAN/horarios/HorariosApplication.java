package com.SIAAN.horarios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class HorariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HorariosApplication.class, args);
    }

    // prueba rápida de conexión
    @Bean
    CommandLineRunner testConnection() {
        return args -> {
            String url = "jdbc:postgresql://localhost:5432/Horarios_SIAAN";
            String user = "postgres";
            String password = "1234";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                if (conn != null) {
                    System.out.println("✅ Conectado correctamente a PostgreSQL!");
                }
            } catch (Exception e) {
                System.out.println("❌ Error de conexión: " + e.getMessage());
            }
        };
    }
}
