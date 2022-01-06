package engineClasses;

public class Item {

    private String symbol;
    private int quantity;



    public Item(String symbol,int quantity) {
        this.symbol=symbol;
        this.quantity=quantity;

    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() { return quantity; }
     public void changeQuantity(int num) {this.quantity=this.quantity-num; }
}
