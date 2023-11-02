package objetosbinarios;

import utilidades.ES;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		
		String textoMenu =
				   "\n1. Insertar coche nuevo\n" +
				   "2. Listar coches\n" + 
				   "3. Modificar coche\n" +
				   "4. Eliminar coche\n" +
				   "0. Salir";

		boolean salir = false;
		int opcion;

		while (!salir) {
			opcion = ES.leeEntero(textoMenu);

			switch (opcion) {
			case 0: {
				salir = true;
				break;
			}

			case 1: {
				Coche nuevoCoche = Coche.nuevoCoche();
				escribirObjetoNuevo(nuevoCoche);
				break;
			}

			case 2: {
				leerObjetos();
				break;
			}

			case 3: {
				modificarObjetos();
				break;
			}
			
			case 4: {
				eliminarObjetos();
				break;
			}
			}
		}
		ES.msgln("Cerrando el programa...");
	}
	
	private static String nombreArchivo = "coches.bin";
	
	private static void escribirObjetoNuevo(Coche nuevoCoche) {
		try {
			File archivoDestino = new File(nombreArchivo);
			FileOutputStream stSalida = new FileOutputStream(archivoDestino, true);

			if (archivoDestino.length() == 0) {
				ObjectOutputStream escritura = new ObjectOutputStream(stSalida);
				escritura.writeObject(nuevoCoche);
				escritura.close();
			} else {
				OOSSinCabecera escrituraSinCabecera = new OOSSinCabecera(stSalida);
				escrituraSinCabecera.writeObject(nuevoCoche);
				escrituraSinCabecera.close();
			}

		} catch (Exception excEscritura) {
			ES.msgErrln("No se ha podido escribir los objectos");
			excEscritura.printStackTrace();
		}
	}
	
	private static void escribirArrayObjetos(ArrayList<Coche> listaCoches) {
		try {
			File archivoDestino = new File(nombreArchivo);
			FileOutputStream stSalida = new FileOutputStream(archivoDestino, false);

			if (archivoDestino.length() == 0) {
				ObjectOutputStream escritura = new ObjectOutputStream(stSalida);
				for (Coche coche : listaCoches) {
				escritura.writeObject(coche);
				}
				escritura.close();
			} else {
				OOSSinCabecera escrituraSinCabecera = new OOSSinCabecera(stSalida);
				escrituraSinCabecera.writeObject(listaCoches);
				escrituraSinCabecera.close();
			}

		} catch (Exception excEscritura) {
			ES.msgErrln("No se ha podido escribir los objectos");
			excEscritura.printStackTrace();
		}
	}
	
	private static void leerObjetos() {
		ArrayList<Coche> listaCoches = new ArrayList<>();
		boolean endOfFile = false;
		int contador = 1;
		try {
			ObjectInputStream lectura = new ObjectInputStream(new FileInputStream(nombreArchivo));

			while (!endOfFile) {
				Coche lecturaCoches = (Coche) lectura.readObject();
				listaCoches.add(lecturaCoches);

			}

			lectura.close();

		} catch (EOFException eofExc) {
			endOfFile = true;
			if (!listaCoches.isEmpty()) {
				for (Coche coche : listaCoches) {
					contador = coche.imprimirCoche(contador);
				}
			} else {
				ES.msgErrln("El archivo está vacío");
			}
		} catch (Exception excLectura) {
			ES.msgErrln("No se ha podido leer los objectos");
			excLectura.printStackTrace();
		}
	}
	
	private static void modificarObjetos() {
		ArrayList<Coche> listaCoches = new ArrayList<>();
		boolean endOfFile = false;
		int contador = 1;

		try {
			ObjectInputStream lectura = new ObjectInputStream(new FileInputStream(nombreArchivo));

			while (!endOfFile) {
				Coche lecturaCoches = (Coche) lectura.readObject();
				listaCoches.add(lecturaCoches);
			}
			lectura.close();

		} catch (EOFException eofExc) {
			endOfFile = true;
			if (!listaCoches.isEmpty()) {
				ES.msgln("Lista de coches: ");
				for (Coche coche : listaCoches) {
					contador = coche.imprimirCoche(contador);
				}
				Coche.modificarCoche(listaCoches);
				escribirArrayObjetos(listaCoches);
			} else {
				ES.msgErrln("El archivo está vacío");
			}
			
		} catch (Exception excLectura) {
			ES.msgErrln("No se ha podido leer los objectos");
			excLectura.printStackTrace();
		}
	}
	
	private static void eliminarObjetos() {
		ArrayList<Coche> listaCoches = new ArrayList<>();
		boolean endOfFile = false;
		int contador = 1;

		try {
			ObjectInputStream lectura = new ObjectInputStream(new FileInputStream(nombreArchivo));

			while (!endOfFile) {
				Coche lecturaCoches = (Coche) lectura.readObject();
				listaCoches.add(lecturaCoches);
			}
			lectura.close();

		} catch (EOFException eofExc) {
			endOfFile = true;
			if (!listaCoches.isEmpty()) {
				ES.msgln("Lista de coches: ");
				for (Coche coche : listaCoches) {
					contador = coche.imprimirCoche(contador);
				}
				int indice = ES.leeEntero("Introduzca el ID del coche que quiere eliminar: ", 1, listaCoches.size());
				listaCoches.remove((indice-1));
				escribirArrayObjetos(listaCoches);
				ES.msgln("Coche nº " + indice + " eliminada.");
			} else {
				ES.msgErrln("El archivo está vacío");
			}
			
		} catch (Exception excLectura) {
			ES.msgErrln("No se ha podido leer los objectos");
			excLectura.printStackTrace();
		}
	}
}
