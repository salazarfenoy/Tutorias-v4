package org.iesalandalus.programacion.tutorias;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainAppIUGrafica extends Application {

	double x, y;

	private void addDragListeners(final Node n, Stage primaryStage){

	    n.setOnMousePressed((MouseEvent mouseEvent) -> {
	        this.x = n.getScene().getWindow().getX() - mouseEvent.getScreenX();
	        this.y = n.getScene().getWindow().getY() - mouseEvent.getScreenY();
	    });

	    n.setOnMouseDragged((MouseEvent mouseEvent) -> {
	        primaryStage.setX(mouseEvent.getScreenX() + this.x);
	        primaryStage.setY(mouseEvent.getScreenY() + this.y);
	    });
	}
	
	
	@Override
	public void start(Stage escenarioPrincipal) {
		try {
			BorderPane raiz = FXMLLoader.load(getClass().getResource("mvc/vista/grafica/VentanaPrincipal.fxml"));
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.initStyle(StageStyle.UNDECORATED);
			addDragListeners(raiz,escenarioPrincipal);
			escenarioPrincipal.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
