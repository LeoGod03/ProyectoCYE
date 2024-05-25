package application.controlador;

import java.io.IOException;
import java.util.Optional;
import application.modelo.Grupo;
import application.dao.GruposDao;
import application.modelo.Alumno;
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

public class VentanaPrincipalAlumnoController {

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnInscribir;
    
    @FXML
    private Button btnSalir;
    

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbMatricula;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbUsuario;

    @FXML
    private ListView<Grupo> lvGrupos;
    
    private Alumno alumno;
    
    private Grupo opcion;
    
    public void setAlumno(Alumno alumno) {
    	this.alumno = alumno;
    }
    
    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	
    	// creamos la alerta
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setWidth(350.0);
	    alert.setHeight(250.0);
	    alert.setTitle("Confirmación");
	    alert.setHeaderText("Eliminar elemento");
	    alert.setContentText("¿Estás seguro que deseas eliminar el elemento seleccionado: "
	        					+ "Id: "+ opcion.getId() + " Grupo: " + opcion.getGrupo() + "?");
	        
	      // para obtener el resultado
	    Optional<ButtonType> result = alert.showAndWait();
	        
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        new GruposDao().darBajaGrupo(opcion, alumno);
	        Alert confirmation = new Alert(AlertType.CONFIRMATION, "Dado de baja del grupo Id: " + opcion.getId() + " grupo: " + opcion.getGrupo(), ButtonType.OK);
			confirmation.show();
			lvGrupos.getItems().remove(opcion);
	        	
	    } else {
	       Alert confirmation = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
			confirmation.show();
	    }
    	
    }

    @FXML
    void btnInscribir_OnClick(ActionEvent event) {
    	try {
    		Double[] bounds = {650.0, 450.0};
    		FXMLLoader loader = VentanaController.crearVentana("Inscribir curso", bounds, "/application/vistas/SceneInscribirGrupo.fxml");
    		InscribirGrupoController controlador = loader.getController();
    		controlador.setAlumno(alumno);
    		controlador.inicializar();
    		
    		Stage stage = (Stage) btnInscribir.getScene().getWindow();
    		stage.close();
    	}catch(IOException e) {
    		
    		e.printStackTrace();
    	}
    	
    }
    

    @FXML
    void btnSalir_OnClick(ActionEvent event) {
    	try {
    		Double [] bounds = {500.0, 400.0};
    		VentanaController.crearVentana("Login", bounds, "/application/vistas/SceneLogin.fxml");
    		Stage stage = (Stage) btnSalir.getScene().getWindow();
    		stage.close();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void loadVentana() {
    	
    	System.out.println(alumno.getNumeroGrupos());
    	
    	btnInscribir.setDisable(alumno.getNumeroGrupos() > 7);
    	btnEliminar.setDisable(alumno.getNumeroGrupos() <= 0);
    	
    	lbMatricula.setText("Matricula: " + alumno.getMatricula());
    	lbNombre.setText("Nombre: " + alumno.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + alumno.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + alumno.getApellidoMaterno());
    	lbUsuario.setText("Usuario: " + alumno.getUsuario().getCorreo());
    	
    	for(Grupo grupo: alumno.getGruposInscritos())
    		lvGrupos.getItems().add(grupo);
    
    	lvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		opcion = (Grupo) newValue;
    		//System.out.println(opcion);
        });
    	
    	lvGrupos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
            	try {
            		Double [] bounds = {750.0, 400.0};
            		FXMLLoader loader =  VentanaController.crearVentana("Grupo", bounds, "/application/vistas/SceneGrupoAlumno.fxml");
            		GrupoAlumnoController controlador = loader.getController();
            		controlador.setGrupo(opcion);
            		controlador.inicialiar();
            		Stage stage = (Stage) btnSalir.getScene().getWindow();
            		stage.close();
            	}catch(IOException e) {
            		e.printStackTrace();
            	}
            }
        });

    }

    
}
