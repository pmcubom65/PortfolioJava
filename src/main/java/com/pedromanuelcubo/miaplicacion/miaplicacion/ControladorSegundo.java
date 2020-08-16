package com.pedromanuelcubo.miaplicacion.miaplicacion;


import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.Jugador;
//import vista.*;


@Component
public class ControladorSegundo implements Initializable {
	private Jugador j1 = null;
	private Jugador j2 = null;
	private Juego j = null;
	private Jugador n = null;

	@FXML
	private Label vida1;

	@FXML
	private Label vida2;

	@FXML
	private Label nombre1;

	@FXML
	private Label nombre2;

	@FXML
	private TextField input1;

	@FXML
	private TextField input2;

	@FXML
	private Button boton1;

	@FXML
	private Button boton2;

	@FXML
	private Label retornos;

	@FXML
	private Button seguir;
	

	@Autowired
	ResourceLoader resourceloader;
	
	
	
	public void datosIniciales(String s1, String s2) {
		retornos.setStyle("-fx-font-size : 60px");
		j1 = new Jugador(s1.trim());
		j2 = new Jugador(s2.trim());
		j = new Juego();

		n = new Jugador(j.start(j1, j2));

		vida1.setText("Vida de " + j1.getNombre() + ": " + String.valueOf(j1.getVida()));

		vida2.setText("Vida de " + j2.getNombre() + ": " + String.valueOf(j2.getVida()));
		nombre1.setText("Jugador 1: " + j1.getNombre());
		nombre2.setText("Jugador 2: " + j2.getNombre());
		retornos.setText("Por sorteo empieza " + n.getNombre());

		if (n.getNombre().contentEquals(j1.getNombre())) {
			turnoJugador1();
		} else {
			turnoJugador2();
		}

	}

	public void turnoJugador1() {
		input1.setStyle("-fx-background-color : #FFFFFF");
		input2.setStyle("-fx-background-color : #454545");
		boton2.setDisable(true);
		input2.setEditable(false);

		boton1.setDisable(false);
		input1.setEditable(true);
	}

	public void turnoJugador2() {
		input2.setStyle("-fx-background-color : #FFFFFF");
		input1.setStyle("-fx-background-color : #454545");
		boton1.setDisable(true);
		input1.setEditable(false);

		boton2.setDisable(false);
		input2.setEditable(true);
	}

	public void finJuego(Jugador x) {

		Jugador n = new Jugador(x);
		boton1.setDisable(true);
		input1.setEditable(false);
		input2.setStyle("-fx-background-color : #454545");
		input1.setStyle("-fx-background-color : #454545");
		boton2.setDisable(true);
		input2.setEditable(false);

		retornos.setStyle("-fx-font-size : 60px");


		retornos.setText("Ha ganado " + x.getNombre());

		seguir.setVisible(true);

	}

	@FXML
	public void cambioLabel(ActionEvent event) throws IOException {
		int pos;
		String perdedor;
		if (retornos.getText().toString().startsWith("Ha ganado ")) {
			
	//		URL url=this.fxml.getURL();
			
		//	FXMLLoader loader=new FXMLLoader(url);
			FXMLLoader loader = new FXMLLoader();
			
		//	Resource resource=resourceloader.getResource("classpath:FXMLFinal.fxml");
			
			loader.setLocation(getClass().getResource("/FXMLFinal.fxml"));

			Parent nuevoparent = loader.load();
			Scene nuevaescena = new Scene(nuevoparent, 1920, 1080);
		//	nuevaescena.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			nuevaescena.getStylesheets().add("application1.css");
			
			
			ControladorFinal micontrolador = loader.getController();

			if (n.getNombre().equals(j1.getNombre())) {
				pos = 1;
				perdedor = j2.getNombre();
			} else {
				pos = 2;
				perdedor = j1.getNombre();
			
			}

			micontrolador.iniciarDatos(n.getNombre(), perdedor, pos);
			j1.getArchivojugador().delete();
			j2.getArchivojugador().delete();
			
			j1 = null;
			j2 = null;
			j = null;
			
			
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(nuevaescena);

			window.show();
		}

	}

	@FXML
	public void juegaDos(ActionEvent event) {
		turnoJugador2();
		retornos.setStyle("-fx-font-size : 60px");
		int numero = 0;
		if (!input2.getText().isEmpty()) {
			try {
				numero = Integer.parseInt(input2.getText());

			} catch (NumberFormatException e) {

			} finally {
				if (numero < 0 || numero > 10) {
					numero = 0;
				}
			}
			if (numero == 0) {
				turnoJugador2();
			}

			if (j.numeroQuizaUsado(j2, numero) && numero != 0) {
				retornos.setText("Número ya usado");
				turnoJugador2();
			} else {
				if (j.computarNumero(j1, numero)) {
					retornos.setText("Has acertado el número  :)");
					vida1.setText("Vida de " + j1.getNombre() + ": " + String.valueOf(j1.getVida()));

					if (j.comprobarFin(j1, j2) instanceof Jugador) {
						n = new Jugador(j.comprobarFin(j1, j2));
						finJuego(n);
					} else {
						turnoJugador1();
					}
				} else {
					String salida = numero == 0 ? "Número no valido" : "Fallaste el número !!!";
					retornos.setText(salida);
					if (numero == 0) {
						turnoJugador2();
					} else {
						turnoJugador1();
					}
				}

			}

		} else {
			retornos.setText("Juegue con un número del 1 al 10");
			turnoJugador2();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void juegaUno(ActionEvent event) {
		turnoJugador1();
		retornos.setStyle("-fx-font-size : 60px");
		int numero = 0;
		if (!input1.getText().isEmpty()) {
			try {
				numero = Integer.parseInt(input1.getText());

			} catch (NumberFormatException e) {

			} finally {
				if (numero < 0 || numero > 10) {
					numero = 0;
				}
			}

			if (numero == 0) {
				turnoJugador1();
			}

			if (j.numeroQuizaUsado(j1, numero) && numero != 0) {
				retornos.setText("Número ya usado ");
				turnoJugador1();
			} else {
				if (j.computarNumero(j2, numero)) {
					retornos.setText("Has acertado el número  :)");
					vida2.setText("Vida de " + j2.getNombre() + ": " + String.valueOf(j2.getVida()));

					if (j.comprobarFin(j1, j2) instanceof Jugador) {
						n = new Jugador(j.comprobarFin(j1, j2));
						finJuego(n);
					} else {
						turnoJugador2();
					}
				} else {
					String salida = numero == 0 ? "Número no válido" : "Fallaste el número !!!";
					retornos.setText(salida);
					if (numero == 0) {
						turnoJugador1();
					} else {
						turnoJugador2();
					}
				}

			}

		}

		else {
			retornos.setText("Juegue con un número del 1 al 10");
			turnoJugador1();
		}
	}

}
