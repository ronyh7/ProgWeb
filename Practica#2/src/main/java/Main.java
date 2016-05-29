import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

/**
 * Created by Rony on 5/27/2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        staticFileLocation("/publico");
        get("/hello",(req,res)-> "Hello");

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
                getConnection("jdbc:h2:~/test", "sa", "1234");
        // add application code here
        conn.close();





    }
}
