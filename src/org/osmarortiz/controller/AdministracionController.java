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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.osmarortiz.bean.Administracion;
import org.osmarortiz.db.Conexion;
import org.osmarortiz.report.GenerarReporte;
import org.osmarortiz.system.Principal;

public class AdministracionController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;

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
    private TextField txtCodigoAdministracion;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;

    @FXML
    private TableView tblAdministracion;

    @FXML
    private TableColumn colCodigoAdministracion;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTelefono;

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
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion, String>("telefono"));
    }

    public void cargarDatos() {
        tblAdministracion.setItems(getAdministracion());

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

    public void seleccionarElemento() {
        if (tblAdministracion.getSelectionModel().getSelectedItem() != null) {
            txtCodigoAdministracion.setText(String.valueOf(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            txtDireccion.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
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
        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarAdministracion(?,?)}");
            procedimiento.setString(1, registro.getDireccion());
            procedimiento.setString(2, registro.getTelefono());
            procedimiento.execute();
            listaAdministracion.add(registro);

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
                if (tblAdministracion.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarAdministracion(?)}");
                            procedimiento.setInt(1, ((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
                            procedimiento.execute();
                            listaAdministracion.remove(tblAdministracion.getSelectionModel().getSelectedIndex());
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
                if (tblAdministracion.getSelectionModel().getSelectedItem() != null) {
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarAdministracion(?,?,?)}");
            Administracion registro = (Administracion) tblAdministracion.getSelectionModel().getSelectedItem();
            registro.setDireccion(txtDireccion.getText());
            registro.setTelefono(txtTelefono.getText());
            procedimiento.setInt(1, registro.getCodigoAdministracion());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
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
                if (tblAdministracion.getSelectionModel().getSelectedItem() != null) {
                    Map parametros = new HashMap();
                    int codigoAdministracion = ((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion();
                    parametros.put("codigoAdministracion", codigoAdministracion);
                    GenerarReporte.mostrarReporte("ReporteAdministracion.jasper", "Reporte administración", parametros);
                } else {
                    Map parametros = new HashMap();
                    parametros.put("codigoAdministracion", null);
                    GenerarReporte.mostrarReporte("ReporteAdministraciones.jasper", "Reporte datos administración", parametros);
                }
            }
        }
    }

    public void desactivarControl() {
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
    }

    public void activarControl() {
        txtCodigoAdministracion.setEditable(false);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }

    public void limpiarControl() {
        txtCodigoAdministracion.clear();
        txtDireccion.clear();
        txtTelefono.clear();
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

    public void ventanaEmpleados() {
        escenarioPrincipal.ventanaEmpleados();
    }

}
