package org.osmarortiz.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.osmarortiz.bean.Administracion;
import org.osmarortiz.bean.Clientes;
import org.osmarortiz.bean.Locales;
import org.osmarortiz.bean.TipoClientes;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.report.GenerarReporte;
import org.osmarortiz.system.Principal;

public class ClientesController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList<Locales> listaLocales;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<TipoClientes> listaTipoClientes;
    private ObservableList<Clientes> listaClientes;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtTelefonoCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtCorreoCliente;

    @FXML
    private ComboBox cmbCodigoLocal;
    @FXML
    private ComboBox cmbCodigoAdministracion;
    @FXML
    private ComboBox cmbCodigoTipoCliente;

    @FXML
    private TableView tblClientes;

    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colApellidoCliente;
    @FXML
    private TableColumn colTelefonoCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colCorreoCliente;
    @FXML
    private TableColumn colCodigoLocal;
    @FXML
    private TableColumn colCodigoAdministracion;
    @FXML
    private TableColumn colCodigoTipoCliente;

    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("codigoLocal"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoClientes, Integer>("codigoTipoCliente"));
        cmbCodigoLocal.setItems(getLocales());
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoTipoCliente.setItems(getTipoClientes());

    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("correoCliente"),
                        resultado.getInt("codigoLocal"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoTipoCliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Locales> getLocales() {
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                        resultado.getDouble("saldoFavor"),
                        resultado.getDouble("saldoContra"),
                        resultado.getInt("mesesPendientes"),
                        resultado.getBoolean("disponibilidad"),
                        resultado.getDouble("valorLocal"),
                        resultado.getDouble("valorAdministracion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaLocales = FXCollections.observableArrayList(lista);
    }

    public Locales buscarLocal(int codigoLocal) {
        Locales resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocales(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Locales(registro.getInt("codigoLocal"),
                        registro.getDouble("saldoFavor"),
                        registro.getDouble("saldoContra"),
                        registro.getInt("mesesPendientes"),
                        registro.getBoolean("disponibilidad"),
                        registro.getDouble("valorLocal"),
                        registro.getDouble("valorAdministracion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Administracion> getAdministracion() {
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaAdministracion = FXCollections.observableArrayList(lista);
    }

    public Administracion buscarAdministracion(int codigoAdministracion) {
        Administracion resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                        registro.getString("direccion"),
                        registro.getString("telefono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<TipoClientes> getTipoClientes() {
        ArrayList<TipoClientes> lista = new ArrayList<TipoClientes>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new TipoClientes(resultado.getInt("codigoTipoCliente"),
                        resultado.getString("descripcion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoClientes = FXCollections.observableArrayList(lista);
    }

    public TipoClientes buscarTipoClientes(int codigoTipoCliente) {
        TipoClientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoClientes(?)}");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new TipoClientes(registro.getInt("codigoTipoCliente"),
                        registro.getString("descripcion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public void seleccionarElemento() {
        if (tblClientes.getSelectionModel().getSelectedItem() != null) {
            txtCodigoCliente.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNombreCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
            txtApellidoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
            txtTelefonoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
            txtDireccionCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
            txtCorreoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoClientes(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
        }
    }

    public void nuevo() {
        switch (tipoDeOperacion) {
            case NINGUNO: {
                activarControl();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/osmarortiz/images/save-icon.png"));
                imgEliminar.setImage(new Image("/org/osmarortiz/images/remove-icon.png"));
                tipoDeOperacion = operaciones.GUARDAR;
            }
            break;

            case GUARDAR: {
                guardar();
                desactivarControl();
                limpiarControl();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/osmarortiz/images/add-icon.png"));
                imgEliminar.setImage(new Image("/org/osmarortiz/images/delete-icon.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            }
            break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setNombreCliente(txtNombreCliente.getText());
        registro.setApellidoCliente(txtApellidoCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());
        registro.setCodigoLocal(((Locales) cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        registro.setCodigoAdministracion(((Administracion) cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoClientes) cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreCliente());
            procedimiento.setString(2, registro.getApellidoCliente());
            procedimiento.setString(3, registro.getTelefonoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getCorreoCliente());
            procedimiento.setInt(6, registro.getCodigoLocal());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoTipoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void eliminar() {
        switch (tipoDeOperacion) {
            case GUARDAR: {
                desactivarControl();
                limpiarControl();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("org/osmarortiz/images/add-icon.png"));
                imgEliminar.setImage(new Image("org/osmarortiz/images/delete-icon.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
            }
            break;
            
            default: {
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
                            limpiarControl();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            }
            break;
        }
    }
    
    public void editar() {
        switch (tipoDeOperacion) {
            case NINGUNO: {
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
                    cmbCodigoLocal.setDisable(true);
                    cmbCodigoAdministracion.setDisable(true);
                    cmbCodigoTipoCliente.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            }
            break;

            case ACTUALIZAR: {
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("org/osmarortiz/images/pencil-icon.png"));
                imgReporte.setImage(new Image("org/osmarortiz/images/paper-icon.png"));
                limpiarControl();
                desactivarControl();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
            }
            break;
        }
    }
    
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNombreCliente.getText());
            registro.setApellidoCliente(txtApellidoCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reporte() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR: {
                desactivarControl();
                limpiarControl();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("org/osmarortiz/images/pencil-icon.png"));
                imgReporte.setImage(new Image("org/osmarortiz/images/paper-icon.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
            }
            break;
            
            default: {
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    Map parametros = new HashMap();
                    int codigoCliente = ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
                    parametros.put("codigoCliente", codigoCliente);
                    GenerarReporte.mostrarReporte("ReporteCliente.jasper", "Reporte cliente", parametros);
                }
            }
        }
    }

    public void desactivarControl() {
        txtCodigoCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }

    public void activarControl() {
        txtCodigoCliente.setEditable(false);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoTipoCliente.setDisable(false);
    }

    public void limpiarControl() {
        txtCodigoCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtCorreoCliente.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection();
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
    
    public void ventanaTipoClientes() {
        escenarioPrincipal.ventanaTipoClientes();
    }
}
