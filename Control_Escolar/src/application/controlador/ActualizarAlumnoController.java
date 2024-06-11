package application.controlador;

import java.util.ArrayList;

import application.dao.AlumnoDao;
import application.dao.LicenciaturasDao;
import application.modelo.Alumno;
import application.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActualizarAlumnoController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cboxLicenciatura;

    @FXML
    private CheckBox chboxEditar;

    @FXML
    private TextField tfApellidoM;

    @FXML
    private TextField tfApellidoP;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfNombre;
    
    private Alumno alumno;
    
    private TextField[] campos = new TextField[4];
    
    public void setAlumno(Alumno alumno) {
    	this.alumno = alumno;
    }
    // botón de actualizar
    @FXML
    void btnActualizar_OnClick(ActionEvent event) {
    	Alert alert = null;
    	boolean lleno = true;
    	// verificamos que todos los campos esten llenos
    	for(int i = 0; i < 4; i++) {
    		if(campos[i].getText().equals("")) {
    			lleno = false;
    			break;
    		}
    	}
    	
    	int idLicenciatura = cboxLicenciatura.getSelectionModel().getSelectedIndex() + 1; 
    	lleno = (lleno == true && idLicenciatura != -1); // que haya una licenciatura asignada
    	if(lleno){
    		if(Alumno.esMatriculaValida(tfMatricula.getText())) { // verificamos que la matricula es valida
		    	Alumno alumnoNuevo = new Alumno(tfMatricula.getText(), // creamos un objeto con los datos del nuevo alumno
						   tfNombre.getText(),
						   tfApellidoP.getText(),
						   tfApellidoM.getText(),
						   idLicenciatura,
						   alumno.getNumeroGrupos(), null);
		    	String[] viejosCampos = new String[] {alumno.getMatricula(), alumno.getNombre(),
		    										  alumno.getApellidoPaterno(), alumno.getApellidoMaterno()};
		    	boolean cambio = false;
		    	for(int i = 0; i < 4; i++) { 
		    		if(!campos[i].getText().equals(viejosCampos[i])){
		    			cambio = true;
		    			break;
		    		}
		    	}		
		    	// si hay un cambio en datos criticos se genera un nuevo usuario, de lo cntrario este se mantiene
		    	alumnoNuevo.setUsuario((cambio)?Usuario.generaUsuario(alumnoNuevo): alumno.getUsuario());
		    	boolean existe = false;
		    	
		    	if(!alumno.getMatricula().equals(alumnoNuevo.getMatricula()))
		    		existe = (new AlumnoDao().buscar(alumnoNuevo) != null);
		    	//Actualizamos y regresamos a la ventana anterior
		    	if(!existe) {
		    		new AlumnoDao().actualizar(alumnoNuevo, alumno);
		    		alert = new Alert(Alert.AlertType.INFORMATION, "¡Alumno actualizado con exito!",ButtonType.OK);
		    		FXMLLoader loader = VentanaController.crearVentana("Datos", new Double[]{400.0, 450.0}, "/application/vistas/SceneDatosRegistro.fxml");
					DatosRegistroController controlador = loader.getController();
					controlador.setVentanaResource("/application/vistas/SceneAlumnosSistema.fxml");
					controlador.setTitle("Profesores en sistema");
					controlador.setPersona(alumnoNuevo);
					controlador.loadVentana();
					Stage stage = (Stage) btnActualizar.getScene().getWindow();
					stage.close();
		    	}else
		    		alert = new Alert(Alert.AlertType.ERROR, "¡Matricula ya existente en el sistema!",ButtonType.OK);
    		}else
    			alert = new Alert(Alert.AlertType.ERROR, "¡Formato de matricula no valido!",ButtonType.OK);
    	}else
    		alert = new Alert(Alert.AlertType.WARNING, "¡Faltan datos por llenar!",ButtonType.OK);
		
    	alert.show(); // se muestra la alerta
    	
    }
    // botón para cerrar la ventana y regresar a la anterior
    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	Double[] bounds = new Double[]{750.0, 400.0};
    	VentanaController.crearVentana("Alumnos en sistema", bounds, "/application/vistas/SceneAlumnosSistema.fxml");
    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
    	stage.close();
    }

    @FXML // habiliatamos la opción de edición
    void chboxEditar_OnClick(ActionEvent event) {
    	for(TextField campo: campos)
    		campo.setEditable(chboxEditar.isSelected());
    	
    	cboxLicenciatura.setDisable(!chboxEditar.isSelected());
    	btnActualizar.setDisable(!chboxEditar.isSelected());
    	
    }
    // metodo para cargar datos en ventana
    public void loadVentana() {
    	campos[0] = tfMatricula;
    	campos[1] = tfNombre;
    	campos[2] = tfApellidoP;
        campos[3] = tfApellidoM;
        ArrayList<String> nombres  = new LicenciaturasDao().obtenerLicenciaturas();
        
        for(String lic: nombres)
        	cboxLicenciatura.getItems().add(lic); // llenamos el combobox con las licenciaturas opfertadas
        
        cboxLicenciatura.getSelectionModel().select(alumno.getIdCarrera() - 1); // le asignamos la licenciatura que el alumno tiene
        
        // llenamos los campos
    	tfMatricula.setText(alumno.getMatricula());
    	tfNombre.setText(alumno.getNombre());
    	tfApellidoP.setText(alumno.getApellidoPaterno());
    	tfApellidoM.setText(alumno.getApellidoMaterno());
    	
    	// de inicio todos los campos no son editables
    	for(TextField campo: campos)
    		campo.setEditable(false);;
    	
    	cboxLicenciatura.setDisable(true);
    	btnActualizar.setDisable(true);
    }

}
