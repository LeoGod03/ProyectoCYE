package application.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.modelo.Profesor;
import application.dao.GruposDao;
import application.dao.ProfesorDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProfesoresSistemaController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> cboxBusqueda;

    @FXML
    private TableView<Profesor> tbvProfesores;

    @FXML
    private TextField tfBusqueda;
    
    private Profesor opcion;
    //botón que regresa a la ventana anterior
    @FXML
    void btnCerrar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Ventana principal", new Double[] {600.0,300.0}, "/application/vistas/SceneVentanaPrincipalAdministradores.fxml");
    	Stage stage = (Stage) btnCerrar.getScene().getWindow();
    	stage.close();
    }
    // botón para eliminar del sistema a un profesor
    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	// primero se tiene que verificar que no este impartiendo ningun curso
    	if(opcion != null) {
    		Alert mensaje = null;
    		if(new GruposDao().obtenerGrupos(opcion).size() == 0) { // verificamos que ya no este impartiendo algún curso
	    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    	alert.setWidth(350.0);
		    	alert.setHeight(250.0);
		        alert.setTitle("Confirmación");
		        alert.setHeaderText("Inscripcion a curso");
		        alert.setContentText("¿Estás seguro que deseas eliminar del sistema al "
		        					+ "Profesor  : "+ opcion.getId()+ "?");
		        
		        // para obtener el resultado
		        Optional<ButtonType> result = alert.showAndWait();
		        
		        if (result.isPresent() && result.get() == ButtonType.OK) { // después de confirmar la acción eliminamos al profesor
		        	new ProfesorDao().eliminar(opcion);
		        	tbvProfesores.getItems().remove(opcion);
		        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Eliminado con exito!", ButtonType.OK);
		        	
		        } else 
		        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
		        
    		}else
    			mensaje = new Alert(AlertType.ERROR, "¡El profesor esta asignado a grupo(s)!", ButtonType.OK);	
    		
			mensaje.show();
    	}
    }
    // metodo de inicializar la ventana
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
    	cboxBusqueda.getItems().add("Id");
    	cboxBusqueda.getItems().add("Nombre/Apellido");
    	// listener para hacer busqueda con el cambio del texto de el textField de busqueda
    	tfBusqueda.textProperty().addListener((observable, valorViejo, nuevoValor) -> {
    		
    		if(cboxBusqueda.getSelectionModel().getSelectedIndex() == -1) // verificamos que haya una selección en el combobox
    			return;
    		
    		Alert alerta;
	    	
    		ArrayList<Profesor> lista;
    		tbvProfesores.getItems().clear();
    	    if(!nuevoValor.equals("")) {
    	    	Profesor profesor;
    	    	// busqueda por ID
    	    	if(cboxBusqueda.getSelectionModel().getSelectedIndex() == 0) { // si es la seleccion 0 del combobox
    	    		String idValor = nuevoValor.replaceAll("[a-zA-Z]", ""); // quitamos las letras del texto
    	    		System.out.println(idValor);
    	    		try{
    	    			Integer id = Integer.parseInt(idValor);
    	    			profesor = new Profesor(id);
    	    			Profesor profesorTemp = new ProfesorDao().buscar(profesor);
    	    			if(profesorTemp != null)
    	    				tbvProfesores.getItems().add(profesorTemp);
    	    		}catch(NumberFormatException e) {
    	    			//e.printStackTrace();
    	    			alerta = new Alert(Alert.AlertType.ERROR, "La busqueda por Id debe ser númerica!", ButtonType.OK);
    	    			alerta.show();
    	    			tfBusqueda.setText("");
    	    			
    	    		}
    	    	}else { // busqueda por Nombre/Apellido
    	    		profesor = new Profesor(0, tfBusqueda.getText(), "", "", "", null);
    	    		lista = new ProfesorDao().buscarCoincidencia(profesor);
    	    		tbvProfesores.getItems().addAll(lista);
    	    	}
    	    		
    	    }else {
    	    	lista = new ProfesorDao().obtenerProfesoresSistema();
    	    	tbvProfesores.getItems().addAll(lista);
    	    }
    	   
    	});
    	
    	// creamos las columnas para los datos del profesor
    	TableColumn<Profesor, Integer> colId = new TableColumn<>("Id");
    	colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    	colId.setMaxWidth(75);
    	
    	TableColumn<Profesor, String> colNombre = new TableColumn<>("Nombre");
    	colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	colNombre.setMaxWidth(200);
    	
    	TableColumn<Profesor, String> colApellidoP = new TableColumn<>("Apellido Paterno");
    	colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
    	colApellidoP.setMaxWidth(150);
    	
    	TableColumn<Profesor, String> colApellidoM = new TableColumn<>("Apellido Materno");
    	colApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
    	colApellidoM.setMaxWidth(150);
    	
    	TableColumn<Profesor, String> colCubiculo = new TableColumn<>("Cubículo");
    	colCubiculo.setCellValueFactory(new PropertyValueFactory<>("cubiculo")); 
    	colCubiculo.setMaxWidth(75);
    	
    	
    	TableColumn<Profesor, String> colUsuario = new TableColumn<>("Correo");
    	colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getCorreo()));
    	
    	// agregamos las columnas a la tabla
    	
    	tbvProfesores.getColumns().add(colId);
    	tbvProfesores.getColumns().add(colNombre);
    	tbvProfesores.getColumns().add(colApellidoP);
    	tbvProfesores.getColumns().add(colApellidoM);
    	tbvProfesores.getColumns().add(colCubiculo);
    	tbvProfesores.getColumns().add(colUsuario);
    	
    	//creamos un listener para saber quien es el profesor seleccionado en la tabla
    	tbvProfesores.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor) -> {
    		opcion = (Profesor) nuevoValor;
        });
    	//agregamos un evento de mouse para que al dar doble clic se habrá una ventana con los datos del profesor elegido
    	tbvProfesores.setOnMouseClicked(event -> {
    		if(event.getClickCount() == 2 && opcion != null) {
    	    	FXMLLoader loader = VentanaController.crearVentana("Profesor", new Double[]{460.0, 350.0}, "/application/vistas/SceneActualizarProfesor.fxml");
    	    	ActualizarProfesorController controlador = loader.getController();
    	    	controlador.setProfesor(opcion);
    	    	controlador.loadVentana();
    	    	Stage stage = (Stage) tbvProfesores.getScene().getWindow();
    	    	stage.close();
    		}
    	});
    	
    	ArrayList<Profesor> lista  = new ProfesorDao().obtenerProfesoresSistema();
    	tbvProfesores.getItems().addAll(lista); // llenamos la tabla
    	
    	
    }

}
