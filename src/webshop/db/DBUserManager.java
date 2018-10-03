package webshop.db;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//https://stackoverflow.com/questions/10006165/converting-string-to-character-array-in-java/10006225

public class DBUserManager {

    //TODO Investigate synchronize

    private DBManager dbManager;

    public DBUserManager(){
        this.dbManager = DBManager.getInstance();
    }

    public boolean authenticate(String username, String password){

        try {
            String encryptedPassword = hashWithSHA256(password);

            dbManager.getConnection().setAutoCommit(false);

            String query = "SELECT count(*) FROM user WHERE hashedpw = ? AND uname = ?";

            PreparedStatement psmt = dbManager.getConnection().prepareStatement(query);

            psmt.setString(1, encryptedPassword);
            psmt.setString(2, username);

            ResultSet resultSet = psmt.executeQuery();

            boolean userFound = false;
            if(resultSet.next()){
                userFound = resultSet.getInt(1) != 0;
            }
            dbManager.getConnection().commit();
            return userFound;
        } catch (SQLException e) {
            System.out.println("SQLException");
            System.out.println("Error: " + e.getErrorCode());
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
                try{
                    dbManager.getConnection().setAutoCommit(true);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean addUser(String username, String password){
        try{
            String encryptedPassword = hashWithSHA256(password);

            dbManager.getConnection().setAutoCommit(false); //Initiate transaction

            String query = "INSERT INTO user (uname, hashedpw) VALUES (?, ?);";

            PreparedStatement psmt = dbManager.getConnection().prepareStatement(query);

            psmt.setString(1, username);
            psmt.setString(2, encryptedPassword);
            psmt.execute();
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

    private String hashWithSHA256(String s) throws NoSuchAlgorithmException{
        //TODO Check wether this is enough or if salt is to be used
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); //https://www.mkyong.com/java/java-sha-hashing-example/
        byte[] hashBytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder encryptedPassword = new StringBuilder();
        for(byte b : hashBytes){
            encryptedPassword.append(String.format("%02x", b));
        }
        return encryptedPassword.toString();
    }

    public static void main(String[] args) {
        DBUserManager dbUserManager = new DBUserManager();
        /*dbUserManager.addUser("erik", "hejsan");
        dbUserManager.addUser("joacim", "tjenare");
        dbUserManager.addUser("joacima", "tjenare");
        dbUserManager.addUser("ecke", "apa");
        dbUserManager.addUser("eckea", "adspa");*/
        System.out.println("Authenticated: " + dbUserManager.authenticate("ecke", "apa"));
    }
}
