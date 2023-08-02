package tpo;
public class SolicitudViaje {
    private String ciudadDestino;
    private String ciudadOrigen;
    private String fechaSolicitud;
    private String tipoDocumento;
    private int numeroDocumento;
    private int cantMetrosCubicos;
    private int cantBultos;
    private String direccionRetiro;
    private String domicilioEntrega;
    private boolean estaPago;

    public SolicitudViaje(String fechaSolicitud, String tipoDocumento, int numeroDocumento, int cantMetrosCubicos, 
                        int cantBultos, String direccionRetiro, String domicilioEntrega, boolean estaPago, String destino,String origen) {
        this.fechaSolicitud = fechaSolicitud;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cantMetrosCubicos = cantMetrosCubicos;
        this.cantBultos = cantBultos;
        this.direccionRetiro = direccionRetiro;
        this.domicilioEntrega = domicilioEntrega;
        this.estaPago = estaPago;
        this.ciudadDestino = destino;
        this.ciudadOrigen = origen;
    }

    public String toString(){
        String res;
        res = "Ciudad destino: "+ciudadDestino+" | Ciudad origen: "+ciudadOrigen/* " | Fecha: "+fechaSolicitud+" | Cantidad de bultos: "+cantBultos+" | Metros cubicos: "+cantMetrosCubicos*/;
        return res;
    }

    // Setters
    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setCantMetrosCubicos(int cantMetrosCubicos) {
        this.cantMetrosCubicos = cantMetrosCubicos;
    }

    public void setCantBultos(int cantBultos) {
        this.cantBultos = cantBultos;
    }

    public void setDireccionRetiro(String direccionRetiro) {
        this.direccionRetiro = direccionRetiro;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }

    // Getters

    public String getClave(){
        return tipoDocumento+numeroDocumento;
    }

    public String getCiudadOrigen(){
        return ciudadOrigen;
    }

    public String getCiudadDestino(){
        return ciudadDestino;
    }

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
    
}
