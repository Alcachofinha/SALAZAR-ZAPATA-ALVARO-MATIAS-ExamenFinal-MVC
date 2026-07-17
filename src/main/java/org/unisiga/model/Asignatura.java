package org.unisiga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo general de asignaturas.
 *
 * Controla:
 * - Los prerrequisitos mediante autoasociación.
 * - Las secciones mediante composición.
 * - Las evaluaciones mediante composición.
 */
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private int creditosSct;

    /**
     * Autoasociación.
     *
     * Una asignatura puede tener otras asignaturas
     * como prerrequisitos.
     */
    private List<Asignatura> prerrequisitos;

    /**
     * Composición.
     *
     * Las secciones son creadas y contenidas
     * por la asignatura.
     */
    private List<Seccion> secciones;

    /**
     * Composición.
     *
     * Las evaluaciones son creadas y contenidas
     * por la asignatura.
     */
    private List<Evaluacion> evaluaciones;

    public Asignatura(
            String codigo,
            String nombre,
            int creditosSct
    ) {
        validarTexto(
                codigo,
                "El código de la asignatura no puede estar vacío."
        );

        validarTexto(
                nombre,
                "El nombre de la asignatura no puede estar vacío."
        );

        if (creditosSct <= 0) {
            throw new IllegalArgumentException(
                    "Los créditos SCT deben ser mayores que cero."
            );
        }

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.creditosSct = creditosSct;

        this.prerrequisitos = new ArrayList<>();
        this.secciones = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    /**
     * Agrega una asignatura como prerrequisito.
     *
     * Reglas:
     * - El prerrequisito no puede ser nulo.
     * - Una asignatura no puede ser prerrequisito de sí misma.
     * - No se permiten prerrequisitos duplicados.
     *
     * @param asignaturaPrerrequisito asignatura requerida
     */
    public void agregarPrerrequisito(
            Asignatura asignaturaPrerrequisito
    ) {
        if (asignaturaPrerrequisito == null) {
            throw new IllegalArgumentException(
                    "El prerrequisito no puede ser nulo."
            );
        }

        /*
         * Evita que la asignatura sea prerrequisito de sí misma.
         *
         * Se compara tanto el objeto como el código.
         */
        if (this == asignaturaPrerrequisito
                || this.codigo.equalsIgnoreCase(
                        asignaturaPrerrequisito.getCodigo()
                )) {

            throw new IllegalArgumentException(
                    "Una asignatura no puede ser prerrequisito de sí misma."
            );
        }

        /*
         * Evita agregar dos asignaturas con el mismo código.
         */
        for (Asignatura prerrequisito : prerrequisitos) {

            if (prerrequisito.getCodigo().equalsIgnoreCase(
                    asignaturaPrerrequisito.getCodigo()
            )) {
                throw new IllegalArgumentException(
                        "La asignatura ya está registrada como prerrequisito."
                );
            }
        }

        prerrequisitos.add(asignaturaPrerrequisito);
    }

    /**
     * Crea una sección perteneciente a esta asignatura.
     *
     * Esta operación representa la composición del UML:
     *
     * Asignatura contiene Seccion.
     *
     * @param idGrupo identificador de la sección
     * @param cupoMaximo capacidad máxima
     * @param horario horario de la sección
     * @return sección creada
     */
    public Seccion crearSeccion(
            char idGrupo,
            int cupoMaximo,
            String horario
    ) {
        if (idGrupo == '\0' || Character.isWhitespace(idGrupo)) {
            throw new IllegalArgumentException(
                    "El identificador de la sección no puede estar vacío."
            );
        }

        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException(
                    "El cupo máximo debe ser mayor que cero."
            );
        }

        validarTexto(
                horario,
                "El horario de la sección no puede estar vacío."
        );

        /*
         * El identificador de la sección no debe repetirse
         * dentro de la misma asignatura.
         */
        for (Seccion seccion : secciones) {

            if (Character.toUpperCase(seccion.getIdGrupo())
                    == Character.toUpperCase(idGrupo)) {

                throw new IllegalArgumentException(
                        "Ya existe una sección con el identificador "
                                + idGrupo + "."
                );
            }
        }

        /*
         * Se utiliza el constructor restringido de Seccion.
         *
         * Asignatura envía this para establecer la relación
         * de composición.
         */
        Seccion nuevaSeccion = new Seccion(
                idGrupo,
                cupoMaximo,
                horario.trim(),
                this
        );

        secciones.add(nuevaSeccion);

        return nuevaSeccion;
    }

    /**
     * Crea una evaluación perteneciente a esta asignatura.
     *
     * Esta operación representa la composición del UML:
     *
     * Asignatura contiene Evaluacion.
     *
     * @param id identificador de la evaluación
     * @param titulo nombre de la evaluación
     * @param ponderacion valor entre 0 y 1
     * @return evaluación creada
     */
    public Evaluacion crearEvaluacion(
            int id,
            String titulo,
            float ponderacion
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El identificador de la evaluación debe ser mayor que cero."
            );
        }

        validarTexto(
                titulo,
                "El título de la evaluación no puede estar vacío."
        );

        if (ponderacion <= 0.0f || ponderacion > 1.0f) {
            throw new IllegalArgumentException(
                    "La ponderación debe ser mayor que 0 y menor o igual que 1."
            );
        }

        /*
         * No se permiten dos evaluaciones con el mismo ID
         * dentro de una asignatura.
         */
        for (Evaluacion evaluacion : evaluaciones) {

            if (evaluacion.getId() == id) {
                throw new IllegalArgumentException(
                        "Ya existe una evaluación con el identificador "
                                + id + "."
                );
            }
        }

        /*
         * Se utiliza el constructor restringido de Evaluacion.
         *
         * Asignatura envía this para establecer la relación
         * de composición.
         */
        Evaluacion nuevaEvaluacion = new Evaluacion(
                id,
                titulo.trim(),
                ponderacion,
                this
        );

        evaluaciones.add(nuevaEvaluacion);

        return nuevaEvaluacion;
    }

    /**
     * Validación auxiliar utilizada únicamente dentro
     * de esta clase.
     */
    private void validarTexto(String valor, String mensaje) {

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
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