package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al alumno de la universidad.
 * [EVALUACIÓN]&#58; El estudiante debe implementar el encapsulamiento
 * y el método de inscripción.
 */
public class Estudiante extends MiembroUniversitario {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private int anioIngreso;
    private float promedioPpa;
    private List<Inscripcion> inscripciones;

    public Estudiante(
            String rut,
            String nombre,
            String correo,
            String matricula,
            int anioIngreso,
            float promedioPpa
    ) {
        super(rut, nombre, correo);
        this.matricula = matricula;
        this.anioIngreso = anioIngreso;
        this.promedioPpa = promedioPpa;
        this.inscripciones = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        // TODO: Implementar validación simulada de clave del estudiante.
        // Regla indicada: largo mínimo de 8 caracteres.
        throw new UnsupportedOperationException(
                "Método login() no implementado aún."
        );
    }

    /**
     * Realiza el proceso de inscripción en una sección.
     * [REGLAS]&#58; Validar que la sección no sea nula y que cuente
     * con cupos disponibles.
     *
     * @param seccion sección en la que se inscribirá el estudiante
     */
    public void inscribirSeccion(Seccion seccion) {
        // TODO: Implementar el control de cupos y la creación
        // de la clase de asociación Inscripcion.
        //
        // La inscripción deberá agregarse tanto a la lista
        // del estudiante como a la lista de la sección.
        throw new UnsupportedOperationException(
                "Método inscribirSeccion() no implementado aún."
        );
    }

    public String getMatricula() {
        return matricula;
    }

    public int getAnioIngreso() {
        return anioIngreso;
    }

    public float getPromedioPpa() {
        return promedioPpa;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}