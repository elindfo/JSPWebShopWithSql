package webshop.db;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//https://stackoverflow.com/questions/10006165/converting-string-to-character-array-in-java/10006225

public class DBUserManager {

    //TODO Investigate synchronize

    private DBManager dbManager;

    public DBUserManager(){
        this.dbManager = DBManager.getInstance();
    }

    public boolean authenticate(String username, String password){
        return true;
    }

    public boolean addUser(String username, String password){
        try{

            //TODO Password hashing
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); //https://stackoverflow.com/questions/2624192/good-hash-function-for-strings
            byte[] hashBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            //https://www.mkyong.com/java/java-sha-hashing-example/
            StringBuilder encryptedPassword = new StringBuilder();
            for(byte b : hashBytes){
                encryptedPassword.append(String.format("%02x", b));
            }

            System.out.println("Password: " + password);
            System.out.println("Encrypted: " + encryptedPassword);

            dbManager.getConnection().setAutoCommit(false); //Initiate transaction

            StringBuffer addUserString = new StringBuffer();
            addUserString.append("INSERT INTO user (uname, hashedpw) ");
            addUserString.append("VALUES (?, ?);");

            PreparedStatement smt = dbManager.getConnection().prepareStatement(addUserString.toString());

            smt.setString(1, username);
            smt.setString(2, encryptedPassword.toString());
            smt.execute();
            dbManager.getConnection().commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception: Unable to add user to DB");
            System.err.println("Error Code: " + e.getErrorCode());
            if(dbManager.getConnection() != null){
                try {
                    dbManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception: Unable to find hashing algorithm");
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
