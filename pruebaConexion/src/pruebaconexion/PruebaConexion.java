/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaconexion;

import dao.AdministradorDao;
import dao.AlumnoDao;
import dao.Conexion;
import dao.CursosInscritosDao;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Curso;
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
        //Administrador admin = new Administrador(1,"Wendolyn","Medina","Chavez",21,new Usuario("wen","12345677",12,"Administrador"));
        Curso curso = new Curso(1,"Algebra lineal",302,12,"CCT","BASICO");
        //new AdministradorDao().insertar(admin);
        //new AdministradorDao().actualizar(admin, admin);
        //new CursosInscritosDao().insertar(curso);
        //Alumno alumno = new Alumno("20-003-0695", "Leonardo", "Rodriguez", "Rodriguez",23,1,new Usuario("Optimus","wen",2,"Alumno"));
       //dao.insertar(alumno);
       System.out.println(new CursosInscritosDao().buscar(curso));
    }
    
}
