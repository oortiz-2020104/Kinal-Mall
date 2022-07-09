package org.osmarortiz.bean;

public class Clientes {

    //Atributos
    private int codigoCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String correoCliente;
    private int codigoLocal;
    private int codigoAdministracion;
    private int codigoTipoCliente;

    //Constructores
    public Clientes() {
    }

    public Clientes(int codigoCliente, String nombreCliente, String apellidoCliente, String telefonoCliente, String direccionCliente, String correoCliente, int codigoLocal, int codigoAdministracion, int codigoTipoCliente) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.correoCliente = correoCliente;
        this.codigoLocal = codigoLocal;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }

    //Métodos de acceso
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    @Override
    public String toString() {
        return getCodigoCliente() + " | " + getNombreCliente() + " | " + getApellidoCliente() + " | " + getTelefonoCliente() + " | " + getDireccionCliente() + " | " + getCorreoCliente() + " | " + getCodigoLocal() + " | " + getCodigoAdministracion() + " | " + getCodigoCliente();
    }
}
