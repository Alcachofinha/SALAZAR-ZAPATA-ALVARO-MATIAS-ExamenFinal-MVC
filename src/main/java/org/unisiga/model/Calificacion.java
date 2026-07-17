package org.unisiga.model;

import java.io.Serializable;

/**
 * Estructura de calificación de tres vías.
 *
 * Une:
 * - La inscripción del estudiante.
 * - La evaluación.
 * - La nota obtenida.
 */
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private float nota;

    // Vía 1: estudiante inscrito en una sección.
    private Inscripcion inscripcion;

    // Vía 2: evaluación oficial de la asignatura.
    private Evaluacion evaluacion;

    public Calificacion(
            float nota,
            Inscripcion inscripcion,
            Evaluacion evaluacion
    ) {
        this.nota = nota;
        this.inscripcion = inscripcion;
        this.evaluacion = evaluacion;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }
}