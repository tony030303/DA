
import java.util.*;

class NodoTrie {

    NodoTrie[] hijos = new NodoTrie[26]; // c/ índice es una letra a-z (opciones posibles)
    boolean esPalabra = false;
    List<String> sinonimos = new ArrayList<>(); // sinónimos de la palabra
}

public class ArbolTrie {

    private NodoTrie raiz = new NodoTrie();

    public void insertar(String palabra, String sinonimo) {
        //Método que inserta una palabra junto a un sinónimo
        NodoTrie actual = raiz;
        //recorro todos los caracteres de la palabra para irlos agregando
        char[] arregloPalabra = palabra.toCharArray();
        int i;
        for (i = 0; i < arregloPalabra.length; i++) {
            int idx = arregloPalabra[i] - 'a'; //busco su indice (valor ASCII)
            if (actual.hijos[idx] == null) { //si no existe, lo agrego
                actual.hijos[idx] = new NodoTrie();
            }
            actual = actual.hijos[idx]; //avanzo
        }
        actual.esPalabra = true; //último nodo = fin de palabra
        actual.sinonimos.add(sinonimo); //agrego el sinonimo
    }

    public boolean agregarSinonimo(String palabra, String sinonimo) {
        //Método que agrega sinónimo a palabra existente
        boolean encontrado = false;
        NodoTrie nodo = buscarNodo(palabra);
        if (nodo != null && nodo.esPalabra) {
            nodo.sinonimos.add(sinonimo);
            encontrado = true;
        }
        return encontrado;
    }

    public List<String> mostrarSinonimos(String palabra) {
        // Método que muestra los sinónimos de una palabra existente
        List<String> sinonimos = new ArrayList<>();
        NodoTrie nodo = buscarNodo(palabra);
        if (nodo != null && nodo.esPalabra) {
            sinonimos = nodo.sinonimos;
        }
        return sinonimos;
    }

    public void listarPalabras() {
        // Método que lista todas las palabras
        listarPalabrasAux(raiz, "");
    }

    private void listarPalabrasAux(NodoTrie nodo, String prefijo) {
        if (nodo.esPalabra) {
            System.out.println(prefijo);
        }
        //Recorro el abecedario para poder ir formando las palabras
        for (int i = 0; i < 26; i++) {
            if (nodo.hijos[i] != null) {
                char c = (char) (i + 'a');
                listarPalabrasAux(nodo.hijos[i], prefijo + c);
            }
        }
    }

    private NodoTrie buscarNodo(String palabra) {
        //Método que busca nodo final de una palabra
        NodoTrie actual = raiz;
        int i = 0;

        while (actual != null && i < palabra.length()) {
            int idx = palabra.charAt(i) - 'a';
            actual = actual.hijos[idx]; // avanza o queda null si no existe
            i++;
        }

        return actual; // si no se encontró, actual será null
    }

}
