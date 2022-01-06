package engineClasses;

public class Transaction {

private String time;
private int stockAmount;
private int price;
private int totalAmount;
private String buyUser;
private String sellUser;




public Transaction(String time,int amount,int price,int total,String buyUser,String sellUser)
{
    this.time=time;
    this.stockAmount=amount;
    this.price=price;
    this.totalAmount=total;
    this.buyUser=buyUser;
    this.sellUser=sellUser;
}

    public int getPrice() { return price; }
    public int getStockAmount() {
        return stockAmount;
    }
    public int getTotalAmount() {
        return totalAmount;
    }
    public String getTime() {
        return time;
    }
    public String getBuyUser() { return buyUser; }
    public String getSellUser() { return sellUser; }
}
