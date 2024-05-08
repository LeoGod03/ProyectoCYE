package application.controlador;

import java.io.IOException;
import java.util.Optional;
import application.modelo.Curso;
import application.modelo.Grupo;
import application.dao.AlumnoDao;
import application.modelo.Alumno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private ListView<String> lvCursos;
    
    private Alumno alumno;
    
    private String opcion;
    
    public void setAlumno(Alumno alumno) {
    	this.alumno = alumno;
    }
    
    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	String[] partesCurso = opcion.split(" ");
    	
    	// creamos la alerta
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setWidth(350.0);
    	alert.setHeight(250.0);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Eliminar elemento");
        alert.setContentText("¿Estás seguro que deseas eliminar el elemento seleccionado: "
        					+ "Id: " + partesCurso[1] + " Grupo: " + partesCurso[3] + "?");
        
        // para obtener el resultado
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	new AlumnoDao().darBajaGrupo(new Grupo(Integer.parseInt(partesCurso[1]),
        								 Integer.parseInt(partesCurso[3])), alumno);
        	
        	lvCursos.getItems().remove(lvCursos.getSelectionModel().getSelectedIndex());
        	Alert confirmation = new Alert(AlertType.CONFIRMATION, "Dado de baja del grupo Id: " + partesCurso[1] + " grupo: " + partesCurso[3], ButtonType.OK);
			confirmation.show();
        	
        } else {
        	Alert confirmation = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
			confirmation.show();
        }
    }

    @FXML
    void btnInscribir_OnClick(ActionEvent event) {

    }
    

    @FXML
    void btnSalir_OnClick(ActionEvent event) {
    	try {
    		Double [] bounds = {500.0, 400.0};
    		VentanaController.crearVentana("Login", bounds, "/application/vistas/SceneLogin.fxml");
    		Stage stage = (Stage) btnSalir.getScene().getWindow();
    		stage.close();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void loadVentana() {
    	lbMatricula.setText("Matricula: " + alumno.getMatricula());
    	lbNombre.setText("Nombre: " + alumno.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + alumno.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + alumno.getApellidoMaterno());
    	lbUsuario.setText("Usuario: " + alumno.getUsuario().getCorreo());
    	ObservableList<String> items = (ObservableList<String>) lvCursos.getItems();
    	
    	for(Grupo curso: alumno.getGruposInscritos())
    		items.add("Id: " + curso.getId() + " Grupo: " + curso.getGrupo() + "]");
    
    	lvCursos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		opcion = (String) newValue;
    		//System.out.println(opcion);
        });
    	
    	lvCursos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
           
                System.out.println(opcion);
            }
        });

    }
}
