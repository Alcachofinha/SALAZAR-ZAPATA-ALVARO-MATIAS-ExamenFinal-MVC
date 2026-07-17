package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de Asociación que resuelve la relación N:M entre Estudiante y Sección.
 */
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Estudiante estudiante;
    private Seccion seccion;

    private String estadoInscripcion;
    private Date fechaInscripcion;

    private List<Calificacion> calificaciones;

    public Inscripcion(
            Estudiante estudiante,
            Seccion seccion
    ) {
        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo."
            );
        }

        if (seccion == null) {
            throw new IllegalArgumentException(
                    "La sección no puede ser nula."
            );
        }

        this.estudiante = estudiante;
        this.seccion = seccion;
        this.estadoInscripcion = "Inscrito";
        this.fechaInscripcion = new Date();
        this.calificaciones = new ArrayList<>();
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public String getEstadoInscripcion() {
        return estadoInscripcion;
    }

    public void setEstadoInscripcion(String estado) {

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo."
            );
        }

        boolean valido =
                estado.equalsIgnoreCase("Inscrito")
                || estado.equalsIgnoreCase("Aprobado")
                || estado.equalsIgnoreCase("Reprobado");

        if (!valido) {
            throw new IllegalArgumentException(
                    "El estado de inscripción no es válido."
            );
        }

        this.estadoInscripcion = estado;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }
}