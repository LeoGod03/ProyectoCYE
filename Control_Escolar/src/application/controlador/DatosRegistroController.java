package application.controlador;


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
    
    private String ventanaResource = "";
    
    private String title = "";
    
    public void setVentanaResource(String ventanaResource) {
    	this.ventanaResource = ventanaResource;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    // botón que redirecciona a la ventana del login o a la anterior dependiendo de ventanaResource
    @FXML
    void btnAceptar_OnClick(ActionEvent event) {
    	if(ventanaResource.equals(""))
    		VentanaController.crearVentanaLogin(btnAceptar);
    	else {
    		VentanaController.crearVentana(title, new Double[]{750.0, 400.0}, ventanaResource);
        	Stage stage = (Stage) btnAceptar.getScene().getWindow();
        	stage.close();
    	}
    }
    
    public void setPersona(Persona persona) {
    	this.persona = persona;
    }
    // metodo para confirgurar la ventana
    public void loadVentana() {
    	
    	// dependiendo de si es alumno o profesor llenamos los datos
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
    	// mostramos los datos genericos 
    	lbNombre.setText(lbNombre.getText() + " " + persona.getNombre());
    	lbApellidoP.setText(lbApellidoP.getText() + " " + persona.getApellidoPaterno());
    	lbApellidoM.setText(lbApellidoM.getText() + " " + persona.getApellidoMaterno());
    	
    }

}
