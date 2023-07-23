package tpo;

import estructuras.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final String ANSI_WHITE = "\u001B[97m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_RESET = "\u001B[0m";
    private static final Scanner sc = new Scanner(System.in);
    private static GrafoDirigidoMod pedidos = new GrafoDirigidoMod();
    private static Diccionario ciudades = new Diccionario();
    private static GrafoEtiquetado rutas = new GrafoEtiquetado();
    private static HashMap<String, Cliente> clientes = new HashMap<>();

    public static void main(String[] args) {
        try {
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(clientes.toString());
    }

    public static void menu() throws FileNotFoundException, IOException {
        int respuesta;
        clearLog();
        do {
            System.out.println(ANSI_BLUE+"---------------------------------------MENU---------------------------------------"+ANSI_RESET);
            System.out.println(ANSI_YELLOW+"<> 1. Carga inicial del sistema.\n<> 2. ABM de ciudades.\n" + //
                    "<> 3. ABM de rutas.\n<> 4. ABM de clientes.\n<> 0. Cerrar el programa."+ANSI_RESET);
            respuesta = sc.nextInt();
            switch (respuesta) {
                case 0:
                    System.out.println(ANSI_BLUE+"---------------------------------------<()>---------------------------------------"+ANSI_RESET);
                break;
                case 1:
                    clearLog();
                    cargaInicial();
                break;
                case 2:
                    clearLog();
                    ABMCiudades();
                break;
                case 3:
                    clearLog();
                    ABMRutas();
                break;
                case 4:
                    clearLog();
                    ABMClientes();
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA"+ANSI_RESET);
                    break;
            }
        } while (respuesta!=0);
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
                        Comparable codPostal = tokens[1];
                        Ciudad actual = new Ciudad(tokens[1], tokens[2], tokens[3]);
                        ciudades.insertar(codPostal, actual); //se debe insertar la ciudad en el diccionario
                        rutas.insertarVertice(actual); //se debe insertar la ciudad en el grafo etiquetado "rutas" ya que cada vertice es una ciudad
                        pedidos.insertarVertice(actual);//se debe insertar la ciudad en el grafo de pedidos, ya que cada vertice es una ciudad
                    break;
                    //es una solicitud
                    case "S":
                        boolean estaPago = tokens[10] == "T";
                        pedidos.insertarArco(tokens[1], tokens[2], new SolicitudViaje(tokens[3],tokens[4],Integer.parseInt(tokens[5]),
                        Integer.parseInt(tokens[6]),Integer.parseInt(tokens[7]),tokens[8],tokens[9],estaPago,tokens[1],tokens[2]));
                    break;
                    //es una ruta
                    case "R":
                        rutas.insertarArco(tokens[1], tokens[2], Double.valueOf(tokens[3]));
                    break;
                    case "P":
                        //clientes.put(new Cliente(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]), tokens[1]+tokens[2]);
                        clientes.put(tokens[1]+tokens[2], new Cliente(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]));
                    break;
                    default: break;
                }
            }
            System.out.println(ANSI_GREEN+"CARGA INICIAL REALIZADA CON EXITO."+ANSI_RESET);
        }
    }

    public static void ABMClientes(){
        int respuesta;
        do{
            menuABMClientes();
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    agregarCliente();
                break;
                case 2:
                    eliminarCliente();
                break;
                case 3:
                    editarCliente();
                break;
                case 4:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        }while(respuesta!=4);
    }

    public static void eliminarCliente(){
        System.out.println(ANSI_WHITE+"Ingrese los datos del cliente a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del cliente a eliminar: "+ANSI_RESET);
        String tipoDni = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el DNI del cliente a eliminar:"+ANSI_RESET);
        String dni = sc.next();
        if(clientes.containsKey(tipoDni+dni)){
            clientes.remove(tipoDni+dni);
            System.out.println(ANSI_GREEN+"EL CLIENTE FUE ELIMINADO CON EXITO"+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"EL CLIENTE NO ESTA INGRESADO, POR ENDE NO FUE ELIMINADO"+ANSI_RED);
        }
    }

    public static void editarCliente(){
        System.out.println(ANSI_WHITE+"Ingrese los datos del cliente a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del cliente a editar: "+ANSI_RESET);
        String tipoDni = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el DNI del cliente a editar:"+ANSI_RESET);
        String dni = sc.next();
        if(clientes.containsKey(tipoDni+dni)){
            System.out.println(ANSI_YELLOW+"<> 1. Editar nombre. \n<> 2. Editar apellido. \n<> 3. Editar numero de telefono.\n<> 4. Editar e-mail.\n<> 5. Volver al ABMClientes"+ANSI_RESET);
            int respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    System.out.println(ANSI_WHITE+"Ingrese el nuevo nombre del cliente (en un solo mensaje): "+ANSI_RESET);
                    String nombre = sc.nextLine();
                    nombre = sc.nextLine();
                    clientes.get(tipoDni+dni).setNombre(nombre);
                    System.out.println(ANSI_GREEN+"EL NOMBRE FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                break;
                case 2:
                    System.out.println(ANSI_WHITE+"Ingrese el nuevo apellido del cliente (en un solo mensaje): "+ANSI_RESET);
                    String apellido = sc.nextLine();
                    apellido = sc.nextLine();
                    clientes.get(tipoDni+dni).setApellido(apellido);
                    System.out.println(ANSI_GREEN+"EL APELLIDO FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                break;
                case 3:
                    System.out.println(ANSI_WHITE+"Ingrese el nuevo numero de telefono del cliente: "+ANSI_RESET);
                    String numero = sc.nextLine();
                    numero = sc.nextLine();
                    clientes.get(tipoDni+dni).setTelefono(numero);
                    System.out.println(ANSI_GREEN+"EL NUMERO DE TELEFONO FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                break;
                case 4:
                    System.out.println(ANSI_WHITE+"Ingrese el nuevo e-mail del cliente: "+ANSI_RESET);
                    String email = sc.nextLine();
                    email = sc.nextLine();
                    clientes.get(tipoDni+dni).setEmail(email);
                    System.out.println(ANSI_GREEN+"EL EMAIL FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                break;
                case 5:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        }
    }


    public static void agregarCliente(){
        //P;DNI;35678965;FERNANDEZ;JUAN CARLOS;299-4495117;juan.carlos@gmail.com
        System.out.println(ANSI_WHITE+"Ingrese los datos del nuevo cliente a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del nuevo cliente: "+ANSI_RESET);
        String tipoDni = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el DNI del nuevo cliente"+ANSI_RESET);
        String dni = sc.next();
        if(!clientes.containsKey(tipoDni+dni)){
            System.out.println(ANSI_WHITE+"Ingrese el/los apellido/s del nuevo cliente (en un solo mensaje): "+ANSI_RESET);
            String apellidos = sc.nextLine();
            apellidos = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese el/los nombre/s del nuevo cliente (en un solo mensaje): "+ANSI_RESET);
            String nombres = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese el numero de telefono del nuevo cliente: "+ANSI_RESET);
            String telefono = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese el email del nuevo cliente: "+ANSI_RESET);
            String email = sc.nextLine();
            clientes.put(tipoDni+dni, new Cliente(tipoDni, dni, apellidos, nombres, telefono, email));
            System.out.println(ANSI_GREEN+"CLIENTE AGREGADO CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"EL CLIENTE YA ESTABA INGRESADO, POR ENDE NO FUE AGREGADO."+ANSI_RESET);
        }
    }

    public static void  menuABMClientes(){
        System.out.println(ANSI_BLUE+"------------------------------------ABMClientes-----------------------------------"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"<> 1. Agregar un cliente. \n<> 2. Eliminar un cliente. \n<> 3. Editar un cliente.\n<> 4. Volver al menu."+ANSI_RESET);
    }

    public static void ABMRutas(){
        int respuesta;
        do{
            menuABMRutas();
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    agregarRuta();
                break;
                case 2:
                    eliminarRuta();
                break;
                case 3:
                    editarRuta();
                break;
                case 4:
                    clearLog();
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        }while(respuesta!=4);
    }

    public static void editarRuta(){
        System.out.println(ANSI_WHITE+"Ingrese los datos de la ruta a editar a cotinuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la ruta a editar: "+ANSI_RESET);
        String codPostal1 = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la ruta a editar: "+ANSI_RESET);
        String codPostal2 = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese la nueva distancia en kilometros entre las 2 ciudades: "+ANSI_RESET);
        double kilometros = sc.nextDouble();
        if(rutas.eliminarArco(codPostal1, codPostal2)){
            rutas.insertarArco(codPostal1, codPostal2, kilometros);
            System.out.println(ANSI_GREEN+"RUTA EDITADA CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"LA RUTA NO PUDO SER EDITADA."+ANSI_RESET);
        }
        
    }

    public static void eliminarRuta(){
        System.out.println(ANSI_WHITE+"Ingrese los datos de la ruta a eliminar a cotinuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la ruta a eliminar: "+ANSI_RESET);
        String codPostal1 = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la ruta a eliminar: "+ANSI_RESET);
        String codPostal2 = sc.next();
        if(rutas.eliminarArco(codPostal1, codPostal2)){
            System.out.println(ANSI_GREEN+"RUTA ELIMINADA CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"LA RUTA NO PUDO SER ELIMINADA."+ANSI_RESET);
        }
    }

    public static void agregarRuta(){
        System.out.println(ANSI_WHITE+"Ingrese los datos de la nueva ruta a cotinuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la nueva ruta: "+ANSI_RESET);
        String codPostal1 = sc.nextLine();
        codPostal1 = sc.nextLine();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la nueva ruta: "+ANSI_RESET);
        String codPostal2 = sc.nextLine();
        System.out.println(ANSI_WHITE+"Ingrese la distancia en kilometros entre las 2 ciudades: "+ANSI_RESET);
        double kilometros = sc.nextDouble();
        if(rutas.insertarArco(codPostal1, codPostal2, kilometros)){
            System.out.println(ANSI_GREEN+"RUTA INSERTADA CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"LA RUTA NO PUDO SER INSERTADA."+ANSI_RESET);
        }
    }

    public static void menuABMRutas(){
        System.out.println(ANSI_BLUE+"------------------------------------ABMRutas-----------------------------------"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"<> 1. Agregar una ruta. \n<> 2. Eliminar una ruta. \n<> 3. Editar los kilometros de una ruta.\n<> 4. Volver al menu."+ANSI_RESET);
    }

    public static void ABMCiudades(){
        int respuesta;
        do{
            menuABMCiudades();
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    agregarCiudad();
                break;
                case 2:
                    eliminarCiudad();
                break;
                case 3:
              
                    editarCiudad();
                break;
                case 4:
                    clearLog();
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        }while(respuesta!=4);
    }

    private static void clearLog() {
        for (int i = 0; i < 15; i++) {
            System.out.println("");
        }
    }

    public static void editarCiudad(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad que quiere editar: "+ANSI_RESET);
        String codigoPostal = sc.nextLine();
        codigoPostal = sc.nextLine();
        Ciudad aux = (Ciudad)ciudades.obtenerDato(codigoPostal);
        if(aux!=null){
            System.out.println(ANSI_YELLOW+"    <> 1. Editar nombre.\n    <> 2. Editar provincia."+ANSI_RESET);
            int respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    System.out.println(ANSI_WHITE+"Ingrese el nuevo nombre:"+ANSI_RESET);
                    String nuevoNomb = sc.nextLine();
                    nuevoNomb = sc.nextLine();
                    aux.setNombre(nuevoNomb);
                    System.out.println(ANSI_GREEN+"NOMBRE DE LA CIUDAD EDITADO CON EXITO."+ANSI_RESET);
                break;
                case 2:
                    System.out.println(ANSI_WHITE+"Ingrese la nueva provincia:"+ANSI_RESET);
                    String nuevaProv = sc.nextLine();
                    nuevaProv = sc.nextLine();
                    aux.setProvincia(nuevaProv);
                    System.out.println(ANSI_RED+"PROVINCIA DE LA CIUDAD EDITADO CON EXITO."+ANSI_RESET);
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA, volviendo al ABM."+ANSI_RESET);
                break;
            }   
        } else {
            System.out.println(ANSI_RED+"CIUDAD NO ENCONTRADA, volviendo al ABM."+ANSI_RESET);
        }
    }

    public static void eliminarCiudad(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad que quiere eliminar: "+ANSI_RESET);
        String codigoPostal = sc.nextLine();
        codigoPostal = sc.nextLine();
        if(ciudades.eliminar(codigoPostal) && pedidos.eliminarVertice(codigoPostal) && rutas.eliminarVertice(codigoPostal)){
            System.out.println(ANSI_GREEN+"LA CIUDAD FUE ELIMINADA CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"LA CIUDAD INDICADA NO EXISTE."+ANSI_RESET);
        }
    }

    public static void agregarCiudad(){
        String codigoPostal, nombre, provincia;
        System.out.println(ANSI_WHITE+"Ingrese los datos de la nueva ciudad a continuacion:"+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Nombre de la ciudad:"+ANSI_RESET);
        nombre = sc.nextLine();
        nombre = sc.nextLine();
        System.out.println(ANSI_WHITE+"Provincia de la ciudad:"+ANSI_RESET);
        provincia = sc.nextLine();
        System.out.println(ANSI_WHITE+"Codigo postal de la ciudad:"+ANSI_RESET);
        codigoPostal = sc.nextLine();
        Ciudad unaCiudad = new Ciudad(codigoPostal, nombre, provincia);
        boolean exito = ciudades.insertar(codigoPostal, unaCiudad) && pedidos.insertarVertice(unaCiudad) && rutas.insertarVertice(unaCiudad);
        if(exito){
            System.out.println(ANSI_GREEN+"LA CIUDAD FUE AGREGADA CON EXITO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"LA CIUDAD YA ESTABA INGRESADA, POR ENDE NO PUDO SER AGREGADA."+ANSI_RESET);
        }
    }

    public static void menuABMCiudades(){
        System.out.println(ANSI_BLUE+"------------------------------------ABMCiudades-----------------------------------"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"<> 1. Agregar una ciudad. \n<> 2. Eliminar una ciudad. \n<> 3. Editar una ciudad.\n<> 4. Volver al menu."+ANSI_RESET);
    }

}
