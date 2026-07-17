package org.unisiga.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;

public class DatosSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Estudiante> estudiantes;
    private final List<Asignatura> asignaturas;

    public DatosSistema() {
        this.estudiantes = new ArrayList<>();
        this.asignaturas = new ArrayList<>();
    }

    public DatosSistema(
            List<Estudiante> estudiantes,
            List<Asignatura> asignaturas
    ) {
        if (estudiantes == null || asignaturas == null) {
            throw new IllegalArgumentException(
                    "Las listas del sistema no pueden ser nulas."
            );
        }

        this.estudiantes = new ArrayList<>(estudiantes);
        this.asignaturas = new ArrayList<>(asignaturas);
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}