/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2ªev;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class Fichero {
    
    private static String nombreFichero;
    
    public static void inicio(String nombre) {
        //le pone nombre a nombreFihero.
        //se le pone el nombre del fichero.
        nombreFichero=nombre;
        
    }
    
    public static ArrayList<String> leoFichero() {
        //leo todo lo que hay en el fichero y lo vas añadiendo al Array.
        ArrayList <String> palabras=new ArrayList <String>();
        
        FileInputStream caminitoLectura = null;
        DataInputStream fR = null;

        try {
            caminitoLectura = new FileInputStream("palabras.txt");
            fR = new DataInputStream(caminitoLectura);
            while (true) {
                palabras.add(fR.readUTF());
            }
        } catch (EOFException ex) {
            return palabras;
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el fichero!!!.");
            return palabras;
        } catch (IOException ex) {
            System.out.println("Error de entrada y salida.");
            return palabras; // Devolvemos el arrayList vacío
        } finally {
            if (fR != null) {
                try {
                    fR.close();
                } catch (IOException ex1) {
                    System.out.println("Error de entrada y salidaaa.");
                }
            }
        }
        
        
    }
    
    public static void escriboFichero(ArrayList <String> palabras) {
        //Escribimos laspalabras en el fichero.
        DataOutputStream fW = null;
        try {
            String palabra="";
            fW = new DataOutputStream(new FileOutputStream("palabras.txt"));
            //Escribimos datos:
            //recorrer el array y guardar al final
            for (int i = 0; i < palabras.size(); i++) {
               fW.writeUTF(palabras.get(i)); 
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no accesible");
        } catch (IOException ex) {
            System.out.println("Imposible escribir en el fichero");
        } finally {
            System.out.println("Intento cerrar el fichero...");
            if (fW != null) {
                try {
                    fW.close();
                } catch (IOException ex) {
                    System.out.println("Imposible cerrar el fichero");
                    try {
                        throw ex;
                    } catch (IOException ex1) {
                        System.out.println("Error de entrada y salida");
                    }
                }
            }
        }
        
        
        
    }
    
    
    
    
}
