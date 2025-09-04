
public class NodoBinomial {

    // Atributos
    private int clave; //valor del nodo
    private int grado; //nro de hijos que tiene el nodo
    private NodoBinomial padre; //referencia al nodo padre
    private NodoBinomial hijo; //referencia al nodo hijo
    private NodoBinomial hermano; //referencia al nodo hermano

    //Constructor
    public NodoBinomial(int clave) {
        this.clave = clave;
        this.grado = 0;
        this.padre = null;
        this.hijo = null;
        this.hermano = null;

    }

    // Getters y Setters
    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public NodoBinomial getPadre() {
        return padre;
    }

    public void setPadre(NodoBinomial padre) {
        this.padre = padre;
    }

    public NodoBinomial getHijo() {
        return hijo;
    }

    public void setHijo(NodoBinomial hijo) {
        this.hijo = hijo;
    }

    public NodoBinomial getHermano() {
        return hermano;
    }

    public void setHermano(NodoBinomial hermano) {
        this.hermano = hermano;
    }

    @Override
    public String toString() {
        return "NodoBinomial{"
                + "clave=" + clave
                + ", grado=" + grado
                + ", padre=" + (padre != null ? padre.clave : "null")
                + ", hijo=" + (hijo != null ? hijo.clave : "null")
                + ", hermano=" + (hermano != null ? hermano.clave : "null")
                + '}';
    }
}
