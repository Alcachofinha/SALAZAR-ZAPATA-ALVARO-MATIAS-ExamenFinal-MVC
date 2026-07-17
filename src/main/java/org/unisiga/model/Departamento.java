package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un Departamento Académico.
 *
 * [EVALUACIÓN]&#58; Demostrar la implementación del concepto de agregación.
 * El académico pertenece al departamento, pero conserva un ciclo
 * de vida independiente.
 */
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoDepto;
    private String nombre;
    private List<Academico> academicos;

    public Departamento(String codigoDepto, String nombre) {
        this.codigoDepto = codigoDepto;
        this.nombre = nombre;
        this.academicos = new ArrayList<>();
    }

    /**
     * Asocia un académico con el departamento.
     *
     * @param acad académico que será asociado
     */
    public void asociarAcademico(Academico acad) {
        // TODO: Asociar el académico asegurando
        // la bidireccionalidad segura.
        throw new UnsupportedOperationException(
                "Método asociarAcademico() no implementado aún."
        );
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