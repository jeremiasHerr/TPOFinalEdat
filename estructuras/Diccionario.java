package estructuras;

public class Diccionario {
        NodoAVLDicc raiz;
    
        public Diccionario() {
            this.raiz = null;
        }
    
        public Lista listarClaves() {
            Lista lista = new Lista();
            listarClavesAux(this.raiz, lista);
            return lista;
        }
    
        private void listarClavesAux(NodoAVLDicc nAux, Lista resultado) {
            if (nAux != null) {
                listarClavesAux(nAux.getIzquierdo(), resultado);
                resultado.insertar(nAux.getClave(), resultado.longitud() + 1);
                listarClavesAux(nAux.getDerecho(), resultado);
            }
        }
    
        public Lista listarDatos() {
            Lista lista = new Lista();
            listarDatosAux(this.raiz, lista);
            return lista;
        }
    
        private void listarDatosAux(NodoAVLDicc nAux, Lista resultado) {
            if (nAux != null) {
                listarDatosAux(nAux.getIzquierdo(), resultado);
                resultado.insertar(nAux.getDato(), resultado.longitud() + 1);
                listarDatosAux(nAux.getDerecho(), resultado);
            }
        }
    
        public boolean existeClave(Comparable unaClave){
            boolean existe = false;
            if(this.raiz!=null){
                existe = existeClaveAux(this.raiz, unaClave);
            }
            return existe;
        }
    
        public boolean esVacio(){
            return this.raiz!=null;
        }
    
        public String toString() {
            String resultado = toStringAux(this.raiz);
            return resultado;
        }
    
        private String toStringAux(NodoAVLDicc nodo) {
            String toString = "Arbol vacio";
            if (nodo != null) {
                toString = nodo.getDato().toString() + " | " + "altura: " + nodo.getAltura();
                NodoAVLDicc hijoIzq = nodo.getIzquierdo();
                NodoAVLDicc hijoDer = nodo.getDerecho();
                if (hijoIzq != null) {
                    toString = toString + ", H.I: " + hijoIzq.getDato().toString();
    
                } else {
                    toString = toString + ", H.I: -";
                }
                if (hijoDer != null) {
                    toString = toString + ", H.D: " + hijoDer.getDato().toString() + "\n";
                } else {
                    toString = toString + ", H.D: -\n";
                }
    
                if (hijoIzq != null) {
                    toString = toString + toStringAux(hijoIzq);
                }
    
                if (hijoDer != null) {
                    toString = toString + toStringAux(hijoDer);
                }
            }
            return toString;
        }

        private boolean existeClaveAux (NodoAVLDicc nAux, Comparable unaClave){
            boolean resultado = false;
            if(nAux!=null){
                if(nAux.getClave().compareTo(unaClave)==0){
                    resultado = true;
                } else {
                    if (nAux.getClave().compareTo(unaClave) > 0) {
                        resultado = existeClaveAux(nAux.getIzquierdo(), unaClave);
                    } else {
                        resultado = existeClaveAux(nAux.getDerecho(), unaClave);
                    }
                }
            }
            return resultado;
        }
    
        public Object obtenerDato(Comparable unaClave){
            Object resultado=null;
            if(this.raiz!=null){
                resultado = obtenerDatoAux(this.raiz, unaClave);
            }
            return resultado;
        }
    
        private Object obtenerDatoAux(NodoAVLDicc nAux, Comparable unaClave){
            Object resultado=null;
            if(nAux!=null){
                if(nAux.getClave().compareTo(unaClave)==0){
                    resultado = nAux.getDato();
                } else {
                    if (nAux.getClave().compareTo(unaClave) > 0) {
                        resultado = obtenerDatoAux(nAux.getIzquierdo(), unaClave);
                    } else {
                        resultado = obtenerDatoAux(nAux.getDerecho(), unaClave);
                    }
                }
            }
            return resultado;
        }
    
