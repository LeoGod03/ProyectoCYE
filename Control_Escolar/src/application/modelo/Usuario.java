/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

import java.util.Random;

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

     
    
    
    public static Usuario generaUsuario(Persona persona) {
    	// quitar los espacios
    	Usuario usuarioGenerado;
    	String[] partesNombre = persona.getNombre().split(" ");
    	String password = persona.getApellidoMaterno().toLowerCase();
    	String correo = partesNombre[0].toLowerCase();
    	correo += "." + persona.getApellidoPaterno().toLowerCase();
    	
    	
    	
    	if(persona instanceof Alumno) {
    		Alumno alumnoTemp = (Alumno)persona;
    		String[] partesMatricula = alumnoTemp.getMatricula().split("-");
    		for(String parte: partesMatricula) {
    			correo += parte;
    			password += parte;
    		}
    		correo +=  "@alumnos.uacm.edu.mx";
    		
    	}else {
    		Profesor profesorTemp = (Profesor)persona;
    		password += profesorTemp.getId();
    		correo += profesorTemp.getId() + "@profesor.uacm.edu.mx";
    	}
    	usuarioGenerado = new Usuario(correo, password, new Random().nextInt(100),(persona instanceof Alumno)? "alumno": "profesor");
    	return usuarioGenerado;
    }
    
   
    @Override
    
    public String toString(){
        String cad = "[correo]: " + correo + " [contraseña]: " + contrasenia + " [llave]: "
                   + llave + " [rol]: " + rol;
        
        return cad;
    }
    
}
