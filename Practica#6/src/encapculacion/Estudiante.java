/**
 * Created by Rony on 5/28/2016.
 */
package encapculacion;

import java.util.ArrayList;

public class Estudiante {
    private String matricula;
    private String nombre;
    private String carrera;
    private ArrayList<Asignatura> asignaturas;

    public Estudiante(String matricula, String nombre, String carrera, ArrayList<Asignatura> asignaturas){
        this.setMatricula(matricula);
        this.setNombre(nombre);
        this.setCarrera(carrera);
        this.setAsignaturas(asignaturas);

    }
    public Estudiante(){
        asignaturas=new ArrayList<>();
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
