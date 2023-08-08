package estructuras;

public class NodoAVLMapeo {
    private Comparable<Object> dominio;
    private Lista rango;
    private NodoAVLMapeo izquierdo;
    private NodoAVLMapeo derecho;
    private int altura;

    public NodoAVLMapeo(Comparable<Object> dominio, Object valor, NodoAVLMapeo izq, NodoAVLMapeo der) {
        this.dominio = dominio;
        this.rango = new Lista();
        this.rango.insertar(valor, 1);
        this.izquierdo = izq;
        this.derecho = der;
        recalcularAltura();
    }

    public void setRango(Lista unRango){
        this.rango = unRango;
    }

    public void setDominio(Comparable<Object> unDominio){
        this.dominio = unDominio;
    }

    public Comparable<Object> getDominio(){
        return this.dominio;
    }

    public Lista getRango(){
        return this.rango;
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

    public NodoAVLMapeo getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVLMapeo getDerecho(){
        return this.derecho;
    }

    public void setIzquierdo(NodoAVLMapeo hijoIzq){
        this.izquierdo = hijoIzq;
        recalcularAltura();
    }

    public void setDerecho(NodoAVLMapeo hijoDer){
        this.derecho = hijoDer;
        recalcularAltura();
    }
     
}
