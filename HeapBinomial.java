
import java.util.ArrayList;
import java.util.List;

public class HeapBinomial {

    private List<ArbolBinomial> raices; // lista de árboles binomiales

    public HeapBinomial() {
        raices = new ArrayList<>();
    }  // referencia al primer árbol de la lista

    public void insertar(int clave) {
        //Método que se utiliza para insertar un nuevo elemento en el árbol.
        ArbolBinomial nuevo = new ArbolBinomial(clave);
        if (this.raices.isEmpty()) { //si es el primer arbol, no es necesario unir a ningún heap
            this.raices.addFirst(nuevo);
        } else { //en caso de que no sea asi, se crea un heap con ese nodo
            HeapBinomial temp = new HeapBinomial();
            temp.raices.addFirst(nuevo);
            // Se verifica si hay arboles en el heap que tengan mismo grado (se deberán unir por condición)
            this.unir(temp);
        }
    }

    private void insertar(ArbolBinomial arbol) {
        //Método que se utiliza para insertar un nuevo elemento en el árbol.

        if (this.raices.isEmpty()) { //si es el primer arbol, no es necesario unir a ningún heap
            this.raices.addFirst(arbol);
        } else { //en caso de que no sea asi, se crea un heap con ese nodo
            HeapBinomial temp = new HeapBinomial();
            temp.raices.addFirst(arbol);
            // Se verifica si hay arboles en el heap que tengan mismo grado (se deberán unir por condición)
            this.unir(temp);
        }
    }

    // Unión de dos heaps binomiales (definitivo, dsp cambiamos un par de cositas)
    public void unir(HeapBinomial otro) {
        // Paso 1: fusión de listas de raíces (ordenadas por grado)
        List<ArbolBinomial> fusion = new ArrayList<>();
        int i = 0, j = 0;
        //algoritmo mergesort O(n * log n)
        while (i < this.raices.size() && j < otro.raices.size()) {
            if (this.raices.get(i).getGrado() <= otro.raices.get(j).getGrado()) {
                fusion.add(this.raices.get(i++));
            } else {
                fusion.add(otro.raices.get(j++));
            }
        }
        while (i < this.raices.size()) {
            fusion.add(this.raices.get(i++));
        }
        while (j < otro.raices.size()) {
            fusion.add(otro.raices.get(j++));
        }

        // Paso 2: combinar árboles de igual grado
        List<ArbolBinomial> resultado = new ArrayList<>();
        int k = 0;
        while (k < fusion.size()) {
            ArbolBinomial actual = fusion.get(k);

            // Si el siguiente tiene el mismo grado, unirlos
            if (k + 1 < fusion.size() && actual.getGrado() == fusion.get(k + 1).getGrado()) {
                ArbolBinomial siguiente = fusion.get(k + 1);
                actual.unir(siguiente); // unir devuelve el árbol combinado
                k += 2; // saltar los dos, ya que se combinaron
                // Importante: no meto el arbol resultante en la lista de árboles a retornar "resultado",
                //porque puede tener que volver a unirse (el siguiente puede ser mismo grado)
                fusion.add(k, actual);
                continue; //ver como reemplazarlo
            }

            resultado.add(actual);
            k++;
        }

        // Paso 3: actualizar raíces
        this.raices = resultado;
    }

    //O(log n) ya que solo reviso las raices de los árboles
    public int buscarMin() {
        //Método que busca el valor minimo de un heap.
        List<ArbolBinomial> recorrido = this.raices;
        int pos = 0;
        int valorMin = Integer.MAX_VALUE;

        //recorro comparando por todas las raices hasta encontrar la menor.
        while (pos < this.raices.size()) {
            if (this.raices.get(pos).getRaiz().getClave() < valorMin) {
                valorMin = this.raices.get(pos).getRaiz().getClave(); //actualizo
            }
            pos++;
        }
        return valorMin;
    }

    private int buscarPosMin() {
        //Método que busca la pos del valor minimo de un heap.
        List<ArbolBinomial> recorrido = this.raices;
        int pos = 0;
        int posMin = 0;
        int valorMin = Integer.MAX_VALUE;

        //recorro comparando por todas las raices hasta encontrar la menor.
        while (pos < this.raices.size()) {
            if (this.raices.get(pos).getRaiz().getClave() < valorMin) {
                valorMin = this.raices.get(pos).getRaiz().getClave(); //actualizo
                posMin = pos;
            }
            pos++;
        }
        return posMin;
    }

