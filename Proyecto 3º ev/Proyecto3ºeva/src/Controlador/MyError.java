/**
 * Clase MyError para controlar los errores.
 * @author Deyvid Uzunov
 * @version 02/06/2017
 */
package Controlador;

/**
 *
 * @author DEYVID
 */
public class MyError extends Exception {
    
    /**
     * constrcutor para MyError
     * @param mensaje mensaje que mostar cuando salga el error.
     */
    public MyError(String mensaje){
        super(mensaje);
    }
}
