/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import modelo.Usuario;
import modelo.Curso;
import java.util.ArrayList;

/**
 *
 * @author leopa
 */
public final class Profesor {
    private int id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private ArrayList<Curso> cursosImpartidos;
    private Usuario usuario;

    
    public Profesor(int id, String nombre, String apellidoP, String apellidoM, ArrayList<Curso> cursosImpartidos, Usuario usuario) {
	setId(id);
	setNombre(nombre);
	setApellidoP(apellidoP);
	setApellidoM(apellidoM);
        setCursosImpartidos(cursosImpartidos);
        setUsuario(usuario);
    }
    
    public Profesor(int id){
        setId(id);
    }
    
    public int getId() {
	return id;
    }
	
    public void setId(int id) {
	this.id = id;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellidoP() {
	return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
	return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
	this.apellidoM = apellidoM;
    }
    
    public ArrayList<Curso> getCursosImpartidos() {
        return cursosImpartidos;
    }
    
    public void setCursosImpartidos(ArrayList<Curso> cursosImpartidos) {
        this.cursosImpartidos = cursosImpartidos;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
        
    @Override
    public String toString() {
	return "Profesor [id=" + id + ", nombre=" + nombre + ", apellidoP=" + apellidoP + ", apellidoM=" + apellidoM
				+ "]";
    }

}
