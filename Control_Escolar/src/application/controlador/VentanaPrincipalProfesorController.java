package application.controlador;

import application.modelo.Grupo;
import application.modelo.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class VentanaPrincipalProfesorController {
	@FXML
    private Button btnSalir;

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbId;
    
    @FXML
    private Label lbCubiculo;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbUsuario;

    @FXML
    private ListView<Grupo> lvGrupos;
    
    private Profesor profesor;
    
    private Grupo opcion;
    
    public void setProfesor(Profesor profesor) {
    	this.profesor = profesor;
    }
    
    public void loadVentana() {
    	// llenamos los labels con los datos del profesor
    	lbId.setText("Id: " + profesor.getId());
    	lbNombre.setText("Nombre: " + profesor.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + profesor.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + profesor.getApellidoMaterno());
    	lbCubiculo.setText("Cubículo: " + profesor.getCubiculo());
    	lbUsuario.setText("Correo: " + profesor.getUsuario().getCorreo());
    	
    	
    	lvGrupos.getItems().addAll(profesor.getGruposImpartidos());
    	// listener para saber la selección del curso de la lista
    	lvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		opcion = (Grupo) newValue;
    		//System.out.println(opcion);
        });
    	// evento del mouse para que al dar doble clic sobre un curso habra una ventana del mismo
    	lvGrupos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
            	Double [] bounds = {650.0, 500.0};
				FXMLLoader loader =  VentanaController.crearVentana("Grupo", bounds, "/application/vistas/SceneGrupoProfesor.fxml");
				GrupoProfesorController controlador = loader.getController();
				controlador.setGrupo(opcion);
				controlador.setProfesor(profesor);
				controlador.loadVentana();
				Stage stage = (Stage) btnSalir.getScene().getWindow();
				stage.close();
            }
        });
    }

    @FXML
    void btnSalir_OnClick(ActionEvent event) {
    	VentanaController.crearVentanaLogin(btnSalir);
    }
    
}
