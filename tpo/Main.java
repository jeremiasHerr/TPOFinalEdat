package tpo;

import estructuras.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class Main {
    private static GrafoMod pedidos = new GrafoMod();
    private static Diccionario ciudades = new Diccionario();
    private static GrafoEtiquetado rutas = new GrafoEtiquetado();

    public static void main(String[] args) {
        /*
         * hay que hacer variable pedidos de grafo
         * ciudades de diccionario
         * mapa de rutas de grafo etiquetado
         * clientes de hashmap creo
         * el toString de pedidos no funciona porque no hay toString en solicitudviaje igual no tiene sentido hacerlo, hay q hacer otra cosa q pide la consigna
         */
        try {
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(pedidos.toString());
        System.out.println(rutas.toString());
    }

    public static void menu() throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        int respuesta;
        boolean salir = false;
        do {
            System.out.println("0. Cerrar el programa.\n1. Carga inicial del sistema.\n2.");
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 0:
                    System.out.println("FIN");
                    salir = true;
                    break;
                case 1:
                    cargaInicial();
                    break;
                default:
                    System.out.println("RESPUESTA INVALIDA");
                    break;
            }
        } while (!salir);
        sc.close();
    }

    public static void cargaInicial() throws FileNotFoundException, IOException {
        try (Reader r = new FileReader("tpo\\cargaInicial.txt")) {
            BufferedReader br = new BufferedReader(r);
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(";");
                switch (tokens[0]) {
                    // significa que es una ciudad
                    case "C":
                        ciudades.insertar(tokens[1], new Ciudad(tokens[1], tokens[2], tokens[3])); //se debe insertar la ciudad en el diccionario
                        rutas.insertarVertice(new Ciudad(tokens[1], tokens[2], tokens[3])); //se debe insertar la ciudad en el grafo etiquetado "rutas" ya que cada vertice es una ciudad
                        pedidos.insertarVertice(new Ciudad(tokens[1], tokens[2], tokens[3]));//se debe insertar la ciudad en el grafo de pedidos, ya que cada vertice es una ciudad
                    break;
                    case "S":
                        boolean estaPago = tokens[10] == "T";
                        pedidos.insertarArco(tokens[1], tokens[2], new SolicitudViaje(tokens[3],tokens[4],Integer.parseInt(tokens[5]),
                        Integer.parseInt(tokens[6]),Integer.parseInt(tokens[7]),tokens[8],tokens[9],estaPago));
                    break;
                    case "R":
                        boolean a = rutas.insertarArco(tokens[1], tokens[2], Double.valueOf(tokens[3]));
                        //System.out.println(a);
                    break;
                    default: break;
                }
            }
        }
    }

}
