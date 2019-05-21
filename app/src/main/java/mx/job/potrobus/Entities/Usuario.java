package mx.job.potrobus.Entities;

public class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String correo;
    private String contrasena;
    private String telefono;

    public Usuario(String nombre, String username, String correo, String contrasena, String telefono) {
        this.nombre = nombre;
        this.username = username;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getTelefono() {
        return telefono;
    }
}
