package org.osmarortiz.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.osmarortiz.bean.Administracion;
import org.osmarortiz.bean.CuentasPorPagar;
import org.osmarortiz.bean.Proveedores;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class CuentasPorPagarController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList<CuentasPorPagar> listaCuentasPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedores> listaProveedores;

    private DatePicker fechaLimite;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoCuentaPorPagar;
    @FXML
    private TextField txtNumeroFactura;
    @FXML
    private TextField txtEstadoPago;
    @FXML
    private TextField txtValorNetoPago;

    @FXML
    private ComboBox cmbCodigoProveedor;
    @FXML
    private ComboBox cmbCodigoAdministracion;

    @FXML
    private GridPane grpCuentasPorPagar;

    @FXML
    private TableView tblCuentasPorPagar;

    @FXML
    private TableColumn colCodigoCuentaPorPagar;
    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colFechaLimitePago;
    @FXML
    private TableColumn colEstadoPago;
    @FXML
    private TableColumn colValorNetoPago;
    @FXML
    private TableColumn colCodigoAdministracion;
    @FXML
    private TableColumn colCodigoProveedor;

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
        colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, Integer>("codigoCuentaPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, DatePicker>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, String>("valorNetoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, Integer>("codigoAdministracion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar, Integer>("codigoProveedor"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoProveedor.setItems(getProveedores());

        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(true);
        grpCuentasPorPagar.add(fechaLimite, 5, 0, 1, 1);
        fechaLimite.setAlignment(Pos.CENTER);
        fechaLimite.setMinSize(172, 37);
        fechaLimite.setMaxSize(172, 37);
        fechaLimite.setPrefSize(172, 37);
        fechaLimite.getStylesheets().add("org/osmarortiz/resources/DatePicker.css");
    }

    public void cargarDatos() {
        tblCuentasPorPagar.setItems(getCuentasPorPagar());
    }

    public ObservableList<CuentasPorPagar> getCuentasPorPagar() {
        ArrayList<CuentasPorPagar> lista = new ArrayList<CuentasPorPagar>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new CuentasPorPagar(resultado.getInt("codigoCuentaPorPagar"),
                        resultado.getString("numeroFactura"),
                        resultado.getDate("fechaLimitePago"),
                        resultado.getString("estadoPago"),
                        resultado.getDouble("valorNetoPago"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCuentasPorPagar = FXCollections.observableArrayList(lista);
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

    public Proveedores buscarProveedores(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("servicioPrestado"),
                        registro.getString("telefonoProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getDouble("saldoFavor"),
                        registro.getDouble("saldoContra"),
                        registro.getInt("codigoAdministracion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void seleccionarElemento() {
        if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null) {
            txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar()));
            txtNumeroFactura.setText(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtEstadoPago.setText(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            fechaLimite.selectedDateProperty().set(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            txtValorNetoPago.setText(String.valueOf(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedores(((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
        CuentasPorPagar registro = new CuentasPorPagar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion) cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorPagar(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentasPorPagar.add(registro);
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorPagar(?)}");
                            procedimiento.setInt(1, ((CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar());
                            procedimiento.execute();
                            listaCuentasPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
                    cmbCodigoAdministracion.setDisable(true);
                    cmbCodigoProveedor.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorPagar(?, ?, ?, ?, ?)}");
            CuentasPorPagar registro = (CuentasPorPagar) tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());
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
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtEstadoPago.setEditable(false);
        fechaLimite.setDisable(true);
        txtValorNetoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }

    public void activarControl() {
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoPago.setEditable(true);
        fechaLimite.setDisable(false);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
    }

    public void limpiarControl() {
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
        txtEstadoPago.clear();
        fechaLimite.setSelectedDate(null);
        txtValorNetoPago.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoProveedor.getSelectionModel().clearSelection();
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

    public void ventanaProveedores() {
        escenarioPrincipal.ventanaProveedores();
    }
}
