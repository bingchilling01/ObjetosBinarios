package org.example;

import java.sql.*;

public class Ejercicio {

    private static final String claseDriver = "com.mysql.cj.jdbc.Driver";
    private static final String[] paramConexion = { "jdbc:mysql://localhost/formula1", "root", "" };

    public static void main(String[] args) {

        int opcion;
        boolean salir = false;

        while (!salir) {
            ES.msgln("1. Insertar un piloto\n2. Leer lista de pilotos\n3. Modificar piloto\n4. Eliminar piloto");
            ES.msgln("5. Insertar escuderìa\n6. Leer Lista de escuderías\n7. Modificar escudería\n8. Eliminar escudería");
            opcion = ES.leeEntero("Opción: ");
            switch (opcion) {
                case 1: {
                    String nombre = ES.leeCadena("Nombre del piloto: ");
                    String apellidos = ES.leeCadena("Apellidos del piloto: ");
                    int escuderia = ES.leeEntero("Escudería: ");

                    insertarPiloto(nombre, apellidos, escuderia);

                    break;
                }

                case 2: {
                    consultaSQL();
                    break;
                }

                case 3: {
                    int idBuscado = ES.leeEntero("ID del piloto a cambiar: ");
                    actualizaSQL(idBuscado);
                }

            }
        }


    }

    private static void insertarPiloto(String nombre, String apellidos, int escuderia) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate("INSERT INTO piloto VALUES (0, '" + nombre + "', '" + apellidos + "', " + escuderia + ");");

            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void consultaSQL() {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
//            sentencia.executeUpdate("INSERT INTO departamentos VALUES (0, 'Logística');");
            ResultSet datosResultantes = sentencia.executeQuery("SELECT * FROM piloto;");

            // Recorremos el resultado para visualizar cada fila
            // Se hace un bucle mientras haya registros y se van visualizando
            System.out.println("Lista de pilotos de la BBDD:");
            while (datosResultantes.next()) {
                ES.msgln("-------------------------------------------------------------------");
                System.out.println("ID: " + datosResultantes.getInt(1) + " Nombre: " + datosResultantes.getString(2) + " Apellidos: " + datosResultantes.getString(3) + " Escudería: " + datosResultantes.getInt(4));
                ES.msgln("-------------------------------------------------------------------");
            }

            datosResultantes.close(); // Cerrar ResultSet
            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void actualizaSQL(int idBuscado) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            if(sentencia.executeQuery("SELECT nombre FROM piloto WHERE id = " + idBuscado).next()) {
                String nombreNuevo = ES.leeCadena("Nuevo nombre: ");
                String apellidosNuevos = ES.leeCadena("Nuevos apellidos: ");
                int escuderiaNueva = ES.leeEntero("Escudería nueva: ");

                sentencia.executeUpdate("UPDATE piloto SET " + "nombre = '" + nombreNuevo + "', apellidos = '" + apellidosNuevos + "', id_escuderia = " + escuderiaNueva + " WHERE id = " + idBuscado + ";");
            } else {
                ES.msgErrln("No se ha encontrado el piloto");
            }

            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
