package webshop.bl;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Item> items;

    public ShoppingCart(){
        items = new ArrayList<>();
    }

    public void clear(){
        items.clear();
    }

    public boolean add(Item item){
        return items.add(item);
    }

    public boolean remove(Item item){
        return items.remove(item);
    }

    public List<Item> getItems(){
        List<Item> cpy = new ArrayList<>();
        for(Item i : items){
            cpy.add(new Item(i.getId(), i.getName(), i.getPrice(), i.getQty(), i.getCategory()));
        }
        return cpy;
    }
}
