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
     * Relación de agregación mostrada en el UML.
     */
    private Departamento departamento;

    /**
     * Secciones dictadas por el académico.
     *
     * Un académico puede dictar cero o muchas secciones.
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
     * Regla indicada en la plantilla:
     * la contraseña debe contener el carácter '@'.
     *
     * @param password contraseña ingresada
     * @return true si la contraseña contiene '@'
     */
    @Override
    public boolean login(String password) {
        return password != null && password.contains("@");
    }

    /**
     * Registra una nota para una inscripción.
     *
     * Se implementará posteriormente.
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
     * Cambia el departamento del académico.
     *
     * Mantiene la relación bidireccional:
     *
     * Academico -> Departamento
     * Departamento -> Academico
     *
     * @param nuevoDepartamento departamento nuevo
     */
    public void setDepartamento(Departamento nuevoDepartamento) {

        if (this.departamento == nuevoDepartamento) {

            if (nuevoDepartamento != null) {
                nuevoDepartamento.agregarAcademicoInterno(this);
            }

            return;
        }

        Departamento departamentoAnterior = this.departamento;

        this.departamento = nuevoDepartamento;

        if (departamentoAnterior != null) {
            departamentoAnterior.removerAcademicoInterno(this);
        }

        if (nuevoDepartamento != null) {
            nuevoDepartamento.agregarAcademicoInterno(this);
        }
    }

    /**
     * Agrega internamente una sección a la lista del académico.
     *
     * No es público porque la asignación debe realizarse
     * mediante Seccion.asignarDocente().
     *
     * @param seccion sección que dictará el académico
     */
    void agregarSeccionInterna(Seccion seccion) {

        if (seccion != null && !seccionesDictadas.contains(seccion)) {
            seccionesDictadas.add(seccion);
        }
    }

    /**
     * Elimina internamente una sección de la lista del académico.
     *
     * Se utiliza cuando la sección cambia de docente.
     *
     * @param seccion sección que dejará de dictar
     */
    void removerSeccionInterna(Seccion seccion) {

        if (seccion != null) {
            seccionesDictadas.remove(seccion);
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