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

    // Asignatura que contiene la evaluación.
    private Asignatura asignatura;

    // Calificaciones registradas para esta evaluación.
    private List<Calificacion> calificaciones;

    /**
     * Constructor package-private.
     *
     * Solo puede ser utilizado directamente por clases
     * pertenecientes al paquete org.unisiga.model.
     */
    Evaluacion(
            int id,
            String titulo,
            float ponderacion,
            Asignatura asignatura
    ) {
        this.id = id;
        this.titulo = titulo;
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