package org.osmarortiz.bean;

import java.util.Date;

public class Empleados {
    private int codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String correoEmpleado;
    private String telefonoEmpleado;
    private Date fechaContratacion;
    private double sueldoEmpleado;

    private int codigoDepartamento;
    private int codigoCargo;
    private int codigoHorario;
    private int codigoAdministracion;

    public Empleados(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, String correoEmpleado, String telefonoEmpleado, Date fechaContratacion, double sueldoEmpleado, int codigoDepartamento, int codigoCargo, int codigoHorario, int codigoAdministracion) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.sueldoEmpleado = sueldoEmpleado;
        this.codigoDepartamento = codigoDepartamento;
        this.codigoCargo = codigoCargo;
        this.codigoHorario = codigoHorario;
        this.codigoAdministracion = codigoAdministracion;
    }

    public Empleados() {
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(double sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
    }

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public int getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(int codigoHorario) {
        this.codigoHorario = codigoHorario;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }
}
