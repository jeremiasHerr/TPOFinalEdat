package estructuras;

public class MapeoMuchos {
    NodoAVLMapeo raiz;

    public MapeoMuchos() {
        this.raiz = null;
    }

    public boolean esVacia(){
        return this.raiz==null;
    }

    public Lista obtenerConjuntoRango(){
        Lista resultado = new Lista();
        if(this.raiz!=null){
            obtenerConjuntoRangoAux(raiz, resultado);
        }
        return resultado;
    }

    private void obtenerConjuntoRangoAux(NodoAVLMapeo nAux, Lista resultado){
        if(nAux!=null){
            obtenerConjuntoRangoAux(nAux.getIzquierdo(), resultado);
            int i=1,longitudRango = nAux.getRango().longitud();
            while(i<=longitudRango){
                resultado.insertar(nAux.getRango().recuperar(i), resultado.longitud()+1);
                i++;
            }
            obtenerConjuntoRangoAux(nAux.getDerecho(), resultado);
        }
    }

    public Lista obtenerConjuntoDominio(){
        Lista resultado = new Lista();
        if(this.raiz!=null){
            obtenerConjuntoDominioAux(raiz, resultado);
        }
        return resultado;
    }

    private void obtenerConjuntoDominioAux(NodoAVLMapeo nAux, Lista resultado){
        if(nAux!=null){
            obtenerConjuntoDominioAux(nAux.getIzquierdo(), resultado);
            resultado.insertar(nAux.getDominio(), resultado.longitud()+1);
            obtenerConjuntoDominioAux(nAux.getDerecho(), resultado);
        }
    }

    public Lista obtenerValores(Comparable<Object> unDominio){
        Lista resultado = new Lista();
        if(this.raiz!=null){
            resultado = obtenerValoresAux(raiz, unDominio);
        }
        return resultado;
    }

    private Lista obtenerValoresAux(NodoAVLMapeo nAux, Comparable<Object> unDominio){
        Lista resultado = null;
        if(nAux!=null){
            Comparable<Object> dominioActual = nAux.getDominio();
            if(dominioActual.compareTo(unDominio)==0){
                resultado = nAux.getRango().clone();
            } else {
                if(dominioActual.compareTo(unDominio)<0){
                    resultado = obtenerValoresAux(nAux.getDerecho(), unDominio);
                } else {
                    resultado = obtenerValoresAux(nAux.getIzquierdo(), unDominio);
                }
            }
        }
        return resultado;
    }

