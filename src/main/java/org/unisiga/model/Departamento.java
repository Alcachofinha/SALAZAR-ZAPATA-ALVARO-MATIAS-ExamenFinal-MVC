package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un departamento académico de la universidad.
 *
 * Relación UML:
 * Un Departamento tiene cero o muchos académicos.
 *
 * La relación es una agregación porque el académico
 * puede existir independientemente del departamento.
 */
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoDepto;
    private String nombre;
    private List<Academico> academicos;

    public Departamento(String codigoDepto, String nombre) {

        if (codigoDepto == null || codigoDepto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código del departamento no puede estar vacío."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del departamento no puede estar vacío."
            );
        }

        this.codigoDepto = codigoDepto.trim();
        this.nombre = nombre.trim();
        this.academicos = new ArrayList<>();
    }

    /**
     * Asocia un académico con este departamento.
     *
     * La asociación se realiza en ambos sentidos:
     *
     * Departamento -> Academico
     * Academico -> Departamento
     *
     * @param academico académico que será asociado
     */
    public void asociarAcademico(Academico academico) {

        if (academico == null) {
            throw new IllegalArgumentException(
                    "El académico no puede ser nulo."
            );
        }

        academico.setDepartamento(this);
    }

    /**
     * Agrega internamente al académico.
     *
     * Este método no es público porque únicamente se utiliza
     * para mantener correctamente la asociación bidireccional.
     *
     * @param academico académico que será agregado
     */
    void agregarAcademicoInterno(Academico academico) {

        if (academico != null && !academicos.contains(academico)) {
            academicos.add(academico);
        }
    }

    /**
     * Elimina internamente al académico del departamento.
     *
     * Se utiliza cuando un académico cambia de departamento.
     *
     * @param academico académico que será eliminado
     */
    void removerAcademicoInterno(Academico academico) {

        if (academico != null) {
            academicos.remove(academico);
        }
    }

    public String getCodigoDepto() {
        return codigoDepto;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Academico> getAcademicos() {
        return academicos;
    }
}