
import freemarker.template.Configuration;
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

        get("/home", (request, response) -> {
            ArrayList<Estudiante> estudiantess;
            estudiantess=database.select();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Home");
            attributes.put("estudiantes",estudiantess);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/formulario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "FreeMarker");
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);



        post("/procesarFormulario/", (request, response) -> {
            String matricula = request.queryParams("matricula");
            String nombre =request.queryParams("nombre");
            String apellidos =request.queryParams("apellidos");
            String telefono =request.queryParams("telefono");
            Estudiante estudiante= new Estudiante(matricula, nombre, apellidos, telefono);
            estudiantes.add(estudiante);
            String[] i = {request.queryParams("matricula"), request.queryParams("nombre"),request.queryParams("apellidos"),request.queryParams("telefono")};
            database.insert(i);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Procesando Estudiante");
            attributes.put("estudiante", estudiante);
            response.redirect("/home");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);






    }
}
