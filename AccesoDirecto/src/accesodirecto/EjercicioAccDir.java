package accesodirecto;

import java.io.IOException;
import java.io.RandomAccessFile;

import utilidades.ES;

public class EjercicioAccDir {

	public static void main(String[] args) {
		int contadorEstudiante = getNumeroElementos();
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
				contadorEstudiante++;
				String nombreEstudiante = ES.leeCadena("Nombre del estudiante: ");
				float nota = ES.leeFloat("Nota del estudiante: ", NOTA_MIN, NOTA_MAX);
				int largoNombre = nombreEstudiante.getBytes().length;
				try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
					ficheroAlumnos.seek(ficheroAlumnos.length());
					ficheroAlumnos.writeInt(contadorEstudiante);
					ficheroAlumnos.writeUTF(nombreEstudiante);
					for (int i = 0; i < (40 - largoNombre); i++) {
						ficheroAlumnos.writeByte(0);
					}
					ficheroAlumnos.writeFloat(nota);
					
				} catch (IOException ioe) {
					ES.msgErrln(errorEscritura);
				}
				break;
			}

			case 2: {
				try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
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
							ES.msgln("Se ha encontrado al alumno: \n" + "ID: " + id + "\n" + "Nombre: " + nombre + "\n"
									+ "Nota: " + nota + "\n");
						} else {
							ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
						}
					}
					
					if (!encontrado) {
						ES.msgErrln(regNoExiste);
					}
					break;
				} catch (IOException ioe) {
					ES.msgErrln(errorLectura);
				}
			}
			case 3: {
				try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
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
					break;
				} catch (IOException ioe) {
					ES.msgErrln(errorLectura);
				}
			}
				break;
			case 4: {
				// TODO eliminar
//				try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
//					ficheroAlumnos.seek(0);
//					boolean encontrado = false;
//					int buscarID = ES.leeEntero("Introduzca el ID del estudiante a eliminar: ");
//					
//					while (!encontrado && ficheroAlumnos.getFilePointer() < ficheroAlumnos.length()) {
//						int id = ficheroAlumnos.readInt();
//						
//						if (id == buscarID) {
//							encontrado = true;
//							
//						} else {
//							ficheroAlumnos.skipBytes(RECORD_SIZE - 2);
//						}
//					}
//					
//					if (!encontrado) {
//						ES.msgErrln(regNoExiste);
//					}
//					break;
//				} catch (IOException ioe) {
//					ES.msgErrln(errorLectura);
//				}
				break;
			}
				
			case 5: {
				try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
					ficheroAlumnos.seek(0);
					for (int i = 0; i < contadorEstudiante; i++) {
						int id = ficheroAlumnos.readInt();
						String nombre = ficheroAlumnos.readUTF();
						int largoNombre = nombre.getBytes().length;
						ficheroAlumnos.skipBytes(40 - largoNombre);
						float nota = ficheroAlumnos.readFloat();

						ES.msgln("ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Nota: " + nota + "\n");
					}
				} catch (IOException ioe) {
					ES.msgErrln(errorLectura);
				}

				break;
			}
			case 6:
				ES.msgln("Saliendo...");
				break;
			default:
				ES.msgErrln("Opción inválida.");
				break;
			}
		}
	}

	static final int RECORD_SIZE = 48;
	static final float NOTA_MAX = 10.0f;
	static final float NOTA_MIN = 0.0f;
	static final String errorEscritura = "No se ha podido escribir los datos del alumno";
	static final String errorLectura = "No se ha podido leer los datos";
	static final String regNoExiste = "El alumno no existe.";
	static final String fichero = "estudiantes.db";
	static final String rw = "rw";

	private static int getNumeroElementos() {
		int numeroElementos = 0;
		try (RandomAccessFile ficheroAlumnos = new RandomAccessFile(fichero, rw)) {
			long tamFichero = ficheroAlumnos.length();
			numeroElementos = (int) (tamFichero / RECORD_SIZE);
			
		} catch (IOException ioe) {
			
		}
		
		return numeroElementos;
	}
}
