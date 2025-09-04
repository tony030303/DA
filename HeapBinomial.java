
import java.util.ArrayList;
import java.util.List;

public class HeapBinomial {

    //Atributos
    private List<ArbolBinomial> raices; //lista de arboles binomiales

    public HeapBinomial() {
        this.raices = new ArrayList<>();
    }

    public HeapBinomial(List<ArbolBinomial> raices) {
        this.raices = raices;
    }

    //Getters y Setters
    public List<ArbolBinomial> getRaices() {
        return raices;
    }

    //OPERACIONES
    public void insertar(int clave) {
        NodoBinomial nodoNuevo = new NodoBinomial(clave);
        ArbolBinomial arbol = new ArbolBinomial(nodoNuevo);
        HeapBinomial heapNuevo = new HeapBinomial();
        heapNuevo.raices.add(arbol);
        HeapBinomial resultado = this.unir(this, heapNuevo);
        this.raices = resultado.raices; //actualizo las raices
    }

    //Unir dos heaps binomiales (crea uno nuevo)
    public static HeapBinomial unir(HeapBinomial h1, HeapBinomial h2) {
        HeapBinomial resultadoHeap = new HeapBinomial();
        int i = 0;
        //Primero, se deben fusionar las listas de raíces por grado.

        List<ArbolBinomial> fusion = fusionarRaices(h1.getRaices(), h2.getRaices());

        boolean fin = false;
        //si no hay raices, retorna el heap vacio.
        if (!fusion.isEmpty()) {
            List<ArbolBinomial> nueva = new ArrayList<>();

            //recorro la lista fusionada para ir agregando los arboles al nuevo heap.
            while (!fin && i < fusion.size()) {
                ArbolBinomial actual = fusion.get(i); //extraigo

                //si es el último arbol, lo agrego y finaliza.
                if (i == fusion.size() - 1) {
                    nueva.add(actual);
                    i++;
                    fin = true; //cambiado
                }
                //si ya fin es true, no hace falta hacer esto
                if (!fin) {
                    ArbolBinomial siguiente = fusion.get(i + 1);

                    //extraigo el arbol siguiente y comparo grados
                    //si son distintos, agrego el actual (no hace falta unir)
                    if (actual.grado() != siguiente.grado()) {
                        nueva.add(actual);
                        i++;
                    } //sino, combinarlos porque tienen mismo grado 
                    else {
                        ArbolBinomial combinado = unirArboles(actual, siguiente);

                        //reemplazo el siguiente por el combinado, y el actual lo descarto
                        fusion.set(i + 1, combinado);
                        i++;
                    }
                }
            }
            //una vez finaliza este proceso, añado las raices al heap binomial
            resultadoHeap.raices = nueva;

        }
        return resultadoHeap;
    }

    private static List<ArbolBinomial> fusionarRaices(List<ArbolBinomial> r1, List<ArbolBinomial> r2) {
        //Método que sirve para fusionar las raices, ordenadas por grado.
        List<ArbolBinomial> fusion = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < r1.size() && j < r2.size()) {
            if (r1.get(i).grado() <= r2.get(j).grado()) {
                fusion.add(r1.get(i++));
            } else {
                fusion.add(r2.get(j++));
            }
        }

        while (i < r1.size()) {
            fusion.add(r1.get(i++));
        }

        while (j < r2.size()) {
            fusion.add(r2.get(j++));
        }

        return fusion;
    }

    private static ArbolBinomial unirArboles(ArbolBinomial a1, ArbolBinomial a2) {
        //Método que une dos árboles del mismo grado
        a1.unir(a2);
        return a1;
    }

    // Método para imprimir el heap completo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HeapBinomial:\n");
        for (int i = 0; i < raices.size(); i++) {
            ArbolBinomial arbol = raices.get(i);
            sb.append("Raíz ").append(i).append(": ").append(arbol).append("\n");
        }
        return sb.toString();
    }

}
