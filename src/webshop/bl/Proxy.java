package webshop.bl;

import webshop.db.DBItemManager;
import webshop.db.DBUserManager;

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
        for(webshop.bl.Item.Category c : webshop.bl.Item.Category.values()){
            categories.add(c.toString());
        }
        return categories;
    }

    public static boolean addUser(String username, String password, int level){
        return DBUserManager.addUser(username, password, level);
    }

    public static List<HashMap<String, String>> findByCategory(webshop.bl.Item.Category category){
        return Item.convertListToItemInfoList(DBItemManager.findByCategory(category));
    }

    public static List<HashMap<String, String>> findAllItems(){
        return Item.convertListToItemInfoList(DBItemManager.findAllItems());
    }

    public static boolean placeOrder(ArrayList<HashMap<String, String>> shoppingCart, int uid){
        List<webshop.bl.Item> order = new ArrayList<>(Item.convertHashMapListToItemList(shoppingCart));
        return DBItemManager.placeOrder(order, uid);
    }

    public static int getUserId(String username){
        return DBUserManager.getUserId(username);
    }

    public static void main(String[] args) {
        for(String s : Proxy.getCategories()){
            System.out.println(s);
        }
    }

    public static int getUserLevel(int uid) {
        return DBUserManager.getUserLevel(uid);
    }

    public static List<HashMap<String, String>> findAllUsers() {
        return UserAccount.convertListToUserAccountInfoList(DBUserManager.getAllUsers());
    }

    public static boolean setUserLevel(int uid, int ulevel) {
        return DBUserManager.setUserLevel(uid, ulevel);
    }

    public static int[] getOrderIds() {
        return DBItemManager.getAllOrderIds().stream().mapToInt(i->i).toArray();
    }

    public static List<HashMap<String, String>> getOrder(int oid) {
        return Order.convertListToOrderInfoList(DBItemManager.getOrder(oid));
    }

    public static boolean packOrder(int oid) {
        return DBItemManager.packOrder(oid);
    }

    public static boolean addItem(String name, String quantity, String price, String category) {
        return DBItemManager.addItem(name, Double.parseDouble(price), Integer.parseInt(quantity), Item.convertStringToCategory(category));
    }

    public static HashMap<String, String> findItemById(String iid) {
        Item item = DBItemManager.findById(Integer.parseInt(iid));
        HashMap<String, String> foundItem = new HashMap<>();
        if(item == null){
            return null;
        }
        foundItem.put("itemId", String.valueOf(item.getId()));
        foundItem.put("name", item.getName());
        foundItem.put("category", item.getCategory().toString());
        foundItem.put("price", String.valueOf(item.getPrice()));
        foundItem.put("quantity", String.valueOf(item.getQty()));
        return foundItem;
    }

    public static boolean updateItem(String iid, String name, String quantity, String price, String category) {
        int itemId = Integer.parseInt(iid);
        double itemPrice = Double.parseDouble(price);
        int itemQuantity = Integer.parseInt(quantity);
        Item.Category itemCategory = Item.convertStringToCategory(category);
        return DBItemManager.update(itemId, name, itemPrice, itemQuantity, itemCategory);
    }
}
