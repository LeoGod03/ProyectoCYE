/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

import java.util.ArrayList;

/**
 *
 * @author leopa
 */
public final class Alumno extends Persona{
    private String matricula;
    private int idCarrera;
    private Usuario usuario;
    private ArrayList<Curso> cursosInscritos;
    private Double[] porcentajes;
    private int numeroCursos;
	
    public Alumno(String matricula,String nombre, String apellidoP, String apellidoM,int edad,
        int idCarrera,int numeroCursos,Usuario usuario) {
        super(nombre, apellidoP, apellidoM, edad);
        setMatricula(matricula);
	setIdCarrera(idCarrera);
        setUsuario(usuario);
        setNumeroCursos(numeroCursos);
    }
    
    public Alumno(String matricula){
        super("","","",0);
        setMatricula(matricula);
        setIdCarrera(0);
        setUsuario(null);
        setNumeroCursos(0);
    }
    public void setCursosInscritos(ArrayList<Curso> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }

    public ArrayList<Curso> getCursosInscritos() {
        return cursosInscritos;
    }
    
    public Alumno(String matricula, String correo){
        super("","","",0);
        this.matricula = matricula;
        usuario = new Usuario(correo,"",0,"");
        
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
    
    public int getNumeroCursos(){
        return numeroCursos;
    }
    
    public void setNumeroCursos(int numeroCursos){
        this.numeroCursos = numeroCursos;
    }
	@Override
    public String toString() {
	return "Alumno [matricula=" + matricula + ", nombre=" + super.getNombre() + ", apellidoP=" + super.getApellidoPaterno() + ", apellidoM="
				+ super.getApellidoMaterno() + ", carrera=" + idCarrera + "Usuario = "+ usuario;
    }

	
}
