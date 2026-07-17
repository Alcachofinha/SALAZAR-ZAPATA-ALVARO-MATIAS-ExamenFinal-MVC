package org.unisiga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import org.unisiga.exception.PrerrequisitoNoCumplidoException;
import org.unisiga.model.Asignatura;
import org.unisiga.model.Estudiante;
import org.unisiga.model.Inscripcion;
import org.unisiga.model.Seccion;
import org.unisiga.persistence.ArchivoSistema;
import org.unisiga.persistence.DatosSistema;
import org.unisiga.view.FrmInscripcion;

/**
 * Controlador de lógica de negocio transaccional.
 * Simula llamadas e interacciones de base de datos.
 */
public class InscripcionController implements ActionListener {

    private static final String RUTA_DATOS = "datos/unisiga.dat";

    private final DatosSistema datosSistema;
    private final ArchivoSistema archivoSistema;
    private final FrmInscripcion vista;

    public InscripcionController(
            FrmInscripcion vista,
            DatosSistema datosSistema
    ) {
        if (vista == null) {
            throw new IllegalArgumentException(
                    "La vista no puede ser nula."
            );
        }

        if (datosSistema == null) {
            throw new IllegalArgumentException(
                    "Los datos del sistema no pueden ser nulos."
            );
        }

        this.vista = vista;
        this.datosSistema = datosSistema;
        this.archivoSistema = new ArchivoSistema();

        vista.btnInscribir.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("Inscripción de estudiantes");
        vista.setLocationByPlatform(true);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == vista.btnInscribir) {
            procesarInscripcion();
        }

        if (evento.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }
    }

    private void procesarInscripcion() {
        try {
            String matricula = vista.txtMatricula.getText();
            String codigo = vista.txtCodigoAsignatura.getText();
            String textoSeccion = vista.txtSeccion.getText().trim();

            if (textoSeccion.length() != 1) {
                throw new IllegalArgumentException(
                        "La sección debe contener un carácter."
                );
            }

            String resultado = inscribirSeccionEstudiante(
                    matricula,
                    codigo,
                    textoSeccion.charAt(0)
            );

            archivoSistema.guardar(RUTA_DATOS, datosSistema);

            JOptionPane.showMessageDialog(
                    vista,
                    resultado,
                    "Inscripción correcta",
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

    private void limpiarCampos() {
        vista.txtMatricula.setText("");
        vista.txtCodigoAsignatura.setText("");
        vista.txtSeccion.setText("");
        vista.txtMatricula.requestFocus();
    }

    // Métodos de sembrado (seeding) de bases de datos
    public void registrarEstudianteEnDb(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo."
            );
        }

        for (Estudiante registrado : datosSistema.getEstudiantes()) {
            if (registrado.getMatricula().equalsIgnoreCase(
                    estudiante.getMatricula()
            )) {
                throw new IllegalArgumentException(
                        "La matrícula ya está registrada."
                );
            }
        }

        datosSistema.getEstudiantes().add(estudiante);
    }

    public void registrarAsignaturaEnDb(Asignatura asignatura) {
        if (asignatura == null) {
            throw new IllegalArgumentException(
                    "La asignatura no puede ser nula."
            );
        }

        for (Asignatura registrada : datosSistema.getAsignaturas()) {
            if (registrada.getCodigo().equalsIgnoreCase(
                    asignatura.getCodigo()
            )) {
                throw new IllegalArgumentException(
                        "La asignatura ya está registrada."
                );
            }
        }

        datosSistema.getAsignaturas().add(asignatura);
    }

    /**
     * Procesa la solicitud de inscripción de asignaturas.
     * [LÓGICA]&#58;      * 1. Buscar estudiante y asignatura.
     * 2. Obtener el grupo solicitado por composición.
     * 3. VALIDAR PRERREQUISITOS (Auto-asociación):
     * El alumno debe tener aprobado el prerrequisito
     * en su historial.
     * 4. Delegar la transacción al dominio del modelo.
     */
    public String inscribirSeccionEstudiante(
            String matricula,
            String codigoAsignatura,
            char idGrupo
    ) {
        validarTexto(
                matricula,
                "La matrícula no puede estar vacía."
        );

        validarTexto(
                codigoAsignatura,
                "El código no puede estar vacío."
        );

        Estudiante estudiante = buscarEstudiante(matricula);
        Asignatura asignatura = buscarAsignatura(codigoAsignatura);
        Seccion seccion = buscarSeccion(asignatura, idGrupo);

        validarPrerrequisitos(estudiante, asignatura);
        estudiante.inscribirSeccion(seccion);

        return "Inscripción realizada: "
                + estudiante.getNombre()
                + " en "
                + asignatura.getNombre()
                + " - Sección "
                + seccion.getIdGrupo();
    }

    public List<Estudiante> getEstudiantesDb() {
        return Collections.unmodifiableList(
                datosSistema.getEstudiantes()
        );
    }

    public List<Asignatura> getAsignaturasDb() {
        return Collections.unmodifiableList(
                datosSistema.getAsignaturas()
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

    private void validarPrerrequisitos(
            Estudiante estudiante,
            Asignatura asignatura
    ) {
        for (Asignatura prerrequisito : asignatura.getPrerrequisitos()) {
            boolean aprobado = false;

            for (Inscripcion inscripcion
                    : estudiante.getInscripciones()) {

                Asignatura cursada = inscripcion
                        .getSeccion()
                        .getAsignatura();

                boolean mismaAsignatura = cursada.getCodigo()
                        .equalsIgnoreCase(prerrequisito.getCodigo());

                boolean estadoAprobado = inscripcion
                        .getEstadoInscripcion()
                        .equalsIgnoreCase("Aprobado");

                if (mismaAsignatura && estadoAprobado) {
                    aprobado = true;
                    break;
                }
            }

            if (!aprobado) {
                throw new PrerrequisitoNoCumplidoException(
                        "Prerrequisito no aprobado: "
                                + prerrequisito.getNombre()
                );
            }
        }
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}