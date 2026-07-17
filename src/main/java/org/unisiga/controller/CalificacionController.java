package org.unisiga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.unisiga.model.Academico;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;
import org.unisiga.model.Evaluacion;
import org.unisiga.model.Inscripcion;
import org.unisiga.model.Seccion;
import org.unisiga.persistence.ArchivoSistema;
import org.unisiga.persistence.DatosSistema;
import org.unisiga.view.FrmCalificacion;

public class CalificacionController implements ActionListener {

    private static final String RUTA_DATOS = "datos/unisiga.dat";

    private final FrmCalificacion vista;
    private final DatosSistema datosSistema;
    private final ArchivoSistema archivoSistema;

    public CalificacionController(
            FrmCalificacion vista,
            DatosSistema datosSistema
    ) {
        if (vista == null || datosSistema == null) {
            throw new IllegalArgumentException(
                    "La vista y los datos son obligatorios."
            );
        }

        this.vista = vista;
        this.datosSistema = datosSistema;
        this.archivoSistema = new ArchivoSistema();

        vista.btnRegistrarNota.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("Registro de calificaciones");
        vista.setLocationByPlatform(true);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == vista.btnRegistrarNota) {
            procesarCalificacion();
        }

        if (evento.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }
    }

    private void procesarCalificacion() {
        try {
            String idAcademico = vista.txtIdAcademico.getText();
            String matricula = vista.txtMatricula.getText();
            String codigo = vista.txtCodigoAsignatura.getText();
            String textoSeccion = vista.txtSeccion.getText().trim();

            validarTexto(
                    idAcademico,
                    "El ID del académico es obligatorio."
            );

            validarTexto(
                    matricula,
                    "La matrícula es obligatoria."
            );

            validarTexto(
                    codigo,
                    "El código de asignatura es obligatorio."
            );

            if (textoSeccion.length() != 1) {
                throw new IllegalArgumentException(
                        "La sección debe contener un carácter."
                );
            }

            int idEvaluacion = convertirEntero(
                    vista.txtIdEvaluacion.getText(),
                    "El ID de evaluación debe ser un número entero."
            );

            float nota = convertirNota(
                    vista.txtNota.getText()
            );

            Academico academico = buscarAcademico(idAcademico);
            Estudiante estudiante = buscarEstudiante(matricula);
            Asignatura asignatura = buscarAsignatura(codigo);

            Seccion seccion = buscarSeccion(
                    asignatura,
                    textoSeccion.charAt(0)
            );

            Inscripcion inscripcion = buscarInscripcion(
                    estudiante,
                    seccion
            );

            Evaluacion evaluacion = buscarEvaluacion(
                    asignatura,
                    idEvaluacion
            );

            academico.registrarNota(
                    inscripcion,
                    evaluacion,
                    nota
            );

            archivoSistema.guardar(
                    RUTA_DATOS,
                    datosSistema
            );

            JOptionPane.showMessageDialog(
                    vista,
                    "Nota registrada correctamente.",
                    "Registro correcto",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();

        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    vista,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Academico buscarAcademico(String idAcademico) {
        for (Asignatura asignatura : datosSistema.getAsignaturas()) {
            for (Seccion seccion : asignatura.getSecciones()) {
                Academico academico = seccion.getDocenteDicta();

                if (academico != null
                        && academico.getIdEmpleado().equalsIgnoreCase(
                                idAcademico.trim()
                        )) {
                    return academico;
                }
            }
        }

        throw new IllegalArgumentException(
                "No existe un académico con ese ID."
        );
    }

    private Estudiante buscarEstudiante(String matricula) {
        for (Estudiante estudiante : datosSistema.getEstudiantes()) {
            if (estudiante.getMatricula().equalsIgnoreCase(
                    matricula.trim()
            )) {
                return estudiante;
            }
        }

        throw new IllegalArgumentException(
                "No existe un estudiante con esa matrícula."
        );
    }

    private Asignatura buscarAsignatura(String codigo) {
        for (Asignatura asignatura : datosSistema.getAsignaturas()) {
            if (asignatura.getCodigo().equalsIgnoreCase(
                    codigo.trim()
            )) {
                return asignatura;
            }
        }

        throw new IllegalArgumentException(
                "No existe una asignatura con ese código."
        );
    }

    private Seccion buscarSeccion(
            Asignatura asignatura,
            char idGrupo
    ) {
        for (Seccion seccion : asignatura.getSecciones()) {
            if (Character.toUpperCase(seccion.getIdGrupo())
                    == Character.toUpperCase(idGrupo)) {
                return seccion;
            }
        }

        throw new IllegalArgumentException(
                "No existe la sección solicitada."
        );
    }

    private Inscripcion buscarInscripcion(
            Estudiante estudiante,
            Seccion seccion
    ) {
        for (Inscripcion inscripcion
                : estudiante.getInscripciones()) {

            if (inscripcion.getSeccion() == seccion) {
                return inscripcion;
            }
        }

        throw new IllegalStateException(
                "El estudiante no está inscrito en esa sección."
        );
    }

    private Evaluacion buscarEvaluacion(
            Asignatura asignatura,
            int idEvaluacion
    ) {
        for (Evaluacion evaluacion
                : asignatura.getEvaluaciones()) {

            if (evaluacion.getId() == idEvaluacion) {
                return evaluacion;
            }
        }

        throw new IllegalArgumentException(
                "No existe la evaluación solicitada."
        );
    }

    private int convertirEntero(
            String texto,
            String mensaje
    ) {
        try {
            return Integer.parseInt(texto.trim());

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private float convertirNota(String texto) {
        try {
            String notaNormalizada = texto
                    .trim()
                    .replace(',', '.');

            return Float.parseFloat(notaNormalizada);

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "La nota debe ser un número válido."
            );
        }
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private void limpiarCampos() {
        vista.txtIdAcademico.setText("");
        vista.txtMatricula.setText("");
        vista.txtCodigoAsignatura.setText("");
        vista.txtSeccion.setText("");
        vista.txtIdEvaluacion.setText("");
        vista.txtNota.setText("");
        vista.txtIdAcademico.requestFocus();
    }
}