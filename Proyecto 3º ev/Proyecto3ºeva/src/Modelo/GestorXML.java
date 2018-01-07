/**
 * Clase GestorXML, contiene lo necesario para crear un archivo xml.
 *
 * @author Deyvid Uzunov
 * @version 02/06/2017
 */
package Modelo;

import Controlador.MyError;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author DEYVID
 */
public class GestorXML {

    public static ArrayList<Vehiculo>listaVehiculos;
    public static ArrayList<Propietario>listaPropietarios;
    
    /**
     * Constructor que inicia la lista de los propietarios y vehiculos y la guarda en el ArrayList correspondiente
     * @throws MyError si ocurre un error
     */
    public GestorXML() throws MyError {
        try {
            listaVehiculos=Database.listaVehiculosCompleta();
            listaPropietarios=Database.listadoPropNormal();
        } catch (MyError ex) {
            throw new MyError("no se an podido guardar los datos al array");
        }
    }

    /**
     * Metodo que crea un fichero xml con los datos qu ele pasamos.
     * @param nombreFichero nombre del fichero
     * @param listaVehiculos ArrayList con los vehiculos
     * @param listaProp ArrayList con los propietarios
     * @throws MyError si ocurre un error
     */
    public static void escriboFichero(String nombreFichero, ArrayList<Vehiculo> listaVehiculos, ArrayList<Propietario> listaProp) throws MyError {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            //creamos el DOM del documento XML

            DOMImplementation implementacion = db.getDOMImplementation();

            Document documento = implementacion.createDocument(null, "vehiculos", null);

            documento.setXmlVersion("1.0");

            Element raiz = documento.getDocumentElement();

            ListIterator<Vehiculo> it = listaVehiculos.listIterator();
            while (it.hasNext()) {
                Vehiculo v = it.next();

                //para cada pelicula creo una nueva etiqueta
                //1º creo todos los elementos que mecesito
                
                Element etiquetaVehiculo = documento.createElement("vehiculo");
                Element etiquetaModelo = documento.createElement("modelo");
                Element etiquetaAnio = documento.createElement("anio");
                Element etiquetaProp = documento.createElement("propietario");
                Element etiquetaNombre = documento.createElement("nombre");
                Element etiquetaApellido = documento.createElement("apellido");

                //Creo los nodos con la informacion del vehiculo y propietario
                Text valorModelo = documento.createTextNode(v.getModelo());
                Text valorAnio = documento.createTextNode(Integer.toString(v.getAnio()));
                Text valorNombre = documento.createTextNode("No se encontro el nombre");
                Text valorApellido = documento.createTextNode("No se encontro el apellido");
                ListIterator<Propietario> iT = listaProp.listIterator();
                while (iT.hasNext()) {
                    Propietario p = iT.next();
                    if (v.getPropietario().equals(p.getDni())) {

                        valorNombre = documento.createTextNode(p.getNombre());
                        valorApellido = documento.createTextNode(p.getApellido());
                    }
                }

                //Añado el texto a cada una d el as etiquetas.
                etiquetaModelo.appendChild(valorModelo);
                etiquetaAnio.appendChild(valorAnio);
                etiquetaNombre.appendChild(valorNombre);
                etiquetaApellido.appendChild(valorApellido);

                //añado el atributo a la etiqueta vehiculo
                etiquetaVehiculo.setAttribute("matricula", v.getMatricula());

                
                //Añadimos los hijos a la etiqueta vehiculos
                etiquetaVehiculo.appendChild(etiquetaModelo);
                etiquetaVehiculo.appendChild(etiquetaAnio);
                etiquetaVehiculo.appendChild(etiquetaProp);
                etiquetaProp.appendChild(etiquetaNombre);
                etiquetaProp.appendChild(etiquetaApellido);

                //añadimos la etiqueta vehiculo a la raiz del documento.
                raiz.appendChild(etiquetaVehiculo);

            }//fin del while

            Source origen = new DOMSource(documento);
            //indico el fichero en el que quiero guardarlo.
            Result destino = new StreamResult(new File("fichero/" + nombreFichero + ".xml"));
            Transformer tF = TransformerFactory.newInstance().newTransformer();
            tF.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tF.setOutputProperty(OutputKeys.INDENT, "indent");
            tF.transform(origen, destino);

        } catch (ParserConfigurationException ex) {
            throw new MyError("Error para crear el documento xml");
        } catch (TransformerConfigurationException ex) {
            throw new MyError("Error al transformar el documento");
        } catch (TransformerException ex) {
            throw new MyError("Error al transformar el documento");
        }
    }
}
