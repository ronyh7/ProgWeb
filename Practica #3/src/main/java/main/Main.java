package main;

import encaculapcion.Articulo;
import encaculapcion.Comentario;
import encaculapcion.Etiqueta;
import encaculapcion.Usuario;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
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
        //Cargar todos los elementos en la base de datos y guardarlos como objetos
        Database database;
        ArrayList<Articulo> articulos= new ArrayList<Articulo>();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
        ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
        database=Database.getDatabase();
        database.selectAll(articulos,usuarios,etiquetas,comentarios);
        /*System.out.println(articulos.size());
        System.out.println(usuarios.size());
        System.out.println(comentarios.size());
        System.out.println(etiquetas.size());*/
        staticFileLocation("/publico");
        new Filtros().aplicarFiltros();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        freeMarkerEngine.setConfiguration(configuration);

        get("/articulo/:username/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            Articulo a= new Articulo();
            Map<String, Object> attributes = new HashMap<>();
            for(int i=0;i<articulos.size();i++){
                if(articulos.get(i).getId()==Long.parseLong(request.params("id"))){
                    a=articulos.get(i);
                    System.out.println(a.getAutor().getUsername());
                    break;
                }
            }

            ArrayList<Comentario> listaC = a.getListaComentarios();
            if(listaC.size()==0){
                listaC=null;
            }
            System.out.println(request.params("titulo"));
            attributes.put("u", a.getAutor());
            attributes.put("a",a);
            attributes.put("articulos",articulos);
            attributes.put("titulo",request.params("titulo"));
            attributes.put("comentarios", listaC);
            System.out.println(articulos.get(0).getQuote());
            System.out.println(articulos.get(0).getTitulo());
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        get("/home/", (request, response) -> {

            Usuario usuario= request.session(true).attribute("usuario");
            if(usuario==null){
                usuario=new Usuario();
            }
            Map<String, Object> attributes = new HashMap<>();
            ArrayList<Articulo> a = new ArrayList<Articulo>();
            ArrayList<Articulo> e = new ArrayList<Articulo>();
            for(int i=0;i<articulos.size();i++){
                if(articulos.get(i).getAutor().getUsername().equals(usuario.getUsername())){
                    a.add(articulos.get(i));
                }
                else
                    e.add(articulos.get(i));
            }
            attributes.put("titulo", "Home");
            attributes.put("articulos",articulos);
            attributes.put("marticulos", a);
            attributes.put("darticulos", e);
            attributes.put("user", usuario);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/insertar/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        get("/usuario/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Insertar estudiante");
            return new ModelAndView(attributes, "usuario.ftl");
        }, freeMarkerEngine);

        get("/login/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo", "Insertar estudiante");
            System.out.println("1");
            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);


        post("/insertar/", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            String titulo = request.queryParams("titulo");
            String cuerpo =request.queryParams("cuerpo");
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

        post("/usuario/", (request, response) -> {
            String username = request.queryParams("username");
            String nombre =request.queryParams("nombre");
           String password =request.queryParams("password");
            String about =request.queryParams("about");
            Boolean autor = Boolean.parseBoolean(request.queryParams("autor"));
            Boolean admin = Boolean.parseBoolean(request.queryParams("admin"));
            Usuario u = new Usuario(username,nombre,password,admin,autor,about);
            String[] p= {username,nombre,password,String.valueOf(admin),String.valueOf(autor),about};
            database.insertUsuario(p);
            usuarios.add(u);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertar.ftl");
        }, freeMarkerEngine);

        post("/articulo/:username/:titulo", (request, response) -> {
            String titulo = request.params("titulo");
            String autor =request.params("username");
            String comentario =request.queryParams("comentario");
            long aid=0;
            long cid=database.count("COMENTARIO")+1;
            Articulo a = new Articulo();
            for(int i=0; i<articulos.size();i++){
                if(articulos.get(i).getTitulo().equals(titulo)){
                    aid=articulos.get(i).getId();
                    articulos.get(i).getListaComentarios().add(new Comentario(cid,comentario,autor, aid));
                    a=articulos.get(i);
                    break;
                }
            }
            String[] c= {String.valueOf(cid),comentario,autor,String.valueOf(aid)};
            database.insertComentario(c);
            Map<String, Object> attributes = new HashMap<>();
            ArrayList<Comentario> listaC = a.getListaComentarios();
            if(listaC.size()==0){
                listaC=null;
            }
            attributes.put("u", usuarios.get(0));
            attributes.put("a",a);
            attributes.put("articulos",articulos);
            attributes.put("username",autor);
            attributes.put("titulo",titulo);
            attributes.put("comentarios",listaC);
            return new ModelAndView(attributes, "articulo.ftl");
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

        post("/login/", (request, response)->{
            //
            Session session= request.session(true);
            //
            Usuario usuario= database.login(request.queryParams("username"),request.queryParams("password"));//FakeServices.getInstancia().autenticarUsuario(request.params("usuario"), request.params("contrasena"));
            /*if(request.params("usuario").equalsIgnoreCase("barcamp") && request.params("contrasena").equalsIgnoreCase("2014")){
                usuario = new Usuario("Barcamp", "2014");
            }*/
            if(usuario==null){
                System.out.println(usuario);
                response.redirect("/login/");
            }
            else {
                session.attribute("usuario", usuario);
                response.redirect("/home/");
            }

            return "";
        });
    }
}
