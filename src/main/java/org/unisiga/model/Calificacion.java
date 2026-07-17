package org.unisiga.model;

import java.io.Serializable;

/**
 * Estructura de Calificación de Tres Vías. Unifica al Alumno (Inscripción) con el Examen (Evaluación).
 */
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private float nota;
    private Inscripcion inscripcion; // Vía 1 (Quién rinde en qué sección)
    private Evaluacion evaluacion;   // Vía 2 (Qué examen unificado de cátedra es)

    public Calificacion(
            float nota,
            Inscripcion inscripcion,
            Evaluacion evaluacion
    ) {
        if (inscripcion == null) {
            throw new IllegalArgumentException(
                    "La inscripción no puede ser nula."
            );
        }

        if (evaluacion == null) {
            throw new IllegalArgumentException(
                    "La evaluación no puede ser nula."
            );
        }

        this.inscripcion = inscripcion;
        this.evaluacion = evaluacion;

        setNota(nota);
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        if (nota < 1.0f || nota > 7.0f) {
            throw new IllegalArgumentException(
                    "La nota debe estar entre 1.0 y 7.0."
            );
        }

        this.nota = nota;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }
}