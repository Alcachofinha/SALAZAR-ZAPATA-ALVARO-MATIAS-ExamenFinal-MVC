package org.unisiga.main;

import org.unisiga.controller.InscripcionController;
import org.unisiga.model.Academico;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Departamento;
import org.unisiga.model.Estudiante;
import org.unisiga.model.Seccion;
import org.unisiga.view.ConsoleView;

/**
 * Orquestador principal de pruebas. Configura el escenario inicial de la pauta.
 */
public class Main {

    public static void main(String[] args) {

        ConsoleView vista = new ConsoleView();
        InscripcionController controlador =
                new InscripcionController();

        vista.desplegarMenu();

        try {
            // 1. Sembrar catálogo de asignaturas
            Asignatura programacion = new Asignatura(
                    "CC101",
                    "Programación Básica",
                    4
            );

            Asignatura poo = new Asignatura(
                    "CC201",
                    "Programación Orientada a Objetos",
                    4
            );

            // 2. Definir prerrequisitos
            poo.agregarPrerrequisito(programacion);

            // 3. Crear secciones y evaluaciones
            Seccion seccionProgramacion =
                    programacion.crearSeccion(
                            'A',
                            30,
                            "Lunes 08:00 - 10:00"
                    );

            Seccion seccionPoo =
                    poo.crearSeccion(
                            'A',
                            30,
                            "Martes 10:00 - 12:00"
                    );

            poo.crearEvaluacion(
                    1,
                    "Examen Parcial",
                    0.40f
            );

            poo.crearEvaluacion(
                    2,
                    "Examen Final",
                    0.60f
            );

            Departamento departamento =
                    new Departamento(
                            "DCC",
                            "Ciencias de la Computación"
                    );

            Academico profesor = new Academico(
                    "12345678-9",
                    "Carlos Pérez",
                    "carlos@universidad.edu",
                    "DOC001",
                    "Tiempo completo"
            );

            departamento.asociarAcademico(profesor);
            seccionPoo.asignarDocente(profesor);

            // 4. Crear estudiantes de prueba
            Estudiante juan = new Estudiante(
                    "11111111-1",
                    "Juan Pérez",
                    "juan@universidad.edu",
                    "20260001",
                    2026,
                    5.5f
            );

            Estudiante maria = new Estudiante(
                    "22222222-2",
                    "María López",
                    "maria@universidad.edu",
                    "20260002",
                    2026,
                    5.2f
            );

            // Juan tiene aprobado el prerrequisito
            juan.inscribirSeccion(seccionProgramacion);

            juan.getInscripciones()
                    .get(0)
                    .setEstadoInscripcion("Aprobado");

            controlador.registrarEstudianteEnDb(juan);
            controlador.registrarEstudianteEnDb(maria);

            controlador.registrarAsignaturaEnDb(programacion);
            controlador.registrarAsignaturaEnDb(poo);

            // 5. Casos de prueba
            probarInscripcionJuan(
                    vista,
                    controlador,
                    juan,
                    poo,
                    seccionPoo
            );

            probarInscripcionMaria(
                    vista,
                    controlador
            );

            probarInscripcionDuplicada(
                    vista,
                    controlador
            );

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            vista.mostrarMensajeProcesamiento(
                    "Error: " + ex.getMessage()
            );
        }
    }

    private static void probarInscripcionJuan(
            ConsoleView vista,
            InscripcionController controlador,
            Estudiante estudiante,
            Asignatura asignatura,
            Seccion seccion
    ) {
        try {
            String resultado =
                    controlador.inscribirSeccionEstudiante(
                            estudiante.getMatricula(),
                            asignatura.getCodigo(),
                            seccion.getIdGrupo()
                    );

            vista.mostrarMensajeProcesamiento(resultado);

            vista.imprimirComprobante(
                    estudiante.getNombre(),
                    asignatura.getNombre(),
                    seccion.getIdGrupo()
            );

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            vista.mostrarMensajeProcesamiento(
                    "Error: " + ex.getMessage()
            );
        }
    }

    private static void probarInscripcionMaria(
            ConsoleView vista,
            InscripcionController controlador
    ) {
        try {
            controlador.inscribirSeccionEstudiante(
                    "20260002",
                    "CC201",
                    'A'
            );

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            vista.mostrarMensajeProcesamiento(
                    "Caso María: " + ex.getMessage()
            );
        }
    }

    private static void probarInscripcionDuplicada(
            ConsoleView vista,
            InscripcionController controlador
    ) {
        try {
            controlador.inscribirSeccionEstudiante(
                    "20260001",
                    "CC201",
                    'A'
            );

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            vista.mostrarMensajeProcesamiento(
                    "Caso duplicado: " + ex.getMessage()
            );
        }
    }
}