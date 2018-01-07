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
 * @author Alumno
 */
public class Proyecto2ªEv {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //al llamar al constructor, cargo el fichero en el ArrayList
        Palabras palabra=new Palabras();
        Jugadores jugadores=new Jugadores();
        Juego juego=new Juego(jugadores);
        //jugadores.inicioJugadores();
        
        int op;
      op=Pantalla.menu();
      while(op>=1 && op<=4){
            try {
                switch(op){
                    case 1:
                        //nueva palabra
                        palabra.nuevaPalabra();
                        break;
                    case 2:
                        //nuevo jugador
                        jugadores.nuevoJugador();
                        break;
                    case 3:
                        //jugar ronda.
                        juego.jugarRonda();
                        break;
                    case 4:
                        //mostrar puntuaciones
                        jugadores.muestraPuntuacion();
                        break;
                    default:
                        
                }
                op=Pantalla.menu();
            } //fin
            catch (MyError ex) {
                ex.getMessage();
            }
      }
      //guardo las palabras en el fichero
      palabra.guardoPalabras();
    } 
}
