package webshop.bl;

import webshop.db.DBUserManager;
import java.util.ArrayList;

public class MainLogic {

    public MainLogic(){

    }

    public boolean tryLogin(String name, String password){
        return DBUserManager.authenticate(name, password);
    }
}
