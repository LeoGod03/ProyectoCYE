package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

import application.dao.AlumnoDao;
import application.modelo.Alumno;
import application.modelo.EnumBusquedas;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
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
		      */
		      ArrayList<Alumno> lista = new AlumnoDao().buscar(new Alumno("20","","","",0,0,0,null), EnumBusquedas.BUSQUEDA.NOMBREAPELLIDO);
		      for(Alumno alumnoI: lista){
		          System.out.println(alumnoI);
		      }

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}