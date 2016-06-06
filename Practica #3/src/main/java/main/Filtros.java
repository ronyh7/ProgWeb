package main;

import encaculapcion.Usuario;

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
                //parada del request, enviando un codigo.
                response.redirect("/home/");
            }
        });

        before("/insertar/:username",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null || usuario.getAutor()==false || !(usuario.getUsername().equals(request.params("username"))) ){
                //parada del request, enviando un codigo.
                response.redirect("/home/");
            }
        });

        before("/editar/:id",(request, response) -> {
            Usuario usuario=request.session(true).attribute("usuario");
            if(usuario==null || usuario.getAutor()==false || usuario.getAdministrador()==false){
                //parada del request, enviando un codigo.
                response.redirect("/articulo/none/"+request.params("id"));
            }
        });



    }
}
