package application.controlador;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaPrincipalAministradoresController {

    @FXML
    private Button btnAlumnos;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnGrupos;

    @FXML
    private Button btnProfesores;
    // botón que envia a la ventana de alumnos en el sistema 
    @FXML
    void btnAlumnos_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Alumnos en sistema", new Double[]{750.0, 400.0}, "/application/vistas/SceneAlumnosSistema.fxml");
    	Stage stage = (Stage) btnAlumnos.getScene().getWindow();
    	stage.close();
    }
    // botón que regresa a la ventana anterior
    @FXML
    void btnCerrar_OnClick(ActionEvent event) {
    	VentanaController.crearVentanaLogin(btnAlumnos);
    }
    //botón para abrir la ventana de grupos en el sistema
    @FXML
    void btnGrupos_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Alumnos en sistema", new Double[]{550.0, 400.0}, "/application/vistas/SceneGruposSistema.fxml");
    	Stage stage = (Stage) btnGrupos.getScene().getWindow();
    	stage.close();
    }
    // botón para abrir la ventana de profesores en el sistema
    @FXML
    void btnProfesores_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Profesores en sistema", new Double[]{750.0, 400.0}, "/application/vistas/SceneProfesoresSistema.fxml");
    	Stage stage = (Stage) btnProfesores.getScene().getWindow();
    	stage.close();
    }

}