package estructuras;

//este grafo va a servir como estructura para los pedidos entre ciudades
public class GrafoMod {
    //atributo
    private NodoVertMod inicio;

    public GrafoMod() {
        this.inicio = null;
    }

    public Lista caminoMasLargo(Object origen, Object destino){
        Lista resultado = new Lista();
        NodoVertMod nOrigen = ubicarVertice(origen);
        NodoVertMod nDestino = ubicarVertice(destino);
        if(nOrigen!=null && nDestino!=null){
            Lista visitados = new Lista(), listaAux = new Lista();
            resultado = caminoMasLargoAux(nOrigen, destino, resultado, listaAux, visitados);
        }
        return resultado;
    }

    private Lista caminoMasLargoAux(NodoVertMod nAux, Object destino, Lista resultado, Lista listaAux, Lista visitados){
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
                NodoAdyMod ady = nAux.getPrimerAdy();
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


    public Lista caminoMasCorto(Object origen, Object destino){
        Lista resultado = new Lista();
        NodoVertMod nOrigen = ubicarVertice(origen);
        NodoVertMod nDestino = ubicarVertice(destino);
        if(nOrigen!=null && nDestino!=null){
            Lista visitados = new Lista(), listaAux = new Lista();
            resultado = caminoMasCortoAux(nOrigen, destino, resultado, listaAux, visitados);
        }
        return resultado;
    }

    private Lista caminoMasCortoAux(NodoVertMod nAux, Object destino, Lista resultado, Lista listaAux, Lista visitados){
        if (nAux != null){
            Object elem = nAux.getElem();
            visitados.insertar(elem, visitados.longitud()+1);
            listaAux.insertar(elem, listaAux.longitud()+1);
            //si llego al destino significa que hay un nuevo camino encontrado y hay que fijarse si es mas corto
            if (elem.equals(destino)){
                if (resultado.esVacia() || (resultado.longitud() > listaAux.longitud())){
                    resultado = listaAux.clone();
                }
            } else { // si no lo encuentra recorre sus adyacentes para buscar el camino
                NodoAdyMod ady = nAux.getPrimerAdy();
                while (ady != null){
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
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
        NodoVertMod aux = this.inicio;
        while(aux!=null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVertMod nodo, Lista visitados){
        Cola nodosCola = new Cola();
        visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
        nodosCola.poner(nodo);
        while(!nodosCola.esVacia()){
            NodoVertMod aux = (NodoVertMod) nodosCola.obtenerFrente();
            nodosCola.sacar();
            NodoAdyMod ady = aux.getPrimerAdy();
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
        NodoVertMod aux = this.inicio;
        while(aux!=null){
            if(visitados.localizar(aux.getElem()) < 0){
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux (NodoVertMod nAux, Lista visitados) {
        if(nAux!=null){
            visitados.insertar(nAux.getElem(), visitados.longitud()+1);
            NodoAdyMod nAdy = nAux.getPrimerAdy();
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

    private String toStringAux(NodoVertMod vertice) {
        String cadena = "";
        if(vertice != null) {
            cadena = cadena + vertice.getElem().toString() + " | Adyacentes: ";
            NodoAdyMod ady = vertice.getPrimerAdy();
            while(ady != null) {
                if(ady.getSigAdyacente() != null) {
                    cadena = cadena + ady.getVertice().getElem().toString() + ", ";
                } else {
                    cadena = cadena + ady.getVertice().getElem().toString();
                }
                ady = ady.getSigAdyacente();
            }
            cadena = cadena + "\n" + toStringAux(vertice.getSigVertice());
        }
        return cadena;
    }

    public boolean existeCamino (Object origen, Object destino) {
        boolean exito = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVertMod nOrigen = ubicarVertice(origen);
            NodoVertMod nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                Lista visitados = new Lista();
                exito = existeCaminoAux(nOrigen, destino, visitados);
            }
        }
       return exito;
    }

    private boolean existeCaminoAux (NodoVertMod nAux, Object destino, Lista visitados) {
        boolean exito = false;
        if(nAux!=null){
            if(nAux.getElem().equals(destino)){
                exito = true;
            } else {
                visitados.insertar(nAux.getElem(), visitados.longitud()+1);
                NodoAdyMod aux = nAux.getPrimerAdy();
                while(!exito && aux!=null){
                    if(visitados.localizar(aux.getVertice().getElem())<0){
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
            NodoVertMod nOrigen = ubicarVertice(origen);
            NodoVertMod nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                if (nOrigen.getPrimerAdy().getVertice() == nDestino) {
                    nOrigen.setPrimerAdy(nOrigen.getPrimerAdy().getSigAdyacente());
                    encontrado = true;
                } else {
                    NodoAdyMod aux = nOrigen.getPrimerAdy();
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
            NodoVertMod nOrigen = ubicarVertice(origen);
            NodoVertMod nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                // Si es el primer adyacente lo borro, sino busco en todos los adyacentes si
                // existe el arco
                if (nOrigen.getPrimerAdy().getVertice() == nDestino) {
                    nOrigen.setPrimerAdy(nOrigen.getPrimerAdy().getSigAdyacente());
                    encontrado = true;
                } else {
                    NodoAdyMod aux = nOrigen.getPrimerAdy();
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
                NodoAdyMod aux2 = nDestino.getPrimerAdy();
                boolean salir2 = false;
                if(encontrado){
                    if (nDestino.getPrimerAdy().getVertice() == nOrigen) {
                        nDestino.setPrimerAdy(nDestino.getPrimerAdy().getSigAdyacente());
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

    public boolean insertarArco(Object origen, Object destino, Object unaEtiqueta) {
        boolean exito = false;
        if (!origen.equals(destino) && this.inicio != null) {
            NodoVertMod nOrigen = ubicarVertice(origen);
            NodoVertMod nDestino = ubicarVertice(destino);
            if (nOrigen != null && nDestino != null) {
                nOrigen.setPrimerAdy(new NodoAdyMod(nDestino, nOrigen.getPrimerAdy(), unaEtiqueta));
                nDestino.setPrimerAdy(new NodoAdyMod(nOrigen, nDestino.getPrimerAdy(), unaEtiqueta));
                exito = true;
            }
        }
        return exito;
    }

    public boolean existeVertice(Object unVertice) {
        boolean exito = false;
        if (inicio != null) {
            NodoVertMod aux = this.inicio;
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
            if (inicio.getElem().equals(unVertice)) {
                 //mando a eliminar todos los arcos que puedan estar conectados con el vertice que voy a borrar
                eliminarAdyacentesDe(this.inicio.getPrimerAdy(), unVertice);
                this.inicio = inicio.getSigVertice();
                exito = true;
            } else {
                NodoVertMod aux = this.inicio;
                while (aux != null && !exito) {
                    if (aux.getSigVertice().getElem().equals(unVertice)) {
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

    private void eliminarAdyacentesDe (NodoAdyMod nAux, Object unVertice){
        //este modulo elimina los adyacentes que tengan como destino a unVertice
        while(nAux!=null){
            NodoAdyMod aux = nAux.getVertice().getPrimerAdy();
            if(aux.getVertice().getElem().equals(unVertice)){
                nAux.getVertice().setPrimerAdy(aux.getSigAdyacente());
            } else {
                boolean salir = false;
                while(aux!=null && !salir){
                    if(aux.getSigAdyacente().getVertice().getElem().equals(unVertice)){
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

    private NodoVertMod ubicarVertice(Object buscado) {
        // aux que se usa para ver si ya existe el nuevo vertice a ingresar
        NodoVertMod aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVertMod aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVertMod(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }
}
