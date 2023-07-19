package tpo;

//originalmente NodoAdy
/*
 *  ciudad de donde sale (es la ciudad que tiene la solicitud)
    ciudadDestino
    fechaSolicitud
    cliente
    cantMetrosCubicos
    cantBultos
 */
public class SolicitudViaje {
    //atributos
    private Ciudad ciudadDestino;
    private SolicitudViaje sigSolicitud;
    private String fechaSolicitud;
    private String tipoDocumento;
    private int numeroDocumento;
    private int cantMetrosCubicos;
    private int cantBultos;
    private String direccionRetiro;
    private String domicilioEntrega;
    private boolean estaPago;

    public SolicitudViaje (Ciudad unaCiudadDestino, SolicitudViaje otraSolicitud) {
        this.ciudadDestino = unaCiudadDestino;
        this.sigSolicitud = otraSolicitud;
    }
    /* 
    public SolicitudViaje (Ciudad unaCiudadDestino, SolicitudViaje otraSolicitud, String fecha, String docu, int num, int metros, int bultos, String retiro, String entrega, boolean pago) {
        this.ciudadDestino = unaCiudadDestino;
        this.sigSolicitud = otraSolicitud;
        this.fechaSolicitud = fecha;
        this.documento = docu;
        this.numeroDocumento = num;
        this.cantMetrosCubicos = metros;
        this.cantBultos = metros;
        this.direccionRetiro = retiro;
        this.domicilioEntrega = entrega;
        this.estaPago = pago;
    }
    */
    // voy agregando los gets y sets a medida que vayan siendo necesarios

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    
    public int getNumeroDocumento() {
        return numeroDocumento;
    }
    
    public int getCantMetrosCubicos() {
        return cantMetrosCubicos;
    }
    
    public int getCantBultos() {
        return cantBultos;
    }
    
    public String getDireccionRetiro() {
        return direccionRetiro;
    }
    
    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }
    
    public boolean estaPago() {
        return estaPago;
    }

    public void setEstaPago (boolean pago){
        this.estaPago = pago;
    }

    public void setDomicilioEntrega(String domicilio){
        this.domicilioEntrega = domicilio;
    }

    public void setDireccionRetiro(String retiro){
        this.direccionRetiro = retiro;
    }

    public void setCantBultos(int bultos){
        this.cantBultos = bultos;
    }
  
    public void setMetrosCubicos(int metros){
        this.cantMetrosCubicos = metros;
    }

    public void setNumeroDocumento(int numero){
        this.numeroDocumento = numero;
    }

    public void setTipoDocumento(String docu){
        this.tipoDocumento = docu;
    }

    public void setFecha(String unaFecha){
        this.fechaSolicitud = unaFecha;
    }

    public Ciudad getVertice() {
        return this.ciudadDestino;
    }
    
    public void setCiudadDestino(Ciudad unaCiudad) {
        this.ciudadDestino = unaCiudad;
    }
    
    public SolicitudViaje getSigAdyacente() {
        return this.sigSolicitud;
    }
    
    public void setSigAdyacente (SolicitudViaje sigAdyacente) {
        this.sigSolicitud = sigAdyacente;
    }
    
}
