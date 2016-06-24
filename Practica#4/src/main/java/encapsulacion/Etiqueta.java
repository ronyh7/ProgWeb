package encapsulacion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Rony on 5/30/2016.
 */
@Entity
public class Etiqueta {
    @Id
    @GeneratedValue
    private long id;
    private String etiqueta;
    @ManyToOne
    private Articulo articulo;


    public Etiqueta(long id, String etiqueta,Articulo Articulo){
        this.id=id;
        this.etiqueta=etiqueta;
        this.articulo=Articulo;
    }
    public Etiqueta(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo Articulo) {
        this.articulo = Articulo;
    }
}
