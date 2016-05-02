package broodjes;

public class OrderLine {

    private final String buyer;
    private final String name;
    private final Currency price;

    public OrderLine (String buyer, String name, Currency price) {
        this.buyer = buyer;
        this.name = name;
        this.price = price;
    }
    
    public String toString() {
        return "["+buyer+"] buys ["+name+"] for ["+price+"]";
    }

    public Currency getPrice() {
        return price;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getName() {
        return name;
    }
    
}