    public boolean desasociar(Comparable<Object> unDominio, Object unRango) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = desasociarAux(null, null, this.raiz, unDominio, unRango);
        }
        return exito;
    }

    private boolean desasociarAux(NodoAVLMapeo abuelo, NodoAVLMapeo padre, NodoAVLMapeo nAux,
        Comparable<Object> unDominio, Object unRango) {
        boolean exito = false;
        if (nAux != null) {
            Comparable<Object> dominioActual = nAux.getDominio(); // clave del nodo actual
            if (dominioActual.compareTo(unDominio) == 0) {
                int posicionEliminar = nAux.getRango().localizar(dominioActual);
                exito = nAux.getRango().eliminar(posicionEliminar);
                if (nAux.getRango().esVacia() && exito) {
                    if (nAux.getIzquierdo() != null && nAux.getDerecho() != null) {
                        // caso en el que tiene ambos hijos, puede ser necesario balancear en caso de eliminacion
                        NodoAVLMapeo reemplazo = obtenerReemplazo(nAux);
                        nAux.setDominio(reemplazo.getDominio());
                        nAux.setRango(reemplazo.getRango());
                        nAux.recalcularAltura();
                        padre.recalcularAltura();
                        balancear(balance(nAux), nAux, padre);
                    } else {
                        if (nAux.getIzquierdo() == null && nAux.getDerecho() == null) {
                            // caso en el que no tiene hijos, puede ser necesario balancear en caso de
                            // eliminacion
                            if (padre != null) {
                                if (padre.getDerecho() != null) {
                                    if (padre.getDerecho().getDominio().equals(unDominio)) {
                                        padre.setDerecho(null);
                                    } else {
                                        padre.setIzquierdo(null);
                                    }
                                } else {
                                    padre.setIzquierdo(null);
                                }
                                padre.recalcularAltura();
                                abuelo.recalcularAltura();
                                balancear(balance(padre), padre, abuelo);
                            } else {
                                this.raiz = null;
                            }
                        } else {
                            // caso en el que solamente tiene uno de los dos hijos
                            if (nAux.getIzquierdo() != null) {
                                if (padre != null) {
                                    if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                                        padre.setDerecho(nAux.getIzquierdo());
                                    } else {
                                        padre.setIzquierdo(nAux.getIzquierdo());
                                    }
                                } else {
                                    this.raiz = raiz.getIzquierdo();
                                }
                            } else if (nAux.getDerecho() != null) {
                                if (padre != null) {
                                    if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                                        padre.setDerecho(nAux.getDerecho());
                                    } else {
                                        padre.setIzquierdo(nAux.getDerecho());
                                    }
                                } else {
                                    this.raiz = raiz.getDerecho();
                                }

                            }
                        }
                    }
                }
            }
        }
        return exito;
    }

    public boolean eliminar(Comparable<Object> unaClave) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(null, null, this.raiz, unaClave);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLMapeo abuelo, NodoAVLMapeo padre, NodoAVLMapeo nAux, Comparable<Object> claveEliminar) {
        boolean exito = false;
        if (nAux != null) {
            Comparable<Object> claveAux = nAux.getDominio(); // clave del nodo actual
            if (claveAux.compareTo(claveEliminar) == 0) {
                // el nodo es encontrado
                exito = true;
                if (nAux.getIzquierdo() != null && nAux.getDerecho() != null) {
                    // caso en el que tiene ambos hijos, puede ser necesario balancear en caso de
                    // eliminacion
                    NodoAVLMapeo reemplazo = obtenerReemplazo(nAux);
                    nAux.setDominio(reemplazo.getDominio());
                    nAux.setRango(reemplazo.getRango());
                    nAux.recalcularAltura();
                    padre.recalcularAltura();
                    balancear(balance(nAux), nAux, padre);
                } else {
                    if (nAux.getIzquierdo() == null && nAux.getDerecho() == null) {
                        // caso en el que no tiene hijos, puede ser necesario balancear en caso de
                        // eliminacion
                        if (padre != null) {

                            if (padre.getDerecho() != null) {
                                if (padre.getDerecho().getDominio().equals(claveEliminar)) {
                                    padre.setDerecho(null);
                                } else {
                                    padre.setIzquierdo(null);
                                }
                            } else {
                                padre.setIzquierdo(null);
                            }
                            padre.recalcularAltura();
                            abuelo.recalcularAltura();
                            balancear(balance(padre), padre, abuelo);
                        } else {
                            this.raiz = null;
                        }
                    } else {
                        // caso en el que solamente tiene uno de los dos hijos
                        if (nAux.getIzquierdo() != null) {
                            if (padre != null) {
                                if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                                    padre.setDerecho(nAux.getIzquierdo());
                                } else {
                                    padre.setIzquierdo(nAux.getIzquierdo());
                                }
                            } else {
                                this.raiz = raiz.getIzquierdo();
                            }
                        } else if (nAux.getDerecho() != null) {
                            if (padre != null) {
                                if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                                    padre.setDerecho(nAux.getDerecho());
                                } else {
                                    padre.setIzquierdo(nAux.getDerecho());
                                }
                            } else {
                                this.raiz = raiz.getDerecho();
                            }

                        }
                    }
                }
            } else {
                if (claveAux.compareTo(claveEliminar) > 0) {
                    exito = eliminarAux(padre, nAux, nAux.getIzquierdo(), claveEliminar);
                } else {
                    exito = eliminarAux(padre, nAux, nAux.getDerecho(), claveEliminar);
                }
            }
        }
        return exito;
    }

    private NodoAVLMapeo obtenerReemplazo(NodoAVLMapeo nAux) {
        NodoAVLMapeo reemplazo = null;
        if (nAux != null) {
            if (nAux.getDerecho().getIzquierdo() == null) {
                reemplazo = new NodoAVLMapeo(nAux.getDerecho().getDominio(), nAux.getDerecho().getRango(), null, null);
                nAux.setDerecho(null);
            } else {
                nAux = nAux.getDerecho();
                while (nAux.getIzquierdo().getIzquierdo() != null) {
                    nAux = nAux.getIzquierdo();
                }
                reemplazo = new NodoAVLMapeo(nAux.getIzquierdo().getDominio(), nAux.getDerecho().getRango(), null,
                        null);
                nAux.setIzquierdo(null);
            }
        }
        return reemplazo;
    }

    public boolean asociar(Comparable<Object> unDominio, Object unRango) {
        boolean exito = false;
        if (this.raiz != null) {
            this.raiz.recalcularAltura();
            exito = asociarAux(null, this.raiz, unDominio, unRango);
        } else {
            this.raiz = new NodoAVLMapeo(unDominio, unRango, null, null);
            exito = true;
        }
        return exito;
    }

    private boolean asociarAux(NodoAVLMapeo padre, NodoAVLMapeo nAux, Comparable<Object> unDominio, Object unRango) {
        boolean exito = false;
        if (nAux != null) {
            if (nAux.getDominio().compareTo(unDominio) == 0) {
                // Ya existe el elemento asi que hay que insertar el nuevo elemento rango
                nAux.getRango().insertar(unRango, nAux.getRango().longitud() + 1);
                exito = true;
            } else if (nAux.getDominio().compareTo(unDominio) < 0) {
                if (nAux.getDerecho() != null) {
                    exito = asociarAux(nAux, nAux.getDerecho(), unDominio, unRango);
                } else {
                    exito = true;
                    nAux.setDerecho(new NodoAVLMapeo(unDominio, unRango, null, null));
                }
            } else {
                if (nAux.getIzquierdo() != null) {
                    exito = asociarAux(nAux, nAux.getIzquierdo(), unDominio, unRango);
                } else {
                    exito = true;
                    nAux.setIzquierdo(new NodoAVLMapeo(unDominio, unRango, null, null));
                }
            }
            if (exito) {
                nAux.recalcularAltura();
                balancear(balance(nAux), nAux, padre);
            }
        }
        return exito;
    }

    private void balancear(int balanceNodo, NodoAVLMapeo nAux, NodoAVLMapeo padre) {
        int balanceHijo;
        switch (balanceNodo) {
            case 2:
                // inclinado a la izquierda
                balanceHijo = balance(nAux.getIzquierdo());
                if (balanceHijo == 0 || balanceHijo == 1) {
                    if (padre == null) {
                        this.raiz = rotacionDer(nAux);
                    } else {
                        if (nAux.getDominio().compareTo(padre.getDominio()) < 0) {
                            padre.setIzquierdo(rotacionDer(nAux));
                        } else {
                            padre.setDerecho(rotacionDer(nAux));
                        }
                    }
                } else if (padre == null) {
                    this.raiz = rotacionIzqDer(nAux);
                } else {
                    if (nAux.getDominio().compareTo(padre.getDominio()) < 0) {
                        padre.setIzquierdo(rotacionIzqDer(nAux));
                    } else {
                        padre.setDerecho(rotacionIzqDer(nAux));
                    }
                }
                break;
            case -2:
                // inclinado a la derecha
                balanceHijo = balance(nAux.getDerecho());
                if (balanceHijo == 0 || balanceHijo == -1) {
                    if (padre == null) {
                        this.raiz = rotacionIzq(nAux);
                    } else {
                        if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                            padre.setDerecho(rotacionIzq(nAux));
                        } else {
                            padre.setIzquierdo(rotacionIzq(nAux));
                        }
                    }
                } else {
                    if (padre == null) {
                        this.raiz = rotacionDerIzq(nAux);
                    } else {
                        if (padre.getDominio().compareTo(nAux.getDominio()) < 0) {
                            padre.setDerecho(rotacionDerIzq(nAux));
                        } else {
                            padre.setIzquierdo(rotacionDerIzq(nAux));
                        }
                    }
                }
                break;
        }
    }

    private NodoAVLMapeo rotacionIzq(NodoAVLMapeo nodo) {
        NodoAVLMapeo hijo = nodo.getDerecho();
        NodoAVLMapeo temp = hijo.getIzquierdo();
        hijo.setIzquierdo(nodo);
        nodo.setDerecho(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVLMapeo rotacionDer(NodoAVLMapeo nodo) {
        NodoAVLMapeo hijo = nodo.getIzquierdo();
        NodoAVLMapeo temp = hijo.getDerecho();
        hijo.setDerecho(nodo);
        nodo.setIzquierdo(temp);
        nodo.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }

    private NodoAVLMapeo rotacionIzqDer(NodoAVLMapeo r) {
        r.setIzquierdo(rotacionIzq(r.getIzquierdo()));
        return rotacionDer(r);
    }

    private NodoAVLMapeo rotacionDerIzq(NodoAVLMapeo r) {
        r.setDerecho(rotacionDer(r.getDerecho()));
        return rotacionIzq(r);
    }

    private int balance(NodoAVLMapeo nAux) {
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