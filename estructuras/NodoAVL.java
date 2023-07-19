

public class NodoAVL {
    private Comparable elem;
    private int altura = 0;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL (Comparable elemento, NodoAVL hijoIzq, NodoAVL hijoDer){
        this.elem = elemento;
        this.izquierdo = hijoIzq;
        this.derecho = hijoDer;
        recalcularAltura();
    }

    public int getAltura() {
        return this.altura;
    }

    public void recalcularAltura(){
        int alturaIzq,alturaDer;
        if(izquierdo==null){
            alturaIzq=-1;
        } else {
            alturaIzq = izquierdo.getAltura();
        }
        if(derecho==null){
            alturaDer = -1;
        } else {
            alturaDer = derecho.getAltura();
        }
        
            altura = Math.max(alturaIzq, alturaDer)+1;
        
        

    }

    public Comparable getElem(){
        return this.elem;
    }

    public void setElem(Comparable elemento){
        this.elem = elemento;
    }

    public NodoAVL getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVL getDerecho(){
        return this.derecho;
    }

    public void setIzquierdo(NodoAVL hijoIzq){
        this.izquierdo = hijoIzq;
    }

    public void setDerecho(NodoAVL hijoDer){
        this.derecho = hijoDer;
    }

}