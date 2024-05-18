package application.controlador;

import java.io.IOException;

import application.modelo.Alumno;
import application.modelo.Cifrar;
import application.modelo.Persona;
import application.modelo.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField tfPassword;

    @FXML
    private TextField tfUsuario;


    @FXML
    private Label lbRol;

    
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
    		tfUsuario.setText(alumnoTemp.getUsuario().getCorreo());
    		tfPassword.setText(Cifrar.cifrar(alumnoTemp.getUsuario().getContrasenia().toCharArray(),
    														  alumnoTemp.getUsuario().getLlave()));
    		lbRol.setText("Rol: " + alumnoTemp.getUsuario().getRol());
    	}else {
    		Profesor profesorTemp = (Profesor) persona;
    		lbId.setText("Id: " + profesorTemp.getId());
    		tfUsuario.setText(profesorTemp.getUsuario().getCorreo());
    		tfPassword.setText(Cifrar.cifrar(profesorTemp.getUsuario().getContrasenia().toCharArray(),
    														  profesorTemp.getUsuario().getLlave()));
    		
    		lbRol.setText(lbRol.getText()+ " " + profesorTemp.getUsuario().getRol());
    	}
    	lbNombre.setText(lbNombre.getText() + " " + persona.getNombre());
    	lbApellidoP.setText(lbApellidoP.getText() + " " + persona.getApellidoPaterno());
    	lbApellidoM.setText(lbApellidoM.getText() + " " + persona.getApellidoMaterno());
    	
    }

}
