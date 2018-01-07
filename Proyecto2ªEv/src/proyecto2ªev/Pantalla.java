/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

import java.util.Scanner;

/**
 *
 * @author Alumno
 */
public class Pantalla {

    public static void mensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static String pideCadena(String mensaje) {
        //pide una cadena y la muestra.
        Scanner teclado = new Scanner(System.in);
        String cadena;
        System.out.println(mensaje);
        cadena = teclado.nextLine();
        return cadena;
    }

    public static int pideEnteroConLimites(int limMax, int limMin, String mensajeCorrecto, String mensajeError) {
        Scanner teclado = new Scanner(System.in);
        int entero;
        mensaje("Dime un entero: ");
        entero = teclado.nextInt();
        while (entero < limMin || entero > limMax) {
            System.out.println(mensajeError);
            entero = teclado.nextInt();
        }
        return entero;
    }

    public static int pideEntero(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        int entero;
        while (true) {
            try {

                mensaje("Elige una opcion: ");
                entero = teclado.nextInt();
                return entero;

            } catch (Exception e) {
                System.out.println("Solo puedes poner enteros");
                teclado.nextLine();
            }
        }

    }

    public static int menu() {
        //Muestra el menú y pide un entero para elegir la opción deseada.
        System.out.println("\t*******************************");
        System.out.println("\t*********AHORCADO **************");
        System.out.println("\t*******************************");
        System.out.println("\t\t1.- Introducir una nueva palabra ");
        System.out.println("\t\t2.- Introducir un nuevo jugador");
        System.out.println("\t\t3.- Jugar una ronda ");
        System.out.println("\t\t4.- Mostrar puntuaciones ");
        System.out.println("\t\tOtro.- Fin");

        return pideEntero("Elige una opción:  ");

    }

    public static String pideLetra() throws MyError {
        //Se encarga de pedir una letra y solo una de las letras que le e puesto comoletrasValidas.
        Scanner teclado = new Scanner(System.in);
        String letraString;
        System.out.println("Dime una letra: ");
        letraString = teclado.next();
        if (letraString.length() > 1) {
            MyError ex = new MyError("Solo pido una letra no una cadena: ");
            throw ex;
        } else {
            String letrasValidas = "abcdefghijklmnñopqrstuvwxyz";
            //si hay mas de 1 letra --> error
            if (letrasValidas.contains(letraString)) {
                return letraString;
            }
        }
        MyError e = new MyError("El caracter introducido no es valido: ");
        throw e;
    }

}
