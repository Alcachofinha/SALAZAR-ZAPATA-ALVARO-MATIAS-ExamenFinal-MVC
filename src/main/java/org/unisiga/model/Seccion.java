package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Materialización de una asignatura durante el semestre.
 *
 * En el UML del profesor aparece con el nombre Grupo.
 * En el código se utiliza el nombre Seccion.
 */
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private char idGrupo;
    private int cupoMaximo;
    private String horario;

    // Contenedor de la composición.
    private Asignatura asignatura;

    // Académico que dicta la sección.
    private Academico docenteDicta;

    // Inscripciones pertenecientes a la sección.
    private List<Inscripcion> inscripciones;

    /**
     * Constructor package-private.
     *
     * No se coloca public para que la sección sea creada
     * principalmente desde Asignatura.
     */
    Seccion(
            char idGrupo,
            int cupoMaximo,
            String horario,
            Asignatura asignatura
    ) {
        this.idGrupo = idGrupo;
        this.cupoMaximo = cupoMaximo;
        this.horario = horario;
        this.asignatura = asignatura;
        this.inscripciones = new ArrayList<>();
    }

    /**
     * Asigna un académico como docente de la sección.
     *
     * @param docente académico que dictará la sección
     */
    public void asignarDocente(Academico docente) {
        // TODO: Asignar el docente controlando
        // la asociación bidireccional.
        throw new UnsupportedOperationException(
                "Método asignarDocente() no implementado aún."
        );
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