package utilidades;

import java.util.Scanner;
public class ES {

    public static int leeEntero() {
        boolean leido = false;
        int numero = 0;
        Scanner teclado = null;
        do {
            try {
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                leido = true;
            } catch (Exception e) {
                ES.msgln("Error: No es un número entero válido. ");
            }

        } while (!leido);
        return numero;
    }

    public static int leeEntero(String mensaje) {
        int numero = 0;
        boolean leido = false;
        Scanner teclado = null;
        do {
            ES.msgln(mensaje);
            try {
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                leido = true;
            } catch (Exception e) {
                ES.msgln("Error: No es un número entero válido. ");
            }
        } while (!leido);
        return numero;
    }

    public static int leeEntero(int minimo) {
        int numero = 0;
        boolean leido = false;
        Scanner teclado = null;
        do {
            try {
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                if (numero >= minimo) {
                    leido = true;
                } else {
                    ES.msgErrln("Error: Debe ser un número entero mayor o igual que " + minimo + ". ");
                }
            } catch (Exception e) {
                ES.msgErrln("Error: No es un número entero válido. ");
            }
        } while (!leido);
        return numero;
    }

    public static int leeEntero(String mensaje, int minimo) {
        int numero = 0;
        boolean leido = false;
        Scanner teclado = null;
        do {
            ES.msgln(mensaje);
            try {
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                if (numero >= minimo) {
                    leido = true;
                } else {
                    ES.msgln("Error: Debe ser un número entero mayor o igual que " + minimo + ".");
                }
            } catch (Exception e) {
                ES.msgln("Error: No es un número entero válido. ");
            }

        } while (!leido);
        return numero;
    }

    public static int leeEntero(int minimo, int maximo) throws IllegalArgumentException {
        int numero = 0;
        boolean leido = false;
        Scanner teclado = null;

        // Si el valor mínimo es mayor que el m?ximo, lanzamos una excepción
        if (minimo <= maximo) {
            do {
                try {
                    teclado = new Scanner(System.in);
                    numero = teclado.nextInt();
                    if (numero >= minimo && numero <= maximo) {
                        leido = true;
                    } else {
                        ES.msgln("Error: Debe ser un número entero mayor o igual que " + minimo + " y menor o igual que " + maximo + ". ");
                    }
                } catch (Exception e) {
                    ES.msgln("Error: No es un número entero válido. ");
                }
            } while (!leido);
        } else {
            throw new IllegalArgumentException("Rango imposible. El valor mínimo no puede ser mayor que el valor máximo");
        }
        return numero;
    }

    public static int leeEntero(String mensaje, int minimo, int maximo) throws IllegalArgumentException {
        int numero = 0;
        boolean leido = false;
        Scanner teclado = null;

        // Si el valor mínimo es mayor que el m?ximo, lanzamos una excepción
        if (minimo <= maximo) {
            do {
                ES.msgln(mensaje);
                try {
                    teclado = new Scanner(System.in);
                    numero = teclado.nextInt();
                    if (numero >= minimo && numero <= maximo) {
                        leido = true;
                    } else {
                        ES.msgErrln("Error: Debe ser un número entero mayor o igual que " + minimo + " y menor o igual que " + maximo + ". ");
                    }
                } catch (Exception e) {
                    ES.msgErrln("Error: No es un número entero válido. ");
                }
            } while (!leido);
        } else {
            throw new IllegalArgumentException("Rango imposible. El valor mínimo no puede ser mayor que el valor m?ximo");
        }
        return numero;
    }

    public static String leeCadena() {
        Scanner teclado = new Scanner(System.in);
        String cadena = "";
        try {
            cadena = teclado.nextLine();
        } catch (Exception e) {
            ES.msgErrln("Error: Ha fallado la entrada de datos.");
        }
        return cadena;
    }
  
    public static String leeCadena(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        String cadena = "";
        try {
            ES.msgln(mensaje);
            cadena = teclado.nextLine();
        } catch (Exception e) {
            ES.msgErrln("Error: Ha fallado la entrada de datos.");
        }
        return cadena;
    }
  
    public static void msg(int entero) {
        System.out.print(entero);
    }

    public static void msg(long enteroLargo) {
        System.out.print(enteroLargo);
    }

    public static void msg(float real) {
        System.out.print(real);
    }

    public static void msg(double realLargo) {
        System.out.print(realLargo);
    }

    public static void msg(String cadenaAImprimir) {
        System.out.print(cadenaAImprimir);
    }

    public static void msg(Object objetoAImprimir) {
        System.out.print(objetoAImprimir.toString());
    }

    public static void msgln() {
        System.out.println();
    }

    public static void msgln(int entero) {
        System.out.println(entero);
    }

    public static void msgln(long enteroLargo) {
        System.out.println(enteroLargo);
    }

    public static void msgln(float real) {
        System.out.println(real);
    }

    public static void msgln(double realLargo) {
        System.out.println(realLargo);
    }

    public static void msgln(String cadenaAImprimir) {
        System.out.println(cadenaAImprimir);
    }

    public static void msgln(Object objetoAImprimir) {
        System.out.println(objetoAImprimir.toString());
    }

    public static String leeRespuesta(String mensaje) {
        boolean correcta = false;
        String cadena = "";
        Scanner teclado = null;
        do {
            ES.msgln(mensaje);
            try {
                teclado = new Scanner(System.in);
                cadena = teclado.nextLine();
                if (cadena != null && cadena.length() == 1 && ((cadena.equalsIgnoreCase("S")) || (cadena.equalsIgnoreCase("N")))) {
                    correcta = true;
                } else {
                    ES.msgln("Error: Solo se admite como respuesta un único carácter, que debe ser 's', 'S', 'n' o 'N'.");
                }
            } catch (Exception e) {
                ES.msgln("Error: Ha fallado la entrada de datos.");
            }
        } while (!correcta);
        return cadena.toUpperCase();
    }

    public static char leeCaracter(String mensaje) {

        Scanner teclado = null;
        teclado = new Scanner(System.in);
        ES.msgln(mensaje);
        String cadena = teclado.next();
        char caracter = cadena.charAt(0);
        return caracter;
    }
    
    public static void msgErrln(String cadenaAImprimir) {
        System.err.println(cadenaAImprimir);
    }

}
