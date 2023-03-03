package it.proconsole;

import java.lang.reflect.Array;

public class ArrayTest {

  public static void main(String[] args) {
    // Creo un array di 10000 elementi con il costruttore normale
    long startTime1 = System.nanoTime(); // misuro il tempo iniziale
    int[] array1 = new int[100]; // creo l'array
    long endTime1 = System.nanoTime(); // misuro il tempo finale
    long duration1 = endTime1 - startTime1; // calcolo la durata

    // Creo un array di 10000 elementi con la riflessione
    long startTime2 = System.nanoTime(); // misuro il tempo iniziale
    int[] array2 = (int[]) Array.newInstance(int.class, 100); // creo l'array con la riflessione
    long endTime2 = System.nanoTime(); // misuro il tempo finale
    long duration2 = endTime2 - startTime2; // calcolo la durata

    // Stampo i risultati
    System.out.println("Tempo impiegato per creare l'array con il costruttore normale: " + duration1 + " nanosecondi");
    System.out.println("Tempo impiegato per creare l'array con la riflessione: " + duration2 + " nanosecondi");
  }
}