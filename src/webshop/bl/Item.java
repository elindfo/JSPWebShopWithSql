package webshop.bl;

public class Item {

    public enum category{
        SPORTS, ELECTRONICS, MUSIC
    }

    private String name;
    private double price;
    private int qty;

    public Item(String name, double price, int qty){
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName(){
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
