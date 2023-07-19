public class Lista {
     public Nodo cabecera;
    private int longitud;
    public Lista() {
        this.cabecera = null;
        this.longitud=0;
    }

    public boolean insertar(Object nuevoElemento, int pos) {
        boolean exito = true;
        int largo = this.longitud();

        if (pos < 1 || pos > largo + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElemento, this.cabecera);
                this.longitud+=1;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(nuevoElemento, aux.getEnlace());
                aux.setEnlace(nuevo);
                this.longitud+=1;
            }
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;
        int largo = this.longitud();

        if (pos < 1 || pos > largo + 1 || this.cabecera == null) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = cabecera.getEnlace();
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem;
        int largo = this.longitud();

        if (pos < 1 || pos > largo) {
            elem = null;
        } else {
            int i = 1;
            Nodo aux = this.cabecera;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem();
        }
        return elem;
    }

    public int localizar(Object elem) {
        int pos = -1, i = 1, largo = this.longitud();
        Nodo aux = this.cabecera;
        boolean resultado = false;

        while (i <= largo && !resultado && aux != null) {
            resultado = aux.getElem() == elem;
            if (resultado) {
                pos = i;
            } else {
                aux = aux.getEnlace();
                i++;
            }
        }
        return pos;
    }

    public int longitud() {
        int i = 0;
        if (cabecera != null) {
            i = 1;
            Nodo enlace = this.cabecera.getEnlace();
            while (enlace != null) {
                enlace = enlace.getEnlace();
                i++;
            }
        }
        return i;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public Lista clone() {
        Lista clon = new Lista();
        int i, largo = this.longitud();

        if (this.cabecera != null) {
            Nodo aux = this.cabecera.getEnlace();
            clon.cabecera = new Nodo(this.cabecera.getElem(), null);
            Nodo aux2 = clon.cabecera;
            for (i = 1; i < largo; i++) {
                aux2.setEnlace(new Nodo(aux.getElem(), null));
                aux = aux.getEnlace();
                aux2 = aux2.getEnlace();
            }
        }
        return clon;
    }

    public String toString() {
        String resultado = "";
        Nodo aux = this.cabecera;
        if (esVacia()) {
            resultado = "La lista esta vacia";
        } else {
            while (aux != null) {
                resultado = resultado + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    resultado = resultado + ",";
                }
            }
        }
        return resultado;
    }
/*
 * numero x es el elemento a borrar
 * primero recuperamos el elemento de la posicion i para saber si es igual a X
 * si es igual hay q eliminarlo, hay 1 caso especial si i es igual a 1
 * 
 * 
 * 
 */



    public void eliminarApariciones(Object x) {
        int i=1;
        Nodo aux = this.cabecera;
        while(aux.getEnlace()!=null){
            if (i == 1 && (aux.getElem()==x)) {
                aux = cabecera.getEnlace();
                this.cabecera = cabecera.getEnlace();
                this.longitud--;
            } else {
                if(aux.getEnlace()!=null){
                    if((aux.getEnlace().getElem()==x)){
                        aux.setEnlace(aux.getEnlace().getEnlace());
                        this.longitud--;
                    } else {
                        aux = aux.getEnlace();
                        i++;
                    }
                }
            }
        }  
        
        
    }
    public void eliminarOcurrencias(Object x) {
        while (cabecera.getElem().equals(x)) {
            cabecera = cabecera.getEnlace();
        }
        Nodo aux = cabecera;
        while (aux.getEnlace() != null) {
            if (aux.getEnlace().getElem().equals(x)) {
                aux.setEnlace(aux.getEnlace().getEnlace());

            } else {
                aux = aux.getEnlace();
            }
        } System.out.println(longitud);
    }
}
