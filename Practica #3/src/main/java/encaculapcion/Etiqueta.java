package encaculapcion;

/**
 * Created by Rony on 5/30/2016.
 */
public class Etiqueta {
    private long id;
    private long idArticulo;
    private String etiqueta;

    public Etiqueta(long id, String etiqueta,long idArticulo){
        this.id=id;
        this.etiqueta=etiqueta;
        this.idArticulo=idArticulo;
    }

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

    public long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        this.idArticulo = idArticulo;
    }
}
