package webshop.db;

import javax.xml.transform.Result;
import java.sql.*;

public class DBManager {

    //http://www.vogella.com/tutorials/MySQLJava/article.html

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

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

    public synchronized static void write(String query) throws SQLException{
        statement = connection.createStatement();
        //resultSet = statement.executeQuery(query);

    }

    public synchronized static void delete(String query){

    }

    public synchronized static void read(String query) throws  SQLException{
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
    }

    public synchronized static void update(String query){

    }

    public static void main(String[] args){
        try {
            DBManager.read("select * from user;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
