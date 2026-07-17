package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al alumno de la universidad.
 * [EVALUACIÓN]&#58; El estudiante debe implementar el encapsulamiento y el método de inscripción.
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

        validarTexto(
                matricula,
                "La matrícula no puede estar vacía."
        );

        if (anioIngreso <= 0) {
            throw new IllegalArgumentException(
                    "El año de ingreso debe ser mayor que cero."
            );
        }

        if (promedioPpa < 0.0f || promedioPpa > 7.0f) {
            throw new IllegalArgumentException(
                    "El promedio debe estar entre 0.0 y 7.0."
            );
        }

        this.matricula = matricula.trim();
        this.anioIngreso = anioIngreso;
        this.promedioPpa = promedioPpa;
        this.inscripciones = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        return password != null
                && password.length() >= 8;
    }

    /**
     * Realiza el proceso de inscripción en una sección.
     * [REGLAS]&#58; Validar que la sección no sea nula y que cuente con cupos disponibles.
     */
    public void inscribirSeccion(Seccion seccion) {

        if (seccion == null) {
            throw new IllegalArgumentException(
                    "La sección no puede ser nula."
            );
        }

        if (seccion.getInscripciones().size()
                >= seccion.getCupoMaximo()) {

            throw new IllegalStateException(
                    "La sección no tiene cupos disponibles."
            );
        }

        for (Inscripcion inscripcion : inscripciones) {

            if (inscripcion.getSeccion() == seccion) {
                throw new IllegalStateException(
                        "El estudiante ya está inscrito en esta sección."
                );
            }
        }

        Inscripcion nuevaInscripcion =
                new Inscripcion(this, seccion);

        inscripciones.add(nuevaInscripcion);
        seccion.agregarInscripcionInterna(nuevaInscripcion);
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