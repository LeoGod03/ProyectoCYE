package application.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.dao.AlumnoDao;
import application.dao.GruposDao;
import application.dao.CursosDao;
import application.dao.ProfesorDao;
import application.modelo.Alumno;
import application.modelo.Curso;
import application.modelo.Grupo;
import application.modelo.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InscribirGrupoController {
	
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnInscribir;

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbCiclo;

    @FXML
    private Label lbColegio;

    @FXML
    private Label lbNombreCurso;

    @FXML
    private Label lbNombreProfesor;

    @FXML
    private ListView<Grupo> lvGrupos;
    
    private Grupo opcion;
    
    private Curso curso; 
    
    private Profesor profesor;
    
    private Alumno alumno;
    
    public void setAlumno(Alumno alumno) {
    	this.alumno = alumno;
    }
    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	Double[] bounds = {650.0, 450.0};
    	FXMLLoader loader;
		try {
			loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/SceneVentanaPrincipalAlumno.fxml");
			VentanaPrincipalAlumnoController controlador = loader.getController();
		    controlador.setAlumno(new AlumnoDao().buscar(alumno));
		    controlador.setAlumno(alumno);
		    controlador.loadVentana();
		    Stage stage = (Stage) btnCancelar.getScene().getWindow();
		    stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
    }

    @FXML
    void btnInscribir_OnClick(ActionEvent event) {
    	Alert mensaje;
    	if(alumno.getGruposInscritos().size() < 7) {
    		if(lvGrupos.getSelectionModel().getSelectedIndex() != -1) {
    			
    			// creamos la alerta
    	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	    	alert.setWidth(350.0);
    	    	alert.setHeight(250.0);
    	        alert.setTitle("Confirmación");
    	        alert.setHeaderText("Inscripcion a curso");
    	        alert.setContentText("¿Estás seguro que deseas inscribirte al grupo: "
    	        					+ "Id: "+ opcion.getId() + " Grupo: " + opcion.getGrupo() + "?");
    	        
    	        // para obtener el resultado
    	        Optional<ButtonType> result = alert.showAndWait();
    	        
    	        if (result.isPresent() && result.get() == ButtonType.OK) {
    	        	new GruposDao().inscribirGrupo(opcion, alumno);
    	        	alumno.getGruposInscritos().add(opcion);
    	        	lvGrupos.getItems().remove(opcion);
    	        	
    	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Inscrito con exito!", ButtonType.OK);
    				mensaje.show();
    	        	
    	        } else {
    	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
    				mensaje.show();
    	        }
    		}
    	}else {
    		mensaje = new Alert(AlertType.CONFIRMATION, "¡Alcansaste el maximo de 7 grupos inscritos!", ButtonType.OK);
			mensaje.show();
    	}
    }
    
    public void inicializar() {
    	ArrayList<Grupo> listaGrupos = new GruposDao().obtenerGruposDisponibles();
    	for(Grupo grupo : listaGrupos)
    		lvGrupos.getItems().add(grupo);
    	
    	for(Grupo grupo: new GruposDao().obtenerGrupos(alumno))
    		lvGrupos.getItems().remove(grupo);
    	
    	lvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		opcion = (Grupo) newValue;
    		curso = new CursosDao().buscar(new Curso(opcion.getId()));
    		profesor = new ProfesorDao().buscar(new Profesor(opcion.getIdProfesor()));
    		lbNombreCurso.setText("Curso: " + curso.getNombre());
    		lbColegio.setText("Colegio: " + curso.getColegio());
    		lbCiclo.setText("Ciclo: " + curso.getCiclo());
    		lbNombreProfesor.setText("Nombre: " + profesor.getNombre());
    		lbApellidoP.setText("Apellido paterno: " + profesor.getApellidoPaterno());
    		lbApellidoM.setText("Apellido materno: " + profesor.getApellidoMaterno());
        });
    }
}
