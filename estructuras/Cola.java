public class Cola {
    Nodo frente;
    Nodo fin;

    public Cola(){
        frente = null;
        fin = null;
    }

    public boolean esVacia(){
        return frente==null;
    }

    public boolean poner(Object elemento){
        Nodo nuevoNodo;
        if (this.esVacia()) {
            nuevoNodo = new Nodo(elemento, null);
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            nuevoNodo = new Nodo(elemento, null);
            fin.setEnlace(nuevoNodo);
            fin = nuevoNodo;
        }
        return true;
    }

    public Object obtenerFrente() {
        Object retorna = null;
        if (!esVacia()) {
            retorna = this.frente.getElem();
        }
        return retorna;
    }

    private void clonarRecursivo(Cola colaClon, Nodo aux1){
        if(aux1!=null){
            if(colaClon.esVacia()){
                colaClon.frente = new Nodo(null,aux1.getEnlace());
                colaClon.fin = aux1;
                clonarRecursivo(colaClon, aux1.getEnlace());
            } else {
                colaClon.fin.setEnlace(aux1);
                colaClon.fin = aux1;
                clonarRecursivo(colaClon, aux1.getEnlace());
            }
        }
    }
    
    public Cola clone(){
        Cola colaNueva = new Cola();
        Nodo aux = this.frente;
        clonarRecursivo(colaNueva, aux);
        return colaNueva;
    }

/* 
    public Cola clone(){
        Cola colaNueva = new Cola();
        Nodo aux1,aux2;
        colaNueva.frente = new Nodo(null,this.frente.getEnlace());
        aux1 = new Nodo(this.frente.getEnlace().getElem(), this.frente.getEnlace());
        colaNueva.fin = aux1;
        aux1 = aux1.getEnlace();
        while(aux1!=null){
            aux2 = new Nodo(aux1.getElem(), null);
            colaNueva.fin.setEnlace(aux2);
            colaNueva.fin = aux2;
            aux1 = aux1.getEnlace();
        }
        return colaNueva;
    }
*/

    @Override
    public String toString(){
        String resultado="";
        Nodo aux = frente.getEnlace();
        if (esVacia()) {
            resultado = "Cola vacia";
        } else {
            while(aux!=null){
                resultado = resultado + aux.getElem().toString();
                aux = aux.getEnlace();
                if(aux!=null){
                    resultado = resultado + ", ";
                }
            }
            resultado = "[" + resultado + "]";
        }
        return resultado;
    }

    public void vaciar(){
        frente = null;
        fin = null;
    }

    public boolean sacar(){
        boolean exito;
        if (this.esVacia()) {
            exito = false;
        } else {
            exito = true;
            frente = frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }
        }
        return exito;
    }
}