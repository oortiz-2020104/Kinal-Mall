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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.osmarortiz.bean.Locales;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.system.Principal;

public class LocalesController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

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
    private TextField txtCodigoLocal;
    @FXML
    private TextField txtSaldoFavor;
    @FXML
    private TextField txtSaldoContra;
    @FXML
    private TextField txtMesesPendientes;
    @FXML
    private TextField txtDisponibilidad;
    @FXML
    private TextField txtValorLocal;
    @FXML
    private TextField txtValorAdministracion;

    @FXML
    private TableView tblLocales;

    @FXML
    private TableColumn colCodigoLocal;
    @FXML
    private TableColumn colSaldoFavor;
    @FXML
    private TableColumn colSaldoContra;
    @FXML
    private TableColumn colMesesPendientes;
    @FXML
    private TableColumn colDisponibilidad;
    @FXML
    private TableColumn colValorLocal;
    @FXML
    private TableColumn colValorAdministracion;

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
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("codigoLocal"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Locales, Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Locales, Double>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales, Boolean>("disponibilidad"));
        colValorLocal.setCellValueFactory(new PropertyValueFactory<Locales, Double>("valorLocal"));
        colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Locales, Double>("valorAdministracion"));
    }
    
    public void cargarDatos() {
        tblLocales.setItems(getLocales());
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
    
    public void seleccionarElemento() {
        if (tblLocales.getSelectionModel().getSelectedItem() != null) {
            txtCodigoLocal.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            txtSaldoFavor.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
            txtMesesPendientes.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
            txtDisponibilidad.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
            txtValorLocal.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
            txtValorAdministracion.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));   
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
        Locales registro = new Locales();
//        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
//        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
//        registro.setMesesPendientes(Integer.parseInt(txtMesesPendientes.getText()));
        registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
        registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
        registro.setValorAdministracion(Double.parseDouble(txtValorAdministracion.getText()));
        
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarLocal(?, ?, ?)}");
//            procedimiento.setDouble(1, registro.getSaldoFavor());
//            procedimiento.setDouble(2, registro.getSaldoContra());
//            procedimiento.setInt(3, registro.getMesesPendientes());
            procedimiento.setBoolean(1, registro.isDisponibilidad());
            procedimiento.setDouble(2, registro.getValorLocal());
            procedimiento.setDouble(3, registro.getValorAdministracion());
            procedimiento.execute();
            listaLocales.add(registro);

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
                if (tblLocales.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarLocales(?)}");
                            procedimiento.setInt(1, ((Locales) tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
                            procedimiento.execute();
                            listaLocales.remove(tblLocales.getSelectionModel().getSelectedIndex());
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
                if (tblLocales.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarLocales(?, ?, ?, ?)}");
            Locales registro = (Locales) tblLocales.getSelectionModel().getSelectedItem();
//            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
//            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
//            registro.setMesesPendientes(Integer.parseInt(txtMesesPendientes.getText()));
            registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
            registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
            registro.setValorAdministracion(Double.parseDouble(txtValorAdministracion.getText()));
            procedimiento.setInt(1, registro.getCodigoLocal());
//            procedimiento.setDouble(2, registro.getSaldoFavor());
//            procedimiento.setDouble(3, registro.getSaldoContra());
//            procedimiento.setInt(4, registro.getMesesPendientes());
            procedimiento.setBoolean(2, registro.isDisponibilidad());
            procedimiento.setDouble(3, registro.getValorLocal());
            procedimiento.setDouble(4, registro.getValorAdministracion());
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
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorLocal.setEditable(false);
        txtValorAdministracion.setEditable(false);
    }

    public void activarControl() {
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(true);
        txtValorLocal.setEditable(true);
        txtValorAdministracion.setEditable(true);
    }

    public void limpiarControl() {
        txtCodigoLocal.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtMesesPendientes.clear();
        txtDisponibilidad.clear();
        txtValorLocal.clear();
        txtValorAdministracion.clear();
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
    
    public void ventanaCuentasPorCobrar() {
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
}
