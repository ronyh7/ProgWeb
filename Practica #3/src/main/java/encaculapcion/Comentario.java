package encaculapcion;

/**
 * Created by Rony on 5/30/2016.
 */
public class Comentario {
    private long id;
    private String comentario;
    private String autor;
    private long articuloid;

    public Comentario(long id, String comentario, String autor, long articuloid){
        this.id=id;
        this.comentario=comentario;
        this.autor=autor;
        this.articuloid=articuloid;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public long getArticuloid() {
        return articuloid;
    }

    public void setArticuloid(long articuloid) {
        this.articuloid = articuloid;
    }
}
