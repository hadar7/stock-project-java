package engineClasses;

import dtos.AddDto;

import java.util.List;

public abstract class CommerceOrdinance  implements Comparable<CommerceOrdinance>{

    private int amount;
    private int limitPrice;
    private String stockSymbol;
    private String time;
    private String userName;
    private int type;//MKT OR LMT


    public CommerceOrdinance(int amount,int limit,String symbol,String time,String userName, int type) {
        this.amount=amount;
        this.limitPrice=limit;
        this.stockSymbol=symbol;
        this.time=time;
        this.userName=userName;
        this.type=type;
    }
    @Override
    public int compareTo(CommerceOrdinance other) {
        if (other.getLimitPrice()==this.getLimitPrice())
            return 0;
        if (other.getLimitPrice()>this.getLimitPrice())
            return 1;
        else
            return -1;
    }
    public abstract AddDto findOrdinance(Stock stock, int type,Users users);
    public abstract AddDto addToCommerceOrdinanceList(Stock stock, AddDto success, boolean allSell, int type, List<Notification> res) ;
    public String getTime() { return time; }
    public int getAmount() { return amount; }
    public int getLimitPrice() { return limitPrice; }
    public String getStockSymbol() { return stockSymbol; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setLimitPrice(int limitPrice) { this.limitPrice = limitPrice; }
    public String getUserName() { return userName; }
    public int getType() { return type; }
    public abstract boolean isFOKok(Stock stock,Users users);
}


