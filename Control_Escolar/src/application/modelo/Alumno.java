/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author leopa
 */
public final class Alumno extends Persona{
    private String matricula;
    private int idCarrera;
    private Usuario usuario;
    private ArrayList<Grupo> gruposInscritos;
    private Double[] porcentajes;
    private int numeroGrupos;
	
    public Alumno(String matricula,String nombre, String apellidoP, String apellidoM,
        int idCarrera,int numeroCursos,Usuario usuario) {
        super(nombre, apellidoP, apellidoM);
        setMatricula(matricula);
        setIdCarrera(idCarrera);
        setUsuario(usuario);
        setNumeroGrupos(numeroCursos);
    }
    
    public Alumno(String matricula){
        super("","","");
        setMatricula(matricula);
        setIdCarrera(0);
        setUsuario(null);
        setNumeroGrupos(0);
    }
   
    
    public Alumno(String matricula, String correo){
        super("","","");
        this.matricula = matricula;
        usuario = new Usuario(correo,"",0,"");
        
    }
	
    public String getMatricula() {
    	return matricula;
    }
    
    public void setGruposInscritos(ArrayList<Grupo> cursosInscritos) {
        this.gruposInscritos = cursosInscritos;
    }

    public ArrayList<Grupo> getGruposInscritos() {
        return gruposInscritos;
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
    
    public int getNumeroGrupos(){
        return numeroGrupos;
    }
    
    public void setNumeroGrupos(int numeroCursos){
        this.numeroGrupos = numeroCursos;
    }
    
    
    public static boolean esMatriculaValida(String matricula) {
    	
        String pattern = "\\d{2}-\\d{3}-\\d{4}"; // El patrón que estás buscando

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(matricula);
        return m.matches();
   
    }
    
	@Override
    public String toString() {
		return "Alumno [matricula=" + matricula + ", nombre=" + super.getNombre() + ", apellidoP=" + super.getApellidoPaterno() + ", apellidoM="
				+ super.getApellidoMaterno() + ", carrera=" + idCarrera + "Usuario = "+ usuario;
    }

	
}
