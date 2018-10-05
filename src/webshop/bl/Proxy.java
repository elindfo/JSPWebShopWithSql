package webshop.bl;

import webshop.db.DBItemManager;
import webshop.db.DBUserManager;
import webshop.view.ItemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Proxy {

    private Proxy(){
    }

    public static List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        for(Item.Category c : Item.Category.values()){
            categories.add(c.toString());
        }
        return categories;
    }

    public static boolean authenticateUser(String username, String password){
        return DBUserManager.authenticate(username, password);
    }

    public static boolean addUser(String username, String password, int level){
        return DBUserManager.addUser(username, password, level);
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

    public static List<HashMap<String, String>> findAllItems(){
        ItemInfo items = new ItemInfo();
        return items.convertListToItemInfoList(DBItemManager.findAllItems());
        //return getDeepCopy(DBItemManager.findAllItems());
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

    public static void main(String[] args) {
        for(String s : Proxy.getCategories()){
            System.out.println(s);
        }
    }

}
