package webshop.bl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item {

    public enum Category{
        CLOTHING, SPORTS, MUSIC, INSTRUMENTS, VEHICLES, FOOD, EVERYTHING
    }

    private int id;
    private String name;
    private double price;
    private int qty;
    private Category category;

    public Item(int id, String name, double price, int qty, Category category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.category = category;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Category getCategory(){
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public static Category convertStringToCategory(String category){
        category = category.toUpperCase();
        switch (category){
            case "CLOTHING": {
                return Category.CLOTHING;
            }
            case "SPORTS": {
                return Category.SPORTS;
            }
            case "MUSIC": {
                return Category.MUSIC;
            }
            case "INSTRUMENTS": {
                return Category.INSTRUMENTS;
            }
            case "VEHICLES": {
                return Category.VEHICLES;
            }
            case "FOOD": {
                return Category.FOOD;
            }
            default: return Category.EVERYTHING;
        }
    }

    @Override
    public String toString(){
        return String.format("Item[id:%d, name:%s, price:%.2f, quantity:%d, category:%s]", id, name, price, qty, category.toString());
    }

    public static List<HashMap<String, String>> convertListToItemInfoList(List<Item> list){
        HashMap<String, String> hashMap;
        List<HashMap<String, String>> items = new ArrayList<>();
        for(int i = 0 ; i < list.size(); i++){
            hashMap = new HashMap<>();
            hashMap.put("itemId", String.valueOf(list.get(i).getId()));
            hashMap.put("name", list.get(i).getName());
            hashMap.put("category", list.get(i).getCategory().toString());
            hashMap.put("price", String.valueOf(list.get(i).getPrice()));
            hashMap.put("quantity", String.valueOf(list.get(i).getQty()));
            items.add(hashMap);
        }
        return items;
    }

    public static List<Item> convertHashMapListToItemList(List<HashMap<String, String>> list){
        ArrayList<Item> order = new ArrayList<>();
        for(HashMap<String, String> item : list){
            Item newItem = new Item(
                    Integer.valueOf(item.get("itemId")),
                    item.get("name"),
                    Double.valueOf(item.get("price")),
                    Integer.valueOf(item.get("quantity")),
                    Item.convertStringToCategory(item.get("category")));
            order.add(newItem);
        }
        return order;
    }
}
