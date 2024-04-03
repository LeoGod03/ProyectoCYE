/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaconexion;

import dao.AdministradorDao;
import dao.AlumnoDao;
import dao.Conexion;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Usuario;

/**
 *
 * @author leopa
 */
public class PruebaConexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        //AlumnoDao dao = new AlumnoDao();
        //Double[] porcentajes = {0.0,0.0,0.0};
        Administrador admin = new Administrador(1,"Wen","Medina","Chavez",21,new Usuario("wen","12345677",12,"Administrador"));
        new AdministradorDao().insertar(admin);
       //Alumno alumno = new Alumno("20-003-0695", "Leonardo", "Rodriguez", "Rodriguez",23,1,new Usuario("Optimus","wen",2,"Alumno"),porcentajes);
       //dao.eliminar(alumno);
        //System.out.println(dao.buscar(alumno));
    }
    
}
