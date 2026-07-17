package org.unisiga.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.unisiga.persistence.DatosSistema;
import org.unisiga.view.FrmCalificacion;
import org.unisiga.view.FrmInscripcion;
import org.unisiga.view.FrmPrincipal;

public class PrincipalController implements ActionListener {

    private final FrmPrincipal vista;
    private final DatosSistema datosSistema;

    public PrincipalController(
            FrmPrincipal vista,
            DatosSistema datosSistema
    ) {
        if (vista == null) {
            throw new IllegalArgumentException(
                    "La vista principal no puede ser nula."
            );
        }

        if (datosSistema == null) {
            throw new IllegalArgumentException(
                    "Los datos del sistema no pueden ser nulos."
            );
        }

        this.vista = vista;
        this.datosSistema = datosSistema;

        vista.btnInscripcion.addActionListener(this);
        vista.btnCalificacion.addActionListener(this);
        vista.btnSalir.addActionListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("UNISIGA - Menú principal");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == vista.btnInscripcion) {
            abrirInscripcion();
        }

        if (evento.getSource() == vista.btnCalificacion) {
            abrirCalificacion();
        }

        if (evento.getSource() == vista.btnSalir) {
            System.exit(0);
        }
    }

    private void abrirInscripcion() {
        FrmInscripcion vistaInscripcion =
                new FrmInscripcion();

        InscripcionController controlador =
                new InscripcionController(
                        vistaInscripcion,
                        datosSistema
                );

        controlador.iniciarVista();
    }

    private void abrirCalificacion() {
        FrmCalificacion vistaCalificacion =
                new FrmCalificacion();

        CalificacionController controlador =
                new CalificacionController(
                        vistaCalificacion,
                        datosSistema
                );

        controlador.iniciarVista();
    }
}