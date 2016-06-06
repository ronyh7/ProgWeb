package main;

import encaculapcion.Articulo;
import encaculapcion.Comentario;
import encaculapcion.Etiqueta;
import encaculapcion.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rony on 5/29/2016.
 */
public class Database {
     private static Connection conexion=null;
     private static Database database=null;
    public static Database getDatabase() throws SQLException{
        if(database==null){
            database= new Database();
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conexion = DriverManager.getConnection("jdbc:h2:~/practica3", "sa", "1234");
        }
        return database;
    }

    public void insertArticulo(String[] params) throws SQLException {
        /*String insert="INSERT INTO ARTICULO(ID, TITULO, CUERPO, AUTOR, DATE, QUOTE, QNAME) VALUES(";
        for(int i=0;i<params.length;i++){
            if(i==0){
                insert+=params[i]+",";
            }
            else if(i!=params.length-1)
                insert+="'"+params[i]+"'"+",";
            else
                insert+="'"+params[i]+"'"+");";
        }*/
       String insert= String.format("INSERT INTO ARTICULO(ID, TITULO, CUERPO, AUTOR, DATE, QUOTE, QNAME) VALUES(%s,'%s','%s','%s','%s','%s','%s');",
                params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
        System.out.println(insert);
        conexion.createStatement().execute(insert);
    }

    public void insertUsuario(String[] params) throws SQLException {
        String insert="INSERT INTO USUARIO(USERNAME,NOMBRE,PASSWORD,ADMINISTRADOR,AUTOR,ABOUT) VALUES(";
        for(int i=0;i<params.length;i++){
            if(i==3 || i==4)
                insert+=params[i]+",";
            else if(i!=params.length-1)
                insert+="'"+ params[i] + "',";
            else
                insert+="'"+params[i]+"'"+");";
        }
        conexion.createStatement().execute(insert);
    }
    public void insertComentario(String[] params) throws SQLException {
        String insert="INSERT INTO COMENTARIO(ID, COMENTARIO, USUARIO, ARTICULO) VALUES(";
        for(int i=0;i<params.length;i++){
            if(i==0){
                insert+=params[i]+",";
            }
            else if(i!=params.length-1)
                insert+="'"+params[i]+"'"+",";
            else
                insert+="'"+params[i]+"'"+");";
        }
        conexion.createStatement().execute(insert);
    }

    public void insertEtiqueta(long id, long aid, String etiqueta) throws SQLException {
        String insert="INSERT INTO ETIQUETA(ID, ARTICULO, ETIQUETA) VALUES(";
        insert+=id+","+aid+",'"+etiqueta+"')";
        conexion.createStatement().execute(insert);
    }

    public void deleteComentario(long id) throws SQLException {
        String delete="DELETE FROM COMENTARIO WHERE ID="+id;
        conexion.createStatement().execute(delete);
    }

    public void update(String[] estudiante) throws SQLException {
        String update=String.format("UPDATE ESTUDIANTES SET NOMBRE = '%s', APELLIDOS = '%s', TELEFONO = '%s'" +
                        " WHERE MATRICULA = '%s'",
                estudiante[1], estudiante[2], estudiante[3], estudiante[0]);
        conexion.createStatement().execute(update);
    }

    public void updateEtiqueta(String e, String id) throws SQLException {
        String update=String.format("UPDATE ETIQUETA SET ETIQUETA = '%s' "+
                        " WHERE ARTICULO = '%s'",
                e,id);
        conexion.createStatement().execute(update);
    }

    public void updateArticulo(Articulo a) throws SQLException {
        PreparedStatement s=conexion.prepareStatement("UPDATE ARTICULO SET TITULO = ?, CUERPO=?, QUOTE =?, QNAME =? WHERE ID = ?");
        s.setString(1,a.getTitulo());
        s.setString(2,a.getCuerpo());
        s.setString(3,a.getQuote());
        s.setString(4,a.getQname());
        s.setLong(5,a.getId());
        s.executeUpdate();
    }

    public void selectAll(ArrayList<Articulo> listaArticulo,ArrayList<Usuario> listaUsuario, ArrayList<Etiqueta> listaEtiqueta, ArrayList<Comentario> listaComentario) throws SQLException {
        selectUsuario(listaUsuario);
        selectEtiqueta(listaEtiqueta);
        selectComentario(listaComentario);
        selectArticulo(listaArticulo,listaUsuario,listaEtiqueta,listaComentario);
    }
    public Usuario login(String username,String password) throws SQLException {
        String select="SELECT * FROM USUARIO WHERE USERNAME='"+username+"' AND PASSWORD='"+ password+"'";
        //System.out.println(select);
        ResultSet r;
        Usuario u=null;
            r = conexion.createStatement().executeQuery(select);
            while(r.next()){
                if(r.getString("USERNAME").isEmpty()){
                    return null;
                }
                else {
                    u= new Usuario();
                    u.setUsername(r.getString("USERNAME"));
                    u.setPassword(r.getString("PASSWORD"));
                    u.setNombre(r.getString("NOMBRE"));
                    u.setAdministrador(r.getBoolean("ADMINISTRADOR"));
                    u.setAutor(r.getBoolean("AUTOR"));
                    u.setAbout(r.getString("ABOUT"));
                }
            }
        return u;
    }


    public void selectArticulo(ArrayList<Articulo> lista,ArrayList<Usuario> listaUsuario, ArrayList<Etiqueta> listaEtiqueta, ArrayList<Comentario> listaComentario) throws SQLException {
        //lista = new ArrayList<Articulo>();
        String select="SELECT * from ARTICULO ORDER BY DATE";
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            long id= r.getLong("ID");
            String titulo = r.getString("TITULO");
            String cuerpo = r.getString("CUERPO");
            String autor = r.getString("AUTOR");
            String quote;
            String qname;
            if(r.getString("QUOTE")==null) {
                quote="";
                qname="";
            }
            else {
                quote = r.getString("QUOTE");
                qname = r.getString("QNAME");
            }
            Date fecha = r.getDate(5);
            Usuario u = new Usuario();
            for(int i=0;i<listaUsuario.size();i++){
                if(listaUsuario.get(i).getUsername().equals(autor)){
                    u=listaUsuario.get(i);
                    break;
                }
            }
            Articulo a = new Articulo(id,titulo,cuerpo,u,fecha,quote,qname);
            lista.add(a);
        }

        for(int i=0;i<lista.size();i++){
            for(int j=0; j<listaEtiqueta.size();j++){
                if(listaEtiqueta.get(j).getIdArticulo()== lista.get(i).getId())
                    lista.get(i).getListaEtiquetas().add(listaEtiqueta.get(j));
            }
        }
        for(int i=0;i<lista.size();i++) {
            for (int j = 0; j < listaComentario.size(); j++) {
                if (listaComentario.get(j).getArticuloid()==lista.get(i).getId())
                    lista.get(i).getListaComentarios().add(listaComentario.get(j));
            }
        }
    }

