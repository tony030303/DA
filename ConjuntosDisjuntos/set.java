
public class set {

    //Atributos
    private int[] set;

    //Constructor: Cada elem comienza en su propio conjunto
    public set(int n) {
        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i; //inicializo cada uno de los conjuntos (siendo su propio representante)
        }
    }

    //Métodos
    //eficiencia en O(1), solo retorna el elemento.
    public int buscar(int x) {
        return this.set[x];
    }

    //Eficiencia en O(n), porque debe recorrer todo el arreglo
    public void fusionar(int a, int b) {
        //Método que fusiona ambos conjuntos (a través de representantes)
        int i = Math.min(a, b);
        int j = Math.max(a, b);
        for (int k = 0; k < set.length; k++) {
            if (set[k] == j) {
                set[k] = i; //reemplazo
            }
        }
    }

}
