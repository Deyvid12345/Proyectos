/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

/**
 *
 * @author Marcos
 */
public class Jugador {

    private String nombre;//Es el nombre del jugador
    private int fallos;//Numero de fallos del jugador
    private boolean descalificado;//Booleano para saber si esta descalificado
    private String secretaGuiones;//la palabra secreta pasada a Guiones para separar los resultados de cada jugador.
    private int aciertos;//variable para guardar el numero de aciertos que a tenido cada jugador.

    //constructoe para crear objetos y guardarlo en listaJugadores
    public Jugador(String nombre, int fallos) {
        this.nombre = nombre;
        this.fallos = fallos;
    }

    public Jugador() {
    }

    @Override
    public String toString() {
        //Sobrescribo el toString para mostrar los jugadores y sus aciertos.
        return "EL jugador " + getNombre() + " Tiene : " + getAciertos() + " Aciertos";
    }

    public void cuentaFallos() throws MyError {
        //Suma los fallos que va cometiendo cada jugador.
        //comprueba si el jugador tiene 5 fallos y lo descalifica.
        fallos++;
        if (fallos == 5) {
            Pantalla.mensaje("Estas descalificado.");
            descalificado = true;
        }
        Pantalla.mensaje("Fallando.... ");
    }

    public boolean verificarFinDeJuego() {
        //comprueba si la palabra del jugador ya es la que hay que adivinar.
        //devuelve true si lo es y false si no.
        if (secretaGuiones.equalsIgnoreCase(Palabras.palabra)) {
            Pantalla.mensaje("Has Ganado!!!!!!!!!!!!!!!!");
            return true;
        }
        return false;
    }

    public boolean juega() {
        //Es el menu dentro de la ronda
        //Se muestra el jugador que esta jugando y sus fallos.
        //se muestra la palabra a adivinar, y se le pide letra llamando a comprueba letra,
        //despues se vuelve a mostrar la palabra guiones y si tiene acertadas su posicion.
        //comprueba si el juego a acabado.
        Pantalla.mensaje("********************************");
        Pantalla.mensaje("JUGADOR: " + getNombre() + " FALLOS: " + getFallos());
        muestraPalabraGuiones();
        compruebaLetra();
        muestraPalabraGuiones();
        return verificarFinDeJuego();
    }

    public String pablabraSecretaGuiones() {
        //transforma la palabra secreta en una palabra con guiones de la misma longitud y la devuelve.
        String palabraGuiones = "";
        for (int x = 0; x < Palabras.palabra.length(); x++) {
            palabraGuiones = palabraGuiones + "_";
        }
        System.out.println(Palabras.palabra);
        return palabraGuiones;
    }

    public void inicio() {
        //pone los jugadores a 0 y les asigna la palabra secreta.
        setFallos(0);
        setAciertos(0);
        descalificado = false;
        secretaGuiones = pablabraSecretaGuiones();
    }

    public void muestraPalabraGuiones() {
        //muestra la palabra secreta en guiones con espacio para que se pueda apreciar cuantos caracteres tiene.
        for (int x = 0; x < secretaGuiones.length(); x++) {
            System.out.print(" " + secretaGuiones.charAt(x) + " ");
        }
        System.out.println("");
    }

    public void compruebaLetra() {
        //pide letra, si la letra esta en la palabra, comprueba en que posicion esta
        //y la escribe en palabra guiones de ese jugador que este jugando.
        //en las posiciones que no este escribe el guion de la palabra con guiones.
        //si la letra no esta en la palabra llama al metodo cuentaFallos() que se encarga de sumarle un fallo al jugador.
        //si la letra que introduce es incorrecta te la pide de nuevo.
        boolean v = true;
        while (v) {
            String palabraNueva = "";
            try {
                String letra = Pantalla.pideLetra();

                if (!Palabras.palabra.contains(letra)) {
                    cuentaFallos();
                }
                for (int a = 0; a < Palabras.palabra.length(); a++) {
                    if (Palabras.palabra.charAt(a) == letra.charAt(0)) {
                        palabraNueva = palabraNueva + letra;
                    } else {
                        palabraNueva = palabraNueva + secretaGuiones.charAt(a);
                    }
                }
                System.out.println(palabraNueva);
                setSecretaGuiones(palabraNueva);
                v = false;

            } catch (MyError ex) {
                Pantalla.mensaje("La letra es incorrecta.");
                compruebaLetra();
            }
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fallos
     */
    public int getFallos() {
        return fallos;
    }

    /**
     * @param aFallos the fallos to set
     */
    public void setFallos(int aFallos) {
        fallos = aFallos;
    }

    /**
     * @return the descalificado
     */
    public boolean getDescalificado() {
        return descalificado;
    }

    /**
     * @param descalificado the aciertos to set
     */
    public void setDescalificado(boolean descalificado) {
        this.descalificado = descalificado;
    }

    /**
     * @return the secretaGuiones
     */
    public String getSecretaGuiones() {
        return secretaGuiones;
    }

    /**
     * @param secretaGuiones the secretaGuiones to set
     */
    public void setSecretaGuiones(String secretaGuiones) {
        this.secretaGuiones = secretaGuiones;
    }

    /**
     * @return the aciertos
     */
    public int getAciertos() {
        return aciertos;
    }

    /**
     * @param aciertos the aciertos to set
     */
    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

}
