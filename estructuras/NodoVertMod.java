package estructuras;

public class NodoVertMod {
    //atributos
    private Object elem;
    private NodoVertMod sigVertice;
    private NodoAdyMod primerAdy;

    public NodoVertMod (Object unElem, NodoVertMod unVertice){
        this.elem = unElem;
        this.sigVertice = unVertice;
        this.primerAdy = null;
    }

    public Object getElem(){
        return this.elem;
    }

    public void setElem(Object unElem){
        this.elem = unElem;
    }

    public NodoVertMod getSigVertice(){
        return this.sigVertice;
    }

    public void setSigVertice(NodoVertMod unNodoVert){
        this.sigVertice = unNodoVert;
    }

    public NodoAdyMod getPrimerAdy(){
        return this.primerAdy;
    }

    public void setPrimerAdy(NodoAdyMod unNodoAdy){
        this.primerAdy = unNodoAdy;
    }

}
