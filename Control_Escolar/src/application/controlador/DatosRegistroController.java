package application.controlador;

import java.io.IOException;

import application.modelo.Alumno;
import application.modelo.Persona;
import application.modelo.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DatosRegistroController {
	@FXML
    private Button btnAceptar;

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbId;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbRol;

    @FXML
    private Label lbUsuario;
    
    private Persona persona;
    

    @FXML
    void btnAceptar_OnClick(ActionEvent event) {
    	try {
    		Double [] bounds = {500.0, 400.0};
    		VentanaController.crearVentana("Login", bounds, "/application/vistas/SceneLogin.fxml");
    		Stage stage = (Stage) btnAceptar.getScene().getWindow();
    		stage.close();
    	}catch(IOException e) {
    		
    		e.printStackTrace();
    	}
    }
    
    public void setPersona(Persona persona) {
    	this.persona = persona;
    }
    
    public void inicializar() {
    	
    	if(persona instanceof Alumno) {
    		Alumno alumnoTemp = (Alumno) persona;
    		lbId.setText("Matricula: " + alumnoTemp.getMatricula());
    		lbUsuario.setText(lbUsuario.getText() + " " + alumnoTemp.getUsuario().getCorreo());
    		lbPassword.setText(lbPassword.getText() + " " + alumnoTemp.getUsuario().getContrasenia());
    		lbRol.setText(lbRol.getText()+ " " + alumnoTemp.getUsuario().getRol());
    	}else {
    		Profesor profesorTemp = (Profesor) persona;
    		lbId.setText("Matricula: " + profesorTemp.getId());
    		lbUsuario.setText(lbUsuario.getText() + " " + profesorTemp.getUsuario().getCorreo());
    		lbPassword.setText(lbPassword.getText() + " " + profesorTemp.getUsuario().getContrasenia());
    		lbRol.setText(lbRol.getText()+ " " + profesorTemp.getUsuario().getRol());
    	}
    	lbNombre.setText(lbNombre.getText() + " " + persona.getNombre());
    	lbApellidoP.setText(lbApellidoP.getText() + " " + persona.getApellidoPaterno());
    	lbApellidoM.setText(lbApellidoM.getText() + " " + persona.getApellidoMaterno());
    	
    }

}
