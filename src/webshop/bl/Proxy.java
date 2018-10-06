package webshop.bl;

import webshop.db.DBItemManager;
import webshop.db.DBUserManager;
import webshop.view.ItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Proxy {

    private static final MainLogic mainLogic = new MainLogic();

    private Proxy(){
    }

    public static boolean tryLogin(String username, String password){
        return mainLogic.tryLogin(username, password);
    }
    public static List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        for(Item.Category c : Item.Category.values()){
            categories.add(c.toString());
        }
        return categories;
    }

    public static boolean addUser(String username, String password, int level){
        return DBUserManager.addUser(username, password, level);
    }

    public static boolean addItem(Item item){
        return DBItemManager.addItem(item.getName(), item.getPrice(), item.getQty(), item.getCategory());
    }

    public static List<HashMap<String, String>> findByCategory(Item.Category category){
        ItemInfo items = new ItemInfo();
        return items.convertListToItemInfoList(DBItemManager.findByCategory(category));
    }

    public static Item findById(int iid){
        Item item = DBItemManager.findById(iid);
        return new Item(item.getId(), item.getName(), item.getPrice(), item.getQty(), item.getCategory());
    }

    public static List<HashMap<String, String>> findAllItems(){
        ItemInfo items = new ItemInfo();
        return items.convertListToItemInfoList(DBItemManager.findAllItems());
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
