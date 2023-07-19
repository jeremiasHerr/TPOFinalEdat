package tpo;

import estructuras.NodoAdy;
import estructuras.NodoVert;
//originalmente NodoVert
public class Ciudad {
    //atributos
    private Object nombreCiudad;
    private Ciudad sigCiudad;
    private SolicitudViaje primerSolicitud;

    public Ciudad (Object unElem, Ciudad unVertice){
        this.nombreCiudad = unElem;
        this.sigCiudad = unVertice;
        this.primerSolicitud = null;
    }

     public Object getElem(){
        return this.nombreCiudad;
    }

    public void setElem(Object unElem){
        this.nombreCiudad = unElem;
    }

    public Ciudad getSigVertice(){
        return this.sigCiudad;
    }

    public void setSigVertice(Ciudad unaCiudad){
        this.sigCiudad = unaCiudad;
    }

    public SolicitudViaje getPrimerAdy(){
        return this.primerSolicitud;
    }

    public void setPrimerAdy(SolicitudViaje unaSolicitudViaje){
        this.primerSolicitud = unaSolicitudViaje;
    }
}
