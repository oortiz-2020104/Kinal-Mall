package org.osmarortiz.controller;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.osmarortiz.bean.Login;
import org.osmarortiz.bean.Usuarios;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class LoginController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private ObservableList<Usuarios> listaUsuarios;
    
    @FXML
    private Button btnIniciarSesion;
    
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContrasena;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public ObservableList<Usuarios> getUsuarios() {
        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
        
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarUsuarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Usuarios(resultado.getInt("codigoUsuario"),
                        resultado.getString("nombreUsuario"),
                        resultado.getString("apellidoUsuario"),
                        resultado.getString("nombreLogin"),
                        resultado.getString("contrasena")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaUsuarios = FXCollections.observableArrayList(lista);
    }
    
    @FXML
    private void login() throws NoSuchAlgorithmException {
        Login li = new Login();
        int ciclo = 0;
        boolean marcador = false;
        li.setUsuarioMaster(txtUsuario.getText());
        li.setPasswordLogin(txtContrasena.getText());
        
        while (ciclo < getUsuarios().size()) {
            String usuario = getUsuarios().get(ciclo).getNombreLogin();
            String contrasena = getUsuarios().get(ciclo).getContrasena();
            if (usuario.equals(li.getUsuarioMaster()) && contrasena.equals(li.getPasswordLogin())) {
                escenarioPrincipal.menuPrincipal();
                ciclo = getUsuarios().size();
                marcador = true;
            }
            ciclo++;
        }
        
        if (marcador == false) {
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectas", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaUsuario() {
        escenarioPrincipal.ventanaUsuario();
    }
}
