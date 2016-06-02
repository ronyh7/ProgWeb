package main;

import encaculapcion.Articulo;
import encaculapcion.Comentario;
import encaculapcion.Etiqueta;
import encaculapcion.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import main.*;
/**
 * Created by Rony on 5/27/2016.
 */
public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database database;
        ArrayList<Articulo> articulos= new ArrayList<Articulo>();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
        database=Database.getDatabase();
        database.selectAll(articulos,usuarios,etiquetas,comentarios);
        System.out.println(articulos.size());
        System.out.println(usuarios.size());
        System.out.println(comentarios.size());
        System.out.println(etiquetas.size());
        staticFileLocation("/publico");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        freeMarkerEngine.setConfiguration(configuration);

        get("/articulo/:titulo", (request, response) -> {
            Articulo a= new Articulo();
            Map<String, Object> attributes = new HashMap<>();
            for(int i=0;i<articulos.size();i++){
                if(articulos.get(i).getTitulo().equalsIgnoreCase(request.params("titulo"))){
                    a=articulos.get(i);
                    break;
                }
            }
            System.out.println(request.queryParams("titulo"));
            attributes.put("titulo", "Home");
            attributes.put("u", usuarios.get(0));
            attributes.put("a",a);
            attributes.put("articulos",articulos);
            System.out.println(articulos.get(0).getQuote());
            System.out.println(articulos.get(0).getTitulo());
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        get("/home/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Home");
            attributes.put("articulos", articulos);
            attributes.put("username", "ronyh7");
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/insertar/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Insertar estudiante");
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);




        post("/insertar/", (request, response) -> {
            String titulo = request.queryParams("titulo");
            String cuerpo =request.queryParams("cuerpo");
            Usuario u = new Usuario("ronyh7","Rony Hernandez","1234",true,true,"I AM VENGENCE I AM THE NIGHT I AM BATMAN");
            String fecha =request.queryParams("fecha");
            String etiquetass =request.queryParams("etiquetas");
            String[] etiqueta=etiquetass.split(",");
            int e= database.count("ETIQUETA")+1;
            ArrayList<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
            int ai=database.count("ARTICULO")+1;
            String[] p= {String.valueOf(ai),titulo,cuerpo,u.getUsername(),fecha};
            database.insertArticulo(p);
            for(int i=0;i< etiqueta.length;i++){
                listaEtiquetas.add(new Etiqueta(e+i,etiqueta[i],ai));
                database.insertEtiqueta(e+i,ai,etiqueta[i]);
            }
            Articulo a = new Articulo(ai,titulo,cuerpo,u,Date.valueOf(fecha),listaEtiquetas);
            articulos.add(a);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        post("/home/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        post("/actualizar/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");
            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);
    }
}
