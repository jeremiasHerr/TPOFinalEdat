package estructuras;

import tpo.Ciudad;

public class NodoVertMod {
    //atributos
    private Ciudad elem;
    private NodoVertMod sigVertice;
    private NodoAdyMod primerAdy;

    public NodoVertMod (Ciudad unElem, NodoVertMod unVertice){
        this.elem = unElem;
        this.sigVertice = unVertice;
        this.primerAdy = null;
    }

    public Ciudad getElem(){
        return this.elem;
    }

    public void setElem(Ciudad unElem){
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
