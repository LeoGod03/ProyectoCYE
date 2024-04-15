/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaconexion;

import dao.AdministradorDao;
import dao.AlumnoDao;
import dao.Conexion;
//<<<<<<< Updated upstream
import dao.CursosInscritosDao;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Curso;
//=======
import dao.ProfesorDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Curso;
import modelo.Profesor;
//>>>>>>> Stashed changes
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
        //Curso curso = new Curso(1,"Algebra lineal",302,12,"CCT","BASICO");
        //CusoInscritoDao cur1 = new CursosInscritosDao().insertar(curso);
        //new AdministradorDao().insertar(admin);
        //new AdministradorDao().actualizar(admin, admin);
        //new CursosInscritosDao().insertar(curso);
        //Alumno alumno = new Alumno("20-003-0695", "Leonardo", "Rodriguez", "Rodriguez",23,1,new Usuario("Optimus","wen",2,"Alumno"));
       //dao.insertar(alumno);
       //System.out.println(new CursosInscritosDao().buscar(curso));
       // AlumnoDao dao = new AlumnoDao();
       // AdministradorDao dao1 = new AdministradorDao();
       // Double[] porcentajes = {0.0,0.0,0.0};
        //List<Curso> cursosImpartidos; 
        //Curso cursosImpartidos = new Curso(01,"Arquitectura", 302, 1,"2024","Ciencia y Tecnologia");
                    //cursosImpartidos = Arrays.asList(01,"Arquitectura", 302, 1,"2024","Ciencia y Tecnologia")
       // Administrador admin = new Administrador(1,"Wen","Medina","Chavez",21,new Usuario("wen","12345677",12,"Administrador"));
        
        
        ProfesorDao dao2 = new ProfesorDao();
        Profesor profesor = new Profesor(1,"Jorge","Walts","Martinez",70,"D-001",new Usuario("walts","09876",12,"Profesor"));
        //dao2.insertar(profesor);
        //System.out.println(dao2.buscar(profesor));
        //dao2.eliminar(profesor);
        //System.out.println("PRUEBA"+dao2.buscar(profesor));
        // new AdministradorDao().insertar(admin);
        //Alumno alumno = new Alumno("20-003-0695", "Leonardo", "Rodriguez", "Rodriguez",23,1,new Usuario("Optimus","wen",2,"Alumno"),porcentajes);
        //dao.eliminar(alumno);
        //System.out.println(dao1.buscar(admin));
        //dao1.eliminar(admin);
        // System.out.println(dao1.buscar(admin));

    }
    
}
