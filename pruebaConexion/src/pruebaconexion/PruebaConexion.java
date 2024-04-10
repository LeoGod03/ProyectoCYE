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
import java.util.ArrayList;
import modelo.Administrador;
import modelo.Alumno;
import modelo.Curso;
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
      /*
      ArrayList<Curso> cursos = new CursosInscritosDao().obtenerCursosInscritos(new Profesor(12));
      for(Curso curso: cursos){
          System.out.println(curso);
      }*/  
    }
    
}
