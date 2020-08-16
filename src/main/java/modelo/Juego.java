package modelo;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Juego {
	
	RandomAccessFile raf=null;
	
	

	
	
	public Jugador comprobarFin(Jugador j1, Jugador j2) {
		if (j1.getVida()==0) {
			return j2;
		}
		if (j2.getVida()==0) {
			return j1;
		} else {
			return null;
		}
	}
	
	
	
	public Jugador start(Jugador j1, Jugador j2) {  // Sirve para saber quien empieza

		generarLista(j1);
		System.out.println(j1.getLista());
	
		generarLista(j2);
		System.out.println(j2.getLista());

		
		if (new Random().ints(0,2).findFirst().getAsInt()==0) {   // genera 0 o 1 de forma aleatoria
			 return j1;	
		} else {
			return j2;	
		}
		
		
	}



	
	
	
	
	
	public boolean numeroQuizaUsado(Jugador jn, int numero) {
		if (jn.getNumerosusados().contains(numero)) {
	//	Comprueba en la lista de numeros usados
			return true;
		} else {
			jn.getNumerosusados().add(numero);
			return false;
		}
	}



	public boolean computarNumero(Jugador jn, int j) {
	
		try {
			raf=new RandomAccessFile(jn.getNombre()+".dat","rw");
			raf.seek(0);
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
		
		}
		int lectura;
		int posterior=0;
		while (true) {
			try {
				lectura=raf.readInt();
				if (lectura==j) {  // encuentra el numero
					jn.setVida(jn.getVida()-1);  //restamos vida
				
					long pos=raf.getFilePointer();
					
					while (raf.getFilePointer()<raf.length()) {
						posterior=raf.readInt(); 
						long posicionposterior=raf.getFilePointer();
						raf.seek(pos);
						raf.writeInt(posterior); //reescribimos el fichero
						pos=posicionposterior;
					}
					raf.close();
					return true;
					

				}
			} catch (EOFException e) {
			
				return false;
			}catch (IOException e) {
				
			}
		}
	
		
	}
	
	public boolean computarNumeroConlaLista(Jugador jn, int j) {
		return jn.getLista().stream().filter(i->i==j).findFirst().get()==j;
	}
	
	
	
	//genera la lista inicial de numeros
	public List<Integer> generarLista(Jugador jn) {
		while (jn.getLista().size() != 5) {
			int numeror = (int) (Math.random() * 10) + 1;
			if (!jn.getLista().contains(numeror)) {
				jn.getLista().add(numeror);

			}
		}
		jn.setArchivojugador(jn.añadirAlArchivo(jn.getLista()));
		return jn.getLista();
	}



}
