package application.controlador;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.UnaryOperator;

import application.dao.AlumnoDao;
import application.dao.CursosDao;
import application.dao.GruposDao;
import application.modelo.Alumno;
import application.modelo.Curso;
import application.modelo.EnumBusquedas;
import application.modelo.Grupo;
import application.modelo.Profesor;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GrupoProfesorController {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> cboxFiltro;

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
    
    private Profesor profesor;
    
    private Alumno opcion;
    
    public void setGrupo(Grupo grupo) {
    	this.grupo = grupo;
    }
    
    public void setProfesor(Profesor profesor) {
    	this.profesor = profesor;
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
    	    btnEliminar.setDisable(lista.size() == 0);
    	});
    	cboxFiltro.getItems().add("Todos");
    	cboxFiltro.getItems().add("Aprobado");
    	cboxFiltro.getItems().add("Reprobado");
    	
    	
    	cboxFiltro.valueProperty().addListener((obseravble, viejoValor, nuevoValor) ->{
    		EnumBusquedas.FILTRO filtro = null;
    		ArrayList<Alumno> lista;
    		if(!nuevoValor.equals("Todos")) {
    			filtro = (nuevoValor.equals("Aprobado"))? EnumBusquedas.FILTRO.APROBADO : EnumBusquedas.FILTRO.REPROBADO;
    			lista = new AlumnoDao().filtrarAlumnos(grupo, filtro);
    		}else
    			lista = new AlumnoDao().obtenerAlumnos(grupo);
    		
    		tbvAlumnos.getItems().clear();
    		tbvAlumnos.getItems().addAll(lista);
    	});
    	
    	tbvAlumnos.getColumns().clear();
    	
    	TableColumn<Alumno, String> colMatricula = new TableColumn<>("Matricula");
    	colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    	
    	TableColumn<Alumno, String> colNombre = new TableColumn<>("Nombre");
    	colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	
    	TableColumn<Alumno, String> colApellidoP = new TableColumn<>("Apellido Paterno");
    	colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));

    	
    	TableColumn<Alumno, String> colApellidoM = new TableColumn<>("Apellido Materno");
    	colApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));

    	
    	TableColumn<Alumno, Double> colProyecto = new TableColumn<>("Proyecto");  
    	colProyecto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(0)).asObject());
    	colProyecto.setEditable(true);
    	
    	configurarColumna(colProyecto, 0);
    	
    	
    	TableColumn<Alumno, Double> colTareas = new TableColumn<>("Tareas");  
    	colTareas.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(1)).asObject());
    	configurarColumna(colTareas, 1);
    	
    	TableColumn<Alumno, Double> colExamenes = new TableColumn<>("Examenes");  
    	colExamenes.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentaje(2)).asObject());
    	configurarColumna(colExamenes, 2);
    	
    	TableColumn<Alumno, Double> colPromedio = new TableColumn<>("Promedio");  
    	colPromedio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPromedio()).asObject());
    	
    	
    	
    	ArrayList<Alumno> lista = new AlumnoDao().obtenerAlumnos(grupo);
    	tbvAlumnos.getItems().addAll(lista);
    	
    	tbvAlumnos.getColumns().add(colMatricula);
    	tbvAlumnos.getColumns().add(colNombre);
    	tbvAlumnos.getColumns().add(colApellidoP);
    	tbvAlumnos.getColumns().add(colApellidoM);
    	tbvAlumnos.getColumns().add(colProyecto);
    	tbvAlumnos.getColumns().add(colTareas);
    	tbvAlumnos.getColumns().add(colExamenes);
    	tbvAlumnos.getColumns().add(colPromedio);
    	tbvAlumnos.setEditable(true);
    	
    	tbvAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, viejoValor, nuevoValor) -> {
    		opcion = (Alumno) nuevoValor;
        });
    	
    	lbGrupo.setText("Grupo: "+grupo.getGrupo());
    	lbIdCurso.setText("Id curso: " + grupo.getId());
    	lbNombre.setText("Nombre curso: " + (new CursosDao().buscar(new Curso(grupo.getId())).getNombre()));
    	lbIdProfesor.setText("Id profesor: " + grupo.getIdProfesor());
    }

    @FXML
    void btnAgregar_OnClick(ActionEvent event) {
    	Double[] bounds = {475.0, 390.0};
    	FXMLLoader loader;
    	loader = VentanaController.crearVentana("Dar de alta alumno", bounds, "/application/vistas/SceneDarAltaAlumno.fxml");
		DarAltaAlumnoController controlador = loader.getController();
		controlador.setGrupo(grupo);
		controlador.loadVentana();
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();
    }

    @FXML
    void btnCerrar_OnClick(ActionEvent event) {
    	Double[] bounds = {650.0, 450.0};
    	FXMLLoader loader;
		loader = VentanaController.crearVentana("Ventana principal", bounds, "/application/vistas/SceneVentanaPrincipalProfesor.fxml");
		VentanaPrincipalProfesorController controlador = loader.getController();
		controlador.setProfesor(profesor);
		controlador.loadVentana();
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();
    }

    @FXML
    void btnEliminar_OnClick(ActionEvent event) {
    	if(opcion != null) {
    		// creamos la alerta
    	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	    alert.setWidth(350.0);
    	    alert.setHeight(250.0);
    	    alert.setTitle("Confirmación");
    	    alert.setHeaderText("Eliminar grupo");
    	    alert.setContentText("¿Estás seguro que deseas darte de baja de el alumno seleccionado: "
    	        					+ "matricula: "+ opcion.getMatricula() +"?");
    	        
    	      // para obtener el resultado
    	    Optional<ButtonType> result = alert.showAndWait();
    	    Alert confirmation;  
    	    if (result.isPresent() && result.get() == ButtonType.OK) {
    	        new GruposDao().darBajaGrupo(grupo, opcion);
    	        confirmation = new Alert(AlertType.CONFIRMATION, "Dado de baja el alumno: " + opcion.getMatricula(), ButtonType.OK);
    			confirmation.show();
    			tbvAlumnos.getItems().remove(opcion);
    	        	
    	    } else {
    	       confirmation = new Alert(AlertType.CONFIRMATION, "¡Acción cancelada!", ButtonType.OK);
    	       confirmation.show();
    	    }
    	}
    }
    
    
    private void configurarColumna(TableColumn<Alumno, Double> columna, int index) {
    	columna.setCellFactory(column -> {
    	    TableCell<Alumno, Double> cell = new TableCell<Alumno, Double>() {
    	        private final TextField textField = new TextField(); // por cada celda crearemos un TextField

    	        {
    	            // Configuramos un flitro para que el texto solo acepte números
    	            UnaryOperator<TextFormatter.Change> filter = change -> {
    	                String newText = change.getControlNewText();
    	                if (newText.isEmpty() || newText.matches("-?\\d*\\.?\\d*")) {
    	                    return change;
    	                }
    	                return null;
    	            };
    	            // creamos un formato para la TextField y le asignamos el filtro
    	            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    	            textField.setTextFormatter(textFormatter);

    	            textField.setOnAction(event -> { // agregamos el evento para cuando se de enter
    	                try {
    	                    Double newValue = Double.parseDouble(textField.getText()); // convertimos el valor a double
    	                    if (newValue >= 0 && newValue <= 10) { // si esta dentro del rango la celda se editará
    	                        commitEdit(newValue);
    	                    } else {
    	                        cancelEdit(); // en caso contrario se cancela la edición
    	                    }
    	                } catch (NumberFormatException e) {
    	                    cancelEdit(); // en caso de que no sea número se cancela la edición
    	                }
    	            });
    	            // hace lo mismo que la función de arriba pero en lugar de al darle enter o clic se hará al perder el foco de edición
    	            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
    	                if (!isNowFocused) {
    	                    try {
    	                        Double newValue = Double.parseDouble(textField.getText());
    	                        if (newValue >= 0 && newValue <= 10) {
    	                            commitEdit(newValue);
    	                        } else {
    	                            cancelEdit();
    	                        }
    	                    } catch (NumberFormatException e) {
    	                        cancelEdit();
    	                    }
    	                }
    	            }); 	            
    	        }
    	        // sobreescribimos los metodos de la celda para personalizar su comportamiento
    	        @Override
    	        protected void updateItem(Double item, boolean empty) {
    	            super.updateItem(item, empty);
    	            if (empty) {
    	                setText(null);
    	                setGraphic(null);
    	            } else {
    	                if (isEditing()) {
    	                    textField.setText(item.toString());
    	                    setText(null);
    	                    setGraphic(textField);
    	                } else {
    	                    setText(item != null ? item.toString() : "");
    	                    setGraphic(null);
    	                }
    	            }
    	        }

    	        @Override
    	        public void startEdit() {
    	        	super.startEdit();
    	            if (getItem() != null) {
    	                textField.setText(getItem().toString());
    	            } else {
    	                textField.setText("");
    	            }
    	            setText(null);
    	            setGraphic(textField);
    	            textField.selectAll();
    	        }

    	        @Override
    	        public void cancelEdit() {
    	        	super.cancelEdit();
    	            setText(getItem() != null ? getItem().toString() : "");
    	            setGraphic(null);
    	        }

    	        @Override
    	        public void commitEdit(Double newValue) {
    	        	super.commitEdit(newValue);
    	            setText(newValue.toString());
    	            setGraphic(null);
    	        }
    	    };

    	    cell.itemProperty().addListener((obs, oldValue, newValue) -> {
    	        cell.setEditable(newValue != null);
    	    });

    	    return cell;
    	});

    	// Manejar el evento de commit para actualizar los datos del alumno
    	columna.setOnEditCommit(event -> {
    	    Alumno alumno = event.getRowValue();
    	    Double nuevoValor = event.getNewValue();

    	    // Actualizar los datos del alumno
    	    alumno.setPorcentaje(index, nuevoValor);
    	    alumno.calcularPromedio();
    	    new GruposDao().actualizarCalificaciones(grupo, alumno);
    	    tbvAlumnos.refresh();
    	    System.out.println(alumno);
    	});
    }

}
