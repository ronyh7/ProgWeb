package main;

import encapsulacion.Usuario;

import static spark.Spark.*;

public class Filtros {


    public void aplicarFiltros(){


/*        before((request, response) -> {
            System.out.println("Filtro Before -> Realizando llamada a la ruta: "+request.pathInfo());
        });

        after((request, response) -> {
            System.out.println("Filtro After -> Incluyendo Header...");
            response.header("barcamp", "2016");
            response.header("otroHeader", "Cualquier Cosa");
        });*/


        before("/usuario/",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null || usuario.getAdministrador()==false ){
                response.redirect("/home/");
            }
        });

        before("/home/",(request, response) -> {
                response.redirect("/home/1");

        });

        before("/insertar/:username",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null || usuario.getAutor()==false || !(usuario.getUsername().equals(request.params("username"))) || usuario.getAdministrador()==false ){
                response.redirect("/home/");
            }
        });

        before("/insertar/",(request, response) -> {
                response.redirect("/home/");
        });

        before("/editar/:id",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null || usuario.getAutor()==false || usuario.getAdministrador()==false){
                response.redirect("/articulo/none/"+request.params("id"));
            }
        });



    }
}
