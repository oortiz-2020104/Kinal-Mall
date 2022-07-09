package org.osmarortiz.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.osmarortiz.system.Principal;

public class ProgramadorController implements Initializable {

    private Principal escenarioPrincipal;

    @FXML
    Button btnProgramador;
    @FXML
    Button btnKinal;
    @FXML
    Label lblNombres;
    @FXML
    Label lblApellidos;
    @FXML
    Label lblCarnet;
    @FXML
    Label lblTitulo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void opciones(ActionEvent event) {
        if (event.getSource() == btnProgramador) {
            lblTitulo.setText("Datos del programador");
            lblNombres.setText("Osmar Emilio");
            lblApellidos.setText("Ortiz de León");
            lblCarnet.setText("2020104");
        } else if (event.getSource() == btnKinal) {
            lblTitulo.setText("Kinal Mall");
            lblNombres.setText("Kinal Mall©");
            lblApellidos.setText("Todos los derechos reservados");
            lblCarnet.setText("Realizado en 2021");
        }
    }

    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }

}