    public int extraerMin() {
        //Método que elimina el valor minimo de un heap (una raiz)
        int posMin = this.buscarPosMin();
        ArbolBinomial arbolRaizMin = this.raices.get(posMin);
        int min = arbolRaizMin.getRaiz().getClave();

        //Paso 1: Eliminar al arbol que contiene el elem minimo del heap
        this.raices.remove(posMin);
        //en caso de que la minima raiz del heap tenga hijos
        if (arbolRaizMin.getRaiz().getHijo() != null) {
            NodoBinomial hijo = arbolRaizMin.getRaiz().getHijo();
            //recorro todos los hijos de la min raiz y los inserto en un heap nuevo.
            HeapBinomial nuevo = new HeapBinomial();
            ArbolBinomial hijoArbolAux = new ArbolBinomial(hijo);
            nuevo.insertar(hijoArbolAux);
            while (hijo.getHermano() != null) {
                hijo = hijo.getHermano();
                hijoArbolAux = new ArbolBinomial(hijo);
                nuevo.insertar(hijoArbolAux);
            }
            //una vez formamos un nuevo heap (con hijos del raizmin eliminado)
            //uno con el heap original
            this.unir(nuevo);
        }
        return min;
    }

    public boolean eliminar(int clave) {
        //método que elimina un nodo (si la clave existe) del heap.
        NodoBinomial nodo = null;
        boolean eliminado = false;
        boolean paso1Exito = false;
        ArbolBinomial actual = null;
        int pos = 0;
        int posArbol = -1;

        //Paso 1: Buscar el nodo.
        while (!paso1Exito && pos < this.raices.size()) {
            actual = this.raices.get(pos); //obtengo el arbol que voy a explorar para averiguar la clave.
            nodo = actual.buscarNodo(clave);
            if (nodo != null) { //lo encuentro, guardo su posición para después eliminarlo
                posArbol = pos;
                paso1Exito = true;
            }
            pos++;
        }

        //Paso 2: convertir ese nodo al minimo valor posible y luego extraerlo.
        if (nodo != null) {
            nodo.setClave(Integer.MIN_VALUE);
            //ordeno el arbol para que quede por encima
            actual.ordenarArbol(nodo);
            //acá podemos invocar a extraerMin 
            this.extraerMin();
            eliminado = true;
        }

        return eliminado;
    }

    public boolean disminuirClave(int viejaC, int nuevaC) {
        //Método que disminuye la clave de un nodo del heap
        NodoBinomial nodo = null;
        boolean claveCambiada = false;
        boolean paso1Exito = false;
        ArbolBinomial actual = null;
        int pos = 0, posArbol = 0;
        //Paso 1: Buscar nodo (con la viejaClave)
        while (!paso1Exito && pos < this.raices.size()) {
            actual = this.raices.get(pos);
            nodo = actual.buscarNodo(viejaC);
            if (nodo != null) {
                posArbol = pos;
                paso1Exito = true;
            }
            pos++;
        }

        //Paso 2: Ordenar de acuerdo a el valor decrementado del nodo
        if (nodo != null && viejaC > nuevaC) { //solo se dará atención si la nueva clave es menor (si se la incrementa no está contemplado en los requisitos que se piden)
            nodo.setClave(nuevaC);
            this.raices.get(posArbol).ordenarArbol(nodo);
            claveCambiada = true;
        }
        return claveCambiada;
    }

    /*
    
    public void disminuirClave(int clave, int nueva) {
        NodoBinomial nodo = null;
        boolean encontrado = false;
        ArbolBinomial actual = null;
        boolean eliminado = false;
        int pos = 0, arbol = 0;
        // Primero buscamos el nodo
        while (pos < this.raices.size()) {
            actual = this.raices.get(pos);
            nodo = actual.buscarNodo(clave);
            if (nodo != null) {
                arbol = pos;
                break;
            }
            pos++;
        }
        if (nodo != null) {
            nodo.setClave(nueva);
            this.raices.get(arbol).ordenar(nodo);
        }
    }
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HeapBinomial:\n");
        for (ArbolBinomial arbol : raices) {
            sb.append(arbol.toString()).append("\n");
        }
        return sb.toString();
    }

}
