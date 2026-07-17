package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de asociación que resuelve la relación N:M
 * entre Estudiante y Seccion.
 *
 * En el UML del profesor aparece con el nombre Matricula.
 * En el código se utiliza el nombre Inscripcion.
 */
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Estudiante estudiante;
    private Seccion seccion;

    // Posibles estados:
    // "Inscrito", "Aprobado" o "Reprobado".
    private String estadoInscripcion;

    private Date fechaInscripcion;
    private List<Calificacion> calificaciones;

    public Inscripcion(Estudiante estudiante, Seccion seccion) {
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
        this.estadoInscripcion = estado;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }
}