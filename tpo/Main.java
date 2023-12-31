package tpo;

import estructuras.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
                
    static final String ANSI_WHITE = "\u001B[97m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_RESET = "\u001B[0m";
    private static final Scanner sc = new Scanner(System.in);
    private static MapeoMuchos pedidos = new MapeoMuchos();
    private static Diccionario ciudades = new Diccionario();
    private static GrafoEtiquetado rutas = new GrafoEtiquetado();
    private static HashMap<String, Cliente> clientes = new HashMap<>();

    public static void main(String[] args) {
        try {
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws FileNotFoundException, IOException {
        int respuesta;
        clearLog();
        do {
            System.out.println(ANSI_BLUE+"---------------------------------------MENU---------------------------------------"+ANSI_RESET);
            System.out.println(ANSI_YELLOW+"<> 1. Carga inicial del sistema.\n<> 2. ABM de ciudades.\n" + //
                    "<> 3. ABM de rutas.\n<> 4. ABM de clientes.\n<> 5. ABM de pedidos.\n<> 6. Consultar la informacion de un cliente."+
                    "\n<> 7. Consultar sobre ciudades.\n<> 8. Consultar sobre viajes.\n<> 9. Verificar viajes.\n<> 10. Mostrar sistema\n<> 0. Cerrar el programa."+ANSI_RESET);
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
                    clearLog();
                    consultarCliente();
                break;
                case 7:
                    clearLog();
                    consultaCiudades();
                break;
                case 8:
                    clearLog();
                    consultasViajes();
                break;
                case 9:
                    clearLog();
                    verificarViaje();
                break;
                case 10:
                    clearLog();
                    mostrarSistema();
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
            FileWriter fileWriter = new FileWriter("tpo\\estadoCargaInicial.txt");
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
                BufferedReader br = new BufferedReader(r);
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] tokens = linea.split(";");
                    switch (tokens[0]) {
                        // significa que es una ciudad
                        case "C":
                            int codPostal = Integer.valueOf(tokens[1]);
                            Ciudad actual = new Ciudad(tokens[1], tokens[2], tokens[3]);
                            ciudades.insertar((Comparable)codPostal, actual); //se debe insertar la ciudad en el diccionario
                            rutas.insertarVertice(tokens[1]); //se debe insertar la ciudad en el grafo etiquetado "rutas" ya que cada vertice es una ciudad
                            bufferedWriter.write("SE CREO LA CIUDAD "+actual.getNombre()+" Y SE INSERTO EN LAS ESTRUCTURAS NECESARIAS.\n");
                        break;
                        //es una solicitud
                        case "S":
                        //public SolicitudViaje(String fechaSolicitud, String tipoDocumento, int numeroDocumento, int cantMetrosCubicos, 
                        //int cantBultos, String direccionRetiro, String domicilioEntrega, boolean estaPago, String destino,String origen)
                            boolean estaPago = tokens[10] == "T";
                            int cosPostalOrigen = Integer.valueOf(tokens[1]);
                            int cosPostalDestino = Integer.valueOf(tokens[2]);
                            SolicitudViaje aux = new SolicitudViaje(tokens[3],tokens[4],Integer.parseInt(tokens[5]),
                            Integer.parseInt(tokens[6]),Integer.parseInt(tokens[7]),tokens[8],tokens[9],estaPago,tokens[2],tokens[1]);
                            pedidos.asociar(cosPostalOrigen+""+cosPostalDestino, aux);
                            bufferedWriter.write("SE CREO LA SOLICITUD DE "+aux.getTipoDocumento()+": "+aux.getNumeroDocumento()+".\n");
                        break;
                        //es una ruta
                        case "R":
                            rutas.insertarArco(tokens[1],tokens[2],Double.valueOf(tokens[3]));
                            bufferedWriter.write("SE CREO LA RUTA QUE VA DESDE "+tokens[1]+" A "+tokens[2]+".\n");
                        break;
                        case "P":
                            clientes.put(tokens[1]+tokens[2], new Cliente(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]));
                            bufferedWriter.write("SE CREO EL CLIENTE "+tokens[4]+" "+tokens[3]+".\n");
                        break;
                        default: break;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            System.out.println(ANSI_GREEN+"CARGA INICIAL REALIZADA CON EXITO."+ANSI_RESET);
        }
    }

    public static void mostrarSistema(){
        int respuesta;
        do{
            System.out.println(ANSI_YELLOW+"<> 1. Mostrar las ciudades.\n<> 2. Mostrar las rutas.\n<> 3. Mostrar los pedidos"+
            "\n<> 4. Mostrar los clientes.\n<> 5. Volver al menu."+ANSI_RESET);
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    System.out.println(ANSI_GREEN+"------------SISTEMA DE CIUDADES------------"+ANSI_RESET);
                    System.out.println(ciudades.toString());
                break;
                case 2:
                    System.out.println(ANSI_GREEN+"------------SISTEMA DE RUTAS------------"+ANSI_RESET);
                    System.out.println(rutas.toString());
                break;
                case 3:
                    System.out.println(ANSI_GREEN+"------------SISTEMA DE PEDIDOS------------"+ANSI_RESET);
                    System.out.println(pedidos.toString());
                break;
                case 4:
                    System.out.println(ANSI_GREEN+"------------SISTEMA DE CLIENTES------------"+ANSI_RESET);
                    System.out.println(clientes.toString());
                break;
                case 5:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        } while(respuesta!=5);
            
    }
    
    public static void verificarViaje(){
        int respuesta;
        do{
            System.out.println(ANSI_BLUE+"-------------------VERIFICAR VIAJES-------------------"+ANSI_RESET);
            System.out.println(ANSI_YELLOW+"<> 1. Dada una ciudad A y una ciudad B mostrar todos los pedidos y calcular cuanto espacio total hace falta en el camion"+
            "\n<> 2. Verificar un camino perfecto usando una lista.\n<> 3. Volver al menu."+ANSI_RESET);
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1: 
                    pedidosYCalcularEspacio();
                break;
                case 2:
                    verificarCaminoPerfecto();
                break;
                case 3:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        } while(respuesta!=3);
    }

    public static void verificarCaminoPerfecto(){
        Lista camino = new Lista();
        System.out.println(ANSI_WHITE+"Ingrese la capacidad del camion"+ANSI_RESET);
        int capacidad = sc.nextInt();
        System.out.println(ANSI_WHITE+"Ingrese la cantidad de ciudades que va a verificar si cumplen un camino perfecto"+ANSI_RESET);
        int cantCiudades = sc.nextInt();
        for (int i = 0; i < cantCiudades; i++) {
            System.out.println("Ingrese una ciudad ("+(i+1)+")");
            String codPostal = sc.next();
            camino.insertar(codPostal, camino.longitud()+1);
        }
        int i = 1, espacioNecesario = 0;
        boolean seguir = true;
        while(i<cantCiudades && seguir && espacioNecesario<=capacidad){
            String codOrigen = (String) camino.recuperar(i);
            int k = i+1;
            seguir=false;
            while(k<=cantCiudades && !seguir) {
                String codDestino = (String) camino.recuperar(k);
                if(rutas.existeCamino(codOrigen, codDestino) && pedidos.obtenerRangoEntre(codOrigen, codDestino)!=null){
                    espacioNecesario += ((SolicitudViaje)pedidos.obtenerRangoEntre(codOrigen, codDestino)).getCantMetrosCubicos();
                    System.out.println(codOrigen + " " +codDestino);
                    seguir = true;
                }
                k++;
            }
            i++;
        }
        if(seguir){
            System.out.println(ANSI_GREEN+"EXISTE UN CAMINO PERFECTO."+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"NO EXISTE CAMINO PERFECTO"+ANSI_RESET);
        }
    }


    public static void pedidosYCalcularEspacio(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la primera ciudad"+ANSI_RESET);
        int primeraCiudad = sc.nextInt();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la segunda ciudad"+ANSI_RESET);
        int segundaCiudad = sc.nextInt();
        Lista solicitudes = pedidos.listarValoresDe(primeraCiudad, segundaCiudad);
        Lista solicitudes2 = pedidos.listarValoresDe(segundaCiudad, primeraCiudad);
        if(solicitudes!=null || solicitudes2!=null){
            int espacioNecesario = 0;
            //se calcula el espacio necesario entre todas las solicitudes
            if(solicitudes!=null){
                for (int i = 1; i <= solicitudes.longitud(); i++) {
                    SolicitudViaje aux = (SolicitudViaje)solicitudes.recuperar(i);
                    espacioNecesario = aux.getCantMetrosCubicos() + espacioNecesario;
                }
            }
            if(solicitudes2!=null){
                for (int i = 1; i <= solicitudes2.longitud(); i++) {
                    SolicitudViaje aux = (SolicitudViaje)solicitudes2.recuperar(i);
                    espacioNecesario = aux.getCantMetrosCubicos() + espacioNecesario;
                }
            }
            System.out.println(ANSI_GREEN+"LAS SOLICITUDES ENTRE AMBAS CIUDADES SON: "+ANSI_RESET+"\n"+solicitudes.toString()+"\n"+solicitudes2.toString());
            System.out.println(ANSI_GREEN+"EL ESPACIO NECESARIO TOTAL EN METROS CUBICOS SERIA: "+ANSI_WHITE+espacioNecesario+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"NO EXISTEN PEDIDOS ENTRE AMBAS CIUDADES"+ANSI_RESET);
        }
    }

    public static void consultaCiudades(){
        int respuesta;
        do{
            System.out.println(ANSI_BLUE+"-------------------------CONSULTA DE CIUDADES--------------------------"+ANSI_RESET);
            System.out.println(ANSI_YELLOW+"<> 1. Mostrar toda la informacion de una ciudad.\n<> 2. Mostrar ciudades con un prefijo particular.\n<> 3. Volver al menu."+ANSI_RESET);
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    informacionDeUnaCiudad();
                break;
                case 2:
                    ciudadesConPrefijo();
                break;
                case 3:
                    clearLog();
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        }while(respuesta!=3);
    }

    public static void consultasViajes(){
        int respuesta;
        do{
            System.out.println(ANSI_BLUE+"----------------------CONSULTAR VIAJES----------------------"+ANSI_WHITE);
            System.out.println(ANSI_YELLOW+"<> 1. Obtener el camino que llegue de A a B que pase por menos ciudades."+
            "\n<> 2. Obtener el camino que llegue de A a B de menor distancia en kilometros.\n<> 3. Volver al menu."+ANSI_RESET);
            respuesta = sc.nextInt();
            switch(respuesta){
                case 1:
                    caminoMenosCiudades();
                break;
                case 2:
                    caminoMenosKilometros();
                break;
                case 3:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA"+ANSI_WHITE);
                break;
            }
        }while(respuesta!=3);
        
    }

    public static void caminoMenosKilometros(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen"+ANSI_RESET);
        String codOrigen = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino"+ANSI_RESET);
        String codDestino = sc.next();
        Lista camino = rutas.caminoMasCortoKm(codOrigen, codDestino);
        if(camino!=null){
            System.out.println(ANSI_GREEN+"EL CAMINO MAS CORTO ES: "+ANSI_RESET+"\n"+camino.toString());
        } else {
            System.out.println(ANSI_RED+"NO EXISTE UN CAMINO ENTRE AMBAS CIUDADES"+ANSI_WHITE);
        }
    }

    public static void caminoMenosCiudades(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen"+ANSI_RESET);
        String codOrigen = sc.next();
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino"+ANSI_RESET);
        String codDestino = sc.next();
        Lista camino = rutas.caminoMasCorto(codOrigen, codDestino);
        if(camino!=null){
            System.out.println(ANSI_GREEN+"EL CAMINO QUE PASA POR MENOS CIUDADES ES: "+ANSI_RESET+"\n"+camino.toString());
        } else {
            System.out.println(ANSI_RED+"NO EXISTE UN CAMINO ENTRE AMBAS CIUDADES"+ANSI_WHITE);
        }
    }

    public static void ciudadesConPrefijo(){
        System.out.println(ANSI_WHITE+"Ingrese el prefijo: "+ANSI_RESET);
        int prefijo = sc.nextInt();
        String prefijoAux = String.valueOf(prefijo);
        int cifras = (int)prefijoAux.length();
        int base = prefijo;
        int min=0,max=0;
        switch(cifras){
            case 1:
                min = base*1000;
                max = min+999;
            break;
            case 2:
                min = base*100;
                max = min+99;
            break;
            case 3:
                min = base*10;
                max = min+9;
            break;
            case 4:
                min = base;
                max = base;
            break;
            default:
                System.out.println(ANSI_RED+"EL PREFIJO DEBE SER MENOR O IGUAL A 4 CIFRAS."+ANSI_RESET);
            break;
        }
        Lista ciudadesResultado = ciudades.listarRango((Comparable)min, (Comparable)max);
        if(ciudadesResultado!=null){
            System.out.println(ANSI_BLUE+"CIUDADES CON PREFIJO "+ANSI_GREEN+prefijo+ANSI_RESET+ANSI_BLUE+":"+ANSI_RESET);
            System.out.println(ciudadesResultado.toString());
        } else {
            System.out.println(ANSI_RED+"NO SE ENCONTRARON CIUDADES CON TAL PREFIJO"+ANSI_RESET);
        }
    }

    public static void informacionDeUnaCiudad(){
        System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad a consultar su informacion: "+ANSI_RESET);
        String codPostal = sc.next();
        Ciudad laCiudad = (Ciudad)ciudades.obtenerDato(codPostal);
        if(laCiudad!=null){
            System.out.println(ANSI_BLUE+"----------------------INFORMACION DE "+ANSI_GREEN+laCiudad.getNombre()+ANSI_BLUE+"----------------------"+ANSI_RESET);
            System.out.println(ANSI_GREEN+laCiudad.toString()+ANSI_RESET);      
        } else {
            System.out.println(ANSI_RED+"LA CIUDAD NO FUE ENCONTRADA"+ANSI_RESET);
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
            if(respuesta!=1 && respuesta!=2){
                System.out.println(ANSI_RED+"RESPUESTA INVALIDA, VOLVIENDO AL MENU"+ANSI_WHITE);
            }
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
                case 4:
                break;
                default:
                    System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                break;
            }
        } while(respuesta!=4);
    }

    public static void editarPedido(){
        try (FileWriter fileWriter = new FileWriter("tpo\\operacionesABM.txt", true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos necesarios para identificar el pedido a continuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el tipo de documento del cliente: "+ANSI_RESET);
            String tipoDni = sc.next().toUpperCase();
            System.out.println(ANSI_WHITE+"Ingrese el numero de documento del ciente: "+ANSI_RESET);
            String dni = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese la ciudad de origen del pedido (codigo postal): "+ANSI_RESET);
            String ciudadOrigen = sc.next();        
            System.out.println(ANSI_WHITE+"Ingrese la ciudad de destino del pedido (codigo postal): "+ANSI_RESET);
            String ciudadDestino = sc.next();
            SolicitudViaje laSolicitud = (SolicitudViaje)pedidos.obtenerRangoDe(ciudadOrigen, ciudadDestino, tipoDni+dni);
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
                            bufferedWriter.write("SE EDITO LA CANTIDAD DE METROS CUBOS EN LA SOLICITUD DE VIAJE DEL CLIENTE: "+laSolicitud.getClave());
                            salir = true;
                        break;
                        case 2:
                            System.out.println(ANSI_WHITE+"Ingrese la nueva cantidad de bultos: "+ANSI_RESET);
                            int cantBultos = sc.nextInt();
                            laSolicitud.setCantBultos(cantBultos);
                            System.out.println(ANSI_GREEN+"CANTIDAD DE BULTOS ACTUALIZADOS CON EXITO."+ANSI_RESET);
                            bufferedWriter.write("SE EDITO LA CANTIDAD DE BULTOS EN LA SOLICITUD DE VIAJE DEL CLIENTE: "+laSolicitud.getClave());
                            salir = true;                
                        break;
                        case 3:
                            System.out.println(ANSI_WHITE+"Ingrese la nueva direccion de retiro: "+ANSI_RESET);
                            String nuevoRetiro = sc.nextLine();
                            nuevoRetiro = sc.nextLine();
                            laSolicitud.setDireccionRetiro(nuevoRetiro);
                            System.out.println(ANSI_GREEN+"NUEVA DIRECCION DE RETIRO ACTUALIZADA CON EXITO."+ANSI_RESET);
                            bufferedWriter.write("SE ACTUALIZO LA DIRECCION DE RETIRO EN LA SOLICITUD DEL CLIENTE: "+laSolicitud.getClave());
                            salir = true;
                        break;
                        case 4:
                            System.out.println(ANSI_WHITE+"Ingrese la nueva direccion de entrega: "+ANSI_RESET);
                            String nuevaEntrega = sc.nextLine();
                            nuevaEntrega = sc.nextLine();
                            laSolicitud.setDomicilioEntrega(nuevaEntrega);
                            System.out.println(ANSI_GREEN+"NUEVA DIRECCION DE ENTREGA ACTUALIZADA CON EXITO."+ANSI_RESET);
                            bufferedWriter.write("SE ACTUALIZO LA DIRECCION DE ENTREGA EN LA SOLICITUD DEL CLIENTE: "+laSolicitud.getClave());
                            salir = true;
                        break;
                        case 5:
                            if(laSolicitud.estaPago()){
                                System.out.println(ANSI_GREEN+"LA SOLICITUD FIGURABA COMO PAGADA, AHORA FIGURA COMO PENDIENTE POR PAGAR."+ANSI_RESET);
                                bufferedWriter.write("AHORA LA SOLICITUD DE: "+laSolicitud.getClave()+" FIGURA COMO PENDIENTE POR PAGAR."+"\n");
                                laSolicitud.setEstaPago(false);
                            } else {
                                System.out.println(ANSI_GREEN+"LA SOLICITUD FIGURABA COMO PENDIENTE POR PAGAR, AHORA FIGURA COMO PAGADA."+ANSI_RESET);
                                bufferedWriter.write("AHORA LA SOLICITUD DE: "+laSolicitud.getClave()+" FIGURA COMO PAGADA.");
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
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menuEditarPedido(){
        System.out.println(ANSI_YELLOW+"<> 1. Editar la cantidad de metros cubicos.\n<> 2. Editar la cantidad de bultos.\n<> 3. Editar la direccion de retiro"
        +"\n<> 4. Editar la direccion de entrega.\n<> 5. Editar si esta pago."+ANSI_RESET);
    }

    public static void eliminarPedido(){
        try {
            FileWriter fileWriter = new FileWriter("tpo\\operacionesABM.txt" , true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos necesarios a continuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el tipo de documento del cliente: "+ANSI_RESET);
            String tipoDni = sc.next().toUpperCase();
            System.out.println(ANSI_WHITE+"Ingrese el numero de documento del ciente: "+ANSI_RESET);
            String dni = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese la ciudad de origen del pedido (codigo postal): "+ANSI_RESET);
            String ciudadOrigen = sc.next();        
            System.out.println(ANSI_WHITE+"Ingrese la ciudad de destino del pedido (codigo postal): "+ANSI_RESET);
            String ciudadDestino = sc.next();
            if(pedidos.desasociar(ciudadOrigen+""+ciudadDestino, ciudadDestino, tipoDni+dni)){
                bufferedWriter.write("SE ELIMINO EL PEDIDO DEL CLIENTE "+tipoDni+dni+" QUE IBA DESDE "+ciudadOrigen+" A "+ciudadDestino);
                System.out.println(ANSI_GREEN+"PEDIDO ELIMINADO CON EXITO"+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"EL PEDIDO NO PUDO SER ELIMINADO"+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void agregarPedido(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos del nuevo pedido a continuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese la ciudad origen del pedido (codigo postal): "+ANSI_RESET);
            int ciudadOrigen = sc.nextInt();
            System.out.println(ANSI_WHITE+"Ingrese la ciudad destino del pedido (codigo postal): "+ANSI_RESET);
            String ciudadDestino = sc.next();
            String auxOrigen = String.valueOf(ciudadOrigen);
            if(rutas.existeCamino(auxOrigen, ciudadDestino)){
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
                SolicitudViaje aux = new SolicitudViaje(fecha, tipoDni, dni,metrosCubicos,cantBultos,direccionRetiro,direccionEntrega,estaPago,ciudadDestino,String.valueOf(ciudadOrigen));
                pedidos.asociar(ciudadOrigen+""+ciudadDestino, aux);
                bufferedWriter.write("SE AGREGO LA SOLICITUD: "+aux.toString());
                System.out.println(ANSI_GREEN+"EL PEDIDO FUE AGREGADO CON EXITO"+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"NO EXISTE UNA RUTA ENTRE LAS CIUDADES, NO SE PUEDE AGREGAR EL PEDIDO."+ANSI_RED);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            FileWriter fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos del cliente a continuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el tipo DNI del cliente a eliminar: "+ANSI_RESET);
            String tipoDni = sc.next().toUpperCase();
            System.out.println(ANSI_WHITE+"Ingrese el DNI del cliente a eliminar:"+ANSI_RESET);
            String dni = sc.next();
            if(clientes.containsKey(tipoDni+dni)){
                Cliente aux = clientes.get(tipoDni+dni);
                bufferedWriter.write("SE ELIMINO EL CLIENTE "+aux.getNombre()+" "+aux.getApellido());
                clientes.remove(tipoDni+dni);
                System.out.println(ANSI_GREEN+"EL CLIENTE FUE ELIMINADO CON EXITO"+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"EL CLIENTE NO ESTA INGRESADO, POR ENDE NO FUE ELIMINADO"+ANSI_RED);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editarCliente(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
                        bufferedWriter.write("SE ACTUALIZO EL NOMBRE DE "+tipoDni+": "+dni+" A "+nombre);
                        System.out.println(ANSI_GREEN+"EL NOMBRE FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                    break;
                    case 2:
                        System.out.println(ANSI_WHITE+"Ingrese el nuevo apellido del cliente (en un solo mensaje): "+ANSI_RESET);
                        String apellido = sc.nextLine();
                        apellido = sc.nextLine();
                        clientes.get(tipoDni+dni).setApellido(apellido);
                        bufferedWriter.write("SE ACTUALIZO EL APELLIDO DE "+tipoDni+": "+dni+" A "+apellido);
                        System.out.println(ANSI_GREEN+"EL APELLIDO FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                    break;
                    case 3:
                        System.out.println(ANSI_WHITE+"Ingrese el nuevo numero de telefono del cliente: "+ANSI_RESET);
                        String numero = sc.nextLine();
                        numero = sc.nextLine();
                        clientes.get(tipoDni+dni).setTelefono(numero);
                        bufferedWriter.write("SE ACTUALIZO EL NUMERO DE TELEFONO DE "+tipoDni+": "+dni+" A "+numero);
                        System.out.println(ANSI_GREEN+"EL NUMERO DE TELEFONO FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                    break;
                    case 4:
                        System.out.println(ANSI_WHITE+"Ingrese el nuevo e-mail del cliente: "+ANSI_RESET);
                        String email = sc.nextLine();
                        email = sc.nextLine();
                        clientes.get(tipoDni+dni).setEmail(email);
                        bufferedWriter.write("SE ACTUALIZO EL EMAIL DE "+tipoDni+": "+dni+" A "+email);
                        System.out.println(ANSI_GREEN+"EL EMAIL FUE ACTUALIZADO CON EXITO"+ANSI_RESET);
                    break;
                    case 5:
                    break;
                    default:
                        System.out.println(ANSI_RED+"RESPUESTA INVALIDA."+ANSI_RESET);
                    break;
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void agregarCliente(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
                Cliente aux = new Cliente(tipoDni, dni, apellidos, nombres, telefono, email);
                clientes.put(tipoDni+dni, aux);
                bufferedWriter.write("SE AGREGO EL CLIENTE: "+aux.toString());
                System.out.println(ANSI_GREEN+"CLIENTE AGREGADO CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"EL CLIENTE YA ESTABA INGRESADO, POR ENDE NO FUE AGREGADO."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos de la ruta a editar a cotinuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la ruta a editar: "+ANSI_RESET);
            String codPostal1 = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la ruta a editar: "+ANSI_RESET);
            String codPostal2 = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese la nueva distancia en kilometros entre las 2 ciudades: "+ANSI_RESET);
            double kilometros = sc.nextDouble();
            if(rutas.eliminarArco(codPostal1, codPostal2)){
                bufferedWriter.write("SE ACTUALIZARON LOS KILOMETROS A "+kilometros+" DE LA RUTA QUE VA DESDE "+codPostal1+" A "+codPostal2);
                rutas.insertarArco(codPostal1, codPostal2, kilometros);
                System.out.println(ANSI_GREEN+"RUTA EDITADA CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"LA RUTA NO PUDO SER EDITADA."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

    public static void eliminarRuta(){
        try {
            FileWriter fileWriter = new FileWriter("tpo\\operacionesABM.txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos de la ruta a eliminar a cotinuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la ruta a eliminar: "+ANSI_RESET);
            String codPostal1 = sc.next();
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la ruta a eliminar: "+ANSI_RESET);
            String codPostal2 = sc.next();
            if(rutas.eliminarArco(codPostal1, codPostal2)){
                bufferedWriter.write("SE ELIMINO LA RUTA QUE VA DESDE "+codPostal1+" A "+codPostal2);
                System.out.println(ANSI_GREEN+"RUTA ELIMINADA CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"LA RUTA NO PUDO SER ELIMINADA."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
        
    }

    public static void agregarRuta(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt" , true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese los datos de la nueva ruta a cotinuacion: "+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad origen de la nueva ruta: "+ANSI_RESET);
            String codPostal1 = sc.nextLine();
            codPostal1 = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad destino de la nueva ruta: "+ANSI_RESET);
            String codPostal2 = sc.nextLine();
            System.out.println(ANSI_WHITE+"Ingrese la distancia en kilometros entre las 2 ciudades: "+ANSI_RESET);
            double kilometros = sc.nextDouble();
            if(rutas.insertarArco(codPostal1, codPostal2, kilometros)){
                bufferedWriter.write("SE AGREGO UNA RUTA QUE VA DESDE "+codPostal1+" A "+codPostal2+" CON UNA DISTANCIA DE "+kilometros+" KILOMETROS\n");
                System.out.println(ANSI_GREEN+"RUTA INSERTADA CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"LA RUTA NO PUDO SER INSERTADA."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
                        bufferedWriter.write("SE CAMBIO EL NOMBRE DE LA CIUDAD "+aux.getNombre()+" A "+nuevoNomb);
                        aux.setNombre(nuevoNomb);
                        System.out.println(ANSI_GREEN+"NOMBRE DE LA CIUDAD EDITADO CON EXITO."+ANSI_RESET);
                    break;
                    case 2:
                        System.out.println(ANSI_WHITE+"Ingrese la nueva provincia:"+ANSI_RESET);
                        String nuevaProv = sc.nextLine();
                        nuevaProv = sc.nextLine();
                        bufferedWriter.write("SE CAMBIO LA PROVINCIA DE LA CIUDAD "+aux.getNombre()+" A "+nuevaProv);
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
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void eliminarCiudad(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(ANSI_WHITE+"Ingrese el codigo postal de la ciudad que quiere eliminar: "+ANSI_RESET);
            String codigoPostal = sc.nextLine();
            codigoPostal = sc.nextLine();
            int aux = Integer.valueOf(codigoPostal);
            String nombreCiudad = ((Ciudad)ciudades.obtenerDato(aux)).getNombre();
            if(ciudades.eliminar(aux) && rutas.eliminarVertice(aux)){
                bufferedWriter.write("SE ELIMINO LA CIUDAD "+nombreCiudad);
                System.out.println(ANSI_GREEN+"LA CIUDAD FUE ELIMINADA CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"LA CIUDAD INDICADA NO EXISTE."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void agregarCiudad(){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("tpo\\operacionesABM.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            /*
            este metodo agrega una ciudad en cada estructura que almacena ciudades del programa,
            para que asi se mantenga una coherencia en el sistema
            */
            String nombre, provincia;
            //tiene que ser int para poder usarlo al comparar en diccionario
            int codigoPostal;
            System.out.println(ANSI_WHITE+"Ingrese los datos de la nueva ciudad a continuacion:"+ANSI_RESET);
            System.out.println(ANSI_WHITE+"Nombre de la ciudad:"+ANSI_RESET);
            nombre = sc.nextLine();
            nombre = sc.nextLine();
            System.out.println(ANSI_WHITE+"Provincia de la ciudad:"+ANSI_RESET);
            provincia = sc.nextLine();
            System.out.println(ANSI_WHITE+"Codigo postal de la ciudad:"+ANSI_RESET);
            codigoPostal = sc.nextInt();
            Ciudad unaCiudad = new Ciudad(String.valueOf(codigoPostal), nombre, provincia);
            boolean exito = ciudades.insertar(codigoPostal, unaCiudad) && rutas.insertarVertice((codigoPostal));
            if(exito){
                bufferedWriter.write("SE AGREGO LA CIUDAD "+unaCiudad.toString()+"\n");
                System.out.println(ANSI_GREEN+"LA CIUDAD FUE AGREGADA CON EXITO."+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED+"LA CIUDAD YA ESTABA INGRESADA, POR ENDE NO PUDO SER AGREGADA."+ANSI_RESET);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void menuABMCiudades(){
        System.out.println(ANSI_BLUE+"------------------------------------ABMCiudades-----------------------------------"+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"<> 1. Agregar una ciudad. \n<> 2. Eliminar una ciudad. \n<> 3. Editar una ciudad.\n<> 4. Volver al menu."+ANSI_RESET);
    }

}
