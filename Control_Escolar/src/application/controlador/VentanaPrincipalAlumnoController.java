package application.controlador;

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
	    alert.setHeaderText("Eliminar grupo");
	    alert.setContentText("¿Estás seguro que deseas darte de baja de el grupo seleccionado: "
	        					+ "Id: "+ opcion.getId() + " Grupo: " + opcion.getGrupo() + "?");
	        
	      // para obtener el resultado
	    Optional<ButtonType> result = alert.showAndWait();
	    Alert confirmation;  
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        new GruposDao().darBajaGrupo(opcion, alumno);
	        confirmation = new Alert(AlertType.CONFIRMATION, "Dado de baja del grupo Id: " + opcion.getId() + " grupo: " + opcion.getGrupo(), ButtonType.OK);
			confirmation.show();
			lvGrupos.getItems().remove(opcion);
	        	
	    } else {
	       confirmation = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
	       confirmation.show();
	    }
    	
    }

    @FXML
    void btnInscribir_OnClick(ActionEvent event) {
    	Double[] bounds = {650.0, 450.0};
		FXMLLoader loader = VentanaController.crearVentana("Inscribir curso", bounds, "/application/vistas/SceneInscribirGrupo.fxml");
		InscribirGrupoController controlador = loader.getController();
		controlador.setAlumno(alumno);
		controlador.inicializar();
		
		Stage stage = (Stage) btnInscribir.getScene().getWindow();
		stage.close();
    	
    }
    

    @FXML
    void btnSalir_OnClick(ActionEvent event) {
    	VentanaController.crearVentanaLogin(btnEliminar);
    }
    
    public void loadVentana() {
    	
    	btnInscribir.setDisable(alumno.getNumeroGrupos() >= 7);
    	btnEliminar.setDisable(alumno.getNumeroGrupos() <= 0);
    	
    	lbMatricula.setText("Matricula: " + alumno.getMatricula());
    	lbNombre.setText("Nombre: " + alumno.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + alumno.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + alumno.getApellidoMaterno());
    	lbUsuario.setText("Usuario: " + alumno.getUsuario().getCorreo());
    	
    	
    	lvGrupos.getItems().addAll(alumno.getGruposInscritos());
    
    	lvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor) -> {
    		opcion = (Grupo) nuevoValor;
    		//System.out.println(opcion);
        });
    	
    	lvGrupos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
            	Double [] bounds = {650.0, 500.0};
				FXMLLoader loader =  VentanaController.crearVentana("Grupo", bounds, "/application/vistas/SceneGrupoAlumno.fxml");
				GrupoAlumnoController controlador = loader.getController();
				controlador.setGrupo(opcion);
				controlador.setAlumno(alumno);
				controlador.loadVentana();
				Stage stage = (Stage) btnSalir.getScene().getWindow();
				stage.close();
            }
        });

    }

    
}
