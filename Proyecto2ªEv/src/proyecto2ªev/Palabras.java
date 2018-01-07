/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

import java.util.ArrayList;


/**
 *
 * @author Marcos
 */
public class Palabras {

    static ArrayList<String> listaPalabras;
    static String palabra;

    
    public Palabras() {
        //lee del fichero todas las palabras
        Fichero.inicio("palabras.txt");
        listaPalabras=Fichero.leoFichero();
    }
    public void nuevaPalabra() throws MyError  {
        //añade una palabra al array, pidiendo la palabra y dando como letras validas el abecedario.
        try{
        setPalabra(Pantalla.pideCadena("Dime la palabra: "));
        String letrasValidas;
        letrasValidas = "abcdefghijklmnñopqrstuvwxyz";
        for (int x = 0; x < palabra.length(); x++) {
            String letra = String.valueOf(palabra.charAt(x));
            if (letrasValidas.contains(letra)) {
                listaPalabras.add(palabra);
            } else {
                MyError ex = new MyError("La palabra tiene caracteres prohibidos: ");
                    throw ex;
            }
        }
        }catch(Exception e){
            Pantalla.mensaje("Error al introducir la palabra.");
        }
    }
    
    
    public void guardoPalabras() {
        //meto en el fichero el array.
        Fichero.escriboFichero(listaPalabras);
    }

    public static void palabraSecreta() {
        //coge una palabra de listaPalabras y la elige como la palabraSecreta que tienen que adivinar.
        inicioTemporal();
        int aleatorio = (int) ((Math.random() * listaPalabras.size()));
        palabra=listaPalabras.get(aleatorio);
    }

    public static void inicioTemporal() {
        //inicio de palabras que se añaden al array
        listaPalabras.add("chirimbolo");
        listaPalabras.add("caramelito");
        listaPalabras.add("conejito");
        listaPalabras.add("armadillo");

    }

    /**
     * @return the listaPalabras
     */
    public ArrayList<String> getListaPalabras() {
        return listaPalabras;
    }

    /**
     * @param listaPalabras the listaPalabras to set
     */
    public void setListaPalabras(ArrayList<String> listaPalabras) {
        this.listaPalabras = listaPalabras;
    }

    /**
     * @return the palabra
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * @param palabra the palabra to set
     */
    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}
