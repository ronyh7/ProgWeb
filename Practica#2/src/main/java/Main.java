
import freemarker.template.Configuration;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

/**
 * Created by Rony on 5/27/2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ArrayList<Estudiante> estudiantes;
        Database database=Database.getDatabase();
        estudiantes=database.select();
        staticFileLocation("/publico");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        freeMarkerEngine.setConfiguration(configuration);

        get("/home/", (request, response) -> {
            ArrayList<Estudiante> estudiantess;
            estudiantess=database.select();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Home");
            attributes.put("estudiantes",estudiantess);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/insertar/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Insertar estudiante");
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        get("/borrar/:matricula", (request, response) -> {
            Estudiante e = new Estudiante();
            for(int i=0; i< estudiantes.size(); i++){
                if(estudiantes.get(i).getMatricula().equals(request.params("matricula"))){
                    e=estudiantes.get(i);
                    break;
                }
            }
            System.out.println(request.params("matricula"));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Borrar");
            attributes.put("estudiante",e);
            return new ModelAndView(attributes, "borrar.ftl");
        }, freeMarkerEngine);

        get("/actualizar/:matricula", (request, response) -> {
            Estudiante e = new Estudiante();
            for(int i=0; i< estudiantes.size(); i++){
                if(estudiantes.get(i).getMatricula().equals(request.params("matricula"))){
                    e=estudiantes.get(i);
                    break;
                }
            }
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Actualizar");
            attributes.put("estudiante", e);
            return new ModelAndView(attributes, "actualizar.ftl");
        }, freeMarkerEngine);



        post("/insertar/", (request, response) -> {
            String matricula = request.queryParams("matricula");
            String nombre =request.queryParams("nombre");
            String apellidos =request.queryParams("apellidos");
            String telefono =request.queryParams("telefono");
            Estudiante estudiante= new Estudiante(matricula, nombre, apellidos, telefono);
            estudiantes.add(estudiante);
            String[] i = {request.queryParams("matricula"), request.queryParams("nombre"),request.queryParams("apellidos"),request.queryParams("telefono")};
            database.insert(i);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("estudiante", estudiante);
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        post("/borrar/", (request, response) -> {
            String matricula = request.queryParams("matricula");
            for(int i=0; i< estudiantes.size(); i++){
                if(estudiantes.get(i).getMatricula().equalsIgnoreCase(matricula)){
                    estudiantes.remove(i);
                }
            }
            database.delete(matricula);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");
            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        post("/actualizar/", (request, response) -> {
            String matricula = request.queryParams("matricula");
            for(int i=0; i< estudiantes.size(); i++){
                if(estudiantes.get(i).getMatricula().equals(matricula)){
                    estudiantes.get(i).setNombre(request.queryParams("nombre"));
                    estudiantes.get(i).setApellidos(request.queryParams("apellidos"));
                    estudiantes.get(i).setTelefono(request.queryParams("telefono"));
                    break;
                }
            }
            System.out.println(request.queryParams("matricula"));
            System.out.println(request.queryParams("nombre"));
            System.out.println(request.queryParams("apellidos"));
            System.out.println(request.queryParams("telefono"));
            String[] i = {request.queryParams("matricula"), request.queryParams("nombre"),request.queryParams("apellidos"),request.queryParams("telefono")};
            database.update(i);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");
            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);






    }
}
