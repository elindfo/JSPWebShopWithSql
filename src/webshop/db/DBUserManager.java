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

    private DBUserManager(){}

    public static boolean authenticate(String username, String password){

        try {
            String encryptedPassword = hashWithSHA256(password);

            DBManager.getConnection().setAutoCommit(false);

            String query = "SELECT count(*) FROM user WHERE hashedpw = ? AND uname = ?";

            PreparedStatement psmt = DBManager.getConnection().prepareStatement(query);

            psmt.setString(1, encryptedPassword);
            psmt.setString(2, username);

            ResultSet resultSet = psmt.executeQuery();

            boolean userFound = false;
            if(resultSet.next()){
                userFound = resultSet.getInt(1) != 0;
            }
            DBManager.getConnection().commit();
            return userFound;
        } catch (SQLException e) {
            System.out.println("SQLException");
            System.out.println("Error: " + e.getErrorCode());
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception: Unable to find hashing algorithm");
            return false;
        } finally{
            if(DBManager.getConnection() != null){
                try{
                    DBManager.getConnection().setAutoCommit(true);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean addUser(String username, String password){
        try{
            String encryptedPassword = hashWithSHA256(password);

            DBManager.getConnection().setAutoCommit(false); //Initiate transaction

            String query = "INSERT INTO user (uname, hashedpw) VALUES (?, ?);";

            PreparedStatement psmt = DBManager.getConnection().prepareStatement(query);

            psmt.setString(1, username);
            psmt.setString(2, encryptedPassword);
            psmt.execute();
            DBManager.getConnection().commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception: Unable to add user to DB");
            System.err.println("Error Code: " + e.getErrorCode());
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception: Unable to find hashing algorithm");
            return false;
        } finally{
            if(DBManager.getConnection() != null){
                try {
                    DBManager.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String hashWithSHA256(String s) throws NoSuchAlgorithmException{
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
        DBUserManager.fill();
    }

    private static void fill(){
        DBUserManager.addUser("Joacim", "usling");
        DBUserManager.addUser("Erik", "glassfish_is_shait");
        DBUserManager.addUser("test", "test");
    }
}
