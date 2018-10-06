package webshop.bl.accounttype;

import webshop.bl.Item;
import webshop.bl.ShoppingCart;

import java.util.List;

public class UserAccount extends Account {

    private ShoppingCart shoppingCart;

    public UserAccount(int uid) {
        super(uid);
        shoppingCart = new ShoppingCart();
    }

    public boolean addItemToShoppingCart(Item item){
        return shoppingCart.add(item);
    }

    public boolean removeItemFromShoppingCart(Item item){
        return shoppingCart.remove(item);
    }

    public List<Item> viewShoppingCart(){
        return shoppingCart.getItems();
    }

    @Override
    public String getUserInfo() {
        return String.format("UserAccount[userId:%d]", getUid());
    }
}
