package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al estudiante de la universidad.
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
                "La matrícula del estudiante no puede estar vacía."
        );

        if (anioIngreso <= 0) {
            throw new IllegalArgumentException(
                    "El año de ingreso debe ser mayor que cero."
            );
        }

        if (promedioPpa < 0.0f || promedioPpa > 7.0f) {
            throw new IllegalArgumentException(
                    "El promedio PPA debe estar entre 0.0 y 7.0."
            );
        }

        this.matricula = matricula.trim();
        this.anioIngreso = anioIngreso;
        this.promedioPpa = promedioPpa;
        this.inscripciones = new ArrayList<>();
    }

    /**
     * Implementación polimórfica del inicio de sesión.
     *
     * Regla indicada por el profesor:
     * la contraseña del estudiante debe tener como mínimo
     * 8 caracteres.
     *
     * @param password contraseña ingresada
     * @return true si tiene como mínimo 8 caracteres
     */
    @Override
    public boolean login(String password) {
        return password != null && password.length() >= 8;
    }

    /**
     * Realiza la inscripción del estudiante en una sección.
     *
     * Este método será implementado en un siguiente paso.
     *
     * @param seccion sección seleccionada
     */
    public void inscribirSeccion(Seccion seccion) {
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