package serializacion;

import java.io.Serializable;

public class Coche extends Cosas implements Serializable {

	private String marca;
	private String modelo;
	private int anyo;
	private int caballos;
	private String tipoCombustible;

	public Coche(String marca, String modelo, int anyo, int caballos, String tipoCombustible) {
		this.marca = marca;
		this.modelo = modelo;
		this.anyo = anyo;
		this.caballos = caballos;
		this.tipoCombustible = tipoCombustible;
	}

	public int imprimirCoche(int contador) {
		System.out.println("\nCoche nº " + contador);
		System.out.println("Marca: " + this.marca);
		System.out.println("Modelo: " + this.modelo);
		System.out.println("Año: " + this.anyo);
		System.out.println("Caballos: " + this.caballos + " CV.");
		System.out.println("Tipo de combustible: " + this.tipoCombustible);
		contador++;

		return contador;
	}

}
