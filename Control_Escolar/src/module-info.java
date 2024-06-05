module Control_Escolar {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires ini4j;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.controlador to javafx.fxml;
	opens application.modelo to javafx.base;
}
