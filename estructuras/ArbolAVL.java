package estructuras;
public class ArbolAVL {
    private NodoAVL raiz;

    public Comparable maximoElem() {
        Comparable resultado = null;
        if (this.raiz != null) {
            NodoAVL aux = this.raiz;
            while (aux.getDerecho() != null) {
                aux = aux.getDerecho();
            }
            resultado = aux.getElem();
        }
        return resultado;
    }

    public Comparable minimoElem() {
        Comparable resultado = null;
        if (this.raiz != null) {
            NodoAVL aux = this.raiz;
            while (aux.getIzquierdo() != null) {
                aux = aux.getIzquierdo();
            }
            resultado = aux.getElem();
        }
        return resultado;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz != null;
    }

    public ArbolAVL() {
        this.raiz = null;
    }

    public Lista listarRango(Comparable minimo, Comparable maximo) {
        Lista resultado = new Lista();
        if (this.raiz != null) {
            listarRangoAux(this.raiz, minimo, maximo, resultado);
        }
        return resultado;
    }

    @SuppressWarnings("unchecked")
    private void listarRangoAux(NodoAVL nAux, Comparable minimo, Comparable maximo, Lista resultado) {
        if (nAux != null) {
            if (nAux.getElem().compareTo(minimo) >= 0 && nAux.getElem().compareTo(maximo) <= 0) {
                resultado.insertar(nAux.getElem(), resultado.longitud() + 1);
            }
            if (nAux.getElem().compareTo(minimo) >= 0) {
                listarRangoAux(nAux.getIzquierdo(), minimo, maximo, resultado);
            }
            if (nAux.getElem().compareTo(maximo) <= 0) {
                listarRangoAux(nAux.getDerecho(), minimo, maximo, resultado);
            }
        }
    }

    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVL nAux, Lista resultado) {
        if (nAux != null) {
            listarAux(nAux.getIzquierdo(), resultado);
            resultado.insertar(nAux.getElem(), resultado.longitud() + 1);
            listarAux(nAux.getDerecho(), resultado);
        }
    }

    public boolean pertenece(Comparable elem) {
        boolean pertenece = false;
        if (this.raiz != null) {
            pertenece = perteneceAux(this.raiz, elem);
        }
        return pertenece;
    }

    private boolean perteneceAux(NodoAVL nAux, Comparable elem) {
        boolean pertenece = false;
        if (nAux != null) {
            if (nAux.getElem().compareTo(elem) == 0) {
                pertenece = true;
            } else {
                if (nAux.getElem().compareTo(elem) < 0) {
                    pertenece = perteneceAux(nAux.getDerecho(), elem);
                } else {
                    pertenece = perteneceAux(nAux.getIzquierdo(), elem);
                }
            }
        }
        return pertenece;
    }

    public boolean eliminar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(null, null, this.raiz, elemento);
        }
        return exito;
    }

    @SuppressWarnings("unchecked")

    private boolean eliminarAux(NodoAVL abuelo, NodoAVL padre, NodoAVL nAux, Comparable elemento) {
        boolean exito = false;
        if (nAux != null) {
            Comparable elemAux = nAux.getElem(); // elemento del nodo actual
            if (elemAux.compareTo(elemento) == 0) {
                //el nodo es encontrado
                exito = true;
                if (nAux.getIzquierdo() != null && nAux.getDerecho() != null) {
                    //caso en el que tiene ambos hijos, puede ser necesario balancear en caso de eliminacion
                    Comparable reemplazo = obtenerReemplazo(nAux);
                    nAux.setElem(reemplazo);
                    nAux.recalcularAltura();
                    padre.recalcularAltura();
                    balancear(nAux, padre);
                } else {
                    if (nAux.getIzquierdo() == null && nAux.getDerecho() == null) {
                        //caso en el que no tiene hijos, puede ser necesario balancear en caso de eliminacion
                        if (padre.getDerecho() != null) {
                            if (padre.getDerecho().getElem().equals(elemento)) {
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
                            if (padre.getElem().compareTo(nAux.getElem()) < 0) {
                                padre.setDerecho(nAux.getIzquierdo());
                            } else {
                                padre.setIzquierdo(nAux.getIzquierdo());
                            }
                        } else if (nAux.getDerecho() != null) {
                            if (padre.getElem().compareTo(nAux.getElem()) < 0) {
                                padre.setDerecho(nAux.getDerecho());
                            } else {
                                padre.setIzquierdo(nAux.getDerecho());
                            }
                        }
                    }
                }

            } else {
                if (elemAux.compareTo(elemento) > 0) {
                    eliminarAux(padre, nAux, nAux.getIzquierdo(), elemento);
                } else {
                    eliminarAux(padre, nAux, nAux.getDerecho(), elemento);
                }
            }
        }
        return exito;
    }

    private Comparable obtenerReemplazo(NodoAVL nAux) {
        Comparable resultado = null;
        if (nAux != null) {
            if (nAux.getDerecho().getIzquierdo() == null) {
                resultado = nAux.getDerecho().getElem();
                nAux.setDerecho(null);
            } else {
                nAux = nAux.getDerecho();
                while (nAux.getIzquierdo().getIzquierdo() != null) {
                    nAux = nAux.getIzquierdo();
                }
                resultado = nAux.getIzquierdo().getElem();
                nAux.setIzquierdo(null);
            }
        }
        return resultado;
    }

    public String toString() {
        String resultado = toStringAux(this.raiz);
        return resultado;
    }

    private String toStringAux(NodoAVL nodo) {
        String toString = "Arbol vacio";
        if (nodo != null) {
            toString = nodo.getElem().toString() + " | " + "altura: " + nodo.getAltura();
            NodoAVL hijoIzq = nodo.getIzquierdo();
            NodoAVL hijoDer = nodo.getDerecho();
            if (hijoIzq != null) {
                toString = toString + ", H.I: " + hijoIzq.getElem().toString();

            } else {
                toString = toString + ", H.I: -";
            }
            if (hijoDer != null) {
                toString = toString + ", H.D: " + hijoDer.getElem().toString() + "\n";
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

    public boolean insertar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = insertarAux(null, this.raiz, elemento);
        } else {
            exito = true;
            raiz = new NodoAVL(elemento, null, null);
        }
        return exito;
    }

    @SuppressWarnings("unchecked")
    private boolean insertarAux(NodoAVL padre, NodoAVL nAux, Comparable elemento) {
        boolean exito = false;
        if (nAux != null) {
            if (nAux.getElem().compareTo(elemento) == 0) {
                exito = false;
            } else if (nAux.getElem().compareTo(elemento) < 0) {
                if (nAux.getDerecho() != null) {
                    exito = insertarAux(nAux, nAux.getDerecho(), elemento);
                } else {
                    exito = true;
                    nAux.setDerecho(new NodoAVL(elemento, null, null));
                }
            } else {
                if (nAux.getIzquierdo() != null) {
                    exito = insertarAux(nAux, nAux.getIzquierdo(), elemento);
                } else {
                    exito = true;
                    nAux.setIzquierdo(new NodoAVL(elemento, null, null));
                }
            }
            if (exito) {
                nAux.recalcularAltura();
                balancear(nAux, padre);
            }
        }
        return exito;
    }

    private void balancear(NodoAVL nAux, NodoAVL padre) {
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
                    if (padre.getIzquierdo().getElem().equals(nAux.getElem())) {
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
                    if (padre.getIzquierdo().getElem().equals(nAux.getElem())) {
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

    private NodoAVL rotacionIzq(NodoAVL r) {
        NodoAVL h = r.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(r);
        r.setDerecho(temp);
        h.recalcularAltura();
        r.recalcularAltura();
        return h;
    }

    private NodoAVL rotacionDer(NodoAVL r) {
        NodoAVL h = r.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(r);
        r.setIzquierdo(temp);
        h.recalcularAltura();
        r.recalcularAltura();
        return h;
    }

    private int balance(NodoAVL nAux) {
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
