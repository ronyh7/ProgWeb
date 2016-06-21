package services;


import encapsulacion.Articulo;

public class ArticuloServices extends GestionDb<Articulo> {

    private static ArticuloServices instancia;

    private ArticuloServices() {
        super(Articulo.class);
    }

    public static ArticuloServices getInstancia(){
        if(instancia==null){
            instancia = new ArticuloServices();
        }
        return instancia;
    }


}
