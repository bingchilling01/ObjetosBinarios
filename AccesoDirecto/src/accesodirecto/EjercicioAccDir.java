package accesodirecto;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

import utilidades.ES;

public class EjercicioAccDir {

	public static void main(String[] args) {

		boolean salir = false;
		int opcion;
		while (!salir) {

			ES.msgln("1. Añadir estudiante");
			ES.msgln("2. Buscar estudiante por ID");
			ES.msgln("3. Modificar nota de estudiante");
			ES.msgln("4. Eliminar estudiante");
			ES.msgln("5. Listar todos los estudiantes");
			ES.msgln("6. Salir");

			opcion = ES.leeEntero(1, 6);

			switch (opcion) {
			case 1: {
				nuevoEstudiante();
				break;
			}

			case 2: {
				getEstudiantePorID();
				break;
			}
			case 3: {
				modificarEstudiante();
				break;
			}
			case 4: {
				eliminarEstudiante();
				break;
			}

			case 5: {
				listarEstudiantes();
				break;
			}

			case 6: {
				ES.msgln("Saliendo...");
				salir = true;
				break;
			}
			default: {
				ES.msgErrln("Opción inválida.");
				break;
			}
			}
		}
	}

	static int contadorEstudiantes = getNumeroElementos();
	static final int RECORD_SIZE = 48;
	static final float NOTA_MAX = 10.0f;
	static final float NOTA_MIN = 0.0f;
	static final String errorEscritura = "No se ha podido escribir los datos del alumno";
	static final String errorLectura = "No se ha podido leer los datos";
	static final String regNoExiste = "El alumno no existe.";
	static final String fichero = "estudiantes.db";
	static final String RO = "r";
	static final String RW = "rw";

	private static int getNumeroElementos() {
		int numeroElementos = 0;
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RW)) {
			long tamFichero = ficheroAlumnos.length();
			numeroElementos = (int) (tamFichero / RECORD_SIZE);

		} catch (IOException ioe) {
			ES.msgErrln("Error al intentar conseguir el número de elementos");
		}

		return numeroElementos;
	}

	private static void nuevoEstudiante() {
		contadorEstudiantes++;
		String nombreEstudiante = ES.leeCadena("Nombre del estudiante: ");
		float nota = ES.leeFloat("Nota del estudiante: ", NOTA_MIN, NOTA_MAX);
		int largoNombre = nombreEstudiante.getBytes().length;
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RW)) {
			ficheroAlumnos.seek(ficheroAlumnos.length());
			ficheroAlumnos.writeInt(contadorEstudiantes);
			ficheroAlumnos.writeUTF(nombreEstudiante);
			for (int i = 0; i < (40 - largoNombre); i++) {
				ficheroAlumnos.writeByte(0);
			}
			ficheroAlumnos.writeFloat(nota);

		} catch (IOException ioe) {
			ES.msgErrln(errorEscritura);
		}
	}

	private static void getEstudiantePorID() {
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RO)) {
			ficheroAlumnos.seek(0);
			boolean encontrado = false;
			int buscarID = ES.leeEntero("Introduzca el ID del estudiante: ");

			while (!encontrado && ficheroAlumnos.getFilePointer() < ficheroAlumnos.length()) {
				int id = ficheroAlumnos.readInt();

				if (id == buscarID) {
					encontrado = true;
					String nombre = ficheroAlumnos.readUTF();
					int largoNombre = nombre.getBytes().length;
					ficheroAlumnos.skipBytes(40 - largoNombre);
					float nota = ficheroAlumnos.readFloat();
					ES.msgln("Se ha encontrado al alumno con ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Nota: "
							+ nota + "\n");
				} else {
					ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
				}
			}

			if (!encontrado) {
				ES.msgErrln(regNoExiste);
			}

		} catch (IOException ioe) {
			ES.msgErrln(errorLectura);
		}
	}

	private static void modificarEstudiante() {
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RW)) {
			ficheroAlumnos.seek(0);
			boolean encontrado = false;
			int buscarID = ES.leeEntero("Introduzca el ID del estudiante a modificar: ");

			while (!encontrado && ficheroAlumnos.getFilePointer() < ficheroAlumnos.length()) {
				int id = ficheroAlumnos.readInt();

				if (id == buscarID) {
					encontrado = true;
					String nuevoNombre = ES.leeCadena("Nuevo nombre del alumno: ");
					int largoNuevoNombre = nuevoNombre.getBytes().length;
					float nuevaNota = ES.leeFloat("Nueva nota: ", NOTA_MIN, NOTA_MAX);
					ficheroAlumnos.writeUTF(nuevoNombre);
					ficheroAlumnos.skipBytes(40 - largoNuevoNombre);
					ficheroAlumnos.writeFloat(nuevaNota);
				} else {
					ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
				}
			}

			if (!encontrado) {
				ES.msgErrln(regNoExiste);
			}

		} catch (IOException ioe) {
			ES.msgErrln(errorLectura);
		}
	}

	private static void listarEstudiantes() {
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RO)) {
			ficheroAlumnos.seek(0);
			for (int i = 0; i <= contadorEstudiantes; i++) {
				int id = ficheroAlumnos.readInt();
				if (id != -1) {
					String nombre = ficheroAlumnos.readUTF();
					int largoNombre = nombre.getBytes().length;
					ficheroAlumnos.skipBytes(40 - largoNombre);
					float nota = ficheroAlumnos.readFloat();

					ES.msgln("ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Nota: " + nota + "\n");
				} else {
					ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
				}
			}
		} catch (EOFException eof) {

		} catch (IOException ioe) {
			ES.msgErrln(errorLectura);
			ioe.printStackTrace();
		}
	}

	private static void eliminarEstudiante() {

		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, RW)) {
			ficheroAlumnos.seek(0);
			boolean encontrado = false;
			int buscarID = ES.leeEntero("Introduzca el ID del estudiante a eliminar: ");

			while (!encontrado && ficheroAlumnos.getFilePointer() < ficheroAlumnos.length()) {
				int id = ficheroAlumnos.readInt();

				if (id == buscarID) {
					encontrado = true;
					ficheroAlumnos.seek(ficheroAlumnos.getFilePointer() - 4);
					ficheroAlumnos.writeInt(-1);
					int bytesEliminado = "eliminado".getBytes().length;
					ficheroAlumnos.writeUTF("eliminado");
					ficheroAlumnos.skipBytes(40 - bytesEliminado);
					ficheroAlumnos.writeFloat(-1f);
				} else {
					ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
				}
			}

			if (!encontrado) {
				ES.msgErrln(regNoExiste);
			}

		} catch (IOException ioe) {
			ES.msgErrln(errorLectura);
		}
	}
}
