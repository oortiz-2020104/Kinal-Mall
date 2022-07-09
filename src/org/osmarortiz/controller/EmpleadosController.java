package org.osmarortiz.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import org.osmarortiz.bean.Cargos;
import org.osmarortiz.bean.Departamentos;
import org.osmarortiz.bean.Empleados;
import org.osmarortiz.bean.Horarios;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.report.GenerarReporte;
import org.osmarortiz.system.Principal;

public class EmpleadosController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperacion = operaciones.NINGUNO;

    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Departamentos> listaDepartamentos;
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Administracion> listaAdministracion;

    private DatePicker fechaContrato;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtNombreEmpleado;
    @FXML
    private TextField txtApellidoEmpleado;
    @FXML
    private TextField txtCorreoEmpleado;
    @FXML
    private TextField txtTelefonoEmpleado;
    @FXML
    private TextField txtSueldoEmpleado;

    @FXML
    private ComboBox cmbCodigoDepartamento;
    @FXML
    private ComboBox cmbCodigoCargo;
    @FXML
    private ComboBox cmbCodigoHorario;
    @FXML
    private ComboBox cmbCodigoAdministracion;

    @FXML
    private GridPane grpEmpleados;

    @FXML
    private TableView tblEmpleados;

    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colNombresEmpleado;
    @FXML
    private TableColumn colApellidoEmpleado;
    @FXML
    private TableColumn colCorreoEmpleado;
    @FXML
    private TableColumn colTelefonoEmpleado;
    @FXML
    private TableColumn colFechaContrato;
    @FXML
    private TableColumn colSueldoEmpleado;
    @FXML
    private TableColumn colCodigoDepartamento;
    @FXML
    private TableColumn colCodigoCargo;
    @FXML
    private TableColumn colCodigoHorario;
    @FXML
    private TableColumn colCodigoAdministracion;

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
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colCorreoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("correoEmpleado"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("telefonoEmpleado"));
        colFechaContrato.setCellValueFactory(new PropertyValueFactory<Empleados, DatePicker>("fechaContratacion"));
        colSueldoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldoEmpleado"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoDepartamento"));
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargo"));
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoHorario"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoAdministracion"));
        cmbCodigoDepartamento.setItems(getDepartamentos());
        cmbCodigoCargo.setItems(getCargos());
        cmbCodigoHorario.setItems(getHorarios());
        cmbCodigoAdministracion.setItems(getAdministracion());

        fechaContrato = new DatePicker(Locale.ENGLISH);
        fechaContrato.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContrato.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContrato.getCalendarView().setShowWeeks(true);
        grpEmpleados.add(fechaContrato, 5, 1, 1, 1);
        fechaContrato.setAlignment(Pos.CENTER);
        fechaContrato.setMinSize(172, 37);
        fechaContrato.setMaxSize(172, 37);
        fechaContrato.setPrefSize(172, 37);
        fechaContrato.getStylesheets().add("org/osmarortiz/resources/DatePicker.css");
    }

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getString("correoEmpleado"),
                        resultado.getString("telefonoEmpleado"),
                        resultado.getDate("fechaContratacion"),
                        resultado.getDouble("sueldoEmpleado"),
                        resultado.getInt("codigoDepartamento"),
                        resultado.getInt("codigoCargo"),
                        resultado.getInt("codigoHorario"),
                        resultado.getInt("codigoAdministracion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Departamentos> getDepartamentos() {
        ArrayList<Departamentos> lista = new ArrayList<Departamentos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Departamentos(resultado.getInt("codigoDepartamento"),
                        resultado.getString("nombreDepartamento")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }

    public Departamentos buscarDepartamentos(int codigoDepartamento) {
        Departamentos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamentos(?)}");
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Departamentos(registro.getInt("codigoDepartamento"),
                        registro.getString("nombreDepartamento"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<Cargos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Cargos(resultado.getInt("codigoCargo"),
                        resultado.getString("nombreCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargos = FXCollections.observableArrayList(lista);
    }

    public Cargos buscarCargos(int codigoCargos) {
        Cargos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargos(?)}");
            procedimiento.setInt(1, codigoCargos);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Cargos(registro.getInt("codigoCargo"),
                        registro.getString("nombreCargo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Horarios> getHorarios() {
        ArrayList<Horarios> lista = new ArrayList<Horarios>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Horarios(resultado.getInt("codigoHorario"),
                        resultado.getString("horarioEntrada"),
                        resultado.getString("horarioSalida"),
                        resultado.getBoolean("lunes"),
                        resultado.getBoolean("martes"),
                        resultado.getBoolean("miercoles"),
                        resultado.getBoolean("jueves"),
                        resultado.getBoolean("viernes")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaHorarios = FXCollections.observableArrayList(lista);
    }

    public Horarios buscarHorarios(int codigoHorario) {
        Horarios resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorarios(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Horarios(registro.getInt("codigoHorario"),
                        registro.getString("horarioEntrada"),
                        registro.getString("horarioSalida"),
                        registro.getBoolean("lunes"),
                        registro.getBoolean("martes"),
                        registro.getBoolean("miercoles"),
                        registro.getBoolean("jueves"),
                        registro.getBoolean("viernes"));
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

    public void seleccionarElemento() {
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
            txtCodigoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNombreEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
            txtApellidoEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
            txtCorreoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoEmpleado()));
            txtTelefonoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado()));
            fechaContrato.selectedDateProperty().set(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
            txtSueldoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldoEmpleado()));
            cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamentos(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            cmbCodigoCargo.getSelectionModel().select(buscarCargos(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodigoHorario.getSelectionModel().select(buscarHorarios(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
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
        Empleados registro = new Empleados();
        registro.setNombreEmpleado(txtNombreEmpleado.getText());
        registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
        registro.setCorreoEmpleado(txtCorreoEmpleado.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaContrato.getSelectedDate());
        registro.setSueldoEmpleado(Double.parseDouble(txtSueldoEmpleado.getText()));
        registro.setCodigoDepartamento(((Departamentos) cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargo(((Cargos) cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
        registro.setCodigoHorario(((Horarios) cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion) cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreEmpleado());
            procedimiento.setString(2, registro.getApellidoEmpleado());
            procedimiento.setString(3, registro.getCorreoEmpleado());
            procedimiento.setString(4, registro.getTelefonoEmpleado());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldoEmpleado());
            procedimiento.setInt(7, registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaEmpleados.add(registro);
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("org/osmarortiz/images/refresh-icon.png"));
                    imgReporte.setImage(new Image("org/osmarortiz/images/remove-icon.png"));
                    activarControl();
                    cmbCodigoDepartamento.setDisable(true);
                    cmbCodigoCargo.setDisable(true);
                    cmbCodigoHorario.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreEmpleado.getText());
            registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
            registro.setCorreoEmpleado(txtCorreoEmpleado.getText());
            registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
            registro.setFechaContratacion(fechaContrato.getSelectedDate());
            registro.setSueldoEmpleado(Double.parseDouble(txtSueldoEmpleado.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoEmpleado());
            procedimiento.setString(5, registro.getTelefonoEmpleado());
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldoEmpleado());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    Map parametros = new HashMap();
                    int codigoEmpleado = ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
                    parametros.put("codigoEmpleado", codigoEmpleado);
                    GenerarReporte.mostrarReporte("ReporteEmpleado.jasper", "Reporte empleado", parametros);
                }
            }
        }
    }

    public void desactivarControl() {
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtCorreoEmpleado.setEditable(true);
        txtTelefonoEmpleado.setEditable(false);
        fechaContrato.setDisable(true);
        txtSueldoEmpleado.setEditable(false);
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
    }

    public void activarControl() {
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtCorreoEmpleado.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        fechaContrato.setDisable(false);
        txtSueldoEmpleado.setEditable(true);
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
    }

    public void limpiarControl() {
        txtCodigoEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtCorreoEmpleado.clear();
        txtTelefonoEmpleado.clear();
        fechaContrato.setSelectedDate(null);
        txtSueldoEmpleado.setEditable(false);
        cmbCodigoDepartamento.getSelectionModel().clearSelection();
        cmbCodigoCargo.getSelectionModel().clearSelection();
        cmbCodigoHorario.getSelectionModel().clearSelection();
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
    
    public void ventanaCargos() {
        escenarioPrincipal.ventanaCargos();
    }
    
    public void ventanaHorarios() {
        escenarioPrincipal.ventanaHorarios();
    }
    
    public void ventanaDepartamentos() {
        escenarioPrincipal.ventanaDepartamentos();
    }
    
}
