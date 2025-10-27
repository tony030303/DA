
public class ArbolBinomial {

    private NodoBinomial raiz;

    //Constructor
    public ArbolBinomial(int clave) {
        NodoBinomial raiz = new NodoBinomial(clave);
        this.raiz = raiz;
    }

    public ArbolBinomial(NodoBinomial nodo) {
        this.raiz = nodo;
    }

    //Getters
    public int getGrado() {
        return this.raiz.getGrado();
    }

    public NodoBinomial getRaiz() {
        return this.raiz;
    }

    //Unión entre dos arboles binomiales (se usará para unión de heaps cuando dos arboles tengan mismo grado)
    public void unir(ArbolBinomial otro) {
        if (otro != null) {
            NodoBinomial raizAux;
            // CASO 1: otro tiene una raiz menor al propio, otro pasa como raiz
            if (this.raiz.getClave() > otro.raiz.getClave()) {
                // otro pasa como raiz, pero debemos ver donde conectar el nuestro si como hijo o hermano de raiz
                raizAux = this.raiz;
                this.raiz = otro.raiz; // seteamos la nueva raiz
                if (this.raiz.getHermano() != null && raizAux.getHermano() != null) { //cambie 2
                    this.raiz.setHermano(raizAux.getHermano()); // Le dejamos los hermanos
                }

            } // CASO 2: propio tiene una raiz menor al otro, propio QUEDA como raiz
            else {
                // propio QUEDA como raiz
                raizAux = otro.raiz;
            }

            // Seteamos los padres, hijos y hermanos
            raizAux.setPadre(this.raiz);
            if (this.raiz.getHijo() != null && this.raiz.getHijo().getClave() < raizAux.getClave()) {
                // Caso en que raizAux debe de ir como HERMANO
                this.raiz.getHijo().setHermano(raizAux); // seteamos como hermano
                this.raiz.incrementarGrado();

            } else if (this.raiz.getHijo() != null) {
                // Caso en que RAIZAUX debe de ir como HIJO
                NodoBinomial nuevoHermano = this.raiz.getHijo(); // Es el hijo actual que pasara a ser hermano
                this.raiz.setHijo(raizAux); // seteamos como hijo
                this.raiz.getHijo().setHermano(nuevoHermano);
                this.raiz.incrementarGrado();
            } else {
                this.raiz.setHijo(raizAux);
                this.raiz.incrementarGrado();
            }
        }
    }

    //Métodos adicionales
    public NodoBinomial buscarNodo(int clave) {
        //Método que busca un nodo dada una clave en un árbol.
        return buscarNodoAux(this.raiz, clave);
    }

    //Versión anterior que provocaba O(n) en la búsqueda
    /*
    private NodoBinomial buscarNodoAux(NodoBinomial actual, int clave) {
        NodoBinomial hijo, nodo = null;
        //en caso de que no lo encontremos, buscamos por sus hijos
        if (actual.getClave() != clave) {
            if (actual.getHijo() != null) {
                hijo = actual.getHijo();
                while (hijo != null) {
                    nodo = buscarNodoAux(hijo, clave);
                    if (nodo != null) { //si lo encuentra, termina bucle.
                        break;
                    }
                    hijo = hijo.getHermano(); //recorre los hermanos.
                }
            }
        } else { //Sino, encontró al nodo
            nodo = actual;
        }

        return nodo;
    }
     */
    //en esta segunda versión, como hay poda de ramas, se puede estimar un O(log n)
    private NodoBinomial buscarNodoAux(NodoBinomial actual, int clave) {
        NodoBinomial retorno = null;
        if (actual != null && actual.getClave() <= clave) {
            //si no se sobrepasa y estamos buscando nodos menores o iguales a esa clave, seguimos por ahi

            if (actual.getClave() == clave) {
                //Si logramos encontrar la clave exacta, guardo nodo a retornar.
                retorno = actual;
            } else {
                //sino, busco por sus hijos
                NodoBinomial resultado = buscarNodoAux(actual.getHijo(), clave);
                if (resultado != null) {
                    //si lo encontramos, lo guardo para retornar
                    retorno = resultado;
                } else { //Sino, busco en los hermanos
                    retorno = buscarNodoAux(actual.getHermano(), clave);
                }
            }

        }
        return retorno;
    }

    public void ordenarArbol(NodoBinomial nodo) {
        //Método que sirve para ordenar un arbol (para que siempre la raiz sea la minima)
        NodoBinomial padre = null;
        while (nodo.getPadre() != null && nodo.getPadre().getClave() > nodo.getClave()) {
            padre = nodo.getPadre();
            int temp = nodo.getClave(); //intercambio de claves
            nodo.setClave(padre.getClave());
            padre.setClave(temp);
            nodo = padre; //sigo con el siguiente padre hasta que quede correcta
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        imprimirRecursivo(raiz, sb, 0);
        return sb.toString();
    }

    private void imprimirRecursivo(NodoBinomial nodo, StringBuilder sb, int nivel) {
        if (nodo == null) {
            return;
        }

        // indentación según el nivel
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }

        // imprimimos clave y grado
        sb.append(nodo.getClave())
                .append(" (grado=")
                .append(nodo.getGrado())
                .append(")\n");

        // recorrer los hijos con recursión
        NodoBinomial actual = nodo.getHijo();
        while (actual != null) {
            imprimirRecursivo(actual, sb, nivel + 1);
            actual = actual.getHermano();
        }
    }

}
