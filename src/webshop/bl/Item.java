package webshop.bl;

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
}
