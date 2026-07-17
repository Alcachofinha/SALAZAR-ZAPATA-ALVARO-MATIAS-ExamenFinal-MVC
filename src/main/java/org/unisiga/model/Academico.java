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

    // Relación de agregación con Departamento.
    private Departamento departamento;

    // Secciones que dicta el académico.
    private List<Seccion> seccionesDictadas;

    public Academico(
            String rut,
            String nombre,
            String correo,
            String idEmpleado,
            String tipoContrato
    ) {
        super(rut, nombre, correo);
        this.idEmpleado = idEmpleado;
        this.tipoContrato = tipoContrato;
        this.seccionesDictadas = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        // TODO: Implementar validación simulada de MFA docente.
        // Regla indicada: la contraseña debe contener '@'.
        throw new UnsupportedOperationException(
                "Método login() no implementado aún."
        );
    }

    /**
     * Registra la nota de un estudiante para una evaluación.
     *
     * [REGLAS]&#58;      * - Validar los parámetros.
     * - Validar el rango de notas [1.0, 7.0].
     * - Validar que la evaluación pertenezca a la asignatura.
     *
     * @param inscripcion inscripción del estudiante
     * @param evaluacion evaluación que será calificada
     * @param valorNota nota obtenida
     */
    public void registrarNota(
            Inscripcion inscripcion,
            Evaluacion evaluacion,
            float valorNota
    ) {
        // TODO: Implementar la validación y la inserción
        // o actualización de la calificación de tres vías.
        throw new UnsupportedOperationException(
                "Método registrarNota() no implementado aún."
        );
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento depto) {
        this.departamento = depto;
    }

    public List<Seccion> getSeccionesDictadas() {
        return seccionesDictadas;
    }
}