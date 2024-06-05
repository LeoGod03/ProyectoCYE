package application.controlador;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VentanaController {
	public static FXMLLoader crearVentana(String titulo,Double[] bounds, String resource){
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
		try {
			Parent root = loader.load();
			Stage ventana = new Stage();
			//ventana.initStyle(StageStyle.UNDECORATED);
			ventana.setTitle(titulo);
		    ventana.getIcons().add(new Image(Main.class.getResourceAsStream("/application/images/Icono.jpg")));
		    ventana.setScene(new Scene(root, bounds[0], bounds[1]));
		    ventana.show();	
		}catch(IOException e) {
			e.printStackTrace();
		}
	    return loader;
	}
	
	public static void crearVentanaLogin(Button boton) {
		Double [] bounds = {500.0, 400.0};
		VentanaController.crearVentana("Login", bounds, "/application/vistas/SceneLogin.fxml");
		Stage stage = (Stage) boton.getScene().getWindow();
		stage.close();
	}
}
