/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author leopa
 */
public final class Curso {
    private int id;
    private String nombre;
    private int grupo;
    private String ciclo;
    private String colegio;
    private int idProfesor;

   
	
    public Curso(int id, String nombre, int grupo, int idProfesor, String ciclo, String colegio) {
	setId(id);
	setNombre(nombre);
	setGrupo(grupo);
	setColegio(colegio);
	setCiclo(ciclo);
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

    public int getGrupo() {
	return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getCiclo() {
	return ciclo;
    }

    public void setCiclo(String ciclo) {
	this.ciclo = ciclo;
    }

    public String getColegio() {
	return colegio;
    }

    public void setColegio(String colegio) {
	this.colegio = colegio;
    }
    
     public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
	
    @Override
    public String toString() {
	return "Curso [id=" + id + ", nombre=" + nombre + "id profesor = "+ idProfesor+  ", grupo=" + grupo + ", ciclo=" + ciclo + ", colegio="
			+ colegio + "]";
    }
}
