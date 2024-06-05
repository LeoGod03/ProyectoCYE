package application.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.modelo.Alumno;
import application.modelo.EnumBusquedas.BUSQUEDA;
import application.dao.AlumnoDao;
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

public class AlumnosSistemaController implements Initializable{

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> cboxBusqueda;

    @FXML
    private TableView<Alumno> tbvAlumnos;

    @FXML
    private TextField tfBusqueda;
    
    private Alumno opcion;
    
    @FXML
    void btnCerrar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Ventana principal", new Double[] {600.0,300.0}, "/application/vistas/SceneVentanaPrincipalAdministradores.fxml");
    	Stage stage = (Stage) btnCerrar.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	if(opcion != null) {
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setWidth(350.0);
	    	alert.setHeight(250.0);
	        alert.setTitle("Confirmación");
	        alert.setHeaderText("Inscripcion a curso");
	        alert.setContentText("¿Estás seguro que deseas eliminar del sistema al "
	        					+ "alumno : "+ opcion.getMatricula() + "?");
	        
	        // para obtener el resultado
	        Optional<ButtonType> result = alert.showAndWait();
	        Alert mensaje;
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	        	new AlumnoDao().eliminar(opcion);
	        	tbvAlumnos.getItems().remove(opcion);
	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Eliminado con exito!", ButtonType.OK);
				mensaje.show();
	        	
	        } else {
	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
				mensaje.show();
	        }
    	}
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	cboxBusqueda.getItems().add("Matrícula");
    	cboxBusqueda.getItems().add("Nombre/Apellido");
    	
    	tfBusqueda.textProperty().addListener((observable, valorViejo, nuevoValor) -> {
    		if(cboxBusqueda.getSelectionModel().getSelectedIndex() == -1) 
	    		return;
    		
    		ArrayList<Alumno> lista;
    	    if(!nuevoValor.equals("")) {
    	    	Alumno alumno = new Alumno(tfBusqueda.getText(), tfBusqueda.getText(),
    	    							  "", "",0,0, null);
    	    	BUSQUEDA filtro = (cboxBusqueda.getSelectionModel().getSelectedIndex() == 0)? BUSQUEDA.MATRICULA: BUSQUEDA.NOMBREAPELLIDO;
    	    	lista = new AlumnoDao().buscar(alumno, filtro);
    	    		
    	    }else 
    	    	lista = new AlumnoDao().obtenerAlumnosSistema();
    	    
    	    tbvAlumnos.getItems().clear();
    	    tbvAlumnos.getItems().addAll(lista);
    	   
    	});
    	TableColumn<Alumno, String> colMatricula = new TableColumn<>("Matricula");
    	colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    	colMatricula.setMaxWidth(100);
    	
    	TableColumn<Alumno, String> colNombre = new TableColumn<>("Nombre");
    	colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	colNombre.setMaxWidth(200);
    	
    	TableColumn<Alumno, String> colApellidoP = new TableColumn<>("Apellido Paterno");
    	colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
    	colApellidoP.setMaxWidth(150);
    	
    	TableColumn<Alumno, String> colApellidoM = new TableColumn<>("Apellido Materno");
    	colApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
    	colApellidoM.setMaxWidth(150);
    	
    	TableColumn<Alumno, Integer> colIdCarrera = new TableColumn<>("Id carrera");
    	colIdCarrera.setCellValueFactory(new PropertyValueFactory<>("idCarrera")); 
    	colIdCarrera.setMaxWidth(50);
    	
    	TableColumn<Alumno, Integer> colGruposInscritos = new TableColumn<>("#Grupos inscritos");
    	colGruposInscritos.setCellValueFactory(new PropertyValueFactory<>("numeroGrupos"));
    	colIdCarrera.setMaxWidth(50);
    	
    	
    	TableColumn<Alumno, String> colUsuario = new TableColumn<>("Correo");
    	colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getCorreo()));
    	
    	
    	
    	tbvAlumnos.getColumns().add(colMatricula);
    	tbvAlumnos.getColumns().add(colNombre);
    	tbvAlumnos.getColumns().add(colApellidoP);
    	tbvAlumnos.getColumns().add(colApellidoM);
    	tbvAlumnos.getColumns().add(colIdCarrera);
    	tbvAlumnos.getColumns().add(colGruposInscritos);
    	tbvAlumnos.getColumns().add(colUsuario);
    	
    	ArrayList<Alumno> lista = new AlumnoDao().obtenerAlumnosSistema();
    	tbvAlumnos.getItems().addAll(lista);
    	
    	tbvAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor) -> {
    		opcion = (Alumno) nuevoValor;
        });
    	tbvAlumnos.setOnMouseClicked(event -> {
    		if(event.getClickCount() == 2 && opcion != null) {
    	    	FXMLLoader loader = VentanaController.crearVentana("Alumno", new Double[]{460.0, 350.0}, "/application/vistas/SceneActualizarAlumno.fxml");
    	    	ActualizarAlumnoController controlador = loader.getController();
    	    	controlador.setAlumno(opcion);
    	    	controlador.loadVentana();
    	    	Stage stage = (Stage) tbvAlumnos.getScene().getWindow();
    	    	stage.close();
    		}
    	});
    }

}