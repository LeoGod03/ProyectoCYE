package application.modelo;

import java.util.ArrayList;

public class Grupo {
	
	private int id;
	private int grupo;
	private int idProfesor;
	private int alumnosInscritos;
	private ArrayList<Alumno> alumnos;
	
	
	public Grupo(int id, int grupo, int idProfesor, int alumnosInscritos) {
		setId(id);
		setGrupo(grupo);
		setIdProfesor(idProfesor);
		setAlumnosInscritos(alumnosInscritos);
	}
	
	public Grupo(int id, int grupo) {
		setId(id);
		setGrupo(grupo);
		idProfesor = -1;
		alumnosInscritos = 0;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	public int getIdProfesor() {
		return idProfesor;
	}
	public void setIdProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
	}
	public int getAlumnosInscritos() {
		return alumnosInscritos;
	}
	public void setAlumnosInscritos(int alumnosInscritos) {
		this.alumnosInscritos = alumnosInscritos;
	}
	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
}
