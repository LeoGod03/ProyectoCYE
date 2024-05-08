package application.controlador;

import java.io.IOException;

import application.dao.ProfesorDao;
import application.modelo.Profesor;
import application.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegistrarProfesorController {
	  @FXML
	    private Button btnCancelar;

	    @FXML
	    private Button btnInsertar;

	    @FXML
	    private Spinner<Integer> spnId;

	    @FXML
	    private TextField tfApellidoM;

	    @FXML
	    private TextField tfApellidoP;

	    @FXML
	    private TextField tfCubiculo;

	    @FXML
	    private TextField tfNombre;
	    
	    private TextField[] campos = new TextField[4];

	    @FXML
	    void btnCancelar_OnClick(ActionEvent event) {
	    	Double[] bounds = {650.0, 450.0};
	    	try {
				VentanaController.crearVentana("Opción de registro", bounds, "/application/vistas/SceneOpcionRegistro.fxml");
				Stage stage = (Stage) btnCancelar.getScene().getWindow();
			    stage.close();
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    @FXML
	    void btnInsertar_OnClick(ActionEvent event) {
	    	Alert alerta;
	    	boolean lleno = true;
	    	if(lleno){
	    		if(new ProfesorDao().buscar(new Profesor(spnId.getValue())) == null){ 
	    			if(Profesor.esCubiculoValido(tfCubiculo.getText())) {
	    				Profesor profesor = new Profesor(spnId.getValue(),
	    										tfNombre.getText(), tfApellidoP.getText(),
	    										tfApellidoM.getText(), tfCubiculo.getText(), null);
	    				
	    				profesor.setUsuario(Usuario.generaUsuario(profesor));
	    				new ProfesorDao().insertar(profesor);
	    				
	    				try {
		    				Double[] bounds = {400.0, 450.0};
		    				FXMLLoader loader = VentanaController.crearVentana("Datos", bounds, "/application/vistas/SceneDatosRegistro.fxml");
		    				DatosRegistroController controlador = loader.getController();
		    				controlador.setPersona(profesor);
		    				controlador.inicializar();
		    				Stage stage = (Stage) btnInsertar.getScene().getWindow();
			    		    stage.close();
	    				}catch (IOException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
	    			}else {
	    				alerta = new Alert(AlertType.ERROR, "¡Formato de cubículo no valido!", ButtonType.OK);
						alerta.show();
	    			}
	    		}else {
	    			alerta = new Alert(AlertType.ERROR, "Id de profesor ya registrado!", ButtonType.OK);
					alerta.show();
	    		}
	    	}else {
	    		alerta = new Alert(AlertType.WARNING, "¡Faltan por llenar datos!", ButtonType.OK);
				alerta.show();
	    	}
	    		
	    	
	    	
	    }
	    
	    public void inicializar() {
	    	
	    	campos[0] = tfNombre;
	    	campos[1] = tfApellidoP;
	    	campos[2] = tfApellidoM;
	    	campos[3] = tfCubiculo;
	    	
	        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
	        spnId.setValueFactory(valueFactory);
	    }

}
