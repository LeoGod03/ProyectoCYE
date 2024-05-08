/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leopa
 */
public final class Profesor extends Persona {
    private int id;
    private String cubiculo;
    private Usuario usuario;
    public Profesor(int id) {
        super("","","");
        setId(id);
        setUsuario(null);
        setCubiculo("");
    }
    
     public Profesor(int id,String nombre, String apellidoPaterno, String apellidoMaterno, String cubiculo, Usuario usuario) {
        super(nombre, apellidoPaterno, apellidoMaterno);
        setId(id);
        setUsuario(usuario);
        setCubiculo(cubiculo);
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCubiculo() {
        return cubiculo;
    }

    public void setCubiculo(String cubiculo) {
        this.cubiculo = cubiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public static boolean esCubiculoValido(String cubiculo) {
    	 String pattern = "[A-Z]-\\d{3}";

         Pattern r = Pattern.compile(pattern);
         Matcher m = r.matcher(cubiculo);
         
         return m.matches();
    }
    
    
    @Override
    public String toString() {
        String cadena = "Profesor:" + super.getNombre()+  " " + super.getApellidoPaterno() + " " + super.getApellidoMaterno() + " Correo: "+ usuario.getCorreo();
        cadena+= " Cubiculo: " + cubiculo;
        return cadena;
    }
     
   
}
