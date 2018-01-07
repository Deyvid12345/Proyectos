/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcos
 */
public class Juego {

    private static Jugadores jugadores;

    public Juego(Jugadores j) {//consructor de jugadores
        jugadores = j;
    }

    public static boolean compruebaJugadorYPalabra() {
        //comprueba si tenemos jugadores y devuelve true o false.
        if (jugadores.obtenerNumeroJugadores() > 0 && Palabras.listaPalabras.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void jugarRonda() {
        //Inicializamos los jugadores, comprueba si hay jugadores y palabras
        //si no las hay nos dice que no hay jugadores o palabras.
        //Por el contrario si tiene empieza el juego.
        Palabras.palabraSecreta();
        jugadores.inicio();
        if (!compruebaJugadorYPalabra()) {
            MyError ex = new MyError("Error no hay jugadores o palabras.");
            try {
                throw ex;
            } catch (MyError ex1) {
                System.out.println("Tiene que haber jugadores y palabras: ");
            }
        } else {
            menuJugador();
        }
    }

    public static void menuJugador() {
        //El juego a comenzado.
        //Mientras no haya ganador, o no hayan fallado todos, Se juega la ronda y hasta que no haya ganador, no acaba.
        //Tambien comprueba si hay descalificados. si los hay el jugador que lo este no juega.
        Jugador j = new Jugador();
        boolean algunoGana = false;
        int todosFallan = 0;
        while (!algunoGana && todosFallan < jugadores.obtenerNumeroJugadores()) {
            for (int i = 0; i < jugadores.obtenerNumeroJugadores(); i++) {
                j = jugadores.obtenerJugador(i);
                if (!j.getDescalificado()) {
                    if (j.juega()) {
                        j.setAciertos(j.getAciertos()+1);
                        algunoGana = true;
                    }
                    if (j.getDescalificado()) {
                        todosFallan++;
                    }
                }

            }//fin del for

        }//fin del while
    }
}
