/**
 * Clase Propietario contenedora de los atributos de un propietario y sus getter y setters
 *
 * @author Deyvid Uzunov
 * @version 02/06/2017
 */
package Modelo;

import Controlador.MyError;

/**
 *
 * @author DEYVID
 */
public class Propietario {

    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String provincia;

    /**
     * Constructor de propietario.
     *
     * @param dni
     * @param nombre
     * @param apellido
     * @param telefono
     * @param provincia
     */
    public Propietario(String dni, String nombre, String apellido, String telefono, String provincia) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.provincia = provincia;
    }

    /**
     * Constructor vacio
     */
    public Propietario() {

    }

    /**
     * Metodo toArray para transformar un ArrayList en String[]
     *
     * @return los datos transformados.
     */
    public String[] toArray() {
        String datos[] = new String[5];
        datos[0] = dni;
        datos[1] = nombre;
        datos[2] = apellido;
        datos[3] = telefono;
        datos[4] = provincia;
        return datos;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni el dni a introducir
     * @throws Controlador.MyError lanza posible errores
     */
    public void setDni(String dni) throws MyError {
        String patron = "[xyzXYZ]?-?\\d{8}-?[a-zA-Z]";
        String patronExtranjero = "[xyzXYZ]?-?\\d{7}-?[a-zA-Z]";
        if (dni.matches(patron)) {
            System.out.println("DNI correcto!");
        } else if (dni.matches(patronExtranjero)) {
            System.out.println("DNI correcto!");
        } else {
            throw new MyError("El DNI introducido no es valido, ej: 'x-4247222-z' o '12345678k'");
        }
        this.dni = dni;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     * @throws Controlador.MyError lanza posible errores
     */
    public void setNombre(String nombre) throws MyError {
        if (nombre.length() > 30 || nombre.length() < 0) {
            throw new MyError("El Nombre introducido es invalido, longitud maxima 30 cracteres");
        }
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     * @throws Controlador.MyError lanza posible errores
     */
    public void setApellido(String apellido) throws MyError {
        if (apellido.length() > 30 || apellido.length() < 0) {
            throw new MyError("El Apellido introducido es invalido, longitud maxima 30 cracteres");
        }
        this.apellido = apellido;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     * @throws Controlador.MyError lanza posible errores
     */
    public void setTelefono(String telefono) throws MyError {
        String patron="([+]\\d{2})?\\d{9}";
        System.out.println("entro?");
        if (!telefono.matches(patron)) {
            throw new MyError("El Telefono introducido es invalido, formato mal introducido ej: '+34123456789' o '678041111'");
        }
        this.telefono = telefono;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     * @throws Controlador.MyError lanza posible errores
     */
    public void setProvincia(String provincia) throws MyError {
        if (provincia.length() > 20 || provincia.length() < 0) {
            throw new MyError("El Telefono introducido es invalido, longitud maxima 20 cracteres");
        }
        this.provincia = provincia;
    }
}
