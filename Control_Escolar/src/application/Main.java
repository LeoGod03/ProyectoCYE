package application;
	

import javafx.application.Application;
import javafx.stage.Stage;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import application.modelo.Cifrar;
//import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vistas/SceneLogin.fxml"));
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/images/Icono.jpg")));
			primaryStage.show();
			System.out.println(Cifrar.cifrar("velasco210031234".toCharArray(), 22));
			
			/*Alumno alumno = new Alumno("20-003-0699");
			alumno.setUsuario(new Usuario("leonardo.rodriguez200030699@alumnos.uacm.edu.mx"));
			alumno.setNumeroGrupos(1);
			alumno.setGruposInscritos(new GruposDao().obtenerGrupos(alumno));
			new AlumnoDao().eliminar(alumno);
				*/	
		
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
		        
		      Alumno alumno = new Alumno("21-003-1605","Brian Miguel","Escalona","Maldonado",21,12,0,new Usuario("Escolanoa","1234",12,"estudiante"));
		      // creamos un nuevo alumno
		      new AlumnoDao().insertar(alumno);
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
		      
		      ArrayList<Alumno> lista = new AlumnoDao().buscar(new Alumno("20-003-1234","","","",0,0,0,null), EnumBusquedas.BUSQUEDA.MATRICULA);
		      for(Alumno alumnoI: lista){
		          System.out.println(alumnoI);
		      }*/
				
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
