package encapsulacion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Rony on 5/30/2016.
 */
@Entity
public class Comentario {
    @Id
    @GeneratedValue
    private long id;
    private String comentario;
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    private Articulo articulo;
    private int cantidadLikes;
    private int cantidadDislikes;


    public Comentario(){
        setCantidadLikes(0);
        setCantidadDislikes(0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articuloid) {
        this.articulo = articuloid;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public int getCantidadDislikes() {
        return cantidadDislikes;
    }

    public void setCantidadDislikes(int cantidadDislikes) {
        this.cantidadDislikes = cantidadDislikes;
    }
}
