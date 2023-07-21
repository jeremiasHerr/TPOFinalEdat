package estructuras;

public class NodoAdyMod {
     //atributos
     private NodoVertMod vertice;
     private NodoAdyMod sigAdyacente;
     private Object solicitud;
 
     public NodoAdyMod(NodoVertMod unVertice, NodoAdyMod unSigAdyacente, Object unaSolicitud) {
         this.vertice = unVertice;
         this.sigAdyacente = unSigAdyacente;
         this.solicitud = unaSolicitud;
     }
     
     public NodoVertMod getVertice() {
         return this.vertice;
     }
     
     public void setVertice(NodoVertMod vertice) {
         this.vertice = vertice;
     }
     
     public NodoAdyMod getSigAdyacente() {
         return this.sigAdyacente;
     }
     
     public void setSigAdyacente(NodoAdyMod sigAdyacente) {
         this.sigAdyacente = sigAdyacente;
     }
     
     public Object getSolicitud() {
         return this.solicitud;
     }
     
     public void setSolicitud(Object unaSolicitud) {
         this.solicitud = unaSolicitud;
     }
}
