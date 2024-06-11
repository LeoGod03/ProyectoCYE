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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ActualizarProfesorController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private CheckBox chboxEditar;

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
    
    private Profesor profesor;
    
    public void setProfesor(Profesor profesor) {
    	this.profesor = profesor;
    }
    // botón para actualizar
    @FXML
    void btnActualizar_OnClick(ActionEvent event) {
    	Alert alerta;
    	boolean lleno = true;
    	//verificamos que todos los campos esten llenos
    	for(int i = 0; i < 4; i++) {
    		if(campos[i].getText().equals("")) {
    			lleno = false;
    			break;
    		}
    	}
    	// verificamos si esta lleno
    	if(lleno){
    		 
    		if(Profesor.esCubiculoValido(tfCubiculo.getText())) { // si el formato del cubiculo es valido
    			Profesor profesorNuevo = new Profesor(spnId.getValue(), tfNombre.getText(),
    												  tfApellidoP.getText(), tfApellidoM.getText(), tfCubiculo.getText(), null);
    			
    			String[] viejosCampos = new String[] {profesor.getNombre(), profesor.getApellidoPaterno(), profesor.getApellidoMaterno()};
				boolean cambio = false;
				for(int i = 0; i < 3; i++) { 
					if(!campos[i].getText().equals(viejosCampos[i])){
						cambio = true;
						break;
					}
				}		
				cambio = (profesor.getId() != profesorNuevo.getId()); // si se cambio el id generará un nuevo usuario
				profesorNuevo.setUsuario((cambio)?Usuario.generaUsuario(profesorNuevo): profesor.getUsuario());
				boolean existe = false;
				
				if(cambio) // verificamos que el nuvo profesor exista
					existe = (new ProfesorDao().buscar(profesorNuevo) != null);
				
				if(!existe) { // si existe llenamos los datos y regresamos a la ventana anterior
					new ProfesorDao().actualizar(profesorNuevo, profesor);
					FXMLLoader loader = VentanaController.crearVentana("Datos", new Double[]{400.0, 450.0}, "/application/vistas/SceneDatosRegistro.fxml");
					DatosRegistroController controlador = loader.getController();
					controlador.setVentanaResource("/application/vistas/SceneProfesoresSistema.fxml");
					controlador.setTitle("Profesores en sistema");
					controlador.setPersona(profesorNuevo);
					controlador.loadVentana();
					Stage stage = (Stage) btnActualizar.getScene().getWindow();
					stage.close();
					alerta = new Alert(Alert.AlertType.INFORMATION, "¡Profesor actualizado con exito!",ButtonType.OK);
				}else
					alerta = new Alert(Alert.AlertType.ERROR, "¡Id ya existente en el sistema!",ButtonType.OK);
	    			
	    			
    		}else 
    			alerta = new Alert(AlertType.ERROR, "¡Formato de cubículo no valido!", ButtonType.OK);
    		
    		
    	}else 
    		alerta = new Alert(AlertType.WARNING, "¡Faltan por llenar datos!", ButtonType.OK);
			
    	alerta.show(); // mostramos la alerta
    	
    }
    // botón que cierra la ventana y vuelve a la anterior
    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Profesores en sistema", new Double[]{750.0, 400.0}, "/application/vistas/SceneProfesoresSistema.fxml");
    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
    	stage.close();
    }
    // botón que habilita los controles para su edición
    @FXML
    void chboxEditar_OnClcik(ActionEvent event) {
    	for(TextField campo: campos)
    		campo.setEditable(chboxEditar.isSelected());
    	
    	spnId.setDisable(!chboxEditar.isSelected());
    	btnActualizar.setDisable(!chboxEditar.isSelected());
    }
    // metodo paara configurar la ventana
    public void loadVentana() {
    	campos[0] = tfNombre;
    	campos[1] = tfApellidoP;
        campos[2] = tfApellidoM;
        campos[3] = tfCubiculo;
        // llenamos los labels con los datos necesarios
    	tfNombre.setText(profesor.getNombre());
    	tfApellidoP.setText(profesor.getApellidoPaterno());
    	tfApellidoM.setText(profesor.getApellidoMaterno());
    	tfCubiculo.setText(profesor.getCubiculo());
    	
    	// los inicializamos sin poder editarlos
    	for(TextField campo: campos)
    		campo.setEditable(false);;
    	
    	spnId.setDisable(true);
    	btnActualizar.setDisable(true);
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
        spnId.setValueFactory(valueFactory);
    }
}
