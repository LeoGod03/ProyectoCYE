package application.controlador;

import java.io.IOException;

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
    void btnAlumno_OnClick(ActionEvent event) {
    	try {
    		Double[] bounds = {650.0,450.0};
    		FXMLLoader loader = VentanaController.crearVentana("Registro alumno", bounds, "/application/vistas/SceneRegistrarAlumno.fxml");
			RegistrarAlumnoController controlador = loader.getController();
			controlador.inicializar();
    		Stage stage = (Stage) btnAlumno.getScene().getWindow();
			stage.close();
    		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void btnProfesor_OnClcik(ActionEvent event) {
    	try {
    		Double[] bounds = {650.0,450.0};
    		FXMLLoader loader = VentanaController.crearVentana("Registro profesor", bounds, "/application/vistas/SceneRegistrarProfesor.fxml");
    		RegistrarProfesorController controlador = loader.getController();
			controlador.inicializar();
    		Stage stage = (Stage) btnProfesor.getScene().getWindow();
			stage.close();
    		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    

}
