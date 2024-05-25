package application.controlador;

import application.modelo.Alumno;
import application.modelo.Grupo;

import java.util.ArrayList;

import application.dao.GruposDao;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GrupoAlumnoController {
	
	   @FXML
	    private ComboBox<String> cboxBusqueda;

	    @FXML
	    private TableColumn<Alumno, String> colApellidoM;

	    @FXML
	    private TableColumn<Alumno, String> colApellidoP;

	    @FXML
	    private TableColumn<Alumno, Double> colExamenes;

	    @FXML
	    private TableColumn<Alumno, String> colMatricula;

	    @FXML
	    private TableColumn<Alumno, String> colNombre;

	    @FXML
	    private TableColumn<Alumno, Double> colPromedio;

	    @FXML
	    private TableColumn<Alumno, Double> colProyecto;

	    @FXML
	    private TableColumn<Alumno, Double> colTareas;

	    @FXML
	    private Label lbGrupo;

	    @FXML
	    private Label lbIdCurso;

	    @FXML
	    private Label lbIdGrupo;

	    @FXML
	    private Label lbNombre;

	    @FXML
	    private TableView<Alumno> tbvAlumnos;

	    @FXML
	    private TextField tfBusqueda;
	    
	    private Grupo grupo;
	    
	    public void setGrupo(Grupo grupo) {
	    	this.grupo = grupo;
	    }
	    

	    @FXML
	    void colExamenes_EditFinish(ActionEvent event) {

	    }

	    @FXML
	    void colExamenes_EditStart(ActionEvent event) {

	    }

	    @FXML
	    void colProyecto_EditFinish(ActionEvent event) {

	    }

	    @FXML
	    void colProyecto_EditStart(ActionEvent event) {

	    }

	    @FXML
	    void colTareas_EditFinish(ActionEvent event) {

	    }

	    @FXML
	    void colTareas_EditStart(ActionEvent event) {

	    }
	    
	    public void inicialiar() {
	    	tbvAlumnos.getColumns().clear();
	    	
	    	colMatricula = new TableColumn<>("Matricula");
	    	colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
	    	colMatricula.setMaxWidth(100);
	    	
	    	colNombre = new TableColumn<>("Nombre");
	    	colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    	colNombre.setMaxWidth(200);
	    	
	    	colApellidoP = new TableColumn<>("Apellido Paterno");
	    	colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
	    	colApellidoP.setMaxWidth(150);
	    	
	    	colApellidoM = new TableColumn<>("Apellido Materno");
	    	colApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
	    	colApellidoM.setMaxWidth(150);
	    	
	    	colProyecto = new TableColumn<>("Proyecto");  
	    	colProyecto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(0)).asObject());
	    	colProyecto.setMaxWidth(75);
	    	
	    	colTareas = new TableColumn<>("Tareas");  
	    	colTareas.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(1)).asObject());
	    	colTareas.setMaxWidth(75);
	    	
	    	colExamenes = new TableColumn<>("Examenes");  
	    	colExamenes.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(2)).asObject());
	    	colExamenes.setMaxWidth(75);
	    	
	    	
	    	tbvAlumnos.getColumns().add(colMatricula);
	    	tbvAlumnos.getColumns().add(colNombre);
	    	tbvAlumnos.getColumns().add(colApellidoP);
	    	tbvAlumnos.getColumns().add(colApellidoM);
	    	tbvAlumnos.getColumns().add(colProyecto);
	    	tbvAlumnos.getColumns().add(colTareas);
	    	tbvAlumnos.getColumns().add(colExamenes);
	    	

	    	
	    	ArrayList<Alumno> lista = new GruposDao().obtenerAlumnos(grupo);
	    	for(Alumno alumno: lista)
	    		tbvAlumnos.getItems().add(alumno);
	    	
	    }  

}
