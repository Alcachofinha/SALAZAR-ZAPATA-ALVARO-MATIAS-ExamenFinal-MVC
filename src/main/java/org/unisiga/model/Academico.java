package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al profesor encargado de dictar cátedras.
 */
public class Academico extends MiembroUniversitario {

    private static final long serialVersionUID = 1L;

    private String idEmpleado;
    private String tipoContrato;
    private Departamento departamento; // Relación de agregación
    private List<Seccion> seccionesDictadas;

    public Academico(
            String rut,
            String nombre,
            String correo,
            String idEmpleado,
            String tipoContrato
    ) {
        super(rut, nombre, correo);

        validarTexto(
                idEmpleado,
                "El identificador del empleado no puede estar vacío."
        );

        validarTexto(
                tipoContrato,
                "El tipo de contrato no puede estar vacío."
        );

        this.idEmpleado = idEmpleado.trim();
        this.tipoContrato = tipoContrato.trim();
        this.seccionesDictadas = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        return password != null
                && password.contains("@");
    }

    /**
     * Registra la nota de un estudiante para una evaluación de la asignatura.
     * [REGLAS]&#58; Validar parámetros, rango de notas [1.0, 7.0] y que la evaluación pertenezca a la asignatura.
     */
    public void registrarNota(
            Inscripcion inscripcion,
            Evaluacion evaluacion,
            float valorNota
    ) {
        if (inscripcion == null) {
            throw new IllegalArgumentException(
                    "La inscripción no puede ser nula."
            );
        }

        if (evaluacion == null) {
            throw new IllegalArgumentException(
                    "La evaluación no puede ser nula."
            );
        }

        if (valorNota < 1.0f || valorNota > 7.0f) {
            throw new IllegalArgumentException(
                    "La nota debe estar entre 1.0 y 7.0."
            );
        }

        Seccion seccion = inscripcion.getSeccion();

        if (seccion == null) {
            throw new IllegalStateException(
                    "La inscripción no tiene una sección."
            );
        }

        if (seccion.getDocenteDicta() != this) {
            throw new IllegalStateException(
                    "El académico no dicta esta sección."
            );
        }

        if (evaluacion.getAsignatura()
                != seccion.getAsignatura()) {

            throw new IllegalArgumentException(
                    "La evaluación no pertenece a la asignatura."
            );
        }

        for (Calificacion calificacion
                : inscripcion.getCalificaciones()) {

            if (calificacion.getEvaluacion()
                    == evaluacion) {

                calificacion.setNota(valorNota);
                return;
            }
        }

        Calificacion nuevaCalificacion =
                new Calificacion(
                        valorNota,
                        inscripcion,
                        evaluacion
                );

        inscripcion
                .getCalificaciones()
                .add(nuevaCalificacion);

        evaluacion
                .getCalificaciones()
                .add(nuevaCalificacion);
    }

    public void setDepartamento(
            Departamento nuevoDepartamento
    ) {
        if (this.departamento == nuevoDepartamento) {

            if (nuevoDepartamento != null) {
                nuevoDepartamento
                        .agregarAcademicoInterno(this);
            }

            return;
        }

        Departamento departamentoAnterior =
                this.departamento;

        this.departamento = nuevoDepartamento;

        if (departamentoAnterior != null) {
            departamentoAnterior
                    .removerAcademicoInterno(this);
        }

        if (nuevoDepartamento != null) {
            nuevoDepartamento
                    .agregarAcademicoInterno(this);
        }
    }

    void agregarSeccionInterna(Seccion seccion) {
        if (seccion != null
                && !seccionesDictadas.contains(seccion)) {

            seccionesDictadas.add(seccion);
        }
    }

    void removerSeccionInterna(Seccion seccion) {
        if (seccion != null) {
            seccionesDictadas.remove(seccion);
        }
    }

    // Getters y Setters

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public List<Seccion> getSeccionesDictadas() {
        return seccionesDictadas;
    }
}