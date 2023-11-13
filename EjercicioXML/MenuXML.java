package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import javax.xml.transform.OutputKeys;

public class MenuXML {
    public static void main(String[] args) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("pelis.xml"));

        // Normalizar el documento XML
        document.getDocumentElement().normalize();
        // Obtener todos los nodos de tipo 'pelicula'
        NodeList peliculas = document.getElementsByTagName("pelicula");

        Element catalogo = (Element) document.getElementsByTagName("catalogo").item(0);

        boolean salir = false;
        int opcion;

        while (!salir) {
            ES.msgln("Menú:\n"
                    + "0. Salir del programa\n"
                    + "1. Nueva película\n"
                    + "2. Mostrar películas\n"
                    + "3. Buscar película por ID\n"
                    + "4. Buscar películas por género\n"
                    + "5. Buscar película por título\n"
                    + "6. Buscar películas por director\n"
                    + "7. Buscar películas desde el año de estreno\n"
                    + "8. Modificar título de una peli\n"
                    + "9. Modificar el director de una peli\n"
                    + "10. Modificar año de estreno de una peli\n"
                    + "11. Modificar el género de una peli\n"
                    + "12. Modificar la valoración de una peli\n"
                    + "13. Eliminar película\n"
                    + "14. Aplicar cambios (Exportar XML)\n");

            opcion = ES.leeEntero();

            switch (opcion) {
                case 0: {
                    ES.msgln("Saliendo...");
                    salir = true;
                    break;
                }
                case 1: {
                    String id = ES.leeCadena("ID de la nueva película: ");

                    String titulo = ES.leeCadena("Título de la nueva película: ");

                    String director = ES.leeCadena("Director: ");

                    int anio = ES.leeEntero("Año de estreno: ", 1900, 2024);

                    String gen = ES.leeCadena("Género: ");

                    float valoracion = ES.leeFloat("Valoración (Entre 0 y 5): ", 0.0f, 5.0f);

                    catalogo.appendChild(creaPelicula(document, id, titulo, director, String.valueOf(anio), gen,
                            String.valueOf(valoracion)));
                    break;
                }

                case 2: {
                    muestraPeliculas(peliculas);
                    break;
                }

                case 3: {
                    String searchID = ES.leeCadena("Introduce el ID: ");
                    buscaPeliculaPorId(peliculas, searchID);
                    break;
                }

                case 4: {
                    String genero_buscado = ES.leeCadena("Introduce el género buscado: ");

                    buscaPeliculaPorGenero(peliculas, genero_buscado);
                    break;
                }

                case 5: {
                    String titulo = ES.leeCadena("Introduce el título de la peli: ");

                    buscaPeliculaPorTitulo(peliculas, titulo);
                    break;
                }

                case 6: {
                    String buscarDirector = ES.leeCadena("Nombre del director: ");

                    buscaPeliculaPorDirector(peliculas, buscarDirector);
                    break;
                }

                case 7: {
                    int anioEstreno = ES.leeEntero("Introduce el año: ", 1900, 2024);

                    buscaPeliculaPorAnio(peliculas, String.valueOf(anioEstreno));
                    break;
                }

                case 8: {
                    actualizaTitulo(peliculas);
                    break;
                }

                case 9: {
                    actualizaDire(peliculas);
                    break;
                }

                case 10: {
                    actualizaAnio(peliculas);
                    break;
                }

                case 11: {
                    actualizaGenero(peliculas);
                    break;
                }

                case 12: {
                    actualizaValoracion(peliculas);
                    break;
                }

                case 13: {
                    eliminaPeli(peliculas, document);
                    break;
                }

                case 14: {
                    String nombreXMLMod = ES.leeCadena("Nombre del fichero XML modificado: ");

                    guardarCambios(document, nombreXMLMod);
                    break;
                }

                default: {
                    ES.msgErrln("Opción NO válida");
                    break;
                }

            }
        }
        System.exit(0);

        eliminaPeliculaBajaValoracion(document, peliculas, 4);



    }

    private static final String notFound = "NO se ha encontrado la/s película/s";

    private static void guardarCambios(Document document, String nombre) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Establece la cantidad de
        // espacios para la
        // indentación
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(nombre + ".xml"));
        transformer.transform(source, result);
    }

    private static void muestraPeliculas(NodeList lista) {
        for (int i = 0; i < lista.getLength(); i++) {
            Element pelicula = (Element) lista.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            NodeList directores = pelicula.getElementsByTagName("director");
            NodeList anios = pelicula.getElementsByTagName("anio");
            NodeList valoraciones = pelicula.getElementsByTagName("valoracion");

            if (titulos.getLength() > 0 && directores.getLength() > 0 && valoraciones.getLength() > 0) {
                Element titulo = (Element) titulos.item(0);
                Element director = (Element) directores.item(0);
                Element anio = (Element) anios.item(0);
                Element valoracion = (Element) valoraciones.item(0);
                /*if (Float.parseFloat(valoracion.getTextContent()) < 5.0f) {
                    double nuevaValoracion = Double.parseDouble(valoracion.getTextContent()) + 0.1;
                    valoracion.setTextContent(String.format("%.2f", nuevaValoracion).replace(",", "."));
                }*/
                ES.msgln("Pelicula " + (i + 1) + ": '" + titulo.getTextContent() + "' dirigida por "
                        + director.getTextContent() + ", estrenada en el año " + anio.getTextContent() + " y con una valoración de " + valoracion.getTextContent());
            }

        }
    }

    private static Element creaPelicula(Document document, String id, String titulo_nuevo, String director_nuevo,
                                        String anio_nuevo, String genero_nuevo, String valoracion_nuevo) {
        Element nuevaPelicula = (Element) document.createElement("pelicula");
        nuevaPelicula.setAttribute("id", id);

        Element titulo = (Element) document.createElement("titulo");
        titulo.appendChild(document.createTextNode(titulo_nuevo));
        nuevaPelicula.appendChild(titulo);

        Element director = (Element) document.createElement("director");
        director.appendChild(document.createTextNode(director_nuevo));
        nuevaPelicula.appendChild(director);

        Element anio = (Element) document.createElement("anio");
        anio.appendChild(document.createTextNode(anio_nuevo));
        nuevaPelicula.appendChild(anio);

        Element genero = (Element) document.createElement("genero");
        genero.appendChild(document.createTextNode(genero_nuevo));
        nuevaPelicula.appendChild(genero);

        Element valoracion = (Element) document.createElement("valoracion");
        valoracion.appendChild(document.createTextNode(valoracion_nuevo));
        nuevaPelicula.appendChild(valoracion);

        return nuevaPelicula;

    }

    private static void buscaPeliculaPorId(NodeList peliculas, String buscaID) {
        boolean encontrada = false;
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            String ids = pelicula.getAttribute("id");
            if (ids.equalsIgnoreCase(buscaID)) {
                encontrada = true;
                NodeList titulos = pelicula.getElementsByTagName("titulo");
                NodeList dire = pelicula.getElementsByTagName("director");
                NodeList anio = pelicula.getElementsByTagName("anio");
                if (titulos.getLength() > 0) {
                    ES.msgln("Película encontrada: " + titulos.item(0).getTextContent() + ", dirigida por "
                            + dire.item(0).getTextContent() + ", estrenada en " + anio.item(0).getTextContent());
                }
            }

        }
        if (!encontrada) {
            ES.msgErrln(notFound);
        }
    }

    private static void buscaPeliculaPorGenero(NodeList peliculas, String genero_buscado) {
        boolean encontrada = false;
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList generos = pelicula.getElementsByTagName("genero");
            if (generos.getLength() > 0) {
                if (generos.item(0).getTextContent().equalsIgnoreCase(genero_buscado)) {
                    encontrada = true;
                    NodeList titulos = pelicula.getElementsByTagName("titulo");
                    NodeList dire = pelicula.getElementsByTagName("director");
                    NodeList anio = pelicula.getElementsByTagName("anio");
                    if (titulos.getLength() > 0) {
                        ES.msgln("Película encontrada: " + titulos.item(0).getTextContent() + ", dirigida por "
                                + dire.item(0).getTextContent() + ", estrenada en " + anio.item(0).getTextContent());
                    }
                }
            }
        }

        if (!encontrada) {
            ES.msgErrln(notFound);
        }
    }

    private static void buscaPeliculaPorTitulo(NodeList peliculas, String titulo_buscado) {
        boolean encontrada = false;
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (titulos.getLength() > 0) {
                if (titulos.item(0).getTextContent().equalsIgnoreCase(titulo_buscado)) {
                    encontrada = true;
                    NodeList dire = pelicula.getElementsByTagName("director");
                    NodeList anio = pelicula.getElementsByTagName("anio");
                    if (titulos.getLength() > 0) {
                        ES.msgln("Película encontrada: " + titulos.item(0).getTextContent() + ", dirigida por "
                                + dire.item(0).getTextContent() + ", estrenada en " + anio.item(0).getTextContent());
                    }
                }
            }
        }

        if (!encontrada) {
            ES.msgErrln(notFound);
        }
    }

    private static void buscaPeliculaPorDirector(NodeList peliculas, String dire) {
        boolean encontrada = false;
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList directores = pelicula.getElementsByTagName("director");
            if (directores.getLength() > 0) {
                if (directores.item(0).getTextContent().equalsIgnoreCase(dire)) {
                    encontrada = true;
                    NodeList titulos = pelicula.getElementsByTagName("titulo");
                    NodeList anio = pelicula.getElementsByTagName("anio");
                    if (titulos.getLength() > 0) {
                        ES.msgln("Película encontrada: " + titulos.item(0).getTextContent() + ", dirigida por " + dire
                                + ", estrenada en " + anio.item(0).getTextContent());
                    }
                }
            }
        }

        if (!encontrada) {
            ES.msgErrln(notFound);
        }
    }

    private static void buscaPeliculaPorAnio(NodeList peliculas, String anio) {
        boolean encontrada = false;
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList anios = pelicula.getElementsByTagName("anio");
            if (anios.getLength() > 0) {
                if ((Integer.parseInt(anios.item(0).getTextContent()) > Integer.parseInt(anio))) {
                    encontrada = true;
                    NodeList titulos = pelicula.getElementsByTagName("titulo");
                    NodeList dire = pelicula.getElementsByTagName("director");
                    if (titulos.getLength() > 0) {
                        ES.msgln("Película encontrada: " + titulos.item(0).getTextContent() + ", dirigida por "
                                + dire.item(0).getTextContent() + ", estrenada en " + anios.item(0).getTextContent());
                    }
                }
            }
        }

        if (!encontrada) {
            ES.msgErrln(notFound);
        }
    }



    private static void actualizaTitulo(NodeList peliculas) {
        boolean encontrado = false;
        String busqueda = ES.leeCadena("Introduce el ID o el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                String nuevoTitulo = ES.leeCadena("Introduce el nuevo título: ");
                titulos.item(0).setTextContent(nuevoTitulo);
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void actualizaDire(NodeList peliculas) {
        boolean encontrado = false;
        String busqueda = ES.leeCadena("Introduce el ID o el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                NodeList dires = pelicula.getElementsByTagName("director");
                String nuevoDire = ES.leeCadena("Introduce el nuevo director: ");
                dires.item(0).setTextContent(nuevoDire);
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void actualizaAnio(NodeList peliculas) {
        boolean encontrado = false;
        String busqueda = ES.leeCadena("Introduce el ID o el el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                NodeList anios = pelicula.getElementsByTagName("anio");
                int nuevo_anio = ES.leeEntero("Introduce el nuevo año de estreno: ", 1900, 2024);
                anios.item(0).setTextContent(String.valueOf(nuevo_anio));
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void actualizaGenero(NodeList peliculas) {
        boolean encontrado = false;
        String busqueda = ES.leeCadena("Introduce el ID o el el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                NodeList anios = pelicula.getElementsByTagName("genero");
                String gen_nuevo = ES.leeCadena("Introduce el nuevo género: ");
                anios.item(0).setTextContent(gen_nuevo);
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void actualizaValoracion(NodeList peliculas) {
        boolean encontrado = false;
        String busqueda = ES.leeCadena("Introduce el ID o el el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("valoracion");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                NodeList valoracion = pelicula.getElementsByTagName("valoracion");
                float val_nuevo = ES.leeFloat("Introduce la nueva valoración (entre 0 y 5): ", 0.0f, 5.0f);
                valoracion.item(0).setTextContent(String.valueOf(val_nuevo));
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void eliminaPeli(NodeList peliculas, Document document) {
        boolean encontrado = false;
        NodeList catalogos = document.getElementsByTagName("catalogo");
        Element catalogo = (Element) catalogos.item(0);
        String busqueda = ES.leeCadena("Introduce el ID o el el título de la peli: ");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList titulos = pelicula.getElementsByTagName("titulo");
            if (pelicula.getAttribute("id").equalsIgnoreCase(busqueda) || titulos.item(0).getTextContent().equalsIgnoreCase(busqueda)) {
                encontrado = true;
                catalogo.removeChild(pelicula);
                ES.msgln("Película eliminada");
            }
        }

        if(!encontrado) {
            ES.msgErrln(notFound);
        }
    }

    private static void eliminaPeliculaBajaValoracion(Document document, NodeList peliculas, int valoracion_minima) {
        NodeList catalogos = document.getElementsByTagName("catalogo");
        Element catalogo = (Element) catalogos.item(0);

        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList valoracion = pelicula.getElementsByTagName("valoracion");

            if (valoracion.getLength() > 0) {
                if (Float.parseFloat(valoracion.item(0).getTextContent()) < valoracion_minima) {
                    catalogo.removeChild(pelicula);
                }
            }
        }
    }
}
