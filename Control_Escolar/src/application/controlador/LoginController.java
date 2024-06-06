package application.controlador;
import application.dao.UsuarioDao;
import application.dao.AlumnoDao;
import application.dao.ProfesorDao;

import java.sql.SQLException;

import application.dao.Conexion;
import application.modelo.Cifrar;
import application.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
	@FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField tfCorreo;

    @FXML
    private PasswordField tfpPassword;

    @FXML
    void Salir_OnClick(ActionEvent event) {
    	Stage stage = (Stage) btnSalir.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void btnIngresar_OnClick(ActionEvent event) {
    	Alert alerta;
    	try {
			Usuario usuario = new UsuarioDao().buscar(new Usuario(tfCorreo.getText()), new Conexion().establecerConexion());
			if(usuario != null) {
				if(tfpPassword.getText().equals(Cifrar.cifrar(usuario.getContrasenia().toCharArray(),usuario.getLlave()))) {
					alerta = new Alert(AlertType.CONFIRMATION, "¡Has iniciado sesión!", ButtonType.OK);
					
					Double[] bounds = {650.0, 450.0};
					FXMLLoader loader;
					switch(usuario.getRol()){
						case "alumno" : 
							loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/SceneVentanaPrincipalAlumno.fxml");
							VentanaPrincipalAlumnoController controlador = loader.getController();
							controlador.setAlumno(new AlumnoDao().buscar(usuario));
							controlador.loadVentana();
							break;
								
						case "profesor":
							loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/SceneVentanaPrincipalProfesor.fxml");
							VentanaPrincipalProfesorController controlador1 = loader.getController();
							controlador1.setProfesor(new ProfesorDao().buscar(usuario));
							controlador1.loadVentana();	
							break;
						default: 
								
							VentanaController.crearVentana("Ventana principal", new Double[] {600.0,300.0}, "/application/vistas/SceneVentanaPrincipalAdministradores.fxml");
							break;
					}
					   
					Stage stage = (Stage) btnIngresar.getScene().getWindow();
					stage.close();
					alerta.show();
			        
					
				}else {
					alerta = new Alert(AlertType.ERROR, "¡Contraseña incorrecta!", ButtonType.OK);
					alerta.show();
				}
			}else {
				alerta = new Alert(AlertType.ERROR, "¡Correo no encontrado!", ButtonType.OK);
				alerta.show();
			}
    	} catch (SQLException e) {
    		alerta = new Alert(AlertType.ERROR, "Error con la BD " + e.getMessage(), ButtonType.OK);
			alerta.show();
			e.printStackTrace();
		}
    	
    }

    @FXML
    void btnRegistrarse_OnClick(ActionEvent event) {
    	
		VentanaController.crearVentana("Opción de registro", new Double[]{500.0, 400.0}, "/application/vistas/SceneOpcionRegistro.fxml");
		Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
		stage.close();
		
	    
    }
    
    
}
