package dtos;

import engineClasses.Transaction;

public class TransactionDto {

    private String time;
    private int stockAmount;
    private int price;
    private int totalAmount;
    private String buyUser;
    private String sellUser;
    private boolean isAddToLineChart;

   public TransactionDto(Transaction t) {
        this.time=t.getTime();
        this.price=t.getPrice();
        this.stockAmount=t.getStockAmount();
        this.totalAmount=t.getTotalAmount();
        this.buyUser= t.getBuyUser();
        this.sellUser=t.getSellUser();
        this.isAddToLineChart=false;

    }

    public void setAddToLineChart(boolean addToLineChart) { isAddToLineChart = addToLineChart; }

    public boolean isAddToLineChart() { return isAddToLineChart; }
    public String getTime() { return time; }
    public int getTotalAmount() {
        return totalAmount;
    }
    public int getStockAmount() {
        return stockAmount;
    }
    public int getPrice() {
        return price;
    }
    public String getBuyUser() { return buyUser; }
    public String getSellUser() { return sellUser; }
}

