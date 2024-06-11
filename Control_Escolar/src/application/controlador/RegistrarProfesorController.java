package application.controlador;

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
	    // botón que te regresa a la ventana anterior
	    @FXML
	    void btnCancelar_OnClick(ActionEvent event) {
	    	VentanaController.crearVentana("Opción de registro", new Double[]{500.0, 400.0}, "/application/vistas/SceneOpcionRegistro.fxml");
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
	    }
	    // botón de insertar 
	    @FXML
	    void btnInsertar_OnClick(ActionEvent event) {
	    	Alert alerta;
	    	boolean lleno = true;
	    	// verificamos que todos los campos esten llenos
	    	for(int i = 0; i < 4; i++) {
	    		if(campos[i].getText().equals("")) {
	    			lleno = false;
	    			break;
	    		}
	    	}
	    	if(lleno){
	    		if(new ProfesorDao().buscar(new Profesor(spnId.getValue())) == null){  // verificamos que el ID no haya sido dado de alta antes
	    			if(Profesor.esCubiculoValido(tfCubiculo.getText())) { // verificamos el formato del cubiculo
	    				Profesor profesor = new Profesor(spnId.getValue(),
	    										tfNombre.getText(), tfApellidoP.getText(),
	    										tfApellidoM.getText(), tfCubiculo.getText(), null);
	    				
	    				profesor.setUsuario(Usuario.generaUsuario(profesor)); // generamos un usuario
	    				new ProfesorDao().insertar(profesor); // insertamos al profesor
	    				
	    				// abrimos la ventana de confirmación de regsitro
	    				Double[] bounds = {400.0, 450.0};
						FXMLLoader loader = VentanaController.crearVentana("Datos", bounds, "/application/vistas/SceneDatosRegistro.fxml");
						DatosRegistroController controlador = loader.getController();
						controlador.setPersona(profesor);
						controlador.loadVentana();
						Stage stage = (Stage) btnInsertar.getScene().getWindow();
						stage.close();
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
	    // metodo para configurar la ventana
	    public void loadVentana() {
	    	
	    	campos[0] = tfNombre;
	    	campos[1] = tfApellidoP;
	    	campos[2] = tfApellidoM;
	    	campos[3] = tfCubiculo;
	    	// le damos valor minimo y maximo al spinner
	        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
	        spnId.setValueFactory(valueFactory);
	    }

}
