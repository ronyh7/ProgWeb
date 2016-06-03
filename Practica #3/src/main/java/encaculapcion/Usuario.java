package encaculapcion;

/**
 * Created by Rony on 5/30/2016.
 */
public class Usuario {
    private String username;
    private String nombre;
    private String password;
    private Boolean administrador;
    private Boolean autor;
    private String about;

    public Usuario(String username, String nombre, String password, Boolean administrador,Boolean autor, String about){
        this.username= username;
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
        this.autor= autor;
        this.about=about;
    }
    public Usuario(){
        this.username= "";
        this.nombre = "";
        this.password = "";
        this.about="";
        this.administrador =false;
        this.autor = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getAutor() {
        return autor;
    }

    public void setAutor(Boolean autor) {
        this.autor = autor;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
