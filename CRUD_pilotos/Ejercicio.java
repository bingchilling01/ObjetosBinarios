package org.example;

import java.sql.*;

public class Ejercicio {

    private static final String claseDriver = "com.mysql.cj.jdbc.Driver";
    private static final String[] paramConexion = { "jdbc:mysql://localhost/formula1", "root", "" };

    public static void main(String[] args) {

        int opcion;
        boolean salir = false;

        int opcionPilotos;
        int opcionEscuderias;

        while (!salir) {
            ES.msgln("1. Pilotos\n2. Escuderías\n0. Salir");
            opcion = ES.leeEntero("Opción: ");
            switch (opcion) {

                case 1: {
                    boolean salirPilotos = false;
                    while (!salirPilotos) {
                        ES.msgln("1. Insertar un piloto\n2. Mostrar lista de pilotos\n3. Modificar piloto\n4. Eliminar piloto\n0. Menú principal");
                        opcionPilotos = ES.leeEntero("Opción: ");
                        switch (opcionPilotos) {
                            case 1: {
                                String nombre = ES.leeCadena("Nombre del piloto: ");
                                String apellidos = ES.leeCadena("Apellidos del piloto: ");
                                int escuderia = ES.leeEntero("Escudería: ");

                                insertarPiloto(nombre, apellidos, escuderia);
                                salirPilotos = true;
                                break;
                            }

                            case 2: {
                                consultaPilotos();
                                salirPilotos = true;
                                break;
                            }

                            case 3: {
                                int idPilotoBuscado = ES.leeEntero("ID del piloto a actualizar: ");
                                actualizaPiloto(idPilotoBuscado);
                                salirPilotos = true;
                                break;
                            }

                            case 4: {
                                int idPilotoBuscado = ES.leeEntero("ID del piloto a eliminar: ");
                                eliminaPiloto(idPilotoBuscado);
                                salirPilotos = true;
                                break;
                            }

                            case 0: {
                                salirPilotos = true;
                                break;
                            }
                        }
                    }
                    break;
                }

                case 2: {
                    boolean salirEscuderias = false;
                    while (!salirEscuderias) {
                        ES.msgln("1. Insertar una escudería\n2. Mostrar lista de escuderías\n3. Modificar escudería\n4. Eliminar escudería\n0. Menú principal");
                        opcionEscuderias = ES.leeEntero("Opción: ");

                        switch (opcionEscuderias) {
                            case 1: {
                                String nombre = ES.leeCadena("Nombre de la escudería: ");
                                insertarEscuderia(nombre);
                                break;
                            }

                            case 2: {
                                consultaEscuderias();
                                break;
                            }

                            case 3: {
                                int idEscuderiaBuscada = ES.leeEntero("ID de la escudería a actualizar: ");
                                actualizaEscuderia(idEscuderiaBuscada);
                                break;
                            }

                            case 4: {
                                int idEscuderiaBuscada = ES.leeEntero("ID de la escudería a eliminar: ");
                                eliminaEscuderia(idEscuderiaBuscada);
                                break;
                            }

                            case 0: {
                                salirEscuderias = true;
                                break;
                            }
                        }
                    }
                    break;
                }

                case 0: {
                    salir = true;
                    ES.msgln("Saliendo del programa...");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {

                    }
                    break;
                }

                default: {
                    ES.msgErrln("Opción no válida");
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
    private static void consultaPilotos() {
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

            if(datosResultantes.next()) {
                ES.msgln("Lista de pilotos de la BBDD:");
                do {
                    ES.msgln("-------------------------------------------------------------------");
                    ES.msgln("ID: " + datosResultantes.getInt(1) + " Nombre: " + datosResultantes.getString(2) + " Apellidos: " + datosResultantes.getString(3) + " Escudería: " + datosResultantes.getInt(4));
                    ES.msgln("-------------------------------------------------------------------");
                } while (datosResultantes.next());
            } else {
                ES.msgErrln("La lista de pilotos está vacía.");
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

    private static void actualizaPiloto(int idBuscado) {
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
                ES.msgln("Piloto actualizado");
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

    private static void eliminaPiloto(int idBuscado) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            if(sentencia.executeQuery("SELECT nombre FROM piloto WHERE id = " + idBuscado).next()) {

                sentencia.executeUpdate("DELETE FROM piloto WHERE id = " + idBuscado);
                ES.msgln("Piloto eliminado");

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

    private static void insertarEscuderia(String nombre) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate("INSERT INTO escuderia VALUES (0, '" + nombre + "';");

            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void consultaEscuderias() {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
//            sentencia.executeUpdate("INSERT INTO departamentos VALUES (0, 'Logística');");
            ResultSet datosResultantes = sentencia.executeQuery("SELECT * FROM escuderia;");

            // Recorremos el resultado para visualizar cada fila
            // Se hace un bucle mientras haya registros y se van visualizando

            if(datosResultantes.next()) {
                ES.msgln("Lista de escuderias de la BBDD:");
                do {
                    ES.msgln("-------------------------------------------------------------------");
                    ES.msgln("ID: " + datosResultantes.getInt(1) + " Nombre: " + datosResultantes.getString(2));
                    ES.msgln("-------------------------------------------------------------------");
                } while (datosResultantes.next());
            } else {
                ES.msgErrln("La lista de escuderías está vacía");
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

    private static void actualizaEscuderia(int idBuscado) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            if(sentencia.executeQuery("SELECT nombre FROM escuderia WHERE id = " + idBuscado).next()) {
                String nombreNuevo = ES.leeCadena("Nuevo nombre: ");

                sentencia.executeUpdate("UPDATE escuderia SET " + "nombre = '" + nombreNuevo + "';");
                ES.msgln("Piloto actualizado");
            } else {
                ES.msgErrln("No se ha encontrado la escudería");
            }

            sentencia.close(); // Cerrar Statement
            conexion.close(); // Cerrar conexión

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eliminaEscuderia(int idBuscado) {
        try {
            // Cargar el driver
            Class.forName(claseDriver);

            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection(paramConexion[0], paramConexion[1], paramConexion[2]);

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            if(sentencia.executeQuery("SELECT nombre FROM escuderia WHERE id = " + idBuscado).next()) {

                sentencia.executeUpdate("DELETE FROM escuderia WHERE id = " + idBuscado);
                ES.msgln("Escudería eliminada");

            } else {
                ES.msgErrln("No se ha encontrado la escudería");
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
