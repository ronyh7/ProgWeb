package example;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;

import encapculacion.Asignatura;
import encapculacion.Estudiante;

/**
 * Created by Rony on 7/11/2016.
 */
@WebService()
public class Main {
  public static ArrayList<Estudiante> estudiantes;
  public static ArrayList<Asignatura> asignaturas;


  @WebMethod
  public Estudiante getEstudiante(String matricula){
    Estudiante e = new Estudiante();
    for(int i=0;i< estudiantes.size();i++){
      if(estudiantes.get(i).getMatricula().equals(matricula)){
         e=estudiantes.get(i);
        break;
      }
    }
    return e;
  }
  @WebMethod
  public ArrayList<Estudiante> getEstudiantes(String matricula){
    return estudiantes;
  }
  @WebMethod
  public void modificarEstudiante(String matricula,String nombre, String carrera){
    for(int i=0;i<estudiantes.size();i++){
      if(estudiantes.get(i).getMatricula().equals(matricula)){
        estudiantes.get(i).setNombre(nombre);
        estudiantes.get(i).setCarrera(carrera);
        break;
      }
    }
  }
  @WebMethod
  public void eliminarEstudiante(String matricula){
    for(int i=0;i<estudiantes.size();i++){
      if(estudiantes.get(i).getMatricula().equals(matricula)){
        estudiantes.remove(i);
        break;
      }
    }
  }
  @WebMethod
  public void crearEstudiante(String matricula,String nombre, String carrera){
    Estudiante estudiante = new Estudiante();
    estudiante.setNombre(nombre);
    estudiante.setCarrera(carrera);
    estudiante.setMatricula(matricula);
    estudiantes.add(estudiante);
  }
  @WebMethod
  public void crearAsignatura(String codigo,String nombre){
    Asignatura asignatura = new Asignatura();
    asignatura.setCodigo(codigo);
    asignatura.setNombre(nombre);
    asignaturas.add(asignatura);
  }
  @WebMethod
  public ArrayList<Asignatura> getAsignaturas(){
    return asignaturas;
  }
  @WebMethod
  public void asignarAsignatura(String codigo,String matricula){
    Asignatura a = new Asignatura();
    for(int i=0; i < asignaturas.size();i++){
      if(asignaturas.get(i).getCodigo().equals(codigo)){
        a=asignaturas.get(i);
        break;
      }
    }
    for(int i=0;i<estudiantes.size();i++){
      if(estudiantes.get(i).getMatricula().equals(matricula)){
        estudiantes.get(i).getAsignaturas().add(a);
        break;
      }
    }
  }
  @WebMethod
  public int size(){
    return estudiantes.size();
  }
  public static void main(String[] argv) {
    estudiantes= new ArrayList<>();
    asignaturas = new ArrayList<>();
    Object implementor = new Main();
    String address = "http://localhost:9000/Main";
    Endpoint.publish(address, implementor);
  }
}
