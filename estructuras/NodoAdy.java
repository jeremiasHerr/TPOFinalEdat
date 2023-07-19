
public class NodoAdy {
    //atributos
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private double etiqueta;

    public NodoAdy(NodoVert unVertice, NodoAdy unSigAdyacente, double unaEtiqueta) {
        this.vertice = unVertice;
        this.sigAdyacente = unSigAdyacente;
        this.etiqueta = unaEtiqueta;
    }
    
    public NodoVert getVertice() {
        return this.vertice;
    }
    
    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }
    
    public NodoAdy getSigAdyacente() {
        return this.sigAdyacente;
    }
    
    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }
    
    public double getEtiqueta() {
        return this.etiqueta;
    }
    
    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
}
