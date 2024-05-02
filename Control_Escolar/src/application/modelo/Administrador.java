/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

/**
 *
 * @author Acer Aspire
 */
public class Administrador extends Persona {
    private int id_administrador;
    private Usuario usuarioA;
    
    public Administrador(){
    
    }
    
     public Administrador(int id_administrador, String nombre, String apellidoPaterno, String apellidoMaterno, int edad, Usuario usuarioA) {
        super(nombre, apellidoPaterno, apellidoMaterno, edad);
        this.id_administrador = id_administrador;
        this.usuarioA = usuarioA;
        
    }

    public int getId_administrador() {
        return id_administrador;
    }

    public void setId_administrador(int id_administrador) {
        this.id_administrador = id_administrador;
    }

   

    public void setUsuario(Usuario usuarioA) {
        this.usuarioA = usuarioA;
    }

    public Usuario getUsuario() {
        return usuarioA;
    }

   @Override
    public String toString() {
        return "Administrador{" + "usuario=" + usuarioA + ", id_administrador=" + id_administrador + '}';
    }
    
	
    
}
