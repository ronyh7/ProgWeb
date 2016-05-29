/**
 * Created by Rony on 5/28/2016.
 */
public class Estudiante {
    private String matricula;
    private String nombre;
    private String apellidos;
    private String telefono;

    public Estudiante(String matricula, String nombre, String apellidos, String telefono){
        this.setMatricula(matricula);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setTelefono(telefono);
    }
    public Estudiante(){}



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
