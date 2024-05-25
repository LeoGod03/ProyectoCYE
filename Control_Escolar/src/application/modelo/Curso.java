/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.modelo;

/**
 *
 * @author leopa
 */
public final class Curso {
    private int id;
    private String nombre;
    private String ciclo;
    private String colegio;

  
    public Curso(int id, String nombre, String ciclo, String colegio){
        setId(id);
        setNombre(nombre);
        setCiclo(ciclo);
        setColegio(colegio);
    }
    
    public Curso(int id, String nombre, int grupo, int idProfesor, String ciclo, String colegio){
        setId(id);
        setNombre(nombre);
        setCiclo(ciclo);
        setColegio(colegio);
    }
    
    public Curso(int id){
        setId(id);
        nombre = "";
        ciclo = "";
        colegio = "";
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
    
    @Override
    public String toString() {
	return "Curso [id=" + id + ", nombre=" + nombre + ", ciclo=" + ciclo + ", colegio="
			+ colegio + "]";
    }
}
