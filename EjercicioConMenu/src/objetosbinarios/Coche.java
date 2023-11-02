package objetosbinarios;

import java.io.Serializable;
import java.util.ArrayList;

import utilidades.ES;

public class Coche implements Serializable {

	private String marca;
	private String modelo;
	private int anyo;
	private int caballos;
	private int tipoCombustible;

	public Coche(String marca, String modelo, int anyo, int caballos, int tipoCombustible) {
		this.marca = marca;
		this.modelo = modelo;
		this.anyo = anyo;
		this.caballos = caballos;
		this.tipoCombustible = tipoCombustible;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public int getCaballos() {
		return caballos;
	}

	public void setCaballos(int caballos) {
		this.caballos = caballos;
	}

	public int getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(int tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	public static Coche nuevoCoche() {
		String marca = ES.leeCadena("Marca del coche: ");
		String modelo = ES.leeCadena("Modelo del coche: ");
		int anyo = ES.leeEntero("Año del coche: ");
		int cv = ES.leeEntero("Caballos: ");
		int tipo = ES.leeEntero("Tipo de combustible: \n 1. Gasolina\n 2. Diésel", 1, 2);
		Coche nuevoCoche = new Coche(marca, modelo, anyo, cv, tipo);

		return nuevoCoche;
	}

	public static void modificarCoche(ArrayList<Coche> listaCoches) {
		int indice = ES.leeEntero("Introduzca el ID del coche a modificar: ", 1, listaCoches.size());

		Coche modCoche = listaCoches.get((indice - 1));
		String marca = ES.leeCadena("Nueva marca del coche: ");
		modCoche.setMarca(marca);
		String modelo = ES.leeCadena("Nuevo modelo del coche: ");
		modCoche.setModelo(modelo);
		int anyo = ES.leeEntero("Año del coche: ");
		modCoche.setAnyo(anyo);
		int cv = ES.leeEntero("Caballos del coche: ");
		modCoche.setCaballos(cv);
		int tipo = ES.leeEntero("Cambiar tipo de combustible: \n 1. Gasolina\n 2. Diésel", 1, 2);
		modCoche.setTipoCombustible(tipo);
	}

	public int imprimirCoche(int contador) {
		System.out.println("\nID del coche: " + contador);
		System.out.println("Marca: " + this.marca);
		System.out.println("Modelo: " + this.modelo);
		System.out.println("Año: " + this.anyo);
		System.out.println("Caballos: " + this.caballos + " CV.");
		if (this.tipoCombustible == 1) {
			System.out.println("Tipo de combustible: Gasolina");
		} else if (this.tipoCombustible == 2) {
			System.out.println("Tipo de combustible: Diésel");
		}
		contador++;

		return contador;
	}

}
