package serializacion;

import java.io.Serializable;

public class Persona extends Cosas implements Serializable {
	private String nombre;
	private String apellidos;
	private int edad;
	private String correo;
	private boolean sexo;

	public Persona(String nombre, String apellidos, int edad, String correo, boolean sexo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.correo = correo;
		this.sexo = sexo;
	}

	public int imprimirPersona(int contador) {
		System.out.println("\nPersona nº " + contador);
		System.out.println("Nombre: " + this.nombre);
		System.out.println("Apellidos: " + this.apellidos);
		System.out.println("Edad: " + this.edad + " años.");
		System.out.println("Correo: " + this.correo);

		System.out.println(this.sexo ? "Sexo: Hombre" : "Sexo: Mujer");
		
		contador++;

		return contador;
	}

}
