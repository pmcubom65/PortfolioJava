package com.pedromanuelcubo.miaplicacion.miaplicacion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

import javafx.css.converter.URLConverter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import modelo.*;
//import vista.*;

@Component
public class ControladorPrimero {

	@FXML
	private TextField t1;

	@FXML
	private TextField t2;

	@FXML
	private Button botonInicio;
	@FXML
	private Label mensajes;
	
	private final Resource fxml;
	private final ApplicationContext ac;
	private final String applicationTitle;
	
  

	
	public ControladorPrimero(@Value("${spring.application.ui.title}") String applicationTitle,
			@Value("classpath:/FXMLSegunda.fxml") Resource resource, ApplicationContext ac) {
		this.applicationTitle=applicationTitle;
		this.fxml=resource;
		this.ac=ac;
	}
	
	


	@FXML
	public void hacerClick(ActionEvent event) throws IOException {

		if (t1.getText().equalsIgnoreCase(t2.getText())) {
			
			mensajes.setText("Los nombres de los jugadores no pueden ser iguales");
		} else if (!t1.getText().isEmpty() && !t2.getText().isEmpty()) {

		//	FXMLLoader loader = new FXMLLoader();
			
			
			URL url=this.fxml.getURL();
			
			FXMLLoader loader=new FXMLLoader(url);
			
		//	loader.setLocation(getClass().getResource("FXMLSegunda.fxml"));

			Parent nuevoparent = loader.load();
			Scene nuevaescena = new Scene(nuevoparent, 1920, 1080);
		
			nuevaescena.getStylesheets().add("application.css");

			ControladorSegundo micontrolador = loader.getController();
			micontrolador.datosIniciales(t1.getText(), t2.getText());

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
			window.setScene(nuevaescena);

			window.show();
		} else {

			mensajes.setText("Tiene que poner ambos nombres para empezar");

		}

	}

}
