package webshop.view;

public class Iteminfo {

    private String name;
    private String price;

    public Iteminfo(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
