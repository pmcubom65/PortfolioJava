package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Jugador {

	private String nombre;
	private List<Integer> lista = new ArrayList<>();
	private List<Integer> numerosusados = new ArrayList<>();
	private File archivojugador = null;
	RandomAccessFile raf = null;
	private int vida;



	public Jugador(Jugador x) {
		this.nombre = x.nombre;
		this.lista=x.lista;
		this.numerosusados=x.numerosusados;
		this.archivojugador=x.archivojugador;
		this.vida=x.vida;
	}
	
	public Jugador() {
		this.vida=5;
	};
	
	
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.vida=5;
	}
	
	




	public void setNumerosusados(List<Integer> numerosusados) {
		this.numerosusados = numerosusados;
	}
	
	
	public List<Integer> getNumerosusados() {
		return numerosusados;
	}

	public File añadirAlArchivo(List<Integer> numeros) {

		this.archivojugador = new File(this.nombre + ".dat");

		try  {
			raf = new RandomAccessFile(archivojugador, "rw");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		numeros.forEach(i -> {
			try {
				raf.writeInt(i);
			} catch (IOException e) {
			
			}
		});
		try {
			raf.close();
		} catch (IOException e) {
		
		}
		return archivojugador;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Integer> getLista() {
		return lista;
	}

	public void setLista(List<Integer> lista) {
		this.lista = lista;
	}

	public File getArchivojugador() {
		return archivojugador;
	}

	public void setArchivojugador(File archivojugador) {
		this.archivojugador = archivojugador;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getVida() {
		return vida;
	}
}
