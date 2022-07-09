package org.osmarortiz.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.osmarortiz.bean.Proveedores;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class ProveedoresController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<Administracion> listaAdministracion;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TextField txtNITProveedor;
    @FXML
    private TextField txtServicioPrestado;
    @FXML
    private TextField txtTelefonoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtSaldoContra;
    @FXML
    private TextField txtSaldoFavor;

    @FXML
    private ComboBox cmbCodigoAdministracion;

    @FXML
    private TableView tblProveedores;
    
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colNITProveedor;
    @FXML
    private TableColumn colServicioPrestado;
    @FXML
    private TableColumn colTelefonoProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colSaldoFavor;
    @FXML
    private TableColumn colSaldoContra;
    @FXML
    private TableColumn colCodigoAdministracion;

    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("servicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("telefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedores, Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedores, Double>("saldoContra"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
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

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("servicioPrestado"),
                        resultado.getString("telefonoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getDouble("saldoFavor"),
                        resultado.getDouble("saldoContra"),
                        resultado.getInt("codigoAdministracion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento() {
        if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
            txtCodigoProveedor.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelefonoProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccionProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
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
        Proveedores registro = new Proveedores();
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion) cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getServicioPrestado());
            procedimiento.setString(3, registro.getTelefonoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setDouble(5, registro.getSaldoFavor());
            procedimiento.setDouble(6, registro.getSaldoContra());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaProveedores.add(registro);
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedIndex());
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
                    cmbCodigoAdministracion.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITProveedor(txtNITProveedor.getText());
            registro.setServicioPrestado(txtServicioPrestado.getText());
            registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getServicioPrestado());
            procedimiento.setString(4, registro.getTelefonoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setDouble(6, registro.getSaldoFavor());
            procedimiento.setDouble(7, registro.getSaldoContra());
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
        }
    }
    
    public void desactivarControl() {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
    }

    public void activarControl() {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
    }

    public void limpiarControl() {
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
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
    
    public void ventanaCuentasPorPagar() {
        escenarioPrincipal.ventanaCuentasPorPagar();
    }

}
