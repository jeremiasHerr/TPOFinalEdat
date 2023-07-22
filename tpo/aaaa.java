package tpo;

import estructuras.GrafoEtiquetado;

public class aaaa {
    public static void main(String[] args) {
        GrafoEtiquetado aux = new GrafoEtiquetado();
        aux.insertarVertice(new Ciudad("8300", "neuquen", "neuquen"));
        aux.insertarVertice(new Ciudad("8200", "plottier", "neuquen"));
        aux.insertarArco("8200", "8300", 10);
        System.out.println(aux.toString());
        aux.eliminarArco("8300", "8200");
        System.out.println(aux.toString());
    }
}
