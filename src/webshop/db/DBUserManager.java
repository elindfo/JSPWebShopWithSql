package webshop.db;

public class DBUserManager {

    private DBManager dbManager;

    public DBUserManager(){
        this.dbManager = DBManager.getInstance();
    }

    public boolean authenticate(String username, String password){
        return true;
    }

    public boolean addUser(String username, String password){
        return true;
    }
}
