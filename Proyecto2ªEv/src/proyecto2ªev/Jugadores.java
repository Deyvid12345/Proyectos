/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Marcos
 */
public class Jugadores {

    private ArrayList<Jugador> listaJugadores = new <Jugador> ArrayList();

    public void nuevoJugador() {
        //metodo que pide un nuevo jugador y lo añade.
        Jugador j = new Jugador(Pantalla.pideCadena("Dime el nombre del nuevo jugador: "), 0);
        listaJugadores.add(j);
    }

    public void muestraPuntuacion() {
        //muestra el menu de jugadores con sus nombres y los aciertos que han tenido.
        if(listaJugadores.size()>0){
        Pantalla.mensaje("*****************************************");
        Pantalla.mensaje("*********** MENU DE GANADORES ***********");
        Pantalla.mensaje("*****************************************");
        Pantalla.mensaje(" ");
        for (Jugador j : listaJugadores) {
            System.out.println(j);
        }
        }else{
            Pantalla.mensaje("No ha jugado nadie. ");
        }
        //// versión II
        /*
        ListIterator <Jugador> it=listaJugadores.listIterator();
        while (it.hasNext()) {
            Jugador j=it.next();
            System.out.println(j);
        }*/
    }
    
    //inicio provisional de jugadores.
    /*public void inicioJugadores() {
        listaJugadores.add(new Jugador("deyvid", 0));
        listaJugadores.add(new Jugador("alguien", 0));

    }*/

    public void inicio() {//para todos los jugadores del array, los inicia a 0 y asigna palabrasecreta.
        for (int i = 0; i < listaJugadores.size(); i++) {
            listaJugadores.get(i).inicio();
        }

    }

    public int obtenerNumeroJugadores() {
        //devuelve el numero de jugadores 
        return listaJugadores.size();
    }

    public Jugador obtenerJugador(int posicion) {
        //obtiene el jugador de la posicion que le des.
        return listaJugadores.get(posicion);
    }
}
