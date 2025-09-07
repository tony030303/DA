
public class NodoBinomial {

    private int grado, clave;
    private NodoBinomial padre, hijo, hermano;

    //Constructor
    public NodoBinomial(int clave) {
        this.clave = clave;
        this.grado = 0;
        this.padre = null;
        this.hijo = null;
        this.hermano = null;
    }

    //Getters
    public int getGrado() {
        return grado;
    }

    public int getClave() {
        return clave;
    }

    public NodoBinomial getPadre() {
        return padre;
    }

    public NodoBinomial getHijo() {
        return hijo;
    }

    public NodoBinomial getHermano() {
        return hermano;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public void setPadre(NodoBinomial padre) {
        this.padre = padre;

    }

    public void setHijo(NodoBinomial hijo) {
        this.hijo = hijo;
        this.hijo.padre = this;
    }

    public void setHermano(NodoBinomial nuevoHermano) {
        if (this.hermano == null) {
            this.hermano = nuevoHermano;
            this.hermano.padre = this;
        } else {
            setHermanoAux(nuevoHermano); //en caso de que ya tenga hermanos el nodo (para no pisar)
        }

    }

    private void setHermanoAux(NodoBinomial nuevoHermano) {
        if (this.hermano == null && nuevoHermano != null) { //cambié acá
            this.hermano = nuevoHermano;
            if (this.hermano != null) {
                this.hermano.padre = this;
            }
        } else {
            // Si ya tenemos hermano recorremos hasta que sea null
            this.hermano.setHermanoAux(nuevoHermano);
        }
    }

    public int calcularGrado() {
        int g = 0;
        NodoBinomial actual = hijo;
        while (actual != null) {
            g++;
            actual = actual.getHermano();
        }
        return g;
    }

    public void incrementarGrado() {
        this.grado++;
    }

    public boolean esRaiz() {
        return padre == null;
    }

    @Override
    public String toString() {
        return "Nodo{"
                + "clave=" + clave
                + ", grado=" + grado
                + '}';
    }

}
