package application.controlador;
import application.modelo.Alumno;
import application.modelo.Grupo;
import application.modelo.Profesor;
import application.dao.ProfesorDao;

import application.dao.AlumnoDao;
import application.dao.GruposDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DarAltaAlumnoController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnInscribir;

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbIdCarrera;

    @FXML
    private Label lbNombre;

    @FXML
    private TextField tfBusqueda;
    
    private Grupo grupo;
    
    private Alumno alumno;
    
    
    public void setGrupo(Grupo grupo) {
    	this.grupo = grupo;
    }
    

    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	Double [] bounds = {650.0, 500.0};
		FXMLLoader loader =  VentanaController.crearVentana("Grupo", bounds, "/application/vistas/SceneGrupoProfesor.fxml");
		GrupoProfesorController controlador = loader.getController();
		controlador.setGrupo(grupo);
		controlador.setProfesor(new ProfesorDao().buscar(new Profesor(grupo.getIdProfesor())));
		controlador.loadVentana();
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();

    }

    @FXML
    void btnInscribir_OnClick(ActionEvent event) {
    	Alert alerta = null;
    	if(new AlumnoDao().buscarAlumnoEnGrupo(alumno, grupo) == null) {
    		if(new GruposDao().obtenerGrupos(alumno).size() < 7) {
	    		new GruposDao().inscribirGrupo(grupo, alumno);
	    		alerta = new Alert(AlertType.INFORMATION, "Alumno: " + alumno.getMatricula() + " inscrito con exito", ButtonType.OK);
				tfBusqueda.setText("");
    		}else
    			alerta = new Alert(AlertType.ERROR, "Alumno: " + alumno.getMatricula() + " alcanzó el limite de materias!", ButtonType.OK);
    		
    	}else
    		alerta = new Alert(AlertType.ERROR, "Alumno: " + alumno.getMatricula() + " ya se encuentra inscrito", ButtonType.OK);
		
    	
    	alerta.show();
    }
    
    public void loadVentana() {
    	btnInscribir.setDisable(true);
    	
    	tfBusqueda.textProperty().addListener((observable, viejoValor, nuevoValor) ->{
    		if(nuevoValor.length() == 11) {
    			if(Alumno.esMatriculaValida(nuevoValor)) {
    				alumno = new AlumnoDao().buscar(new Alumno(nuevoValor));
    				if(alumno != null) {
    					lbNombre.setText("Nombre: " + alumno.getNombre());
    					lbApellidoP.setText("Apellido paterno: " + alumno.getApellidoPaterno());
    					lbApellidoM.setText("Apellido materno: " + alumno.getApellidoMaterno());
    					lbIdCarrera.setText("Id carrera: " + alumno.getIdCarrera());
    					
    				}
    				
    				
    			}
    		}else {
    			lbNombre.setText("Nombre:");
				lbApellidoP.setText("Apellido paterno:");
				lbApellidoM.setText("Apellido materno:");
				lbIdCarrera.setText("Id carrera:");
    		}
    		btnInscribir.setDisable(alumno == null || nuevoValor.length() != 11);
    	});
    }

}
