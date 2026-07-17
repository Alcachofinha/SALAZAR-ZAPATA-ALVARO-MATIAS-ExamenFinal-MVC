package org.unisiga.model;

import java.io.Serializable;

/**
 * Clase abstracta base que representa a cualquier miembro
 * de la universidad.
 *
 * De esta clase heredan:
 * - Estudiante
 * - Academico
 */
public abstract class MiembroUniversitario implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String rut;
    protected String nombre;
    protected String correoInstitucional;

    public MiembroUniversitario(
            String rut,
            String nombre,
            String correoInstitucional
    ) {
        validarTexto(rut, "El RUT no puede estar vacío.");
        validarTexto(nombre, "El nombre no puede estar vacío.");
        validarTexto(
                correoInstitucional,
                "El correo institucional no puede estar vacío."
        );

        this.rut = rut.trim();
        this.nombre = nombre.trim();
        this.correoInstitucional = correoInstitucional.trim();
    }

    /**
     * Cada subclase implementa su propia regla de inicio de sesión.
     *
     * @param password contraseña ingresada
     * @return true cuando la contraseña cumple la regla
     */
    public abstract boolean login(String password);

    /**
     * Método auxiliar para validar textos.
     *
     * Es protected para que también pueda ser utilizado
     * por Estudiante y Academico.
     */
    protected final void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        validarTexto(rut, "El RUT no puede estar vacío.");
        this.rut = rut.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correo) {
        validarTexto(
                correo,
                "El correo institucional no puede estar vacío."
        );

        this.correoInstitucional = correo.trim();
    }
}