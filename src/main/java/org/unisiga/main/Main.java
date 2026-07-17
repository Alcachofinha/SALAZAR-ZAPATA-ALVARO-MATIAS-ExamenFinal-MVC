package org.unisiga.main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.unisiga.controller.CalificacionController;
import org.unisiga.controller.InscripcionController;
import org.unisiga.model.Academico;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Departamento;
import org.unisiga.model.Estudiante;
import org.unisiga.model.Seccion;
import org.unisiga.persistence.ArchivoSistema;
import org.unisiga.persistence.DatosSistema;
import org.unisiga.view.FrmCalificacion;
import org.unisiga.view.FrmInscripcion;

/**
 * Orquestador principal de pruebas. Configura el escenario inicial de la pauta.
 */
public class Main {

    private static final String RUTA_DATOS =
            "datos/unisiga.dat";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciarAplicacion);
    }

    private static void iniciarAplicacion() {
        try {
            ArchivoSistema archivoSistema =
                    new ArchivoSistema();

            DatosSistema datosSistema;

            if (archivoSistema.existe(RUTA_DATOS)) {
                datosSistema = archivoSistema.cargar(RUTA_DATOS);
            } else {
                datosSistema = crearDatosIniciales();
                archivoSistema.guardar(RUTA_DATOS, datosSistema);
            }

            FrmInscripcion vistaInscripcion =
                    new FrmInscripcion();

            FrmCalificacion vistaCalificacion =
                    new FrmCalificacion();

            InscripcionController inscripcionController =
                    new InscripcionController(
                            vistaInscripcion,
                            datosSistema
                    );

            CalificacionController calificacionController =
                    new CalificacionController(
                            vistaCalificacion,
                            datosSistema
                    );

            inscripcionController.iniciarVista();
            calificacionController.iniciarVista();

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage(),
                    "Error al iniciar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static DatosSistema crearDatosIniciales() {
        DatosSistema datosSistema =
                new DatosSistema();

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

        poo.agregarPrerrequisito(programacion);

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

        juan.inscribirSeccion(seccionProgramacion);

        juan.getInscripciones()
                .get(0)
                .setEstadoInscripcion("Aprobado");

        datosSistema.getEstudiantes().add(juan);
        datosSistema.getEstudiantes().add(maria);

        datosSistema.getAsignaturas().add(programacion);
        datosSistema.getAsignaturas().add(poo);

        return datosSistema;
    }
}