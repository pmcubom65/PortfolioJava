package com.pedromanuelcubo.miaplicacion.miaplicacion;


import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.gluonhq.charm.glisten.layout.Layer;


import javafx.stage.Stage;

@Component
public class ControladorFinal {

	
	   @FXML
	    private Button botonsi;

	    @FXML
	    private Button botonno;

	    @FXML
	    private Label resultado;
	    
	    private String nombre1;
	    private String nombre2;

	    @FXML
	    private Layer capa1;

	    @FXML
	    private Layer capa2;
	    
	

	    @FXML
	   public void clickSi(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/FXMLSegunda.fxml"));

			Parent nuevoparent = loader.load();
			Scene nuevaescena = new Scene(nuevoparent, 1920, 1080);
		//	nuevaescena.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());

			
			nuevaescena.getStylesheets().add("application.css");
			ControladorSegundo micontrolador = loader.getController();
			micontrolador.datosIniciales(nombre1, nombre2);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(nuevaescena);

			window.show();
	    }

	    @FXML
	    public void clickNo(ActionEvent event) {
	    		System.exit(0);
	    }

	
	    public void iniciarDatos(String p1, String p2, int p) throws IOException {
	    	resultado.setStyle("-fx-font-size : 60px");
		
			resultado.setText("Ha ganado " + p1);

			
			if (p==1) {
				nombre1=p1;
				nombre2=p2;
				resultado.setStyle("-fx-font-size : 60px");
				resultado.setAlignment(Pos.CENTER_LEFT);
				capa1.setStyle("-fx-background-color : rgba(0,0,0,0.7)");
	
			} if (p==2) {
				nombre1=p2;
				nombre2=p1;
				resultado.setAlignment(Pos.CENTER_RIGHT);
				capa2.setStyle("-fx-background-color : rgba(0,0,0,0.7)");

			}
			
			
	    }
}

