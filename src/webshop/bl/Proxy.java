package webshop.bl;

import webshop.db.DBItemManager;
import webshop.db.DBUserManager;

import java.util.ArrayList;
import java.util.List;

public class Proxy {
    private static DBUserManager dbUserManager;
    private static DBItemManager dbItemManager;

    private Proxy(){
        this.dbUserManager = new DBUserManager();
        this.dbItemManager = new DBItemManager();
    }

    public static boolean authenticateUser(String username, String password){
        return dbUserManager.authenticate(username, password);
    }

    public static boolean addUser(String username, String password){
        return dbUserManager.addUser(username, password);
    }

    public static boolean addItem(Item item){
        return dbItemManager.addItem(item.getName(), item.getPrice(), item.getQty(), item.getCategory());
    }

    public static List<Item> findByCategory(Item.Category category){
        return getDeepCopy(dbItemManager.findByCategory(category));
    }

    public static Item findById(int iid){
        Item item = dbItemManager.findById(iid);
        Item itemDeepCopy = new Item(item.getId(), item.getName(), item.getPrice(), item.getQty(), item.getCategory());
        return itemDeepCopy;
    }

    public static List<Item> findAllItems(){
        return getDeepCopy(dbItemManager.findAllItems());
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
