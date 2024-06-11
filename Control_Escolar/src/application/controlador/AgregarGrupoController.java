package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import application.modelo.Curso;
import application.modelo.Grupo;
import application.modelo.Profesor;
import application.dao.CursosDao;
import application.dao.GruposDao;
import application.dao.ProfesorDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AgregarGrupoController implements Initializable{

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

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
    
    private Curso curso;
    
    private Profesor profesor;
    
    private int grupo;
    // botón de agregar grupo
    @FXML
    void btnAgregar_OnClick(ActionEvent event) {
    	Alert alerta = null;
    	if(curso == null || profesor == null) { // verifica que el profesor y el curso existe
    		alerta = new Alert(AlertType.ERROR, "¡Hay datos incorrectos, revisa por favor!", ButtonType.OK);
    		alerta.show();
    		return;
    	}
    	Grupo grupoNuevo = new Grupo(curso.getId(), grupo, profesor.getId(), 0);
		if(new GruposDao().buscar(grupoNuevo) == null) { // verifiamos que el nuevo grupo no existe
			if(new GruposDao().obtenerGrupos(profesor).size() < 3) {
				new GruposDao().insertar(grupoNuevo); // se agrega el grupo
				alerta = new Alert(AlertType.INFORMATION, "¡Id: " + grupoNuevo.getId() + " grupo: " + grupoNuevo.getGrupo() +" abierto con exíto!", ButtonType.OK);
			}else
				alerta = new Alert(AlertType.ERROR, "¡El profesor " + profesor.getId() + " ya esta impartiendo 3 grupos que es el maximo!", ButtonType.OK);
		}else
			alerta = new Alert(AlertType.ERROR, "¡Id: " + grupoNuevo.getId() + " grupo: " + grupoNuevo.getGrupo() + " ya esta registrado!", ButtonType.OK);
    	alerta.show();// se muestra la alerta
    	// se restablecen los labes y las valores
    	lbNombreCurso.setText("Nombre:");
		lbColegio.setText("Colegio:");
		lbCiclo.setText("Ciclo:");
		
		lbNombreProfesor.setText("Nombre:");
		lbApellidoP.setText("Apellido paterno:");
		lbApellidoM.setText("Apellido materno:");
		
		spnIdCurso.getValueFactory().setValue(0);
		spnIdProfesor.getValueFactory().setValue(0);
		spnGrupo.getValueFactory().setValue(1);
    }
    
    // botón para regresar a la ventana anterior
    @FXML
    void btnCancelar_OnClick(ActionEvent event) {
    	VentanaController.crearVentana("Alumnos en sistema", new Double[]{550.0, 400.0}, "/application/vistas/SceneGruposSistema.fxml");
    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
    	stage.close();
    }
    // metodo de inicializar ventana
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 // asignamos rango a los spinners
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500);
		SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200);
		SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200);
		spnGrupo.setValueFactory(valueFactory);
		spnIdProfesor.setValueFactory(valueFactory2);
		spnIdCurso.setValueFactory(valueFactory3);
		
		// obtenemos el cambio en el valor del spinner grupo
		spnGrupo.valueProperty().addListener((observable, viejoValor, nuevoValor)->{
			grupo = nuevoValor;
		});
		
		// actualizamos los labels con el cambio de el valor del spnIdCurso en la BD
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
		// busca al profesor con el cambio del valor del spnIdProfesor en la BD
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

}
