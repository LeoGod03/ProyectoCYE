package application;
	

import javafx.application.Application;
import javafx.stage.Stage;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
//import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vistas/SceneLogin.fxml"));
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/images/Icono.jpg")));
			primaryStage.setOnCloseRequest(event -> {
			    event.consume(); // Evita que se cierre la ventana
			   
			});
			primaryStage.show();
			
			
				
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