        public boolean eliminar (Comparable unaClave){
            boolean exito = false;
            if(this.raiz!=null){
                exito = eliminarAux(null,null,this.raiz,unaClave);
            }
            return exito;
        }
    
        private boolean eliminarAux(NodoAVLDicc abuelo, NodoAVLDicc padre, NodoAVLDicc nAux, Comparable claveEliminar) {
            boolean exito = false;
            if (nAux != null) {
                Comparable claveAux = nAux.getClave(); // clave del nodo actual
                if (claveAux.compareTo(claveEliminar) == 0) {
                    //el nodo es encontrado
                    exito = true;
                    if (nAux.getIzquierdo() != null && nAux.getDerecho() != null) {
                        //caso en el que tiene ambos hijos, puede ser necesario balancear en caso de eliminacion
                        NodoAVLDicc reemplazo = obtenerReemplazo(nAux);
                        nAux.setClave(reemplazo.getClave());
                        nAux.setDato(reemplazo.getDato());
                        nAux.recalcularAltura();
                        padre.recalcularAltura();
                        balancear(nAux, padre);
                    } else {
                        if (nAux.getIzquierdo() == null && nAux.getDerecho() == null) {
                            //caso en el que no tiene hijos, puede ser necesario balancear en caso de eliminacion
                            if (padre.getDerecho() != null) {
                                if (padre.getDerecho().getClave().equals(claveEliminar)) {
                                    padre.setDerecho(null);
                                } else {
                                    padre.setIzquierdo(null);
                                }
                            } else {
                                padre.setIzquierdo(null);
                            }
                            padre.recalcularAltura();
                            abuelo.recalcularAltura();
                            balancear(padre, abuelo);
                        } else {
                            //caso en el que solamente tiene uno de los dos hijos
                            if (nAux.getIzquierdo() != null) {
                                if (padre.getClave().compareTo(nAux.getClave()) < 0) {
                                    padre.setDerecho(nAux.getIzquierdo());
                                } else {
                                    padre.setIzquierdo(nAux.getIzquierdo());
                                }
                            } else if (nAux.getDerecho() != null) {
                                if (padre.getClave().compareTo(nAux.getClave()) < 0) {
                                    padre.setDerecho(nAux.getDerecho());
                                } else {
                                    padre.setIzquierdo(nAux.getDerecho());
                                }
                            }
                        }
                    }
    
                } else {
                    if (claveAux.compareTo(claveEliminar) > 0) {
                        eliminarAux(padre, nAux, nAux.getIzquierdo(), claveEliminar);
                    } else {
                        eliminarAux(padre, nAux, nAux.getDerecho(), claveEliminar);
                    }
                }
            }
            return exito;
        }
    
        private NodoAVLDicc obtenerReemplazo(NodoAVLDicc nAux) {
            NodoAVLDicc reemplazo=null;
            if (nAux != null) {
                if (nAux.getDerecho().getIzquierdo() == null) {
                    reemplazo = new NodoAVLDicc(nAux.getDerecho().getClave(), nAux.getDerecho().getDato(), null, null);
                    nAux.setDerecho(null);
                } else {
                    nAux = nAux.getDerecho();
                    while (nAux.getIzquierdo().getIzquierdo() != null) {
                        nAux = nAux.getIzquierdo();
                    }
                    reemplazo = new NodoAVLDicc(nAux.getIzquierdo().getClave(), nAux.getDerecho().getDato(), null, null);
                    nAux.setIzquierdo(null);
                }
            }
            return reemplazo;
        }
    
        public boolean insertar(Comparable unaClave, Object unDato){
            boolean exito = false;
            if(this.raiz!=null){
                exito = insertarAux(null,this.raiz, unaClave, unDato);
            } else{
                this.raiz = new NodoAVLDicc(unaClave, unDato, null, null);
            }
            return exito;
        }
    
