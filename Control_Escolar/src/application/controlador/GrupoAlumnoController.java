package application.controlador;

import application.modelo.Alumno;
import application.modelo.Curso;
import application.modelo.Grupo;
import application.dao.CursosDao;
import application.dao.AlumnoDao;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GrupoAlumnoController {


	    @FXML
	    private Button btnCerrar;
	
	    @FXML
	    private Label lbGrupo;

	    @FXML
	    private Label lbIdCurso;

	    @FXML
	    private Label lbIdProfesor;

	    @FXML
	    private Label lbNombre;

	    @FXML
	    private TableView<Alumno> tbvAlumnos;

	    @FXML
	    private TextField tfBusqueda;
	    
	    private Grupo grupo;
	    
	    private Alumno alumno;
	    
	    public void setGrupo(Grupo grupo) {
	    	this.grupo = grupo;
	    	
	    }
	    public void setAlumno(Alumno alumno) {
	    	this.alumno = alumno;
	    }
	    
	    @FXML
	    void btnCerrar_OnClick(ActionEvent event) {
	    	Double[] bounds = {650.0, 450.0};
	    	FXMLLoader loader;
			loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/SceneVentanaPrincipalAlumno.fxml");
			VentanaPrincipalAlumnoController controlador = loader.getController();
			controlador.setAlumno(alumno);
			controlador.loadVentana();
			Stage stage = (Stage) btnCerrar.getScene().getWindow();
			stage.close();
	    }
	   
	    
	    public void loadVentana() {
	    	tfBusqueda.textProperty().addListener((observable, valorViejo, nuevoValor) -> {
	    		ArrayList<Alumno> lista;
	    		tbvAlumnos.getItems().clear();
	    	    if(!nuevoValor.equals("")) {
	    	    	Alumno alumno = new Alumno(tfBusqueda.getText());
	    	    	lista = new AlumnoDao().buscarEnGrupo(alumno, grupo);
	    	    		
	    	    }else {
	    	    	lista = new AlumnoDao().obtenerAlumnos(grupo);
	    	    }
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
	    	
	    	TableColumn<Alumno, Double> colProyecto = new TableColumn<>("Proyecto");  
	    	colProyecto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(0)).asObject());
	    	colProyecto.setMaxWidth(75);
	    	
	    	TableColumn<Alumno, Double> colTareas = new TableColumn<>("Tareas");  
	    	colTareas.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(1)).asObject());
	    	colTareas.setMaxWidth(75);
	    	
	    	TableColumn<Alumno, Double> colExamenes = new TableColumn<>("Examenes");  
	    	colExamenes.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(2)).asObject());
	    	colExamenes.setMaxWidth(75);
	    	
	    	TableColumn<Alumno, Double> colPromedio = new TableColumn<>("Promedio");  
	    	colPromedio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPromedio()).asObject());
	    	colPromedio.setMaxWidth(75);
	    	
	    	
	    	tbvAlumnos.getColumns().add(colMatricula);
	    	tbvAlumnos.getColumns().add(colNombre);
	    	tbvAlumnos.getColumns().add(colApellidoP);
	    	tbvAlumnos.getColumns().add(colApellidoM);
	    	tbvAlumnos.getColumns().add(colProyecto);
	    	tbvAlumnos.getColumns().add(colTareas);
	    	tbvAlumnos.getColumns().add(colExamenes);
	    	tbvAlumnos.getColumns().add(colPromedio);
	    	

	    
	    	tbvAlumnos.getItems().addAll(new AlumnoDao().obtenerAlumnos(grupo));
	    	
	    	
	    	lbGrupo.setText("Grupo: "+grupo.getGrupo());
	    	lbIdCurso.setText("Id curso: " + grupo.getId());
	    	lbNombre.setText("Nombre curso: " + (new CursosDao().buscar(new Curso(grupo.getId())).getNombre()));
	    	lbIdProfesor.setText("Id profesor: " + grupo.getIdProfesor());
	    }  

}
