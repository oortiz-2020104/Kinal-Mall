package org.osmarortiz.bean;

public class Departamentos {
    
    //Atributos
    private int codigoDepartamento;
    private String nombreDepartamento;
    
    //Constructores
    public Departamentos() {
    }

    public Departamentos(int codigoDepartamento, String nombreDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
    }
    
    //Métodos de acceso
    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
    
    @Override
    public String toString() {
        return getCodigoDepartamento()+ " | " + getNombreDepartamento();
    }
}
