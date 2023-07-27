package tpo;
public class Ciudad {
    private String codigoPostal;
    private String nombre;
    private String provincia;

    public Ciudad() {

    }

    public Ciudad(String codigoPostal, String nombre, String provincia) {
        this.codigoPostal = codigoPostal;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public String getCodigoPostal(){
        return codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String toString(){
        return "Cod. Postal: " + this.codigoPostal + " | Ciudad: " + this.nombre + " | Provincia: " + this.provincia+"\n";
    }

}
