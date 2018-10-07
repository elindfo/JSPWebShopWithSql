package webshop.view;

import webshop.bl.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemInfo {
    private HashMap<String, String> hashMap;
    private List<HashMap<String, String>> items = null;

    public ItemInfo() {
        this.hashMap = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public List<HashMap<String, String>> convertListToItemInfoList(List<Item> list){
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

    public static List<Item> convertHashToItem(List<HashMap<String, String>> list){
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

    public List<HashMap<String, String>> getItems() {
        return items;
    }
}
