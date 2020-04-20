package org.iesalandalus.programacion.tutorias.mvc.vista.grafica;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControladorPrincipal implements Initializable {

	private IControlador controladorMVC;

	// ER

	private static final String ER_OBLIGATORIO = ".+";

	private static final String ER_CORREO = "([\\w\\.]+[^.])@[\\w^\\_]+\\.[a-z]{2,3}";
	private static final String ER_NOMBRE = "([a-zA-ZÁÉÍÓÚáéíóú]+)(\\s+([a-zA-ZÁÉÍÓÚáéíóú]+))+";

	private static final String ER_DNI = "\\d{8}[A-Za-z]";

	// TableViews

	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private FilteredList<Alumno> alumnosFiltrados = new FilteredList<>(alumnos, p -> true);
	
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private FilteredList<Profesor> profesoresFiltrados = new FilteredList<>(profesores, p -> true);

	@FXML
	private TableView<Alumno> tvAlumnado;
	@FXML
	private TableView<Profesor> tvProfesorado;

	@FXML
	private TableColumn<Alumno, String> tcNombre;
	@FXML
	private TableColumn<Alumno, String> tcCorreo;
	@FXML
	private TableColumn<Alumno, String> tcExpediente;
	
	@FXML
	private TableColumn<Profesor, String> tcNombreP;
	@FXML
	private TableColumn<Profesor, String> tcCorreoP;
	@FXML
	private TableColumn<Profesor, String> tcDni;

	// TextFields
	@FXML
	private TextField tfNombreAlumno;
	@FXML
	private TextField tfCorreo;
	@FXML
	private TextField buscarAlumno;
	@FXML 
	private TextField buscarProfesor;
@FXML
private TextField tfNombreProfesor;
@FXML
private TextField tfDni;
@FXML
private TextField tfCorreoProfesor;
	// Botones y Paneles
	@FXML
	private ImageView botonVolverAlumnado;
	@FXML
	private Button botonSalir, botonAlumnado, botonProfesorado, botonTutorias, botonSesiones, botonCitas, botonAcercaDe,
	botonAceptarAlumno, botonAceptarProfesor;
	@FXML
	private BorderPane panelRaiz;
	@FXML
	private AnchorPane panelAlumnado;
	@FXML
	private AnchorPane panelCrearAlumno;
	@FXML
	private AnchorPane panelProfesorado;
	@FXML
	private AnchorPane panelCrearProfesor;

	public void setControlador(IControlador controlador) {
		this.controladorMVC = controlador;
	}

	public void actualizaTablas() {
		alumnos.setAll(controladorMVC.getAlumnos());
		tvAlumnado.setPlaceholder(new Label("No hay alumnos para mostrar."));
		
		profesores.setAll(controladorMVC.getProfesores());
		tvProfesorado.setPlaceholder(new Label("No hay profesores para mostrar."));
	}

	private void filtrarTablas() {
		// filtrar alumnos

		buscarAlumno.textProperty().addListener((prop, old, text) -> {
			alumnosFiltrados.setPredicate(alumno -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = alumno.getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});
		
		buscarProfesor.textProperty().addListener((prop, old, text) -> {
			profesoresFiltrados.setPredicate(profesor -> {
				if (text == null || text.isEmpty())
					return true;

				String nombre = profesor.getNombre().toLowerCase();
				return nombre.contains(text.toLowerCase());
			});

		});
	}

	private void limpiaFormulario() {
		tfNombreAlumno.setText("");

		tfCorreo.setText("");

	}


	// Menú Principal

	@FXML
	private void irACrear(ActionEvent e) {
		if(panelAlumnado==(AnchorPane)e.getSource())
		panelCrearAlumno.toFront();
		if(panelProfesorado==(AnchorPane)e.getSource())
			panelCrearProfesor.toFront();
	}

	@FXML
	private void salir(ActionEvent e) {

		Stage escenarioPrincipal = (Stage) botonSalir.getScene().getWindow();

		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir?", null)) {
			controladorMVC.terminar();
			escenarioPrincipal.close();

		} else {
			e.consume();
		}

	}

	private void cambiarEstilo(Button botonElegido) {
		Button[] botones = { botonAlumnado, botonProfesorado, botonTutorias, botonSesiones, botonCitas, botonAcercaDe };

		for (Button boton : botones) {
			if (boton != botonElegido) {
				boton.setId("botonMenu");
			}
		}
	}

	@FXML
	private void elegirBotonMenu(ActionEvent evento) {
		Button botonPulsado = (Button) evento.getSource();

		if (botonPulsado == botonAlumnado) {
			botonAlumnado.setId("botonPulsado");
			cambiarEstilo(botonAlumnado);
			panelAlumnado.toFront();

		}

		if (botonPulsado == botonProfesorado) {
			botonProfesorado.setId("botonPulsado");
			cambiarEstilo(botonProfesorado);
			panelProfesorado.toFront();
		}

		if (botonPulsado == botonTutorias) {
			botonTutorias.setId("botonPulsado");
			cambiarEstilo(botonTutorias);
		}

		if (botonPulsado == botonSesiones) {
			botonSesiones.setId("botonPulsado");
			cambiarEstilo(botonSesiones);
		}

		if (botonPulsado == botonCitas) {
			botonCitas.setId("botonPulsado");
			cambiarEstilo(botonCitas);
		}

		if (botonPulsado == botonAcercaDe) {
			botonAcercaDe.setId("botonPulsado");
			cambiarEstilo(botonAcercaDe);
		}
	}

	@FXML
	private void volver(MouseEvent evento) {
		ImageView botonPulsado = (ImageView) evento.getSource();

		if (botonPulsado == botonVolverAlumnado) {
			limpiaFormulario();
			panelAlumnado.toFront();
		}
	}

	// Creación objetos

	@FXML
	private void aceptarYCrear(ActionEvent evento) {
		Button botonPulsado = (Button) evento.getSource();

		if (botonPulsado == botonAceptarAlumno) {
			crearAlumno();
		}
	}

	private void compruebaCampoTexto(String er, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green;");
		} else if (texto.matches("")) {
			campoTexto.setStyle("-fx-border-color: gray;");
		} else {
			campoTexto.setStyle("-fx-border-color: red;");
		}
	}

	private void observarTextFields() {
		tfNombreAlumno.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombreAlumno));
		tfCorreo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreo));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		iniciarTablaAlumnos();
		observarTextFields();
		filtrarTablas();
		menuContextualAlumnado();

	}

	// Alumnos

	private void iniciarTablaAlumnos() {
		tcNombre.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getNombre()));
		tcCorreo.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getCorreo()));
		tcExpediente.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getExpediente()));

		SortedList<Alumno> alumnosOrdenados = new SortedList<>(alumnosFiltrados);
		alumnosOrdenados.comparatorProperty().bind(tvAlumnado.comparatorProperty());
		tvAlumnado.setItems(alumnosOrdenados);

	}

	
	private void crearAlumno() {
		Alumno alumno = null;
		try {
			alumno = getAlumno();
			controladorMVC.insertar(alumno);
			alumnos.setAll(controladorMVC.getAlumnos());
			Dialogos.mostrarDialogoInformacion("", "Alumno añadido satisfactoriamente.", null);
			limpiaFormulario();
			panelAlumnado.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}


	@FXML
	private void borrarAlumno() throws OperationNotSupportedException {
		Alumno alumno = (Alumno) tvAlumnado.getSelectionModel().getSelectedItem();
		if (alumno == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un alumno de la tabla.");
		} else {
			String nombre = alumno.getNombre();
			if (Dialogos.mostrarDialogoConfirmacion("",
					"¿Estás seguro de que quieres eliminar al alumno " + nombre + "?", null)) {
				controladorMVC.borrar(alumno);
				Dialogos.mostrarDialogoAdvertencia("", "Alumno borrado satisfactoriamente");
				actualizaTablas();
			}
		}
	}

	@FXML
	private void verAlumno() {

		Alumno alumno = (Alumno) tvAlumnado.getSelectionModel().getSelectedItem();
		if (alumno == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un alumno de la tabla.");
		} else {
			GridPane panelVerAlumno = new GridPane();
			panelVerAlumno.getStyleClass().add("gridPaneAlert");
			TextField nombre = new TextField();
			TextField correo = new TextField();
			TextField expediente = new TextField();
			nombre.setEditable(false);
			correo.setEditable(false);
			expediente.setEditable(false);
			nombre.setText(alumno.getNombre());
			correo.setText(alumno.getCorreo());
			expediente.setText(alumno.getExpediente());
			nombre.getStyleClass().addAll("textoPlano", "textoVer");
			correo.getStyleClass().addAll("textoPlano", "textoVer");
			expediente.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerAlumno.add(new Text("Nombre:"), 1, 0);
			panelVerAlumno.add(nombre, 2, 0);
			panelVerAlumno.add(new Text("Correo:"), 1, 1);
			panelVerAlumno.add(correo, 2, 1);
			panelVerAlumno.add(new Text("Expediente:"), 1, 2);
			panelVerAlumno.add(expediente, 2, 2);

			Dialogos.mostrarDialogoInformacionPersonalizado("Alumno", panelVerAlumno);

		}
	}

	private Alumno getAlumno() {
		Alumno alumno = null;
		try {
			String nombre = tfNombreAlumno.getText();
			String correo = tfCorreo.getText();
			alumno = new Alumno(nombre, correo);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("Añadir alumno", e.getMessage());
		}
		return alumno;
	}

	private void menuContextualAlumnado(){
		ContextMenu cm = new ContextMenu();
		MenuItem ver = new MenuItem("Ver");
		cm.getItems().add(ver);
		MenuItem borrar = new MenuItem("Borrar");
		cm.getItems().add(borrar);

		tvAlumnado.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					cm.show(tvAlumnado, t.getScreenX(), t.getScreenY());
				}
			}
		});

		tvAlumnado.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					cm.show(tvAlumnado, t.getScreenX(), t.getScreenY());
				}
			}
		});

		ver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				verAlumno();

			}
		});

		borrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					borrarAlumno();
				} catch (OperationNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

		});
	}
	// Profesorado
	
	private void iniciarTablaProfesores() {
		tcNombreP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		tcCorreoP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		tcDni.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getDni()));

		SortedList<Profesor> profesoresOrdenados = new SortedList<>(profesoresFiltrados);
		profesoresOrdenados.comparatorProperty().bind(tvProfesorado.comparatorProperty());
		tvProfesorado.setItems(profesoresOrdenados);

	}

	
	private void crearProfesor() {
		Profesor profesor = null;
		try {
			profesor = getProfesor();
			controladorMVC.insertar(profesor);
			profesores.setAll(controladorMVC.getProfesores());
			Dialogos.mostrarDialogoInformacion("", "Profesor añadido satisfactoriamente.", null);
			limpiaFormulario();
			panelProfesorado.toFront();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("", e.getMessage());
		}
	}


	@FXML
	private void borrarProfesor() throws OperationNotSupportedException {
		Profesor profesor = (Profesor) tvProfesorado.getSelectionModel().getSelectedItem();
		if (profesor == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un profesor de la tabla.");
		} else {
			String nombre = profesor.getNombre();
			if (Dialogos.mostrarDialogoConfirmacion("",
					"¿Estás seguro de que quieres eliminar al profesor " + nombre + "?", null)) {
				controladorMVC.borrar(profesor);
				Dialogos.mostrarDialogoAdvertencia("", "Profesor borrado satisfactoriamente");
				actualizaTablas();
			}
		}
	}

	@FXML
	private void verProfesor() {

		Profesor profesor = (Profesor) tvAlumnado.getSelectionModel().getSelectedItem();
		if (alumno == null) {
			Dialogos.mostrarDialogoAdvertencia("", "Debes seleccionar un alumno de la tabla.");
		} else {
			GridPane panelVerAlumno = new GridPane();
			panelVerAlumno.getStyleClass().add("gridPaneAlert");
			TextField nombre = new TextField();
			TextField correo = new TextField();
			TextField expediente = new TextField();
			nombre.setEditable(false);
			correo.setEditable(false);
			expediente.setEditable(false);
			nombre.setText(alumno.getNombre());
			correo.setText(alumno.getCorreo());
			expediente.setText(alumno.getExpediente());
			nombre.getStyleClass().addAll("textoPlano", "textoVer");
			correo.getStyleClass().addAll("textoPlano", "textoVer");
			expediente.getStyleClass().addAll("textoPlano", "textoVer");
			panelVerAlumno.add(new Text("Nombre:"), 1, 0);
			panelVerAlumno.add(nombre, 2, 0);
			panelVerAlumno.add(new Text("Correo:"), 1, 1);
			panelVerAlumno.add(correo, 2, 1);
			panelVerAlumno.add(new Text("Expediente:"), 1, 2);
			panelVerAlumno.add(expediente, 2, 2);

			Dialogos.mostrarDialogoInformacionPersonalizado("Alumno", panelVerAlumno);

		}
	}

	private Alumno getAlumno() {
		Alumno alumno = null;
		try {
			String nombre = tfNombreAlumno.getText();
			String correo = tfCorreo.getText();
			alumno = new Alumno(nombre, correo);
		} catch (NumberFormatException e) {
			Dialogos.mostrarDialogoError("Añadir alumno", e.getMessage());
		}
		return alumno;
	}

	private void menuContextualAlumnado(){
		ContextMenu cm = new ContextMenu();
		MenuItem ver = new MenuItem("Ver");
		cm.getItems().add(ver);
		MenuItem borrar = new MenuItem("Borrar");
		cm.getItems().add(borrar);

		tvAlumnado.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					cm.show(tvAlumnado, t.getScreenX(), t.getScreenY());
				}
			}
		});

		tvAlumnado.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					cm.show(tvAlumnado, t.getScreenX(), t.getScreenY());
				}
			}
		});

		ver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				verAlumno();

			}
		});

		borrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					borrarAlumno();
				} catch (OperationNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

		});
	}

	// Tutorías

	// Sesiones

	// Citas

}
