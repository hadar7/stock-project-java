package dtos;

import engineClasses.Stock;

public class StockDto {
    private String symbol;
    private  String company;
    private int price;
    private int numTransactions;
    private int sumTransactions;

    private CommerceOrdinanceDto[] sell;
    private CommerceOrdinanceDto [] buy;
    private TransactionDto[] transaction;
    private int totalBuy;
    private int totalSell;

    public StockDto(Stock s) {
        this.symbol = s.getSymbol();
        this.company = s.getCompany();
        this.price = s.getPrice();
        this.numTransactions = s.getNumTransactions();
        this.sumTransactions = s.getSumTransactions();
        this.buy=s.getBuyDto();
        this.sell=s.getSellDto();
        this.transaction=s.getTransactionsDto();
    }
    public void setTotals() {
        int total=0;
        for (CommerceOrdinanceDto c:buy) {
            total = total + (c.getLimitPrice() * c.getAmount());
        }
        this.totalBuy=total;
        total=0;
        for (CommerceOrdinanceDto c:sell) {
         total=total+(c.getLimitPrice()*c.getAmount());
        }
        this.totalSell=total;
    }
    public CommerceOrdinanceDto[] getBuy() { return buy;}
    public CommerceOrdinanceDto[] getSell() { return sell; }
    public TransactionDto[] getTransaction() { return transaction; }
    public String getSymbol() { return symbol; }
    public int getNumTransactions() { return numTransactions; }
    public int getSumTransactions() { return sumTransactions; }
    public int getPrice() { return price; }
    public String getCompany() { return company; }
    public int getTotalBuy() { return totalBuy; }
    public int getTotalSell() { return totalSell; }
}
