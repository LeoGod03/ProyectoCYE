/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author leopa
 */
public final class Usuario {

   
    private String correo;
    private String contrasenia;
    private int llave;
    private String rol;
    
    public Usuario(String correo, String contrasenia, int llave, String rol){
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.llave = llave;
        this.rol = rol;
    }
    
    public Usuario(String correo){
        this.correo = correo;
        this.contrasenia = "";
        this.llave = 0;
        this.rol = "";
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
    
     public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    
    public int getLlave() {
        return llave;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
     public void setLlave(int llave) {
        this.llave = llave;
    }

  
    
    public static void cifrado (String contrasenia, String llave){
        
    }

   
    @Override
    
    public String toString(){
        String cad = "[correo]: " + correo + " [contraseña]: " + contrasenia + " [llave]: "
                   + llave + " [rol]: " + rol;
        
        return cad;
    }
    
}
