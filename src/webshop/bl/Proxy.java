package webshop.bl;

import webshop.db.DBItemManager;
import webshop.db.DBUserManager;

import java.util.ArrayList;
import java.util.List;

public class Proxy {

    private Proxy(){
    }

    public static boolean authenticateUser(String username, String password){
        return DBUserManager.authenticate(username, password);
    }

    public static boolean addUser(String username, String password){
        return DBUserManager.addUser(username, password);
    }

    public static boolean addItem(Item item){
        return DBItemManager.addItem(item.getName(), item.getPrice(), item.getQty(), item.getCategory());
    }

    public static List<Item> findByCategory(Item.Category category){
        return getDeepCopy(DBItemManager.findByCategory(category));
    }

    public static Item findById(int iid){
        Item item = DBItemManager.findById(iid);
        return new Item(item.getId(), item.getName(), item.getPrice(), item.getQty(), item.getCategory());
    }

    public static List<Item> findAllItems(){
        return getDeepCopy(DBItemManager.findAllItems());
    }

    private static List<Item> getDeepCopy(List<Item> items){
        //TODO fråga reine om deepCopy är OK?
        ArrayList<Item> copy = new ArrayList<>();
        for(Item i : items){
            Item newItem = new Item(i.getId(), i.getName(), i.getPrice(), i.getQty(), i.getCategory());
            copy.add(newItem);
        }
        return copy;
    }

}
