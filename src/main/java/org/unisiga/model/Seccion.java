package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Materialización física de una asignatura en el semestre.
 */
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private char idGrupo;
    private int cupoMaximo;
    private String horario;

    private Asignatura asignatura;
    private Academico docenteDicta;

    private List<Inscripcion> inscripciones;

    // [DISEÑO TÉCNICO]: Constructor package-private para forzar que solo 'Asignatura' (mismo package) lo invoque.
    Seccion(
            char idGrupo,
            int cupoMaximo,
            String horario,
            Asignatura asignatura
    ) {
        if (idGrupo == '\0'
                || Character.isWhitespace(idGrupo)) {

            throw new IllegalArgumentException(
                    "El identificador no puede estar vacío."
            );
        }

        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException(
                    "El cupo máximo debe ser mayor que cero."
            );
        }

        if (horario == null
                || horario.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El horario no puede estar vacío."
            );
        }

        if (asignatura == null) {
            throw new IllegalArgumentException(
                    "La asignatura no puede ser nula."
            );
        }

        this.idGrupo = idGrupo;
        this.cupoMaximo = cupoMaximo;
        this.horario = horario.trim();
        this.asignatura = asignatura;
        this.docenteDicta = null;
        this.inscripciones = new ArrayList<>();
    }

    public void asignarDocente(Academico docente) {

        if (docente == null) {
            throw new IllegalArgumentException(
                    "El docente no puede ser nulo."
            );
        }

        if (this.docenteDicta == docente) {
            docente.agregarSeccionInterna(this);
            return;
        }

        Academico docenteAnterior =
                this.docenteDicta;

        this.docenteDicta = docente;

        if (docenteAnterior != null) {
            docenteAnterior.removerSeccionInterna(this);
        }

        docente.agregarSeccionInterna(this);
    }

    void agregarInscripcionInterna(
            Inscripcion inscripcion
    ) {
        if (inscripcion == null) {
            throw new IllegalArgumentException(
                    "La inscripción no puede ser nula."
            );
        }

        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
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