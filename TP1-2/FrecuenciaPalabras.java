
import java.io.*;
import java.util.*;

//ejercicio 3, TP 1-2
public class FrecuenciaPalabras {

    public static void main(String[] args) {
        //archivo de entrada
        String archivo = "texto.txt";

        //HashMap para almacenar palabra --> Frecuencia de aparición
        Map<String, Integer> frecuencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            //Recorreremos linea por linea 
            while ((linea = br.readLine()) != null) {
                //Paso a minuscula, reemplazo las no-letras por espacios
                linea = linea.toLowerCase().replaceAll("[^a-záéíóúüñ]", " "); //reemplaza por espacio todo lo que NO esta dentro de los parentesis (letras de a - z , letras con tilde y ñ)
                String[] palabras = linea.split("\\s+"); //obtengo las palabras de la linea (espaciadas)

                //Recorro todas las palabras de la linea del texto:
                for (int i = 0; i < palabras.length; i++) {
                    //Si no es vacia, coloco en el hash la palabra y su frecuencia (si aparece por primera vez es 0, sino retorna la cant. de frecuencias)
                    //luego, le suma uno más
                    if (!palabras[i].isEmpty()) {
                        frecuencias.put(palabras[i], frecuencias.getOrDefault(palabras[i], 0) + 1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + e.getMessage());
        }

        // Imprimir resultados
        System.out.println("\nListado de palabras y frecuencias:");

        List<String> claves = new ArrayList<>(frecuencias.keySet()); //armo una lista con las claves para poder imprimir el hash resultante

        for (int i = 0; i < claves.size(); i++) {
            String clave = claves.get(i);
            Integer valor = frecuencias.get(clave);
            System.out.println(clave + " -> " + valor);
        }
    }
}
