package org.osmarortiz.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.osmarortiz.system.Principal;

public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void ventanaProgramador() {
        escenarioPrincipal.ventanaProgramador();
    }

    public void ventanaAdministracion() {
        escenarioPrincipal.ventanaAdministracion();
    }

    public void ventanaTipoClientes() {
        escenarioPrincipal.ventanaTipoClientes();
    }

    public void ventanaDepartamentos() {
        escenarioPrincipal.ventanaDepartamentos();
    }
    
    public void ventanaLocales() {
        escenarioPrincipal.ventanaLocales();
    }
    
    public void ventanaClientes() {
        escenarioPrincipal.ventanaClientes();
    }
    
    public void ventanaProveedores() {
        escenarioPrincipal.ventanaProveedores();
    }
    
    public void ventanaCuentasPorPagar() {
        escenarioPrincipal.ventanaCuentasPorPagar();
    }
    
    public void ventanaHorarios() {
        escenarioPrincipal.ventanaHorarios();
    }
    
    public void ventanaCargos() {
        escenarioPrincipal.ventanaCargos();
    }
    
    public void ventanaEmpleados() {
        escenarioPrincipal.ventanaEmpleados();
    }
    
    public void ventanaCuentasPorCobrar() {
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
    public void cerrarSesion() {
        escenarioPrincipal.ventanaLogin();
    }
}