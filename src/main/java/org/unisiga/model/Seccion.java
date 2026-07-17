package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Materialización de una asignatura durante el semestre.
 *
 * Esta clase representa a Grupo en el UML del profesor.
 */
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private char idGrupo;
    private int cupoMaximo;
    private String horario;

    /**
     * Asignatura que contiene esta sección.
     *
     * Corresponde a la relación de composición.
     */
    private Asignatura asignatura;

    /**
     * Académico encargado de dictar la sección.
     */
    private Academico docenteDicta;

    /**
     * Inscripciones registradas en la sección.
     */
    private List<Inscripcion> inscripciones;

    /**
     * Constructor con acceso restringido al paquete.
     *
     * La sección debe ser creada desde Asignatura,
     * respetando la composición mostrada en el UML.
     */
    Seccion(
            char idGrupo,
            int cupoMaximo,
            String horario,
            Asignatura asignatura
    ) {

        if (Character.isWhitespace(idGrupo)) {
            throw new IllegalArgumentException(
                    "El identificador de la sección no puede estar vacío."
            );
        }

        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException(
                    "El cupo máximo debe ser mayor que cero."
            );
        }

        if (horario == null || horario.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El horario de la sección no puede estar vacío."
            );
        }

        if (asignatura == null) {
            throw new IllegalArgumentException(
                    "La sección debe pertenecer a una asignatura."
            );
        }

        this.idGrupo = idGrupo;
        this.cupoMaximo = cupoMaximo;
        this.horario = horario.trim();
        this.asignatura = asignatura;
        this.docenteDicta = null;
        this.inscripciones = new ArrayList<>();
    }

    /**
     * Asigna un académico como docente de la sección.
     *
     * Mantiene la relación bidireccional:
     *
     * Seccion -> Academico
     * Academico -> Seccion
     *
     * Si la sección ya tenía otro docente, primero se elimina
     * de la lista de secciones del docente anterior.
     *
     * @param nuevoDocente académico que dictará la sección
     */
    public void asignarDocente(Academico nuevoDocente) {

        if (nuevoDocente == null) {
            throw new IllegalArgumentException(
                    "El docente no puede ser nulo."
            );
        }

        /*
         * Si ya tiene asignado al mismo docente,
         * solamente aseguramos que la sección esté
         * incluida en su lista.
         */
        if (this.docenteDicta == nuevoDocente) {
            nuevoDocente.agregarSeccionInterna(this);
            return;
        }

        Academico docenteAnterior = this.docenteDicta;

        /*
         * Actualizamos la referencia de la sección.
         */
        this.docenteDicta = nuevoDocente;

        /*
         * Eliminamos la sección del docente anterior.
         */
        if (docenteAnterior != null) {
            docenteAnterior.removerSeccionInterna(this);
        }

        /*
         * Agregamos la sección al docente nuevo.
         */
        nuevoDocente.agregarSeccionInterna(this);
    }

    public char getIdGrupo() {
        return idGrupo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public String getHorario() {
        return horario;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public Academico getDocenteDicta() {
        return docenteDicta;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}