import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            conexion = DriverManager.getConnection("jdbc:h2:~/practica5", "sa", "1234");
        }
        return database;
    }

    public void insert(String[] params) throws SQLException {

        String insert="INSERT INTO ESTUDIANTES(MATRICULA,NOMBRE,APELLIDOS,TELEFONO) VALUES(";
        for(int i=0;i<params.length;i++){
            if(i!=params.length-1)
                insert+="'"+params[i]+"'"+",";
            else
                insert+="'"+params[i]+"'"+");";
        }
        conexion.createStatement().execute(insert);
    }

    public void delete(String Matricula) throws SQLException {
        String delete="DELETE FROM ESTUDIANTES WHERE MATRICULA="+"'"+Matricula+"'";
        conexion.createStatement().execute(delete);
    }

    public void update(String[] estudiante) throws SQLException {
        String update=String.format("UPDATE ESTUDIANTES SET NOMBRE = '%s', APELLIDOS = '%s', TELEFONO = '%s'" +
                        " WHERE MATRICULA = '%s'",
                estudiante[1], estudiante[2], estudiante[3], estudiante[0]);
        conexion.createStatement().execute(update);
    }

    public ArrayList<Estudiante> select() throws SQLException {
        ArrayList<Estudiante> lista = new ArrayList<Estudiante>();
        String select="SELECT * FROM ESTUDIANTES";
        ResultSet r= conexion.createStatement().executeQuery(select);
        while(r.next()){
            Estudiante e = new Estudiante();
            e.setMatricula(r.getString("matricula"));
            e.setNombre(r.getString("nombre"));
            e.setApellidos(r.getString("apellidos"));
            e.setTelefono(r.getString("telefono"));
            lista.add(e);
        }
        return lista;
    }
    public void close() throws SQLException {
        conexion.close();
    }

}
