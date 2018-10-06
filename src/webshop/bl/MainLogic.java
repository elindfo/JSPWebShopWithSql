package webshop.bl;

import webshop.bl.accounttype.Account;
import webshop.bl.accounttype.AdminAccount;
import webshop.bl.accounttype.UserAccount;
import webshop.db.DBUserManager;

import java.util.ArrayList;
import java.util.List;

public class MainLogic {

    private List<Account> currentlyLoggedIn;

    public MainLogic(){
        currentlyLoggedIn = new ArrayList<>();
    }

    public boolean tryLogin(String name, String password){
        boolean isAuthenticated = DBUserManager.authenticate(name, password);
        if(isAuthenticated){
            int uid = DBUserManager.getUserId(name);
            int uLevel = DBUserManager.getUserLevel(uid);
            switch(uLevel){
                case 1: {
                    currentlyLoggedIn.add(new UserAccount(uid));
                    break;
                }
                case 2: {
                    currentlyLoggedIn.add(new AdminAccount(uid));
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        return isAuthenticated;
    }

    public boolean tryLogoff(String name){
        return true;
    }


}
