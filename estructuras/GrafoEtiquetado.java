package estructuras;
import tpo.Ciudad;
public class GrafoEtiquetado {
    //atributo
    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public Lista caminoMasLargo(Object origen, Object destino){
        Lista resultado = new Lista();
        NodoVert nOrigen = ubicarVertice(origen);
        NodoVert nDestino = ubicarVertice(destino);
        if(nOrigen!=null && nDestino!=null){
            Lista visitados = new Lista(), listaAux = new Lista();
            resultado = caminoMasLargoAux(nOrigen, destino, resultado, listaAux, visitados);
        }
        return resultado;
    }

    private Lista caminoMasLargoAux(NodoVert nAux, Object destino, Lista resultado, Lista listaAux, Lista visitados){
        if (nAux != null){
            Object elem = nAux.getElem();
            visitados.insertar(elem, visitados.longitud()+1);
            listaAux.insertar(elem, listaAux.longitud()+1);
            //si llego al destino significa que hay un nuevo camino encontrado y hay que fijarse si es mas corto
            if (elem.equals(destino)){
                if (resultado.esVacia() || (resultado.longitud() < listaAux.longitud())){
                    resultado = listaAux.clone();
                }
            } else { // si no lo encuentra recorre sus adyacentes para buscar el camino
                NodoAdy ady = nAux.getPrimerAdy();
                while (ady != null){
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
                        resultado = caminoMasLargoAux(ady.getVertice(), destino, resultado, listaAux, visitados); 
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            listaAux.eliminar(listaAux.longitud());
            visitados.eliminar(visitados.longitud());//es necesario sacarlo ya que para nuevos caminos puede necesitar pasa por el mismo nodo otra vez
        }
        return resultado;
    }

    public Lista caminoMasCortoKm(Object origen, Object destino){
        Lista resultado = new Lista();
        NodoVert nOrigen = ubicarVertice(origen);
        NodoVert nDestino = ubicarVertice(destino);
        double[] kmResultado = new double[1]; 
        if(nOrigen!=null && nDestino!=null){
            Lista visitados = new Lista(), listaAux = new Lista();
            resultado = caminoMasCortoKmAux(nOrigen, destino,0,kmResultado, resultado, listaAux, visitados);
        }
        return resultado;
    }

    private Lista caminoMasCortoKmAux(NodoVert nAux, Object destino, double kmActual, double[]kmResultado ,Lista resultado, Lista listaAux, Lista visitados){
        if (nAux != null){
            Ciudad elem = (Ciudad)nAux.getElem();
            visitados.insertar(elem.getCodigoPostal(), visitados.longitud()+1);
            listaAux.insertar(elem.getCodigoPostal(), listaAux.longitud()+1);
            //si llego al destino significa que hay un nuevo camino encontrado y hay que fijarse si es mas corto
            if (elem.getCodigoPostal().equals((String)destino)){
                if (kmResultado[0]==0 || kmResultado[0]>kmActual){
                    resultado = listaAux.clone();
                    kmResultado[0] = kmActual;
                }
            } else { // si no lo encuentra recorre sus adyacentes para buscar el camino
                NodoAdy ady = nAux.getPrimerAdy();
                while (ady != null){
                    if (visitados.localizar(((Ciudad)(ady.getVertice().getElem())).getCodigoPostal()) < 0){
                        resultado = caminoMasCortoKmAux(ady.getVertice(), destino,kmActual+ady.getEtiqueta(),kmResultado, resultado, listaAux, visitados); 
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            listaAux.eliminar(listaAux.longitud());
            visitados.eliminar(visitados.longitud());//es necesario sacarlo ya que para nuevos caminos puede necesitar pasa por el mismo nodo otra vez
        }
        return resultado;
    }


    public Lista caminoMasCorto(Object origen, Object destino){
        Lista resultado = new Lista();
        NodoVert nOrigen = ubicarVertice(origen);
        NodoVert nDestino = ubicarVertice(destino);
        if(nOrigen!=null && nDestino!=null){
            Lista visitados = new Lista(), listaAux = new Lista();
            resultado = caminoMasCortoAux(nOrigen, destino, resultado, listaAux, visitados);
        }
        return resultado;
    }

    private Lista caminoMasCortoAux(NodoVert nAux, Object destino, Lista resultado, Lista listaAux, Lista visitados){
        if (nAux != null){
            Ciudad elem = (Ciudad)nAux.getElem();
            visitados.insertar(elem.getCodigoPostal(), visitados.longitud()+1);
            listaAux.insertar(elem.getCodigoPostal(), listaAux.longitud()+1);
            //si llego al destino significa que hay un nuevo camino encontrado y hay que fijarse si es mas corto
            if (elem.getCodigoPostal().equals((String)destino)){
                if (resultado.esVacia() || (resultado.longitud() > listaAux.longitud())){
                    resultado = listaAux.clone();
                }
            } else { // si no lo encuentra recorre sus adyacentes para buscar el camino
                NodoAdy ady = nAux.getPrimerAdy();
                while (ady != null){
                    if (visitados.localizar(((Ciudad)(ady.getVertice().getElem())).getCodigoPostal()) < 0){
                        resultado = caminoMasCortoAux(ady.getVertice(), destino, resultado, listaAux, visitados); 
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            listaAux.eliminar(listaAux.longitud());
            visitados.eliminar(visitados.longitud());//es necesario sacarlo ya que para nuevos caminos puede necesitar pasa por el mismo nodo otra vez
        }
        return resultado;
    }

    public Lista listarEnAnchura(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while(aux!=null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert nodo, Lista visitados){
        Cola nodosCola = new Cola();
        visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
        nodosCola.poner(nodo);
        while(!nodosCola.esVacia()){
            NodoVert aux = (NodoVert) nodosCola.obtenerFrente();
            nodosCola.sacar();
            NodoAdy ady = aux.getPrimerAdy();
            while(ady!=null){
                if(visitados.localizar(ady.getVertice().getElem()) < 0){
                    visitados.insertar(ady.getVertice().getElem(), visitados.longitud() + 1);
                    nodosCola.poner(ady.getVertice());
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnProfundidad(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while(aux!=null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux (NodoVert nAux, Lista visitados) {
        if(nAux!=null){
            visitados.insertar(nAux.getElem(), visitados.longitud()+1);
            NodoAdy nAdy = nAux.getPrimerAdy();
            while(nAdy!=null){
                if(visitados.localizar(nAdy.getVertice().getElem()) < 0){
                    listarEnProfundidadAux(nAdy.getVertice(), visitados);
                }
                nAdy = nAdy.getSigAdyacente();
            }
        }
    }

    public String toString() {
        //este modulo es para testeo
        String resultado;
        if(this.inicio != null) {
            resultado = toStringAux(this.inicio);
        } else {
            resultado = "El grafo esta vacio";
        }
        return resultado;
    }

    private String toStringAux(NodoVert vertice) {
        String cadena = "";
        if(vertice != null) {
            cadena = cadena + vertice.getElem().toString() + " | Adyacentes: ";
            NodoAdy ady = vertice.getPrimerAdy();
            while(ady != null) {
                if(ady.getSigAdyacente() != null) {
                    cadena = cadena + ady.getVertice().getElem().toString() + " | ";
                } else {
                    cadena = cadena + ady.getVertice().getElem().toString();
                }
                ady = ady.getSigAdyacente();
            }
            cadena = cadena + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" + toStringAux(vertice.getSigVertice());
        }
        return cadena;
    }

    public boolean existeCamino (Object origen, Object destino) {
        boolean exito = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVert nOrigen = ubicarVertice(origen);
            NodoVert nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                Lista visitados = new Lista();
                exito = existeCaminoAux(nOrigen, destino, visitados);
            }
        }
       return exito;
    }

    private boolean existeCaminoAux (NodoVert nAux, Object destino, Lista visitados) {
        boolean exito = false;
        if(nAux!=null){
            Ciudad elem = (Ciudad)nAux.getElem();
            if(((String)elem.getCodigoPostal()).equals((String)destino)){
                exito = true;
                System.out.println("existe");
            } else {
                visitados.insertar((String)elem.getCodigoPostal(), visitados.longitud()+1);
                NodoAdy aux = nAux.getPrimerAdy();
                while(!exito && aux!=null){
                    if(visitados.localizar((String)elem.getCodigoPostal())<0){
                        exito = existeCaminoAux(aux.getVertice(), destino, visitados);
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public void vaciar(){
        this.inicio = null;
    }

    public boolean esVacio(){
        return this.inicio==null;
    }

    public boolean existeArco (Object origen, Object destino){
        boolean encontrado = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVert nOrigen = ubicarVertice(origen);
            NodoVert nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                if (nOrigen.getPrimerAdy().getVertice() == nDestino) {
                    nOrigen.setPrimerAdy(nOrigen.getPrimerAdy().getSigAdyacente());
                    encontrado = true;
                } else {
                    NodoAdy aux = nOrigen.getPrimerAdy();
                    while(aux!=null && !encontrado){
                        encontrado = aux.getVertice().getElem().equals(nDestino.getElem());
                        aux = aux.getSigAdyacente();
                    }
                }
            }
        }
        return encontrado;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false, encontrado = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVert nOrigen = ubicarVertice(origen);
            NodoVert nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                // Si es el primer adyacente lo borro, sino busco en todos los adyacentes si
                // existe el arco
                if (nOrigen.getPrimerAdy().getVertice() == nDestino) {
                    nOrigen.setPrimerAdy(nOrigen.getPrimerAdy().getSigAdyacente());
                    encontrado = true;
                } else {
                    NodoAdy aux = nOrigen.getPrimerAdy();
                    boolean salir = false;
                    while (aux != null && !salir) {
                        if (aux.getSigAdyacente().getVertice() == nDestino) {
                            salir = true;
                        } else {
                            aux = aux.getSigAdyacente();
                        }
                    }
                    // Si aux es distinto de null significa que lo encontró al adyacente
                    if (aux != null) {
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        encontrado = true;
                    }
                }
                // Ahora queda eliminarlo desde el destino al origen, ya que es un grafo no dirigido y los
                // arcos van en ambos sentidos
                NodoAdy aux2 = nDestino.getPrimerAdy();
                boolean salir2 = false;
                if(encontrado){
                    if (nDestino.getPrimerAdy().getVertice() == nOrigen) {
                        nDestino.setPrimerAdy(nDestino.getPrimerAdy().getSigAdyacente());
                        exito = true;
                    } else {
                        while (aux2 != null && !salir2) {
                            if (aux2.getSigAdyacente().getVertice() == nOrigen) {
                                salir2 = true;
                            } else {
                                aux2 = aux2.getSigAdyacente();
                            }
                        }
                        // Si aux es distinto de null significa que lo encontró al adyacente
                        if (aux2 != null) {
                            aux2.setSigAdyacente(aux2.getSigAdyacente().getSigAdyacente());
                            exito = true;
                        }
                    }
                }
                
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, double unaEtiqueta) {
        boolean exito = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVert nOrigen = ubicarVertice(origen);
            NodoVert nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                nOrigen.setPrimerAdy(new NodoAdy(nDestino, nOrigen.getPrimerAdy(), unaEtiqueta));
                nDestino.setPrimerAdy(new NodoAdy(nOrigen, nDestino.getPrimerAdy(), unaEtiqueta));
                exito = true;
            }
        }
        return exito;
    }

    public boolean existeVertice(Object unVertice) {
        boolean exito = false;
        if (inicio != null) {
            NodoVert aux = this.inicio;
            while (aux != null && !exito) {
                aux = aux.getSigVertice();
                exito = aux.getElem().equals(unVertice);
            }
        }
        return exito;
    }

    public boolean eliminarVertice(Object unVertice) {
        boolean exito = false;
        if (inicio != null) {
            if (((Ciudad)inicio.getElem()).getCodigoPostal().equals(unVertice)) {
                 //mando a eliminar todos los arcos que puedan estar conectados con el vertice que voy a borrar
                eliminarAdyacentesDe(this.inicio.getPrimerAdy(), unVertice);
                this.inicio = inicio.getSigVertice();
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                while (aux.getSigVertice() != null && !exito) {
                    if (((Ciudad)aux.getSigVertice().getElem()).getCodigoPostal().equals(unVertice)) {
                        //mando a eliminar todos los arcos que puedan estar conectados con el vertice que voy a borrar
                        eliminarAdyacentesDe(aux.getSigVertice().getPrimerAdy(), unVertice);
                        aux.setSigVertice(aux.getSigVertice().getSigVertice());
                        exito = true;
                    } else {
                        aux = aux.getSigVertice();
                    }
                }
            }
        }
        return exito;
    }

    private void eliminarAdyacentesDe (NodoAdy nAux, Object unVertice){
        //este modulo elimina los adyacentes que tengan como destino a unVertice
        while(nAux!=null){
            NodoAdy aux = nAux.getVertice().getPrimerAdy();
            if(((Ciudad)aux.getVertice().getElem()).getCodigoPostal().equals(unVertice)){
                nAux.getVertice().setPrimerAdy(aux.getSigAdyacente());
            } else {
                boolean salir = false;
                while(aux.getSigAdyacente()!=null && !salir){
                    if(((Ciudad)aux.getSigAdyacente().getVertice().getElem()).getCodigoPostal().equals(unVertice)){
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        salir = true;
                    } else {
                        aux = aux.getSigAdyacente();
                    }
                }
            }
            nAux = nAux.getSigAdyacente();
        }
    }        

    private NodoVert ubicarVertice(Object buscado) {
        // aux que se usa para ver si ya existe el nuevo vertice a ingresar
        NodoVert aux = this.inicio;
        while (aux != null && !((Ciudad)aux.getElem()).getCodigoPostal().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarVertice(Ciudad nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }
}
