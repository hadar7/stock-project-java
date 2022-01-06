package dtos;

import engineClasses.CommerceOrdinance;

public class CommerceOrdinanceDto {

    private int amount;
    private int limitPrice;
    private String stockSymbol;
    private String time;
    private String userName;
    private int type;//MKR OT LMT

    public CommerceOrdinanceDto(CommerceOrdinance c) {
        this.amount=c.getAmount();
        this.limitPrice=c.getLimitPrice();
        this.stockSymbol=c.getStockSymbol();
        this.time=c.getTime();
        this.userName=c.getUserName();
        this.type= c.getType();
    }
    public String getStockSymbol() {
        return stockSymbol;
    }
    public int getAmount() {
        return amount;
    }
    public String getTime() {
        return time;
    }
    public int getLimitPrice() { return limitPrice; }
    public int getType() { return type; }
    public String getUserName() { return userName; }
}

