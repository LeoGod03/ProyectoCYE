/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;



/**
 *
 * @author leopa
 */
public final class Profesor extends Persona {
    private int id;
    private String cubiculo;
    private Usuario usuario;

    public Profesor() {
        super("","","",0);
    }
    
     public Profesor(int id,String nombre, String apellidoPaterno, String apellidoMaterno, int edad, String cubiculo, Usuario usuario) {
        super(nombre, apellidoPaterno, apellidoMaterno, edad);
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

    @Override
    public String toString() {
        return "Profesor{" + "id=" + id + ", cubiculo=" + cubiculo + ", usuario=" + usuario + '}';
    }
     
   
}
