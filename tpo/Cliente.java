package tpo;

public class Cliente {
    private String tipoDni;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Cliente(String tipoDni, String numeroDocumento, String apellido, String nombre,String telefono, String email) {
        this.tipoDni = tipoDni;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public String toString(){
        return " | Tipo DNI: "+tipoDni+"DNI: "+numeroDocumento+"Nombre/s: "+nombre+"Apellido/s: "+apellido+
        "E-mail: "+email+"Numero de telefono: "+telefono+"\n";
    }

    public String getTipoDni() {
        return tipoDni;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
