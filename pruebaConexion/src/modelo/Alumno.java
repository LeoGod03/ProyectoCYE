/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Arrays;

/**
 *
 * @author leopa
 */
public class Alumno extends Persona{
    private String matricula;
    private int idCarrera;
    private Usuario usuario;
    private Double[] porcentajes;
	
    public Alumno(String matricula,String nombre, String apellidoP, String apellidoM,int edad,
        int idCarrera,Usuario usuario, Double[] porcentajes) {
        super(nombre, apellidoP, apellidoM, edad);
        this.matricula = matricula;
	this.idCarrera = idCarrera;
	this.porcentajes = porcentajes;
        this.usuario = usuario;
    }
	
    public String getMatricula() {
	return matricula;
    }

    public void setMatricula(String matricula) {
	this.matricula = matricula;
    }


    public int getIdCarrera() {
	return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Double[] getPorcentajes() {
	return porcentajes;
    }

    public void setPorcentajes(Double[] porcentajes) {
        this.porcentajes = porcentajes;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

	@Override
    public String toString() {
	return "Alumno [matricula=" + matricula + ", nombre=" + super.getNombre() + ", apellidoP=" + super.getApellidoPaterno() + ", apellidoM="
				+ super.getApellidoMaterno() + ", carrera=" + idCarrera + "Usuario = "+ usuario +", porcentajes="
				+ Arrays.toString(porcentajes) + "]";
    }

	
}