package estructuras;

public class Nodo {
    private Object elem;
    private Nodo enlace;

    
    public Nodo(Object unElem, Nodo unEnlace){
        this.elem = unElem;
        this.enlace = unEnlace;
    }
    //Observadores
    public Object getElem() {
        return elem;
    }

    public Nodo getEnlace() {
        return enlace;
    }
    //Modificadores
    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
}
