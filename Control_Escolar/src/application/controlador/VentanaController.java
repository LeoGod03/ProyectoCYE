package application.controlador;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VentanaController {
	
	public static FXMLLoader crearVentana(String titulo,Double[] bounds, String resource) throws IOException {
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
		Parent root = loader.load();
		
		Stage ventana = new Stage();
	    ventana.setTitle(titulo);
	    ventana.getIcons().add(new Image(Main.class.getResourceAsStream("/application/images/Icono.jpg")));
	    ventana.setScene(new Scene(root, bounds[0], bounds[1]));
	    ventana.show();	
	    return loader;
	}
}
