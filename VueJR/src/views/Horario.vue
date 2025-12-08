<template>
  <div class="container">
    <div class="header">
      <h1>Materias Sistemas</h1>
      <p>Gestión 2025</p>
    </div>

    <div class="pensum-grid">
      <div 
        v-for="semestre in 9" 
        :key="semestre" 
        class="semestre-column"
      >
        <div class="semestre-header">
          {{ semestre }}° SEMESTRE
        </div>
        <div class="materias-container">
          <div
            v-for="materia in pensumData[semestre]"
            :key="materia.id"
            class="materia-card"
            :class="{
              'selected': isSelected(materia.id),
              'disabled': isDisabled(materia.id)
            }"
            @click="toggleMateria(materia)"
          >
            <div class="check-icon">✓</div>
            <div class="materia-sigla">{{ materia.sigla }}</div>
            <div class="materia-nombre">{{ materia.nombre }}</div>
            <div class="materia-info">
              <span class="materia-creditos">{{ materia.creditos }} UVE</span>
              <span>{{ materia.tipo }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="controls">
      <div class="btn-group">
        <button class="btn btn-secondary" @click="limpiarSeleccion">
          Limpiar Selección
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MateriasSistemas',
  data() {
    return {
      MAX_MATERIAS: 7,
      materiasSeleccionadas: [],
      pensumData: {
        1: [
          { id: 1, sigla: 'MAT-123', nombre: 'Álgebra Lineal', creditos: 5, tipo: 'Básica' },
          { id: 2, sigla: 'MAT-122', nombre: 'Matemáticas Discretas', creditos: 5, tipo: 'Básica' },
          { id: 3, sigla: 'SIS-111', nombre: 'Introducción a la Programación', creditos: 6, tipo: 'Software' },
          { id: 4, sigla: 'SIS-123', nombre: 'Ingeniería de Sistemas', creditos: 5, tipo: 'Básica' },
          { id: 5, sigla: 'FIL-107', nombre: 'Pensamiento Crítico', creditos: 2, tipo: 'Genérica' }
        ],
        2: [
          { id: 6, sigla: 'MAT-132', nombre: 'Cálculo I', creditos: 5, tipo: 'Básica' },
          { id: 7, sigla: 'MAT-251', nombre: 'Investigación Operativa I', creditos: 5, tipo: 'Básica' },
          { id: 8, sigla: 'MAT-142', nombre: 'Probabilidad y Estadística I', creditos: 5, tipo: 'Básica' },
          { id: 9, sigla: 'SIS-112', nombre: 'Programación I', creditos: 6, tipo: 'Software' },
          { id: 10, sigla: 'SIS-133', nombre: 'Arquitectura Computacional y Sistemas Operativos', creditos: 6, tipo: 'Hardware' },
          { id: 11, sigla: 'FHC-102', nombre: 'Formación Humano Cristiana I', creditos: 5, tipo: 'Genérica' }
        ],
        3: [
          { id: 12, sigla: 'MAT-133', nombre: 'Cálculo II', creditos: 5, tipo: 'Básica' },
          { id: 13, sigla: 'FIS-111', nombre: 'Física I y Laboratorio', creditos: 7, tipo: 'Básica' },
          { id: 14, sigla: 'MAT-143', nombre: 'Probabilidad y Estadística II', creditos: 5, tipo: 'Básica' },
          { id: 15, sigla: 'SIS-113', nombre: 'Programación II', creditos: 6, tipo: 'Software' },
          { id: 16, sigla: 'SIS-122', nombre: 'Bases de Datos I', creditos: 6, tipo: 'Información' },
          { id: 17, sigla: 'IND-260', nombre: 'Metodología de la Investigación', creditos: 5, tipo: 'Básica' },
          { id: 18, sigla: 'FIL-207', nombre: 'Epistemología', creditos: 2, tipo: 'Genérica' }
        ],
        4: [
          { id: 19, sigla: 'MAT-252', nombre: 'Investigación Operativa II y Laboratorio', creditos: 7, tipo: 'Básica' },
          { id: 20, sigla: 'SIS-211', nombre: 'Estructuras de Datos', creditos: 6, tipo: 'Software' },
          { id: 21, sigla: 'SIS-214', nombre: 'Tecnologías Web I', creditos: 6, tipo: 'Software' },
          { id: 22, sigla: 'SIS-221', nombre: 'Bases de Datos II', creditos: 6, tipo: 'Información' },
          { id: 23, sigla: 'SIS-225', nombre: 'Sistemas de Información', creditos: 6, tipo: 'Información' },
          { id: 24, sigla: 'FHC-202', nombre: 'Formación Humano Cristiana II', creditos: 5, tipo: 'Genérica' }
        ],
        5: [
          { id: 25, sigla: 'MAT-361', nombre: 'Análisis de Algoritmos', creditos: 6, tipo: 'Software' },
          { id: 26, sigla: 'SIS-212', nombre: 'Programación de Dispositivos Móviles', creditos: 5, tipo: 'Software' },
          { id: 27, sigla: 'SIS-215', nombre: 'Tecnologías Web II', creditos: 6, tipo: 'Software' },
          { id: 28, sigla: 'SIS-251', nombre: 'Ingeniería de Datos', creditos: 6, tipo: 'IA' },
          { id: 29, sigla: 'SIS-226', nombre: 'Sistemas de Soporte a las Decisiones Gerenciales', creditos: 6, tipo: 'Información' },
          { id: 30, sigla: 'SIS-231', nombre: 'Redes de Computadoras I', creditos: 6, tipo: 'Hardware' }
        ],
        6: [
          { id: 31, sigla: 'SIS-213', nombre: 'Ingeniería de Software', creditos: 6, tipo: 'Software' },
          { id: 32, sigla: 'SIS-216', nombre: 'Modelado, Dinámica de Sistemas y Simulación', creditos: 5, tipo: 'Información' },
          { id: 33, sigla: 'SIS-252', nombre: 'Inteligencia Artificial', creditos: 6, tipo: 'IA' },
          { id: 34, sigla: 'DER-394', nombre: 'Derecho de las TIC', creditos: 5, tipo: 'Genérica' },
          { id: 35, sigla: 'SIS-323', nombre: 'Taller de Sistemas de Información', creditos: 5, tipo: 'Información' },
          { id: 36, sigla: 'SIS-233', nombre: 'Redes de Computadoras II', creditos: 6, tipo: 'Hardware' }
        ],
        7: [
          { id: 37, sigla: 'SIS-311', nombre: 'Arquitectura de Software', creditos: 6, tipo: 'Software' },
          { id: 38, sigla: 'SIS-312', nombre: 'Gestión de Calidad de Sistemas', creditos: 6, tipo: 'Software' },
          { id: 39, sigla: 'SIS-351', nombre: 'Machine Learning', creditos: 6, tipo: 'IA' },
          { id: 40, sigla: 'SIS-325', nombre: 'Ética y Seguridad de Sistemas', creditos: 6, tipo: 'Información' },
          { id: 41, sigla: 'SIS-331', nombre: 'Computación en la Nube', creditos: 6, tipo: 'Hardware' },
          { id: 42, sigla: 'FHC-302', nombre: 'Formación Humano Cristiana III', creditos: 5, tipo: 'Genérica' },
          { id: 43, sigla: 'SIS-234', nombre: 'Internet de las Cosas', creditos: 6, tipo: 'Hardware' }
        ],
        8: [
          { id: 44, sigla: 'SIS-313', nombre: 'Gerencia de las TIC', creditos: 5, tipo: 'Información' },
          { id: 45, sigla: 'SIS-352', nombre: 'Taller de Sistemas Inteligentes', creditos: 5, tipo: 'IA' },
          { id: 46, sigla: 'SIS-324', nombre: 'Auditoría de Sistemas', creditos: 5, tipo: 'Información' },
          { id: 47, sigla: 'SIS-361', nombre: 'Práctica Preprofesional', creditos: 2, tipo: 'Genérica' },
          { id: 48, sigla: 'SIS-362', nombre: 'Taller de Grado I', creditos: 15, tipo: 'Genérica' }
        ],
        9: [
          { id: 49, sigla: 'SIS-363', nombre: 'Taller de Grado II', creditos: 15, tipo: 'Genérica' }
        ]
      }
    }
  },
  methods: {
    isSelected(id) {
      return this.materiasSeleccionadas.some(m => m.id === id)
    },
    isDisabled(id) {
      return this.materiasSeleccionadas.length >= this.MAX_MATERIAS && !this.isSelected(id)
    },
    toggleMateria(materia) {
      const index = this.materiasSeleccionadas.findIndex(m => m.id === materia.id)
      
      if (index > -1) {
        this.materiasSeleccionadas.splice(index, 1)
      } else {
        if (this.materiasSeleccionadas.length < this.MAX_MATERIAS) {
          this.materiasSeleccionadas.push(materia)
        }
      }
    },
    limpiarSeleccion() {
      this.materiasSeleccionadas = []
    },
    // Funciones de utilidad para generar queries
    obtenerIdsSeleccionados() {
      return this.materiasSeleccionadas.map(m => m.id)
    },
    obtenerSiglasSeleccionadas() {
      return this.materiasSeleccionadas.map(m => m.sigla)
    },
    generarQueryInscripcion(idEstudiante, gestion = '2025', semestre = 1) {
      const ids = this.obtenerIdsSeleccionados()
      if (ids.length === 0) return null
      
      return `INSERT INTO HORARIOS_ESTUDIANTE (id_estudiante, id_materia, gestion, semestre)
VALUES 
${ids.map(id => `    (${idEstudiante}, ${id}, '${gestion}', ${semestre})`).join(',\n')};`
    },
    generarQueryConsultaHorarios() {
      const ids = this.obtenerIdsSeleccionados()
      if (ids.length === 0) return null
      
      return `SELECT 
    m.id_materia,
    m.sigla,
    m.nombre,
    p.num_paralelo,
    d.nombre AS docente,
    h.hora_i,
    h.hora_f,
    ds.nombre AS dia,
    a.lugar AS aula
FROM MATERIAS m
JOIN PARALELOS p ON m.id_materia = p.id_materia
JOIN HORARIOS h ON p.id_paralelo = h.id_paralelo
JOIN DIAS_SEMANA ds ON h.id_dia = ds.id_dia
JOIN DOCENTES d ON p.id_docente = d.id_docente
LEFT JOIN AULAS a ON h.id_aula = a.id_aula
WHERE m.id_materia IN (${ids.join(', ')})
ORDER BY m.id_materia, ds.id_dia, h.hora_i;`
    },
    generarDatosParaBackend(idEstudiante, gestion = '2025', semestre = 1) {
      return {
        id_estudiante: idEstudiante,
        materias: this.obtenerIdsSeleccionados(),
        gestion: gestion,
        semestre: semestre,
        detalle_materias: this.materiasSeleccionadas
      }
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.container {
  max-width: 1400px;
  margin: 0 auto;
  background: white;
  border-radius: 15px;
  box-shadow: 0 20px 60px rgba(0,0,0,0.3);
  overflow: hidden;
}

.header {
  background: linear-gradient(135deg, #feda73 0%);
  color: rgba(36, 57, 65);
  padding: 30px;
  text-align: center;
}

.header h1 {
  font-size: 28px;
  margin-bottom: 10px;
}

.header p {
  opacity: 0.9;
  font-size: 14px;
}

.controls {
  background: #f8f9fa;
  padding: 20px 30px;
  border-bottom: 2px solid #e9ecef;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 15px;
}

.btn-group {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-secondary {
  background: #6c757d;
  color: white;
}

.btn-secondary:hover {
  background: #5a6268;
}

.pensum-grid {
  padding: 30px;
  overflow-x: auto;
  white-space: nowrap;
}

.semestre-column {
  display: inline-block;
  vertical-align: top;
  width: 280px;
  margin-right: 20px;
}

.semestre-header {
  background: linear-gradient(135deg, #2dc0d4 0%);
  color: rgba(36, 57, 65);
  padding: 15px;
  border-radius: 10px 10px 0 0;
  text-align: center;
  font-weight: bold;
  font-size: 16px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}

.materias-container {
  background: #f8f9fa;
  border: 2px solid #e9ecef;
  border-top: none;
  border-radius: 0 0 10px 10px;
  padding: 15px;
  min-height: 200px;
}

.materia-card {
  background: white;
  border: 2px solid #dee2e6;
  border-radius: 8px;
  padding: 12px;
  margin-bottom: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  white-space: normal;
}

.materia-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  border-color: #050505;
}

.materia-card.selected {
  background: linear-gradient(135deg, #f5f4e6 0%);
  color: #050505;
  border-color: #050505;
  box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
}

.materia-card.disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.materia-card.disabled:hover {
  transform: none;
  box-shadow: none;
  border-color: #dee2e6;
}

.materia-sigla {
  font-size: 11px;
  font-weight: bold;
  color: #050505;
  margin-bottom: 5px;
}

.materia-card.selected .materia-sigla {
  color: #050505;
}

.materia-nombre {
  font-size: 13px;
  font-weight: 600;
  line-height: 1.3;
  margin-bottom: 8px;
}

.materia-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 11px;
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid #dee2e6;
}

.materia-card.selected .materia-info {
  border-top-color: rgba(255,255,255,0.3);
}

.materia-creditos {
  background: #e9ecef;
  padding: 3px 8px;
  border-radius: 4px;
  font-weight: bold;
}

.materia-card.selected .materia-creditos {
  background: #e9ecef;
}

.check-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 20px;
  height: 20px;
  background: white;
  border-radius: 50%;
  display: none;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #667eea;
}

@media (max-width: 1200px) {
  .semestre-column {
    width: 250px;
  }
}

@media (max-width: 768px) {
  .pensum-grid {
    padding: 15px;
  }

  .semestre-column {
    width: 100%;
    margin-right: 0;
    margin-bottom: 20px;
    display: block;
  }

  .controls {
    flex-direction: column;
    align-items: stretch;
  }

  .btn-group {
    width: 100%;
    flex-direction: column;
  }

  .btn {
    width: 100%;
  }
}
</style>