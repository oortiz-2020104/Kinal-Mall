package org.osmarortiz.bean;

public class Cargos {
    
    //Atributos
    private int codigoCargo;
    private String nombreCargo;
    
    //Constructores    
    public Cargos() {
    }

    public Cargos(int codigoCargo, String nombreCargo) {
        this.codigoCargo = codigoCargo;
        this.nombreCargo = nombreCargo;
    }
    
    //Métodos de acceso
    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    @Override
    public String toString() {
        return getCodigoCargo() + " | " + getNombreCargo();
    }
}
