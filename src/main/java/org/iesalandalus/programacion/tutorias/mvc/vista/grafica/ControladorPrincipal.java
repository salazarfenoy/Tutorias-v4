package org.iesalandalus.programacion.tutorias.mvc.vista.grafica;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Alumnos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorPrincipal implements Initializable {

	@FXML private TableView<Alumno> tvAlumnado;

	@FXML private TableColumn<Alumno, String> tcNombre;
	@FXML private TableColumn<Alumno, String> tcCorreo;
	@FXML private TableColumn<Alumno, String> tcExpediente;
	
	
	@FXML private Button botonSalir, botonAlumnado, botonProfesorado, botonTutorias, botonSesiones, botonCitas, botonAcercaDe;
	@FXML private BorderPane panelRaiz;
	@FXML private AnchorPane panelAlumnado;
	
	
	
	
	
	private void cambiarEstilo(Button botonElegido) {
		Button[] botones = {botonAlumnado,botonProfesorado,botonTutorias,botonSesiones,botonCitas,botonAcercaDe};
	
		for(Button boton: botones) {
			if(boton!=botonElegido) {
				boton.setId("botonMenu");
			}
		}
	}
	
	@FXML
	private void elegirBotonMenu(ActionEvent evento){
		Button botonPulsado = (Button)evento.getSource();
		
		
		if(botonPulsado == botonAlumnado) {
			botonAlumnado.setId("botonPulsado");
			cambiarEstilo(botonAlumnado);
			panelAlumnado.toFront();
		}
		
		if(botonPulsado == botonProfesorado) {
			botonProfesorado.setId("botonPulsado");
			cambiarEstilo(botonProfesorado);
		}
		
		if(botonPulsado == botonTutorias) {
			botonTutorias.setId("botonPulsado");
			cambiarEstilo(botonTutorias);
		}
		
		if(botonPulsado == botonSesiones) {
			botonSesiones.setId("botonPulsado");
			cambiarEstilo(botonSesiones);
		}
		
		if(botonPulsado == botonCitas) {
			botonCitas.setId("botonPulsado");
			cambiarEstilo(botonCitas);
		}
		
		if(botonPulsado == botonAcercaDe) {
			botonAcercaDe.setId("botonPulsado");
			cambiarEstilo(botonAcercaDe);
		}
	}
	
	@FXML
	private void salir() {
		Stage escenario = (Stage) panelRaiz.getScene().getWindow();
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		dialogo.initStyle(StageStyle.UNDECORATED);
	    DialogPane dialogPane = dialogo.getDialogPane();
		dialogo.setTitle("Salir");
		dialogo.setHeaderText(null);
		dialogo.setContentText("¿Estás seguro de que quieres salir?");
		
		dialogPane.getStylesheets().add(getClass().getResource("estilos/estilo1.css").toExternalForm());
		dialogPane.getStyleClass().add("salir");
		

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		if (respuesta.isPresent() && respuesta.get() == ButtonType.OK){
		   escenario.close();
		} else {
		    dialogo.close();
		}
	}
	
	
	private IControlador controladorMVC;

	
	
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private FilteredList<Alumno> alumnosFiltrados = new FilteredList<>(alumnos, p -> true);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		tcNombre.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getNombre()));
		tcCorreo.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getCorreo()));
		tcExpediente.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getExpediente()));
		
		
		SortedList<Alumno> alumnosOrdenados = new SortedList<>(alumnosFiltrados);
		alumnosOrdenados.comparatorProperty().bind(tvAlumnado.comparatorProperty());
		tvAlumnado.setItems(alumnosOrdenados);
		
		
	}
	
}
