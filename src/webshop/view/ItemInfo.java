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

    public List<HashMap<String, String>> getItems() {
        return items;
    }
}
