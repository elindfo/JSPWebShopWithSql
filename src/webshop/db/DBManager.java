package webshop.db;

import javax.xml.transform.Result;
import java.sql.*;

public class DBManager {

    //http://www.vogella.com/tutorials/MySQLJava/article.html

    private static Connection connection = null;

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/webshop?" +
                    "user=eoj&password=abcd");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBManager(){}

    public static Connection getConnection(){
        return connection;
    }
}
