package application.controlador;

import application.modelo.Curso;
import application.modelo.Grupo;
import application.modelo.Profesor;
import application.dao.CursosDao;
import application.dao.GruposDao;
import application.dao.ProfesorDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ActualizarGrupoController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private CheckBox chboxEditar;

    @FXML
    private Label lbApellidoM;

    @FXML
    private Label lbApellidoP;

    @FXML
    private Label lbCiclo;

    @FXML
    private Label lbColegio;

    @FXML
    private Label lbNombreCurso;

    @FXML
    private Label lbNombreProfesor;

    @FXML
    private Spinner<Integer> spnGrupo;

    @FXML
    private Spinner<Integer> spnIdCurso;

    @FXML
    private Spinner<Integer> spnIdProfesor;
    
    private Grupo grupo;
    
    private Curso curso;
    
    private Profesor profesor;
    
    public void setGrupo(Grupo grupo) {
    	this.grupo = grupo;
    }
    
    @FXML
    void btnActualizar_OnClick(ActionEvent event) {
    	Alert alerta = null;
    	if(curso == null || profesor == null) {
    		alerta = new Alert(AlertType.ERROR, "¡Hay datos incorrectos, revisa por favor!", ButtonType.OK);
    		alerta.show();
    		return;
    	}
    	Grupo grupoNuevo = new Grupo(curso.getId(), spnGrupo.getValue(), profesor.getId(), 0);
    	boolean existe = true;
    	boolean profesorValido = true;
    	if(grupoNuevo.getId() != grupo.getId() || grupoNuevo.getGrupo() != grupo.getGrupo())
    		existe = (new GruposDao().buscar(grupoNuevo) != null);
    	
    	if(grupo.getIdProfesor() != grupoNuevo.getIdProfesor())
    		profesorValido = (new  GruposDao().obtenerGrupos(profesor).size() < 3);
    	
		if(!existe){
			if(profesorValido) {
				System.out.println(grupo);
				new GruposDao().actualizar(grupoNuevo, grupo);
				alerta = new Alert(AlertType.INFORMATION, "¡Grupo actualizado con exito!", ButtonType.OK);
				alerta.show();
				btnCancelar_OnClick(null);
			}else
				alerta = new Alert(AlertType.ERROR, "¡El profesor " + profesor.getId() + " ya esta impartiendo 3 grupos que es el maximo!", ButtonType.OK);
		}else
			alerta = new Alert(AlertType.ERROR, "¡Id: " + grupoNuevo.getId() + " grupo: " + grupoNuevo.getGrupo() + " ya esta registrado!", ButtonType.OK);
    	
    	alerta.show();
    
    	datosGrupo();
    }

    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Alumnos en sistema", new Double[]{550.0, 400.0}, "/application/vistas/SceneGruposSistema.fxml");
    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void chboxEditar_OnClick(ActionEvent event) {
    	btnActualizar.setDisable(!chboxEditar.isSelected());
    	spnGrupo.setDisable(!chboxEditar.isSelected());
    	spnIdCurso.setDisable(!chboxEditar.isSelected());
    	spnIdProfesor.setDisable(!chboxEditar.isSelected());
    }
    
    public void loadVentana() {
    	datosGrupo();
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, grupo.getGrupo());
		SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, grupo.getIdProfesor());
		SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, grupo.getId());
		spnGrupo.setValueFactory(valueFactory);
		spnIdProfesor.setValueFactory(valueFactory2);
		spnIdCurso.setValueFactory(valueFactory3);
		
    	btnActualizar.setDisable(true);
    	spnGrupo.setDisable(true);
    	spnIdCurso.setDisable(true);
    	spnIdProfesor.setDisable(true);
    	
    	spnIdCurso.valueProperty().addListener((observable, viejoValor, nuevoValor)->{
			curso = new CursosDao().buscar(new Curso(nuevoValor));
			if(curso != null) {
				lbNombreCurso.setText("Nombre: " + curso.getNombre());
				lbColegio.setText("Colegio: " + curso.getColegio());
				lbCiclo.setText("Ciclo: " + curso.getCiclo());
			}else {
				lbNombreCurso.setText("Nombre:");
				lbColegio.setText("Colegio:");
				lbCiclo.setText("Ciclo:");
			}
		});
		
		spnIdProfesor.valueProperty().addListener((observable, viejoValor, nuevoValor)->{
			profesor = new ProfesorDao().buscar(new Profesor(nuevoValor));
			if(profesor != null) {
				lbNombreProfesor.setText("Nombre: " + profesor.getNombre());
				lbApellidoP.setText("Apellido paterno: " + profesor.getApellidoPaterno());
				lbApellidoM.setText("Apellido materno: " + profesor.getApellidoMaterno());
			}else {
				lbNombreProfesor.setText("Nombre:");
				lbApellidoP.setText("Apellido paterno:");
				lbApellidoM.setText("Apellido materno:");
			}
		});
		
    }
    private void datosGrupo() {
    	profesor = new ProfesorDao().buscar(new Profesor(grupo.getIdProfesor()));
    	curso = new CursosDao().buscar(new Curso(grupo.getId()));
    	
    	lbNombreCurso.setText("Nombre: " + curso.getNombre());
    	lbCiclo.setText("Ciclo: " + curso.getCiclo());
    	lbColegio.setText("Colegio: " + curso.getColegio());
    	
    	lbNombreProfesor.setText("Nombre: " + profesor.getNombre());
    	lbApellidoP.setText("Apellido paterno: " + profesor.getApellidoPaterno());
    	lbApellidoM.setText("Apellido materno: " + profesor.getApellidoMaterno());
    }
}
