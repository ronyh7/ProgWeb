package main;

import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import org.h2.util.StringUtils;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import services.*;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.persistence.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static spark.Spark.*;

/**
 * Created by Rony on 5/27/2016.
 */
public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Cargar todos los elementos en la base de datos y guardarlos como objetos

        staticFileLocation("/publico");
        new Filtros().aplicarFiltros();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        freeMarkerEngine.setConfiguration(configuration);
        GestionDb g = new GestionDb<>(GestionDb.class);
        g.getEntityManager();
        EntityManager entityManager = g.getEntityManager();





        get("/home/:pag", (request, response) -> {
            EntityManager entityManage = g.getEntityManager();
            Usuario usuario= request.session(true).attribute("usuario");
            if(usuario==null){
                usuario=new Usuario();
            }
            Map<String, Object> attributes = new HashMap<>();
            String res="select a from Articulo a order by a.fecha desc";
            Query q = entityManage.createQuery(res,Articulo.class);
            int p = Integer.parseInt(request.params("pag"));
            if(p==1) {
                q.setFirstResult(0);
                q.setMaxResults(5);
            }
            else if(p==2){
                q.setFirstResult(5);
                q.setMaxResults(10);
            }
            else if(p==3){
                q.setFirstResult(10);
                q.setMaxResults(15);
            }
            else if(p==4){
                q.setFirstResult(15);
                q.setMaxResults(20);
            }
            else {
                q.setFirstResult(20);
                q.setMaxResults(25);
            }
            List<Articulo> lista = q.getResultList();
            if(lista.size()==0){
                System.out.println("HEY");
            }
            for(int i=0;i<lista.size();i++){
                String s = lista.get(i).getCuerpo();
                if(s.length()>=70)
                    s.substring(0,70);
                lista.get(i).setCuerpo(s);
            }
            attributes.put("titulo", "Articulos");
            attributes.put("tipo", "home");
            attributes.put("articulos",lista);
            attributes.put("user", usuario);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);


        get("/articulo/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            if(u==null){
                u= new Usuario();
            }

            Long id = Long.parseLong(request.params("id"));
            Articulo a= ArticuloServices.getInstancia().find(id);
            for(Usuario uo: a.getUsuariosOpiniones()){
                if(uo.getUsername().equals(u.getUsername())) {
                    a.setOpinionDada(true);
                    break;
                }
            }

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("u", a.getAutor());
            attributes.put("a",a);
            attributes.put("ul",u);
            attributes.put("etiquetas",a.getListaEtiquetas());
            attributes.put("comentarios", a.getListaComentarios());
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);



        get("/eliminar/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            Long id = Long.parseLong(request.params("id"));
            Articulo a = ArticuloServices.getInstancia().find(id);
            for(Etiqueta e: a.getListaEtiquetas()){

                EtiquetaServices.getInstancia().eliminar(e);
            }
            a.setListaEtiquetas(new HashSet<>());
            for(Comentario c: a.getListaComentarios()){
                ComentarioServices.getInstancia().eliminar(c);
            }
            a.setListaComentarios(new HashSet<>());
            ArticuloServices.getInstancia().eliminar(a);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);


        get("/insertar/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);


        get("/etiqueta/:pag/:etiqueta", (request, response) -> {
            EntityManager entityManage = g.getEntityManager();
            Usuario usuario= request.session(true).attribute("usuario");
            if(usuario==null){
                usuario=new Usuario();
            }
            Long idEtiqueta = Long.parseLong(request.params("etiqueta"));
            Etiqueta et = EtiquetaServices.getInstancia().find(idEtiqueta);
            Map<String, Object> attributes = new HashMap<>();
            String res="select a from Articulo a, Etiqueta e WHERE a.id = e.articulo.id AND e.etiqueta='"+et.getEtiqueta()+"' order by a.fecha desc";
            Query q = entityManage.createQuery(res,Articulo.class);
            int p = Integer.parseInt(request.params("pag"));
            if(p==1) {
                System.out.println("lol");
                q.setFirstResult(0);
                q.setMaxResults(5);
            }
            else if(p==2){
                q.setFirstResult(5);
                q.setMaxResults(10);
            }
            else if(p==3){
                q.setFirstResult(10);
                q.setMaxResults(15);
            }
            else if(p==4){
                q.setFirstResult(15);
                q.setMaxResults(20);
            }
            else {
                q.setFirstResult(20);
                q.setMaxResults(25);
            }
            List<Articulo> lista = q.getResultList();
            for(int i=0;i<lista.size();i++){
                String s = lista.get(i).getCuerpo();
                if(s.length()>=70)
                    s.substring(0,70);
                lista.get(i).setCuerpo(s);
            }
            System.out.println("lista: "+lista.size());
            attributes.put("etiqueta",request.params("etiqueta"));
            attributes.put("titulo", et.getEtiqueta());
            attributes.put("tipo", "etiqueta");
            attributes.put("articulos",lista);
            attributes.put("user", usuario);
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        get("/insertar/:username", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        get("/editar/:id", (request, response) -> {
            long id = Long.parseLong(request.params("id"));
            Articulo a= ArticuloServices.getInstancia().find(id);
            String es="";
            Set<Etiqueta> etiquetas = a.getListaEtiquetas();
            if(!etiquetas.isEmpty() || etiquetas!=null){
                for(Etiqueta e: etiquetas){
                    es += e.getEtiqueta() +",";
                }
            }
            System.out.println(a.getTitulo());
            System.out.print(es);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("a",a);
            attributes.put("etiquetas",es.substring(0,es.length()-1));
            attributes.put("id",id);
            return new ModelAndView(attributes, "editarArticulo.ftl");
        }, freeMarkerEngine);

        get("/usuario/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "usuario.ftl");
        }, freeMarkerEngine);

        get("/login/", (request, response) -> {
            Usuario usuario= request.session(true).attribute("usuario");
            if(usuario!=null){
                request.session(true).invalidate();
                System.out.println("yolo");
            }
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);


       post("/insertar/", (request, response) -> {
           Usuario u= request.session(true).attribute("usuario");
           Articulo a = new Articulo();
           a.setTitulo(request.queryParams("titulo"));
           a.setCuerpo(request.queryParams("cuerpo"));
           a.setFecha(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
           a.setAutor(u);
           String quote =request.queryParams("quote");
           String qname =request.queryParams("qname");
           if(quote==null){
               a.setQuote("");
               a.setQname("");
           }
           else{
               a.setQuote(quote);
               a.setQname(qname);
           }
           a.setListaEtiquetas(new HashSet<Etiqueta>());
           a.setListaComentarios(new HashSet<Comentario>());
           ArticuloServices.getInstancia().crear(a);

           String etiquetas =request.queryParams("etiquetas");
           String[] etiqueta=etiquetas.split(",");
           Set<Etiqueta> et = new HashSet<Etiqueta>();
           for(int i=0; i < etiqueta.length;i++){
               Etiqueta e= new Etiqueta();
               e.setEtiqueta(etiqueta[i]);
               e.setArticulo(a);
               EtiquetaServices.getInstancia().crear(e);
               et.add(e);
           }
           a.setListaEtiquetas(et);
           ArticuloServices.getInstancia().editar(a);
           Map<String, Object> attributes = new HashMap<>();
           response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);


       post("/editar/:id", (request, response) -> {
           Usuario u= request.session(true).attribute("usuario");
           Long id = Long.parseLong(request.params("id"));
           Articulo a=ArticuloServices.getInstancia().find(id);

           String titulo = request.queryParams("titulo");
           String cuerpo =request.queryParams("cuerpo");
           String quote =request.queryParams("quote");
           String qname =request.queryParams("qname");

           String etiquetass =request.queryParams("etiquetas");
           System.out.println(etiquetass);
           String[] etiqueta=etiquetass.split(",");
           if(quote==null){
                quote="";
                qname="";
           }
           Set<Etiqueta> lista = a.getListaEtiquetas();
           Set<Etiqueta> nEtiqueta= new HashSet<Etiqueta>();
            Object[] ev = lista.toArray();

           for(int i=0; i < ev.length;i++ ){
               EtiquetaServices.getInstancia().eliminar((Etiqueta)ev[i]);
           }

           for(int i=0; i < etiqueta.length ; i++){
               Etiqueta e = new Etiqueta();
               e.setEtiqueta(etiqueta[i]);
               e.setArticulo(a);
               EtiquetaServices.getInstancia().crear(e);
               nEtiqueta.add(e);
           }
           a.setTitulo(titulo);
           a.setCuerpo(cuerpo);
           a.setQname(qname);
           a.setQuote(quote);
           a.setListaEtiquetas(nEtiqueta);
           ArticuloServices.getInstancia().editar(a);

           Map<String, Object> attributes = new HashMap<>();
           response.redirect("/articulo/"+id);

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "editarArticulo.ftl");
        }, freeMarkerEngine);

        post("/usuario/", (request, response) -> {
            String username = request.queryParams("username");
            String nombre =request.queryParams("nombre");
           String password =request.queryParams("password");
            String about =request.queryParams("about");
            Boolean autor = Boolean.parseBoolean(request.queryParams("autor"));
            Boolean admin = Boolean.parseBoolean(request.queryParams("admin"));
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setPassword(password);
            u.setNombre(nombre);
            u.setAbout(about);
            u.setAutor(autor);
            u.setAdministrador(admin);
            UsuarioServices.getInstancia().crear(u);
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/home/");

            //enviando los parametros a la vista.
            return new ModelAndView(attributes, "insertarArticulo.ftl");
        }, freeMarkerEngine);

        post("/articulo/:id", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            long id = Long.parseLong(request.params("id"));
            Articulo a = ArticuloServices.getInstancia().find(id);
            if(request.queryParams("Like")!=null || request.queryParams("Dislike")!= null){

                if(request.queryParams("Like")!=null){
                    int l = a.getCantidadLikes();
                    a.setCantidadLikes(l+1);
                }
                else{
                    int d = a.getCantidadDislikes();
                    a.setCantidadDislikes(d+1);
                }
                a.getUsuariosOpiniones().add(u);
                a.setOpinionDada(false);
                ArticuloServices.getInstancia().editar(a);
            }
            else {
                String comentario = request.queryParams("comentario");
                Comentario c = new Comentario();
                c.setComentario(comentario);
                c.setArticulo(a);
                c.setAutor(u);
                ComentarioServices.getInstancia().crear(c);
                a.getListaComentarios().add(c);
                ArticuloServices.getInstancia().editar(a);
            }
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("ul",u);
            attributes.put("a",a);
            attributes.put("u",a.getAutor());
            attributes.put("id",id);
            attributes.put("comentarios",a.getListaComentarios());
            response.redirect("/articulo/"+id);
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);

        post("/home/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "home.ftl");
        }, freeMarkerEngine);

        post("/articulo/:id/:cid", (request, response) -> {
            Usuario u= request.session(true).attribute("usuario");
            Long cid = Long.parseLong(request.params("cid"));
            Map<String, Object> attributes = new HashMap<>();
            Long id = Long.parseLong(request.params("id"));
            Articulo a = ArticuloServices.getInstancia().find(id);
            Comentario c = ComentarioServices.getInstancia().find(cid);
            a.getListaComentarios().remove(c);
            ArticuloServices.getInstancia().editar(a);
            ComentarioServices.getInstancia().eliminar(c);
            attributes.put("u", a.getAutor());
            attributes.put("a",a);
            attributes.put("ul",u);
            attributes.put("comentarios", a.getListaComentarios());
            response.redirect("/articulo/"+id);
            return new ModelAndView(attributes, "articulo.ftl");
        }, freeMarkerEngine);


        post("/login/", (request, response)->{

            Session session= request.session(true);

            Usuario usuario= UsuarioServices.getInstancia().find(request.queryParams("username"));


            if(usuario==null){
                response.redirect("/login/");
            }
            else {
                if(usuario.getPassword().equals(request.queryParams("password"))){
                    session.attribute("usuario", usuario);
                    response.redirect("/home/");
                }
                else{
                    response.redirect("/login/");
                }
            }
            return "";
        });
    }
    int paginacion(int p){
        if(p==1) {
            return 0;
        }
        else if(p==2){
            return 5;
        }
        else if(p==3){
            return 10;
        }
        else if(p==4){
            return 15;
        }
        else{
            return 20;
        }
    }
}
