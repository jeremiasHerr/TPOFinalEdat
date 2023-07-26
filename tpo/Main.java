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
        //S;9000;6000;23/06/2023;DNI;23659874;21;9;Av. de Mayo 1000;Av. San Martin 1400;T
        System.out.println(pedidos.eliminarArco("9000", "6000", "DNI23659874"));
        System.out.println(pedidos.toString());
    }

    public static void menu() throws FileNotFoundException, IOException {
        int respuesta;
        clearLog();
        do {
            System.out.println(ANSI_BLUE+"---------------------------------------MENU---------------------------------------"+ANSI_RESET);
            System.out.println(ANSI_YELLOW+"<> 1. Carga inicial del sistema.\n<> 2. ABM de ciudades.\n" + //
                    "<> 3. ABM de rutas.\n<> 4. ABM de clientes.\n<> 5. ABM de pedidos.\n<> 6. Consultar la informacion de un cliente.\n<> 0. Cerrar el programa."+ANSI_RESET);
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
                case 5:
                    clearLog();
                    ABMPedidos();
                break;
                case 6:
                    consultarCliente();
                    clearLog();
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

    public static void consultarCliente(){
        int respuesta;
        do{
            System.out.println(ANSI_BLUE+"----------------------------------CONSULTAR CLIENTE---------------------------------"+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del cliente a consultar"+ANSI_RESET);
            String tipoDni = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese el DNI del cliente a consultar"+ANSI_RESET);
            String dni = sc.next();
            System.out.println();
            if(clientes.containsKey(tipoDni+dni)){
                System.out.println(ANSI_GREEN+"INFORMACION DE "+ANSI_RESET+ANSI_WHITE+clientes.get(tipoDni+dni).getNombre()+ANSI_RESET+ANSI_GREEN+": "+ANSI_RESET);
                System.out.println(clientes.get(tipoDni+dni).toString());
            } else {
                System.out.println(ANSI_RED+"EL CLIENTE AUN NO ESTA INGRESADO"+ANSI_RESET);
            }
            System.out.println(ANSI_YELLOW+"<> 1. Consultar la informacion de otro cliente.\n<> 2. Volver al menu."+ANSI_RESET);
            respuesta = sc.nextInt();
        }while(respuesta!=2);
    }

    public static void ABMPedidos(){
        int respuesta;
        do{
            menuABMPedidos();
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    agregarPedido();
                break;
                case 2:
                    eliminarPedido();
                break;
                case 3:
                    editarPedido();
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        } while(respuesta!=3);
    }

    public static void editarPedido(){
        System.out.println(ANSI_WHITE+"Ingrese los datos necesarios para identificar el pedido a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo de documento del cliente: "+ANSI_RESET);
        String tipoDni = sc.next().toUpperCase();
        System.out.println(ANSI_WHITE+"Ingrese el numero de documento del ciente: "+ANSI_RESET);
        String dni = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese la ciudad de origen del pedido (codigo postal): "+ANSI_RESET);
        String ciudadOrigen = sc.next();        
        System.out.println(ANSI_WHITE+"Ingrese la ciudad de destino del pedido (codigo postal): "+ANSI_RESET);
        String ciudadDestino = sc.next();
        SolicitudViaje laSolicitud = pedidos.obtenerEtiqueta(ciudadOrigen, ciudadDestino, tipoDni+dni);
        boolean salir=false;
        if(laSolicitud!=null){
            do{
            menuEditarPedido();
            int respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    System.out.println(ANSI_WHITE+"Ingrese la nueva cantidad de metros cubicos: "+ANSI_RESET);
                    int cantMetrosCubicos = sc.nextInt();
                    laSolicitud.setCantMetrosCubicos(cantMetrosCubicos);
                    System.out.println(ANSI_GREEN+"CANTIDAD DE METROS CUBICOS ACTUALIZADOS CON EXITO."+ANSI_RESET);
                    salir = true;
                break;
                case 2:
                    System.out.println(ANSI_WHITE+"Ingrese la nueva cantidad de bultos: "+ANSI_RESET);
                    int cantBultos = sc.nextInt();
                    laSolicitud.setCantBultos(cantBultos);
                    System.out.println(ANSI_GREEN+"CANTIDAD DE BULTOS ACTUALIZADOS CON EXITO."+ANSI_RESET);
                    salir = true;                
                break;
                case 3:
                    System.out.println(ANSI_WHITE+"Ingrese la nueva direccion de retiro: "+ANSI_RESET);
                    String nuevoRetiro = sc.nextLine();
                    nuevoRetiro = sc.nextLine();
                    laSolicitud.setDireccionRetiro(nuevoRetiro);
                    System.out.println(ANSI_GREEN+"NUEVA DIRECCION DE RETIRO ACTUALIZADA CON EXITO."+ANSI_RESET);
                    salir = true;
                break;
                case 4:
                    System.out.println(ANSI_WHITE+"Ingrese la nueva direccion de entrega: "+ANSI_RESET);
                    String nuevaEntrega = sc.nextLine();
                    nuevaEntrega = sc.nextLine();
                    laSolicitud.setDomicilioEntrega(nuevaEntrega);
                    System.out.println(ANSI_GREEN+"NUEVA DIRECCION DE ENTREGA ACTUALIZADA CON EXITO."+ANSI_RESET);
                    salir = true;
                break;
                case 5:
                    if(laSolicitud.estaPago()){
                        System.out.println(ANSI_GREEN+"LA SOLICITUD FIGURABA COMO PAGADA, AHORA FIGURA COMO PENDIENTE POR PAGAR."+ANSI_RESET);
                        laSolicitud.setEstaPago(false);
                    } else {
                        System.out.println(ANSI_GREEN+"LA SOLICITUD FIGURABA COMO PENDIENTE POR PAGAR, AHORA FIGURA COMO PAGADA."+ANSI_RESET);
                        laSolicitud.setEstaPago(true);
                    }
                    salir = true;
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        } while(!salir);
        } else {
            System.out.println(ANSI_RED+"NO SE PUDO ENCONTRAR LA SOLICITUD"+ANSI_RESET);
        }
    }

    public static void menuEditarPedido(){
        System.out.println(ANSI_BLUE+"<> 1. Editar la cantidad de metros cubicos.\n<> 2. Editar la cantidad de bultos.\n<> 3. Editar la direccion de retiro"
        +"\n<> 4. Editar la direccion de entrega.\n<> 5. Editar si esta pago."+ANSI_RESET);
    }

    public static void eliminarPedido(){
        System.out.println(ANSI_WHITE+"Ingrese los datos necesarios a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo de documento del cliente: "+ANSI_RESET);
        String tipoDni = sc.next().toUpperCase();
        System.out.println(ANSI_WHITE+"Ingrese el numero de documento del ciente: "+ANSI_RESET);
        String dni = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese la ciudad de origen del pedido (codigo postal): "+ANSI_RESET);
        String ciudadOrigen = sc.next();        
        System.out.println(ANSI_WHITE+"Ingrese la ciudad de destino del pedido (codigo postal): "+ANSI_RESET);
        String ciudadDestino = sc.next();
        if(pedidos.eliminarArco(ciudadOrigen, ciudadDestino, tipoDni+dni)){
            System.out.println(ANSI_GREEN+"PEDIDO ELIMINADO CON EXITO"+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"EL PEDIDO NO PUDO SER ELIMINADO"+ANSI_RESET);
        }

    }

    public static void agregarPedido(){
        //S;5000;8000;15/09/2023;DNI;35678965;13;5;Sarmiento 3400;Roca 2100;T
        System.out.println(ANSI_WHITE+"Ingrese los datos del nuevo pedido a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese la ciudad origen del pedido (codigo postal): "+ANSI_RESET);
        String ciudadOrigen = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese la ciudad destino del pedido (codigo postal): "+ANSI_RESET);
        String ciudadDestino = sc.next();
        if(rutas.existeCamino(ciudadOrigen, ciudadDestino)){
            System.out.println(ANSI_WHITE+"Ingrese la fecha del pedido: "+ANSI_RESET);
            String fecha = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del cliente: "+ANSI_RESET);
            String tipoDni = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese el DNI del cliente"+ANSI_RESET);
            int dni = sc.nextInt();
            System.out.println(ANSI_WHITE+"Ingrese la cantidad de metros cubicos a ocupar en el camion: "+ANSI_RESET);
            int metrosCubicos = sc.nextInt();
            System.out.println(ANSI_WHITE+"Ingrese la cantidad de bultos del pedido: "+ANSI_RESET);
            int cantBultos = sc.nextInt();
            System.out.println(ANSI_WHITE+"Ingrese la direccion del domicilio de retiro del pedido: "+ANSI_RESET);
            String direccionRetiro = sc.nextLine();
            direccionRetiro = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese la direccion del domicilio de entrega del pedido: "+ANSI_RESET);
            String direccionEntrega = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese si el pedido esta pago o no (SI para true NO para false): "+ANSI_RESET);
            String respuestaPago = sc.next();
            boolean estaPago = "SI".equals(respuestaPago.toUpperCase());
            pedidos.insertarArco(ciudadOrigen, ciudadDestino, new SolicitudViaje(fecha, tipoDni, dni,metrosCubicos,cantBultos,direccionRetiro,direccionEntrega,estaPago,ciudadDestino,ciudadOrigen));
            System.out.println(ANSI_GREEN+"EL PEDIDO FUE AGREGADO CON EXITO"+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"NO EXISTE UNA RUTA ENTRE LAS CIUDADES, NO SE PUEDE AGREGAR EL PEDIDO."+ANSI_RED);
        }
    }

    public static void menuABMPedidos(){
        System.out.println(ANSI_BLUE+"------------------------------------ABMPedidos-----------------------------------"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"<> 1. Agregar un pedido. \n<> 2. Eliminar los pedidos de un cliente.\n<> 3. Editar un pedido \n<> 4. Volver al menu."+ANSI_RESET);
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
        String tipoDni = sc.next().toUpperCase();
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
        System.out.println(ANSI_WHITE+"Ingrese los datos del nuevo cliente a continuacion: "+ANSI_RESET);
        System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del nuevo cliente: "+ANSI_RESET);
        String tipoDni = sc.next().toUpperCase();
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
            System.out.println(ANSI_YELLOW+"<> 1. Editar nombre.\n<> 2. Editar provincia."+ANSI_RESET);
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
