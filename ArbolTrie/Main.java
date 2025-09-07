
public class Main {

    public static void main(String[] args) {
        ArbolTrie diccionario = new ArbolTrie();

        // Insertar palabras con un sinónimo inicial
        diccionario.insertar("feliz", "contento");
        diccionario.insertar("triste", "melancolico");
        diccionario.insertar("rapido", "veloz");
        diccionario.insertar("lento", "pausado");

        // Agregar más sinónimos
        diccionario.agregarSinonimo("feliz", "alegre");
        diccionario.agregarSinonimo("triste", "abatido");
        diccionario.agregarSinonimo("rapido", "ligero");

        // Listar todas las palabras
        System.out.println("Palabras del diccionario:");
        diccionario.listarPalabras();

        // Mostrar sinónimos de algunas palabras
        System.out.println("\nSinónimos de 'feliz': " + diccionario.mostrarSinonimos("feliz"));
        System.out.println("Sinónimos de 'triste': " + diccionario.mostrarSinonimos("triste"));
        System.out.println("Sinónimos de 'rápido': " + diccionario.mostrarSinonimos("rapido"));
        System.out.println("Sinónimos de 'lento': " + diccionario.mostrarSinonimos("lento"));

        // Intentar mostrar sinónimos de palabra que no existe
        System.out.println("Sinónimos de 'alegría': " + diccionario.mostrarSinonimos("alegria"));
    }
}
