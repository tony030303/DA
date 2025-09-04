
public class ArbolBinomial {

    // Atributos
    private NodoBinomial raiz;

    // Constructor
    public ArbolBinomial(NodoBinomial raiz) {
        this.raiz = raiz;
    }

    // Getters y Setters
    public NodoBinomial getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoBinomial raiz) {
        this.raiz = raiz;
    }

    @Override
    public String toString() {
        return "ArbolBinomial{" + "raiz=" + (raiz != null ? raiz.toString() : "null") + '}';
    }

    // Otros métodos
    public int grado() {
        int grado = -1;
        if (raiz != null) {
            grado = raiz.getGrado();
        }
        return grado;
    }

    public void unir(ArbolBinomial otro) {
        //si la clave del arbol 'this' es > clave arbol 'otro'
        //los intercambiamos para que la raiz de 'this' sea la menor.
        if (this.raiz.getClave() > otro.raiz.getClave()) {
            NodoBinomial temp = this.raiz;
            this.raiz = otro.raiz;
            otro.raiz = temp;
        }
        //actualizamos
        otro.raiz.setPadre(this.raiz);
        otro.raiz.setHermano(this.raiz.getHijo());
        this.raiz.setHijo(otro.raiz);
        this.raiz.setGrado(this.raiz.getGrado() + 1);

    }

}
