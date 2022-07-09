package org.osmarortiz.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.osmarortiz.bean.Usuarios;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class UsuariosController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private enum operaciones {
        GUARDAR, NINGUNO
    }
    
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    
    @FXML
    private TextField txtCodigoUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtApellidoUsuario;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContrasena;
    
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void nuevo() {
        switch (tipoDeOperacion) {
            case NINGUNO: {
                activarControl();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/osmarortiz/images/save-icon.png"));
                tipoDeOperacion = operaciones.GUARDAR;
            }
            break;

            case GUARDAR: {
                guardar();
                desactivarControl();
                limpiarControl();
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/osmarortiz/images/add-icon.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                ventanaLogin();
            }
            break;
        }
    }
    
    public void guardar() {
        Usuarios registro = new Usuarios();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setNombreLogin(txtUsuario.getText());
        registro.setContrasena(txtContrasena.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getNombreLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cancelar() {
        ventanaLogin();
    }
    
    public void desactivarControl() {
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtContrasena.setEditable(false);
    }

    public void activarControl() {
        txtCodigoUsuario.setEditable(false);
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtContrasena.setEditable(true);
    }

    public void limpiarControl() {
        txtCodigoUsuario.clear();
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtContrasena.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void ventanaLogin() {
        escenarioPrincipal.ventanaLogin();
    }
    
}
