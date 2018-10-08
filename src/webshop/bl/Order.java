package webshop.bl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    private List<Item> items;
    private int oid;

    public Order() {
        this.items = new ArrayList<>();
    }
    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Override
    public String toString(){
        return String.format("Order[oid:%d, items:%s]", oid, items);
    }

    public static List<HashMap<String, String>> convertListToOrderInfoList(Order order){
        HashMap<String, String> hashMap;
        List<HashMap<String, String>> items = new ArrayList<>();
        if(order == null){
            return items;
        }
        for(int i = 0 ; i < order.items.size(); i++){
            hashMap = new HashMap<>();
            hashMap.put("oid", String.valueOf(order.oid));
            hashMap.put("itemId", String.valueOf(order.items.get(i).getId()));
            hashMap.put("name", order.items.get(i).getName());
            hashMap.put("category", order.items.get(i).getCategory().toString());
            hashMap.put("price", String.valueOf(order.items.get(i).getPrice()));
            hashMap.put("quantity", String.valueOf(order.items.get(i).getQty()));
            items.add(hashMap);
        }
        return items;
    }

}
