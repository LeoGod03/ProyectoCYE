package application.controlador;


import java.util.ArrayList;

import application.dao.AlumnoDao;
import application.dao.LicenciaturasDao;
import application.modelo.Usuario;
import application.modelo.Alumno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarAlumnoController {
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cboxLicenciatura;

    @FXML
    private TextField tfApellidoM;

    @FXML
    private TextField tfApellidoP;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfNombre;
    
    private TextField[] campos = new TextField[4];
    
    public void loadVentana() {
    	campos[0] = tfMatricula;
    	campos[1] = tfNombre;
    	campos[2] = tfApellidoP;
        campos[3] = tfApellidoM;
        ArrayList<String> nombres  = new LicenciaturasDao().obtenerLicenciaturas();
        for(String lic: nombres)
        	cboxLicenciatura.getItems().add(lic);
    }
    
    @FXML
    void btnInsertar_OnClick(ActionEvent event) {
    	Alert alert;
    	boolean lleno = true;
    	for(int i = 0; i < 4; i++) {
    		if(campos[i].getText().equals("")) {
    			lleno = false;
    			break;
    		}
    	}
    	
    	int idLicenciatura = cboxLicenciatura.getSelectionModel().getSelectedIndex(); 
    	lleno = (lleno == true && idLicenciatura != -1);
    	
    	
    	if(lleno){
    		if(Alumno.esMatriculaValida(tfMatricula.getText())){
	    		if(new AlumnoDao().buscar(new Alumno(tfMatricula.getText())) == null) {
	    			Alumno alumno = new Alumno(tfMatricula.getText(),
	    									   tfNombre.getText(),
	    									   tfApellidoP.getText(),
	    									   tfApellidoM.getText(),
	    									   idLicenciatura + 1,0, null);
	    			
	    			alumno.setUsuario(Usuario.generaUsuario(alumno));
	    			new AlumnoDao().insertar(alumno);   
	    	        
	    			Double[] bounds = {400.0, 450.0};
					FXMLLoader loader = VentanaController.crearVentana("Datos", bounds, "/application/vistas/SceneDatosRegistro.fxml");
					DatosRegistroController controlador = loader.getController();
					controlador.setPersona(alumno);
					controlador.loadVentana();
					Stage stage = (Stage) btnInsertar.getScene().getWindow();
					stage.close();
	    			
	    	        
	    		}else {
	    			alert = new Alert(Alert.AlertType.ERROR, "¡Matricula ya existente en el sistema!",ButtonType.OK);
	    			alert.show();
	    		}
    		}else {
    			alert = new Alert(Alert.AlertType.ERROR, "¡Formato de matricula no valido!",ButtonType.OK);
    			alert.show();
    		}
    	}else {
    		alert = new Alert(Alert.AlertType.WARNING, "¡Faltan datos por llenar!",ButtonType.OK);
    		alert.show();
    	}
    	
    }

    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Opción de registro", new Double[]{500.0, 400.0}, "/application/vistas/SceneOpcionRegistro.fxml");
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
    }
}
