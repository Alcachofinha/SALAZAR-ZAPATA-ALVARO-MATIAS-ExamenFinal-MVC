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

    /**
     * Departamento al que pertenece el académico.
     *
     * Esta relación corresponde a la agregación mostrada
     * en el UML del profesor.
     */
    private Departamento departamento;

    /**
     * Secciones dictadas por el académico.
     */
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
        this.departamento = null;
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
     * Registra una nota para una inscripción y evaluación.
     *
     * Este método será implementado posteriormente.
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

    /**
     * Asocia el académico con un departamento.
     *
     * También actualiza la lista de académicos del departamento
     * para conservar la relación bidireccional.
     *
     * Si el académico ya pertenecía a otro departamento,
     * primero será retirado del departamento anterior.
     *
     * @param nuevoDepartamento nuevo departamento del académico
     */
    public void setDepartamento(Departamento nuevoDepartamento) {

        /*
         * Si ya está asociado con ese mismo departamento,
         * solamente comprobamos que también aparezca en su lista.
         */
        if (this.departamento == nuevoDepartamento) {

            if (nuevoDepartamento != null) {
                nuevoDepartamento.agregarAcademicoInterno(this);
            }

            return;
        }

        Departamento departamentoAnterior = this.departamento;

        /*
         * Primero actualizamos la referencia del académico.
         */
        this.departamento = nuevoDepartamento;

        /*
         * Después eliminamos al académico del departamento anterior.
         */
        if (departamentoAnterior != null) {
            departamentoAnterior.removerAcademicoInterno(this);
        }

        /*
         * Finalmente agregamos al académico al nuevo departamento.
         */
        if (nuevoDepartamento != null) {
            nuevoDepartamento.agregarAcademicoInterno(this);
        }
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

    public List<Seccion> getSeccionesDictadas() {
        return seccionesDictadas;
    }
}