package serializacion;

import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

public class Main {

	public static void main(String[] args) {
		Cosas[] cosas = new Cosas[5];

		cosas[0] = new Coche("SEAT", "León", 2009, 120, "Gasoil");
		cosas[1] = new Coche("VW", "Golf", 2015, 100, "Gasolina");
		cosas[2] = new Coche("Audi", "A5", 2018, 150, "Gasoil");
		cosas[3] = new Persona("Jose", "Garcia Garces", 21, "maildejose@sucorreo.com", true);
		cosas[4] = new Persona("Maria", "Lopez Garrido", 19, "maildemari@sucorreo.com", false);

		try {
			FileOutputStream archivo = new FileOutputStream("cosas.bin");
			ObjectOutputStream escritura = new ObjectOutputStream(archivo);

			for (int i = 0; i < cosas.length; i++) {
				escritura.writeObject(cosas[i]);
			}

			escritura.close();

			System.out.println("Aparcao");
		} catch (Exception excep) {
			System.err.println("No se ha podido escribir los objectos");
			excep.printStackTrace();
		} finally {
			System.out.println("Try-catch hecho!");
		}

		try {
			FileInputStream archivo = new FileInputStream("cosas.bin");
			ObjectInputStream lectura = new ObjectInputStream(archivo);

			for (int i = 0; i < cosas.length; i++) {
				if (cosas[i] instanceof Coche) {
					cosas[i] = (Coche) lectura.readObject();
				} else {
					cosas[i] = (Persona) lectura.readObject();
				}
			}

			int contCoche = 1;
			int contPersona = 1;

			for (int i = 0; i < cosas.length; i++) {
				if (cosas[i] instanceof Coche) {
					contCoche = (int) ((Coche) cosas[i]).imprimirCoche(contCoche);
				} else {
					contPersona = (int) ((Persona) cosas[i]).imprimirPersona(contPersona);
				}
			}

			lectura.close();

		} catch (Exception exc) {
			System.err.println("No se ha podido leer los objectos");
			exc.printStackTrace();
		}
	}

}
