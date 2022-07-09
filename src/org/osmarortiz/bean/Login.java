package org.osmarortiz.bean;

public class Login {
    
    //Atributos
    private String usuarioMaster;
    private String passwordLogin;
    
    //Constructores
    public Login() {
    }

    public Login(String usuarioMaster, String passwordLogin) {
        this.usuarioMaster = usuarioMaster;
        this.passwordLogin = passwordLogin;
    }
    
    //Métodos de acceso
    public String getUsuarioMaster() {
        return usuarioMaster;
    }

    public void setUsuarioMaster(String usuarioMaster) {
        this.usuarioMaster = usuarioMaster;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }
    
}
