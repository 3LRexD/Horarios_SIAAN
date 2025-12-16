# SIAAN - Generador Automático de Horarios

Sistema inteligente de gestion y generacion de horarios academicos. Esta aplicacion "Full Stack" permite a los estudiantes visualizar su malla curricular, seleccionar materias y generar automaticamente todas las combinaciones posibles de horarios sin choques, con opciones de exportacion a Excel y PDF.

# Características Principales

* **Motor de Generación (Backtracking):** Algoritmo recursivo que encuentra todas las combinaciones validas de paralelos.
* **Precision por Minuto:** Deteccion de choques utilizando una matriz de ocupacion (`ScheduleMatrix`) con precisión de minutos (1440 mins/dia), permitiendo horarios complejos (ej. 07:15 - 08:45).
* **Validación Academica:** Verifica prerrequisitos y bloquea materias segun el estado academico del estudiante mediante consultas SQL nativas.
* **Exportación Visual:**
    * **Excel:** Genera una hoja de calculo con celdas combinadas y colores por materia.
    * **PDF:** Reporte listo para imprimir con el horario seleccionado.

# Tecnologías

### Backend (API REST)
* **Java 21**
* **Spring Boot 3.5.8**
* **Spring Data JPA** (Hibernate)
* **PostgreSQL**
* **Librerías:** Apache POI (Excel), OpenPDF (PDF), Lombok.

### Frontend (SPA)
* **Vue.js 3** (Composition API)
* **Vite** (Build tool)
* **Vue Router**
* **Axios** (Conexión HTTP)
* **FontAwesome**

---

# Pre-requisitos

Tener instalado:
* [Java JDK 21](https://www.oracle.com/java/technologies/downloads/)
* [Node.js](https://nodejs.org/) (v16 o superior)
* [PostgreSQL](https://www.postgresql.org/)

---

# Configuración e Instalación

### 1. Base de Datos (PostgreSQL)

El proyecto está configurado para correr en el puerto **5433** (no el estándar 5432).

1.  Crea una base de datos llamada `horario_siaan2`.
2.  **Importante:** La configuración `ddl-auto` está en `none`, por lo que **debes ejecutar tu script SQL manual** para crear las tablas (`docentes`, `materias`, `paralelos`, etc.) e insertar los datos iniciales antes de iniciar.
3.  Credenciales por defecto (configuradas en `application.properties`):
    * **User:** `postgres`
    * **Pass:** `1234`

### 2. Backend (Spring Boot)

Navega a la carpeta del servidor:

```bash
cd backend
