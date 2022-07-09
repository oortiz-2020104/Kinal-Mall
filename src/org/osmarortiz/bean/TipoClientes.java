package org.osmarortiz.bean;

public class TipoClientes {

    //Atributos
    private int codigoTipoCliente;
    private String descripcion;

    //Constructores
    public TipoClientes() {
    }

    public TipoClientes(int codigoTipoCliente, String descripcion) {
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }

    //Métodos de acceso
    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoTipoCliente() + " | " + getDescripcion();
    }

    
    
}
