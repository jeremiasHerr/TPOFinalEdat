package tpo;

import estructuras.Diccionario;
import estructuras.GrafoEtiquetado;

public class aaaa {
    public static void main(String[] args) {
        Diccionario ayudameloko = new Diccionario();
        ayudameloko.insertar(1000, "aaaaaa");
        ayudameloko.insertar(2000, "aaaaaa");
        ayudameloko.insertar(1500, "aaaaaa");
        ayudameloko.insertar(100, "aaaaaa");
        ayudameloko.insertar(900, "aaaaaa");
        ayudameloko.insertar(800, "aaaaaa");
        int mi = 500, ma = 3000;
        System.out.println(ayudameloko.listarRango(mi, ma).toString());
        
    }
}
