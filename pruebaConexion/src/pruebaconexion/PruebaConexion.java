/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaconexion;

import dao.AdministradorDao;
import dao.AlumnoDao;
import dao.Conexion;
import dao.CursosDao;
import dao.CursosInscritosDao;
import dao.ProfesorDao;
import java.util.ArrayList;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Curso;
import modelo.EnumBusquedas;
import modelo.Profesor;
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
       
      // creamos dos cursos
      //Curso curso = new Curso(1,"Algebra lineal","CCT","BASICO");
      //Curso curso = new Curso(2,"Introducción a la programacion","CCT","BASICO");
        
      // creamos dos grupos para algebra lineal
      //Curso grupo = new Curso(1,302,12);
      //Curso grupo = new Curso(1,402,17);
      
      // creamos dos grupos para IP
      //Curso grupo = new Curso(2,201,12);
      //Curso grupo = new Curso(2,302,21);
      
      //new CursosInscritosDao().insertar(grupo);
      //System.out.println(new CursosInscritosDao().buscar(grupo));
      
      // pedimos y mostramos todos los cursos impartidos por el profesor 12
      
      /*ArrayList<Curso> cursos = new CursosInscritosDao().obtenerCursos(new Profesor(12));
      for(Curso curso: cursos){
          System.out.println(curso);
      }*/  
      
      // creamos un nuevo alumno
      Alumno alumno = new Alumno("20-003-0699", "Wendolyn", "Medina", "Chavez",22, 1, 0, new Usuario("Wen", "54321", 1, "Alumno"));
      //new AlumnoDao().insertar(alumno);
      //new AlumnoDao().darBajaCurso(new Curso(2,302,21), new AlumnoDao().buscar(alumno));
      
      //Profesor profesor = new Profesor(12, "Jorge Enrique", "Wals", "Selvas", 34, "D-004", new Usuario("WalsSelvas", "12345", 12, "Profesor"));
      
      //new ProfesorDao().insertar(profesor);
      // pedimos y mostramos todos los cursos impartidos por el profesor 20-003-0699
      
        //System.out.println(new ProfesorDao().buscar(new Profesor(12)));
      /*  
      ArrayList<Profesor> profesores = new ProfesorDao().buscar(new Profesor(12), EnumBusquedas.BUSQUEDA.IDPROFESOR);
      for(Profesor i: profesores)
          System.out.println(i);
      */
      /*  
      ArrayList<Curso> cursos = new CursosInscritosDao().obtenerCursos(new AlumnoDao().buscar(alumno));
      for(Curso curso: cursos){
          System.out.println(curso);
      }
      ArrayList<Alumno> lista = new AlumnoDao().buscar(new Alumno("20","","","",0,0,0,null), EnumBusquedas.BUSQUEDA.NOMBREAPELLIDO);
      for(Alumno alumnoI: lista){
          System.out.println(alumnoI);
      }*/
    }
    
}
