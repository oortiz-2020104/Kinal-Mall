package org.osmarortiz.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.osmarortiz.controller.AdministracionController;
import org.osmarortiz.controller.CargosController;
import org.osmarortiz.controller.ClientesController;
import org.osmarortiz.controller.CuentasPorCobrarController;
import org.osmarortiz.controller.CuentasPorPagarController;
import org.osmarortiz.controller.DepartamentosController;
import org.osmarortiz.controller.EmpleadosController;
import org.osmarortiz.controller.HorariosController;
import org.osmarortiz.controller.LocalesController;
import org.osmarortiz.controller.LoginController;
import org.osmarortiz.controller.MenuPrincipalController;
import org.osmarortiz.controller.ProgramadorController;
import org.osmarortiz.controller.ProveedoresController;
import org.osmarortiz.controller.TipoClientesController;
import org.osmarortiz.controller.UsuariosController;

/**
 * Programador: Osmar Ortiz 
 * Fecha de creacion: 28/04/2021 Modificaciones:
 * 28/04/2021 
 * 29/04/2021 
 * 05/05/2021 
 * 06/05/2021 
 * 12/05/2021 
 * 16/05/2021 
 * 26/05/2021
 * 27/05/2021 
 * 02/06/2021 
 * 03/06/2021 
 * 04/06/2021 
 * 05/06/2021 
 * 06/06/2021 
 * 07/06/2021
 * 09/06/2021
 * 10/06/2021
 * 16/06/2021
 * 17/06/2021
 * 30/06/2021
 * 01/07/2021
 * 02/07/2021
 * 03/07/2021
 * 04/07/2021
 * 07/07/2021
 * 08/07/2021
 * 09/07/2021
 * 14/07/2021
 * 15/07/2021
 */
public class Principal extends Application {

    private final String PaqueteVistas = "/org/osmarortiz/view/";
    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall");
        this.escenarioPrincipal.getIcons().add(new Image("/org/osmarortiz/images/IconoKinallMall.png"));
        ventanaLogin();
        escenarioPrincipal.show();
    }

    public void menuPrincipal() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 993, 328);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaProgramador() {
        try {
            ProgramadorController programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 750, 450);
            programador.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaAdministracion() {
        try {
            AdministracionController admin = (AdministracionController) cambiarEscena("AdministracionView.fxml", 1312, 450);
            admin.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaTipoClientes() {
        try {
            TipoClientesController tipoClientes = (TipoClientesController) cambiarEscena("TipoClientesView.fxml", 966, 450);
            tipoClientes.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaDepartamentos() {
        try {
            DepartamentosController departamentos = (DepartamentosController) cambiarEscena("DepartamentosView.fxml", 902, 450);
            departamentos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaLocales() {
        try {
            LocalesController locales = (LocalesController) cambiarEscena("LocalesView.fxml", 1256, 547);
            locales.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventanaClientes() {
        try {
            ClientesController clientes = (ClientesController) cambiarEscena("ClientesView.fxml", 1256, 547);
            clientes.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedores() {
        try {
            ProveedoresController proveedores = (ProveedoresController) cambiarEscena("ProveedoresView.fxml", 1256, 547);
            proveedores.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorPagar() {
        try {
            CuentasPorPagarController cuentasPorPagar = (CuentasPorPagarController) cambiarEscena("CuentasPorPagarView.fxml", 1256, 547);
            cuentasPorPagar.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaHorarios() {
        try {
            HorariosController horarios = (HorariosController) cambiarEscena("HorariosView.fxml", 1163, 480);
            horarios.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaCargos() {
        try {
            CargosController cargos = (CargosController) cambiarEscena("CargosView.fxml", 902, 450);
            cargos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleados() {
        try {
            EmpleadosController empleados = (EmpleadosController) cambiarEscena("EmpleadosView.fxml", 1331, 591);
            empleados.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorCobrar() {
        try {
            CuentasPorCobrarController cuentrasPorCobrar = (CuentasPorCobrarController) cambiarEscena("CuentasPorCobrarView.fxml", 1256, 547);
            cuentrasPorCobrar.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario() {
        try {
            UsuariosController usuarios = (UsuariosController) cambiarEscena("UsuariosView.fxml", 727, 250);
            usuarios.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin() {
        try {
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 600, 450);
            login.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PaqueteVistas + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PaqueteVistas + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }
}