    public void selectUsuario(ArrayList<Usuario> lista) throws SQLException {
        //lista = new ArrayList<>();
        String select="SELECT * FROM USUARIO";
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            lista.add(new Usuario(r.getString("USERNAME"),r.getString("NOMBRE"),r.getString("PASSWORD"),r.getBoolean("ADMINISTRADOR"),r.getBoolean("AUTOR"),r.getString("ABOUT")));
        }
    }

    public void selectEtiqueta(ArrayList<Etiqueta> lista) throws SQLException {
        //lista = new ArrayList<>();
        String select="SELECT * FROM ETIQUETA";
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            lista.add(new Etiqueta(r.getInt("ID"),r.getString("ETIQUETA"),r.getLong("ARTICULO")));
        }
    }

    public void selectUpdatedEtiqueta(ArrayList<Etiqueta> lista, String id) throws SQLException {
        //lista = new ArrayList<>();
        String select="SELECT * FROM ETIQUETA WHERE ARTICULO="+id;
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            lista.add(new Etiqueta(r.getInt("ID"),r.getString("ETIQUETA"),r.getLong("ARTICULO")));
        }
    }

    public void selectComentario(ArrayList<Comentario> lista) throws SQLException {
       // lista = new ArrayList<>();
        String select="SELECT * FROM COMENTARIO";
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            lista.add(new Comentario(r.getLong("ID"),r.getString("COMENTARIO"),r.getString("USUARIO"),r.getLong("ARTICULO")));
        }
    }

    public int count(String tabla) throws SQLException {

        String select="SELECT COUNT(*) FROM "+tabla;
        ResultSet r= conexion.createStatement().executeQuery(select);
        int i=0;
        while(r.next()){
            i=Integer.parseInt(r.getString("COUNT(*)"));
        }
        return i;
    }
    public void close() throws SQLException {
        conexion.close();
    }

}
