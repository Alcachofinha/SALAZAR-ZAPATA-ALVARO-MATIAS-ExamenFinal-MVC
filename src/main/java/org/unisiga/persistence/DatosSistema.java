package org.unisiga.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;

public class DatosSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Estudiante> estudiantes;
    private List<Asignatura> asignaturas;

    public DatosSistema(
            List<Estudiante> estudiantes,
            List<Asignatura> asignaturas
    ) {
        if (estudiantes == null) {
            throw new IllegalArgumentException(
                    "La lista de estudiantes no puede ser nula."
            );
        }

        if (asignaturas == null) {
            throw new IllegalArgumentException(
                    "La lista de asignaturas no puede ser nula."
            );
        }

        this.estudiantes =
                new ArrayList<>(estudiantes);

        this.asignaturas =
                new ArrayList<>(asignaturas);
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}