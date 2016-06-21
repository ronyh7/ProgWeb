package encapsulacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Rony on 5/30/2016.
 */
@Entity
public class Articulo {
    @Id
    @GeneratedValue
    private long id;

    private String titulo;
    @Column(length = 500)
    private String cuerpo;
    @ManyToOne
    private Usuario autor;
    private String quote;
    private String qname;
    private LocalDate fecha;
    private int cantidadLikes;
    private int cantidadDislikes;
    @OneToMany(mappedBy = "articulo", fetch = FetchType.EAGER)
    private Set<Comentario> listaComentarios;

    @OneToMany(mappedBy = "articulo", fetch = FetchType.EAGER)
    private Set<Etiqueta> listaEtiquetas;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Usuario> usuariosOpiniones;

    @Transient
    private Boolean opinionDada;

    public Articulo(){
        setListaComentarios(new HashSet<>());
        setListaEtiquetas(new HashSet<>());
        setUsuariosOpiniones(new HashSet<>());
        opinionDada= false;
        setCantidadLikes(0);
        setCantidadDislikes(0);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Set<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(Set<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public Set<Etiqueta> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(Set<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
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

    public Set<Usuario> getUsuariosOpiniones() {
        return usuariosOpiniones;
    }

    public void setUsuariosOpiniones(Set<Usuario> usuariosOpiniones) {
        this.usuariosOpiniones = usuariosOpiniones;
    }

    public Boolean getOpinionDada() {
        return opinionDada;
    }

    public void setOpinionDada(Boolean opinion) {
        this.opinionDada = opinion;
    }
}
