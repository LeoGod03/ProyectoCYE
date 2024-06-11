package application.controlador;

import java.util.Optional;
import application.modelo.Grupo;
import application.dao.GruposDao;
import application.modelo.Alumno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class VentanaPrincipalAlumnoController {

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnInscribir;
    
    @FXML
    private Button btnSalir;
    

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbMatricula;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbUsuario;

    @FXML
    private ListView<Grupo> lvGrupos;
    
    private Alumno alumno;
    
    private Grupo opcion;
    
    public void setAlumno(Alumno alumno) {
    	this.alumno = alumno;
    }
    // botón para dar de baja un curso
    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	
    	// creamos la alerta
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setWidth(350.0);
	    alert.setHeight(250.0);
	    alert.setTitle("Confirmación");
	    alert.setHeaderText("Eliminar grupo");
	    alert.setContentText("¿Estás seguro que deseas darte de baja de el grupo seleccionado: "
	        					+ "Id: "+ opcion.getId() + " Grupo: " + opcion.getGrupo() + "?");
	        
	    // para obtener el resultado
	    Optional<ButtonType> result = alert.showAndWait();
	    Alert confirmation;  
	    if (result.isPresent() && result.get() == ButtonType.OK) { // en caso afirmativo damos de baja al alumno del grupo
	        new GruposDao().darBajaGrupo(opcion, alumno);
	        confirmation = new Alert(AlertType.CONFIRMATION, "Dado de baja del grupo Id: " + opcion.getId() + " grupo: " + opcion.getGrupo(), ButtonType.OK);
			confirmation.show();
			lvGrupos.getItems().remove(opcion);
	        	
	    } else {
	       confirmation = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
	       confirmation.show();
	    }
    	
    }
    //botón que abre la ventana para inscribir al alumnos a grupo(s)
    @FXML
    void btnInscribir_OnClick(ActionEvent event) {
    	Double[] bounds = {650.0, 450.0};
		FXMLLoader loader = VentanaController.crearVentana("Inscribir curso", bounds, "/application/vistas/SceneInscribirGrupo.fxml");
		InscribirGrupoController controlador = loader.getController();
		controlador.setAlumno(alumno);
		controlador.loadVentana();
		
		Stage stage = (Stage) btnInscribir.getScene().getWindow();
		stage.close();
    	
    }
    
    // botón que te regresa a la ventana anterior
    @FXML
    void btnSalir_OnClick(ActionEvent event) {
    	VentanaController.crearVentanaLogin(btnEliminar);
    }
    // metodo para configurar la ventana
    public void loadVentana() {
    	// dependiendo de las condiciones del alumno habilitamos o deshabilitamos los botones
    	btnInscribir.setDisable(alumno.getNumeroGrupos() >= 7);
    	btnEliminar.setDisable(alumno.getNumeroGrupos() <= 0);
    	
    	// llenamos los datos de los labels
    	lbMatricula.setText("Matricula: " + alumno.getMatricula());
    	lbNombre.setText("Nombre: " + alumno.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + alumno.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + alumno.getApellidoMaterno());
    	lbUsuario.setText("Usuario: " + alumno.getUsuario().getCorreo());
    	
    	
    	lvGrupos.getItems().addAll(alumno.getGruposInscritos()); // añadimos los grupos a los que esta isncrito el alumno
    	// listener que obtiene la elección del grupo de la lista mostrada
    	lvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor) -> {
    		opcion = (Grupo) nuevoValor;
    		//System.out.println(opcion);
        });
    	// acción del ratón cuando le damos doble clic se muestra la ventana del grupo seleccionado en la vista de alumnos
    	lvGrupos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
            	Double [] bounds = {650.0, 500.0};
				FXMLLoader loader =  VentanaController.crearVentana("Grupo", bounds, "/application/vistas/SceneGrupoAlumno.fxml");
				GrupoAlumnoController controlador = loader.getController();
				controlador.setGrupo(opcion);
				controlador.setAlumno(alumno);
				controlador.loadVentana();
				Stage stage = (Stage) btnSalir.getScene().getWindow();
				stage.close();
            }
        });

    }

    
}
