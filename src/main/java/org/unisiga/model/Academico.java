package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al académico encargado de dictar secciones.
 */
public class Academico extends MiembroUniversitario {

    private static final long serialVersionUID = 1L;

    private String idEmpleado;
    private String tipoContrato;

    // Relación de agregación con Departamento.
    private Departamento departamento;

    // Secciones dictadas por el académico.
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

    /**
     * Implementación polimórfica del inicio de sesión.
     *
     * Regla indicada por el profesor:
     * la contraseña del académico debe contener '@'.
     *
     * @param password contraseña ingresada
     * @return true si contiene el carácter '@'
     */
    @Override
    public boolean login(String password) {
        return password != null && password.contains("@");
    }

    /**
     * Registra la nota de un estudiante.
     *
     * Este método será implementado en un siguiente paso.
     *
     * @param inscripcion inscripción del estudiante
     * @param evaluacion evaluación correspondiente
     * @param valorNota nota obtenida
     */
    public void registrarNota(
            Inscripcion inscripcion,
            Evaluacion evaluacion,
            float valorNota
    ) {
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