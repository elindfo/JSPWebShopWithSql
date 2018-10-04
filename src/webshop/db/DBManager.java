package webshop.db;

import javax.xml.transform.Result;
import java.sql.*;

public class DBManager {

    //http://www.vogella.com/tutorials/MySQLJava/article.html

    private static Connection connection = null;

    private DBManager(){}

    public static Connection getConnection(){
        if(connection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("DBMANAGER: GETTING CONNECTION");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/webshop?" +
                        "user=eoj&password=abcd");
                System.out.println("DBMANAGER: CONNECTION SET");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
