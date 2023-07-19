

public class NodoVert {
    //atributos
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert (Object unElem, NodoVert unVertice){
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

    public NodoVert getSigVertice(){
        return this.sigVertice;
    }

    public void setSigVertice(NodoVert unNodoVert){
        this.sigVertice = unNodoVert;
    }

    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }

    public void setPrimerAdy(NodoAdy unNodoAdy){
        this.primerAdy = unNodoAdy;
    }


}
