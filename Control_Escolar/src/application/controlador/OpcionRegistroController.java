package application.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OpcionRegistroController {
	

    @FXML
    private Button btnAlumno;

    @FXML
    private Button btnProfesor;
    
    @FXML
    private Button btnCancelar;

    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	VentanaController.crearVentanaLogin(btnAlumno);
    }
    
    @FXML
    void btnAlumno_OnClick(ActionEvent event) {
		FXMLLoader loader = VentanaController.crearVentana("Registro alumno", new Double[] {550.0,350.0}, "/application/vistas/SceneRegistrarAlumno.fxml");
		RegistrarAlumnoController controlador = loader.getController();
		controlador.loadVentana();
		Stage stage = (Stage) btnAlumno.getScene().getWindow();
		stage.close();
    }

    @FXML
    void btnProfesor_OnClcik(ActionEvent event) {
		FXMLLoader loader = VentanaController.crearVentana("Registro profesor", new Double[] {550.0,350.0}, "/application/vistas/SceneRegistrarProfesor.fxml");
		RegistrarProfesorController controlador = loader.getController();
		controlador.loadVentana();
		Stage stage = (Stage) btnProfesor.getScene().getWindow();
		stage.close();
    }
    

}
