package dtos;

import engineClasses.Item;

public class ItemDto {
    private String symbol;
    private int amount;
    private int currentPrice;

    public ItemDto(String symbol, int amount,int price) {
        this.symbol=symbol;
        this.amount=amount;
        this.currentPrice=price;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }
}
