package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Hito evaluativo del plan unificado de la asignatura.
 */
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private float ponderacion;
    private Asignatura asignatura; // Contenedor (Composición)
    private List<Calificacion> calificaciones;

    // [DISEÑO TÉCNICO]: Constructor package-private para forzar que solo 'Asignatura' lo invoque.
    Evaluacion(
            int id,
            String titulo,
            float ponderacion,
            Asignatura asignatura
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }

        if (titulo == null
                || titulo.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El título no puede estar vacío."
            );
        }

        if (ponderacion <= 0.0f
                || ponderacion > 1.0f) {

            throw new IllegalArgumentException(
                    "La ponderación debe estar entre 0 y 1."
            );
        }

        if (asignatura == null) {
            throw new IllegalArgumentException(
                    "La asignatura no puede ser nula."
            );
        }

        this.id = id;
        this.titulo = titulo.trim();
        this.ponderacion = ponderacion;
        this.asignatura = asignatura;
        this.calificaciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public float getPonderacion() {
        return ponderacion;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }
}