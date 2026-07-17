package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo general de asignaturas.
 * Controla las secciones y evaluaciones unificadas.
 *
 * [EVALUACIÓN]&#58;  * - Composición fuerte.
 * - Autoasociación mediante prerrequisitos.
 */
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private int creditosSct;

    // Autoasociación recursiva para prerrequisitos.
    private List<Asignatura> prerrequisitos;

    // Composiciones fuertes.
    // Solo Asignatura debe crear las secciones y evaluaciones.
    private List<Seccion> secciones;
    private List<Evaluacion> evaluaciones;

    public Asignatura(String codigo, String nombre, int creditosSct) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditosSct = creditosSct;
        this.prerrequisitos = new ArrayList<>();
        this.secciones = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    /**
     * Agrega una asignatura como prerrequisito.
     *
     * @param asig asignatura que será prerrequisito
     */
    public void agregarPrerrequisito(Asignatura asig) {
        // TODO: Agregar el prerrequisito evitando duplicados.
        throw new UnsupportedOperationException(
                "Método agregarPrerrequisito() no implementado aún."
        );
    }

    /**
     * Lógica de composición.
     * Crea una sección y la asocia con esta asignatura.
     *
     * @param idGrupo identificador de la sección
     * @param cupoMaximo número máximo de estudiantes
     * @param horario horario de la sección
     * @return nueva sección creada
     */
    public Seccion crearSeccion(
            char idGrupo,
            int cupoMaximo,
            String horario
    ) {
        // TODO: Crear y retornar una nueva sección.
        // El constructor de Seccion es restringido.
        throw new UnsupportedOperationException(
                "Método crearSeccion() no implementado aún."
        );
    }

    /**
     * Lógica de composición.
     * Crea una evaluación oficial de esta asignatura.
     *
     * @param id identificador de la evaluación
     * @param titulo título de la evaluación
     * @param ponderacion ponderación de la evaluación
     * @return nueva evaluación creada
     */
    public Evaluacion crearEvaluacion(
            int id,
            String titulo,
            float ponderacion
    ) {
        // TODO: Crear y retornar una evaluación oficial.
        throw new UnsupportedOperationException(
                "Método crearEvaluacion() no implementado aún."
        );
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditosSct() {
        return creditosSct;
    }

    public List<Asignatura> getPrerrequisitos() {
        return prerrequisitos;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }
}