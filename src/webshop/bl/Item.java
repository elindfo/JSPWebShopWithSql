package webshop.bl;

public class Item {

    public enum Category{
        SPORTS, ELECTRONICS, MUSIC
    }

    private String name;
    private double price;
    private int qty;
    private Category category;

    public Item(String name, double price, int qty, Category category){
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.category = category;
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
