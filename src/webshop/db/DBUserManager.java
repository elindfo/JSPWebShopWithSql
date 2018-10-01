package webshop.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//https://stackoverflow.com/questions/10006165/converting-string-to-character-array-in-java/10006225

public class DBUserManager {

    //TODO Investigate synchronized

    private DBManager dbManager;

    public DBUserManager(){
        this.dbManager = DBManager.getInstance();
    }

    public boolean authenticate(String username, String password){
        return true;
    }

    public boolean addUser(String username, String password){ //TODO Add insertion into all relevant tables
        try{
            dbManager.getConnection().setAutoCommit(false); //Initiate transaction

            StringBuffer addUserString = new StringBuffer();
            addUserString.append("INSERT INTO user (uname, hashedpw) ");
            addUserString.append("VALUES (?, ?);");

            PreparedStatement smt = dbManager.getConnection().prepareStatement(addUserString.toString());

            smt.setString(1, username);
            smt.setString(2, password);
            smt.execute();
            dbManager.getConnection().commit();
            return true;
        } catch (SQLException e) {
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("Sql exception");
            return false;
        } finally{
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        DBUserManager dbUserManager = new DBUserManager();
        dbUserManager.addUser("erik", "hejsan");
        dbUserManager.addUser("joacim", "tjenare");
        dbUserManager.addUser("joacima", "tjenare");
    }
}
