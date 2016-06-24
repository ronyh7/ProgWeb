package encapsulacion;

import javax.persistence.*;
import java.util.Set;

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




    public Comentario(){
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


}
