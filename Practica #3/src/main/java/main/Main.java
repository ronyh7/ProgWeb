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
        staticFileLocation("/publico");
        new Filtros().aplicarFiltros();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        freeMarkerEngine.setConfiguration(configuration);

        get("/articulo/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            if(u==null){
                u= new Usuario();
            }
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

            attributes.put("u", a.getAutor());
            attributes.put("a",a);
            attributes.put("ul",u);
            attributes.put("articulos",articulos);
            attributes.put("comentarios", listaC);
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        get("/articulo/:id/:comentario", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            String comentario = request.params("comentario");
            Comentario c = new Comentario();
            Map<String, Object> attributes = new HashMap<>();
            Articulo a = new Articulo();
            Long id = Long.parseLong(request.params("id"));
            for(int i=0;i<articulos.size();i++){
                if(articulos.get(i).getId()==Long.parseLong(request.params("id"))){
                    for(int j=0; j < articulos.get(i).getListaComentarios().size(); j++){
                        if(articulos.get(i).getListaComentarios().get(j).getComentario().equals(comentario))
                            c=articulos.get(i).getListaComentarios().get(j);
                            articulos.get(i).getListaComentarios().remove(j);
                            a= articulos.get(i);
                            break;
                    }
                }
            }
            ArrayList<Comentario> listaC = a.getListaComentarios();
            database.deleteComentario(c.getId());
            attributes.put("u", a.getAutor());
            attributes.put("a",a);
            attributes.put("ul",u);
            attributes.put("articulos",articulos);
            attributes.put("comentarios", listaC);
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
            System.out.println(articulos.size());
            attributes.put("titulo", "Home");
            attributes.put("articulos",articulos);
            attributes.put("marticulos", a);
            attributes.put("darticulos", e);
            attributes.put("user", usuario);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/insertar/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        get("/editar/:id", (request, response) -> {
            Articulo a= new Articulo();
            String e="";
            long id = Long.parseLong(request.params("id"));
            for(int i=0; i< articulos.size();i++){
                if(articulos.get(i).getId()==id){
                    a=articulos.get(i);
                    break;
                }
            }
            for(int i=0;i<a.getListaEtiquetas().size();i++){
                if(i!=a.getListaEtiquetas().size()-1){
                    e+=a.getListaEtiquetas().get(i).getEtiqueta()+",";
                }
                else{
                    e+=a.getListaEtiquetas().get(i).getEtiqueta();
                }
            }
            System.out.println(a.getTitulo());
            System.out.print(e);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("a",a);
            attributes.put("etiquetas",e);
            attributes.put("id",request.params("id"));
            return new ModelAndView(attributes, "editarArticulo.ftl");
        }, freeMarkerEngine);

        get("/usuario/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "usuario.ftl");
        }, freeMarkerEngine);

        get("/login/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            System.out.println("1");
            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);


        post("/insertar/", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            String titulo = request.queryParams("titulo");
            String cuerpo =request.queryParams("cuerpo");
            String fecha =request.queryParams("fecha");
            String quote =request.queryParams("quote");
            String qname =request.queryParams("qname");
            String etiquetass =request.queryParams("etiquetas");
            String[] etiqueta=etiquetass.split(",");
            if(quote==null){
                quote="";
                qname="";
            }
            int e= database.count("ETIQUETA")+1;
            ArrayList<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
            int ai=database.count("ARTICULO")+1;
            String[] p= {String.valueOf(ai),titulo,cuerpo,u.getUsername(),fecha,quote,qname};
            database.insertArticulo(p);
            for(int i=0;i< etiqueta.length;i++){
                listaEtiquetas.add(new Etiqueta(e+i,etiqueta[i],ai));
                database.insertEtiqueta(e+i,ai,etiqueta[i]);
            }
            Articulo a = new Articulo(ai,titulo,cuerpo,u,Date.valueOf(fecha),listaEtiquetas,quote,qname);
            articulos.add(a);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        post("/editar/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            String titulo = request.queryParams("titulo");
            String cuerpo =request.queryParams("cuerpo");
            String quote =request.queryParams("quote");
            String qname =request.queryParams("qname");
            String id = request.params("id");
            System.out.println(request.queryParams("quote"));
            System.out.println(request.queryParams("qname"));

            String etiquetass =request.queryParams("etiquetas");
            System.out.println(etiquetass);
            String[] etiqueta=etiquetass.split(",");
            if(quote==null){
                quote="";
                qname="";
            }
            ArrayList<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
            for(int i=0; i < etiqueta.length ; i++){
                database.updateEtiqueta(etiqueta[i],id);

            }
            database.selectUpdatedEtiqueta(listaEtiquetas,id);
            int j=0;
            for(int i=0; i<articulos.size();i++){
                if(articulos.get(i).getId()==Long.parseLong(id)){
                    articulos.get(i).setTitulo(titulo);
                    articulos.get(i).setCuerpo(cuerpo);
                    articulos.get(i).setQuote(quote);
                    articulos.get(i).setQname(qname);
                    articulos.get(i).setListaEtiquetas(listaEtiquetas);
                    j=i;
                    break;
                }
            }
            database.updateArticulo(articulos.get(j));
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/articulo/"+u.getUsername()+"/"+id);

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertarArticulo.ftl");
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
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        post("/articulo/:username/:titulo", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
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
            attributes.put("ul",u);
            attributes.put("a",a);
            attributes.put("u",a.getAutor());
            attributes.put("articulos",articulos);
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
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        post("/articulo/:comentario", (request, response) -> {
            System.out.println("WHat's up");
            Map<String, Object> attributes = new HashMap<>();
            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "articulo.ftl");
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
