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
import org.osmarortiz.bean.Clientes;
import org.osmarortiz.bean.CuentasPorCobrar;
import org.osmarortiz.bean.Locales;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class CuentasPorCobrarController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList<CuentasPorCobrar> listaCuentasPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Locales> listaLocales;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoCuentaPorCobrar;
    @FXML
    private TextField txtNumeroFactura;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtMes;
    @FXML
    private TextField txtValorNetoPago;
    @FXML
    private TextField txtEstadoPago;

    @FXML
    private ComboBox cmbCodigoAdministracion;
    @FXML
    private ComboBox cmbCodigoCliente;
    @FXML
    private ComboBox cmbCodigoLocal;

    @FXML
    private TableView tblCuentasPorCobrar;

    @FXML
    private TableColumn colCodigoCuentaPorCobrar;
    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colMes;
    @FXML
    private TableColumn colValorNetoPago;
    @FXML
    private TableColumn colEstadoPago;
    @FXML
    private TableColumn colCodigoAdministracion;
    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colCodigoLocal;

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
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCuentaPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Double>("valorNetoPago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoAdministracion"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoLocal"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoLocal.setItems(getLocales());

    }

    public void cargarDatos() {
        tblCuentasPorCobrar.setItems(getCuentasPorCobrar());
    }

    public ObservableList<CuentasPorCobrar> getCuentasPorCobrar() {
        ArrayList<CuentasPorCobrar> lista = new ArrayList<CuentasPorCobrar>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentaPorCobrar"),
                        resultado.getString("numeroFactura"),
                        resultado.getString("anio"),
                        resultado.getString("mes"),
                        resultado.getDouble("valorNetoPago"),
                        resultado.getString("estadoPago"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoLocal")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCuentasPorCobrar = FXCollections.observableArrayList(lista);
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

    public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("correoCliente"),
                        registro.getInt("codigoLocal"),
                        registro.getInt("codigoAdministracion"),
                        registro.getInt("codigoTipoCliente"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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

    public void seleccionarElemento() {
        if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null) {
            txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
            txtNumeroFactura.setText(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtAnio.setText(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio());
            txtMes.setText(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes());
            txtValorNetoPago.setText(String.valueOf(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstadoPago.setText(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
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
        CuentasPorCobrar registro = new CuentasPorCobrar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(txtAnio.getText());
        registro.setMes(txtMes.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion) cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Locales) cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorCobrar(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getAnio());
            procedimiento.setString(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentasPorCobrar.add(registro);
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorCobrar(?)}");
                            procedimiento.setInt(1, ((CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                            procedimiento.execute();
                            listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
                    cmbCodigoAdministracion.setDisable(true);
                    cmbCodigoCliente.setDisable(true);
                    cmbCodigoLocal.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorCobrar(?, ?, ?, ?, ?, ?)}");
            CuentasPorCobrar registro = (CuentasPorCobrar) tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(txtAnio.getText());
            registro.setMes(txtMes.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setString(3, registro.getAnio());
            procedimiento.setString(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
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
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);

    }

    public void activarControl() {
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoLocal.setDisable(false);
    }

    public void limpiarControl() {
        txtCodigoCuentaPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoCliente.getSelectionModel().clearSelection();
        cmbCodigoLocal.getSelectionModel().clearSelection();
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

    public void ventanaLocales() {
        escenarioPrincipal.ventanaLocales();
    }
}