        private boolean insertarAux(NodoAVLDicc padre, NodoAVLDicc nAux, Comparable unaClave, Object unDato) {
            boolean exito = false;
            if (nAux != null) {
                if (nAux.getClave().compareTo(unaClave) == 0) {
                    //Ya existe el elemento
                    exito = false;
                } else if (nAux.getClave().compareTo(unaClave) < 0) {
                    if (nAux.getDerecho() != null) {
                        exito = insertarAux(nAux, nAux.getDerecho(), unaClave, unDato);
                    } else {
                        exito = true;
                        nAux.setDerecho(new NodoAVLDicc(unaClave, unDato, null, null));
                    }
                } else {
                    if (nAux.getIzquierdo() != null) {
                        exito = insertarAux(nAux, nAux.getIzquierdo(), unaClave, unDato);
                    } else {
                        exito = true;
                        nAux.setIzquierdo(new NodoAVLDicc(unaClave, unDato, null, null));
                    }
                }
                if (exito) {
                    nAux.recalcularAltura();
                    balancear(nAux, padre);
                }
            }
            return exito;
        }
    
        private void balancear(NodoAVLDicc nAux, NodoAVLDicc padre) {
            int balanceNodo;
            int balanceHijo;
            balanceNodo = balance(nAux);
            if (balanceNodo == 2) {
                balanceHijo = balance(nAux.getIzquierdo());
                if (balanceHijo == 1 || balanceHijo == 0) {
                    if (padre == null) {
                        this.raiz = rotacionDer(nAux);
                    } else {
                        padre.setIzquierdo(rotacionDer(nAux));
                    }
                } else if (balanceHijo == -1) {
                    nAux.setIzquierdo(rotacionIzq(nAux.getIzquierdo()));
                    if (padre != null) {
                        if (padre.getIzquierdo().getClave().equals(nAux.getClave())) {
                            padre.setIzquierdo(rotacionDer(nAux));
                        } else {
                            padre.setDerecho(rotacionDer(nAux));
                        }
                    } else {
                        this.rotacionDer(nAux);
                    }
    
                }
            } else if (balanceNodo == -2) {
                balanceHijo = balance(nAux.getDerecho());
                if (balanceHijo == -1 || balanceHijo == 0) {
                    if (padre == null) {
                        this.raiz = rotacionIzq(nAux);
                    } else {
                        padre.setDerecho(rotacionIzq(nAux));
                    }
                } else if (balanceHijo == 1) {
                    nAux.setDerecho(rotacionDer(nAux.getDerecho()));
                    if (padre != null) {
                        if (padre.getIzquierdo().getClave().equals(nAux.getClave())) {
                            padre.setIzquierdo(rotacionIzq(nAux));
                        } else {
                            padre.setDerecho(rotacionIzq(nAux));
                        }
                    } else {
                        this.raiz = rotacionIzq(nAux);
                    }
    
                }
            }
            nAux.recalcularAltura();
        }
    
        private NodoAVLDicc rotacionIzq(NodoAVLDicc r) {
            NodoAVLDicc h = r.getDerecho();
            NodoAVLDicc temp = h.getIzquierdo();
            h.setIzquierdo(r);
            r.setDerecho(temp);
            h.recalcularAltura();
            r.recalcularAltura();
            return h;
        }
    
        private NodoAVLDicc rotacionDer(NodoAVLDicc r) {
            NodoAVLDicc h = r.getIzquierdo();
            NodoAVLDicc temp = h.getDerecho();
            h.setDerecho(r);
            r.setIzquierdo(temp);
            h.recalcularAltura();
            r.recalcularAltura();
            return h;
        }
    
        private int balance(NodoAVLDicc nAux) {
            int balanceNodo;
            int alturaHijoIzquierdo = -1;
            int alturaHijoDerecho = -1;
            if (nAux.getIzquierdo() != null) {
                alturaHijoIzquierdo = nAux.getIzquierdo().getAltura();
            }
            if (nAux.getDerecho() != null) {
                alturaHijoDerecho = nAux.getDerecho().getAltura();
            }
            balanceNodo = alturaHijoIzquierdo - alturaHijoDerecho;
            return balanceNodo;
        }
    
}
