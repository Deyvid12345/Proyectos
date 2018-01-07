/**
 * Clase Vehiculo contenedora de los atributos de un vehiculo y sus getter y setters
 * @author Deyvid Uzunov
 * @version 02/06/2017
 */
package Modelo;

import Controlador.MyError;

/**
 *
 * @author DEYVID
 */
public class Vehiculo {
    private String matricula;
    private String modelo;
    private int anio;
    private String propietario;

    /**
     * Constructor de vehiculo
     * @param matricula
     * @param modelo
     * @param anio
     * @param propietario 
     */
    public Vehiculo(String matricula, String modelo, int anio, String propietario) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.anio = anio;
        this.propietario = propietario;
    }

    
    /**
     * constructor vacio
     */
    public Vehiculo(){
        
    }
    
    /**
     * Metodo toArray para transformar un ArrayList en String[]
     * @return los datos transformados.
     */
    public String[] toArray(){
        String []datos=new String[4];
        datos[0]=matricula;
        datos[1]=modelo;
        datos[2]=Integer.toString(anio);
        datos[3]=propietario;
        return datos;
    }
    
    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     * @throws Controlador.MyError lanza posibles errores
     */
    public void setMatricula(String matricula) throws MyError {
        String patron1 = "\\d{4}-?\\D{3}";
        String patron2 = "\\d{4}-?[^aAeEiIoOuU]{3}";
        if (matricula.matches(patron1)) {
            if (matricula.matches(patron2)) {
                System.out.println("Matricula correcta");
            } else {
                throw new MyError("La Matricula introducida no es valida, no puede contener vocales");
            }
        } else {
            throw new MyError("La Matricula introducida no es valida, formato mal introducido");
        }
        this.matricula = matricula;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     * @throws Controlador.MyError lanza posibles errores
     */
    public void setModelo(String modelo) throws MyError {
        if(modelo.length()>25 || modelo.length()<0){
            throw new MyError("El Modelo introducido no es valido,longitud maxima 25 cracteres");
        }
        this.modelo = modelo;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) throws MyError {
        if(Integer.toString(anio).length()>11 && Integer.toString(anio).length()<0){
            throw new MyError("El año introducido no es valido,  ej:(2000)");
        }
        this.anio = anio;
    }

    /**Integer.toString(anio).length()<4
     * @return the propietario
     */
    public String getPropietario() {
        return propietario;
    }

    /**
     * @param propietario the propietario to set
     * @throws Controlador.MyError si ocuure un error
     */
    public void setPropietario(String propietario) throws MyError {
        String patron = "[xyzXYZ]?-?\\d{8}-?[a-zA-Z]";
        String patronExtranjero = "[xyzXYZ]?-?\\d{7}-?[a-zA-Z]";
        if (propietario.matches(patron)) {
            System.out.println("DNI correcto!");
        } else if (propietario.matches(patronExtranjero)) {
            System.out.println("DNI correcto!");
        } else {
            throw new MyError("El DNI introducido no es valido, formato: '12345678k' o 'x-1234567-z'");
        }
        this.propietario = propietario;
    }
}
