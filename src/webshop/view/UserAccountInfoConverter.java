package webshop.view;

import webshop.bl.Item;
import webshop.bl.UserAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserAccountInfoConverter {

    private UserAccountInfoConverter() {}

    public static List<HashMap<String, String>> convertListToUserAccountInfoList(List<UserAccount> list){
        HashMap<String, String> hashMap;
        List<HashMap<String, String>> items = new ArrayList<>();
        for(int i = 0 ; i < list.size(); i++){
            hashMap = new HashMap<>();
            hashMap.put("uid", String.valueOf(list.get(i).getUid()));
            hashMap.put("uname", list.get(i).getUname());
            hashMap.put("ulevel", String.valueOf(list.get(i).getLevel()));
            items.add(hashMap);
        }
        return items;
    }

    public static List<UserAccount> convertHashMapListToUserAccountList(List<HashMap<String, String>> list){
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        for(HashMap<String, String> userAccount : list){
            UserAccount newAccount = new UserAccount(
                    Integer.valueOf(userAccount.get("uid")),
                    userAccount.get("uname"),
                    Integer.valueOf(userAccount.get("ulevel"))
            );
            userAccounts.add(newAccount);
        }
        return userAccounts;
    }
}
