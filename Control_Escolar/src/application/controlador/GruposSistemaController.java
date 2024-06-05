package application.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.modelo.EnumBusquedas.BUSQUEDA;
import application.modelo.Grupo;
import application.dao.GruposDao;
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

public class GruposSistemaController implements Initializable{

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> cboxBusqueda;

    @FXML
    private TableView<Grupo> tbvGrupos;

    @FXML
    private TextField tfBusqueda;
    
    private Grupo opcion;

    @FXML
    void btnAgregar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Alumnos en sistema", new Double[]{600.0, 350.0}, "/application/vistas/SceneAgregarGrupo.fxml");
    	Stage stage = (Stage) btnAgregar.getScene().getWindow();
    	stage.close();
    }

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
	        					+ "grupo : "+ opcion.getId() + " grupo: " + opcion.getGrupo()+ "?");
	        
	        // para obtener el resultado
	        Optional<ButtonType> result = alert.showAndWait();
	        Alert mensaje;
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	        	new GruposDao().eliminar(opcion);
	        	tbvGrupos.getItems().remove(opcion);
	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Eliminado con exito!", ButtonType.OK);
				mensaje.show();
	        	
	        } else {
	        	mensaje = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
				mensaje.show();
	        }
    	}
    }
  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cboxBusqueda.getItems().add("Id");
		cboxBusqueda.getItems().add("Grupo");
		cboxBusqueda.getItems().add("Id de profesor");
		
		tfBusqueda.textProperty().addListener((observable, viejoValor, nuevoValor) ->{
			
			if(cboxBusqueda.getSelectionModel().getSelectedIndex() == -1)
				return;
			
			ArrayList<Grupo> lista = new ArrayList<>();		
			if(!nuevoValor.equals("")) {
				try {
					int indiceBusqueda = cboxBusqueda.getSelectionModel().getSelectedIndex();
					int busqueda = Integer.parseInt(nuevoValor);
					Grupo grupoBusqueda = new Grupo(busqueda, 0);
					BUSQUEDA tipoBusqueda;
					
					if(indiceBusqueda == 0)
						tipoBusqueda = BUSQUEDA.IDCURSO;
					else if(indiceBusqueda == 1)
						tipoBusqueda = BUSQUEDA.GRUPO;
					else
						tipoBusqueda = BUSQUEDA.IDPROFESOR;
					
					lista = new GruposDao().buscar(grupoBusqueda, tipoBusqueda);
					
				}catch(NumberFormatException e) {
					Alert alerta = new Alert(Alert.AlertType.ERROR, "La busqueda debe ser númerica!", ButtonType.OK);
	    			alerta.show();
	    			tfBusqueda.setText("");
	    			lista = new GruposDao().obtenerGruposDisponibles();
				}
			}else
				lista = new GruposDao().obtenerGruposDisponibles();
			
			tbvGrupos.getItems().clear();
			tbvGrupos.getItems().addAll(lista);
		});
		
		TableColumn<Grupo, Integer> colId = new TableColumn<>("Id curso");
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		//colId.setMaxWidth(75);
		
		TableColumn<Grupo, Integer> colGrupo = new TableColumn<>("# de grupo");
		colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
		
		TableColumn <Grupo, Integer> colIdProfesor = new TableColumn<>("Id del profesor");
		colIdProfesor.setCellValueFactory(new PropertyValueFactory<>("idProfesor"));
		
		TableColumn<Grupo, Integer> colAlumnosInscritos = new TableColumn<>("# de alumnos inscritos");
		colAlumnosInscritos.setCellValueFactory(new PropertyValueFactory<>("alumnosInscritos"));
		
		tbvGrupos.getColumns().add(colId);
		tbvGrupos.getColumns().add(colGrupo);
		tbvGrupos.getColumns().add(colIdProfesor);
		tbvGrupos.getColumns().add(colAlumnosInscritos);
		
		tbvGrupos.getItems().addAll(new GruposDao().obtenerGruposDisponibles());
		
		tbvGrupos.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor)->{
			opcion = (Grupo) nuevoValor;
		});
		tbvGrupos.setOnMouseClicked(event->{
			if (event.getClickCount() == 2){
				FXMLLoader loader = VentanaController.crearVentana("Grupo",new Double[]{500.0, 450.0}, "/application/vistas/SceneActualizarGrupo.fxml");
				ActualizarGrupoController controlador = loader.getController();
				controlador.setGrupo(opcion);
				controlador.loadVentana();
				Stage stage = (Stage) tbvGrupos.getScene().getWindow();
				stage.close();
			}
		});
		
	}
    

}
