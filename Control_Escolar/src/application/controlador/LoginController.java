package application.controlador;
import application.dao.UsuarioDao;
import application.dao.AlumnoDao;

import java.io.IOException;
import java.sql.SQLException;

import application.dao.Conexion;
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
				if(tfpPassword.getText().equals(usuario.getContrasenia())) {
					//alerta = new Alert(AlertType.CONFIRMATION, "¡Has iniciado Sesión! " + resourceUrl, ButtonType.OK);
					//alerta.show();
					try {
						String resource = (usuario.getRol().equals("alumno"))? "SceneVentanaPrincipalAlumno.fxml" : "Scene.fxml";
						Double[] bounds = {650.0, 450.0};
						FXMLLoader loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/" + resource);
					    VentanaPrincipalAlumnoController controlador = loader.getController();
					    controlador.setAlumno(new AlumnoDao().buscar(usuario));
					    controlador.loadVentana();
					   
					    Stage stage = (Stage) btnIngresar.getScene().getWindow();
					    stage.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
					
					
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
    	// Obtener el Stage de la ventana actual
        
        
		try {
			Double[] bounds = {650.0, 450.0};
			VentanaController.crearVentana("Opción de registro", bounds, "/application/vistas/SceneOpcionRegistro.fxml");
			Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
    }
    
}
