package org.osmarortiz.bean;

public class Usuarios {
    //Atributos
    private int codigoUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String nombreLogin;
    private String contrasena;
    
    //Constructores
    public Usuarios() {
    }

    public Usuarios(int codigoUsuario, String nombreUsuario, String apellidoUsuario, String nombreLogin, String contrasena) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.nombreLogin = nombreLogin;
        this.contrasena = contrasena;
    }
    
    //Métodos de acceso
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getNombreLogin() {
        return nombreLogin;
    }

    public void setNombreLogin(String nombreLogin) {
        this.nombreLogin = nombreLogin;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
