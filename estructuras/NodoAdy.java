package estructuras;
public class SolicitudViaje {
    //atributos
    private Ciudad vertice;
    private SolicitudViaje sigAdyacente;
    private double etiqueta;

    public SolicitudViaje(Ciudad unVertice, SolicitudViaje unSigAdyacente, double unaEtiqueta) {
        this.vertice = unVertice;
        this.sigAdyacente = unSigAdyacente;
        this.etiqueta = unaEtiqueta;
    }
    
    public Ciudad getVertice() {
        return this.vertice;
    }
    
    public void setVertice(Ciudad vertice) {
        this.vertice = vertice;
    }
    
    public SolicitudViaje getSigAdyacente() {
        return this.sigAdyacente;
    }
    
    public void setSigAdyacente(SolicitudViaje sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }
    
    public double getEtiqueta() {
        return this.etiqueta;
    }
    
    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }
}
