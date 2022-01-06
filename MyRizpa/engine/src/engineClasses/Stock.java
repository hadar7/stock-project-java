package engineClasses;

import dtos.CommerceOrdinanceDto;
import dtos.TransactionDto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Stock {
    private String symbol;
    private String company;
    private int price;
    private int numTransactions;
    private int sumTransactions;
    private List<Transaction> Transactions;
    private List<CommerceOrdinanceSell> sellOrdinances;
    private List<CommerceOrdinanceBuy> buyOrdinances;


    public Stock(String symbol, String company, int price) {
        this.symbol = symbol;
        this.company = company;
        this.price = price;
        this.numTransactions = 0;
        this.sumTransactions = 0;
        this.Transactions = new ArrayList<>();
        this.buyOrdinances = new LinkedList<>();
        this.sellOrdinances = new LinkedList<>();
    }

    public Stock(Stock other) {
        this.symbol = other.symbol;
        this.price = other.price;
        this.numTransactions = other.numTransactions;
        this.sumTransactions = other.sumTransactions;
        this.company = other.company;
        this.Transactions = other.Transactions;
        this.sellOrdinances = other.sellOrdinances;
        this.buyOrdinances = other.buyOrdinances;
    }

    public void increaseNumTransactions() {
        this.numTransactions++;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() { return price; }
    public int getSumTransactions() {
        return sumTransactions;
    }
    public int getNumTransactions() {
        return numTransactions;
    }
    public String getCompany() {
        return company;
    }
    public String getSymbol() {
        return symbol;
    }
    public List<Transaction> getTransactions() {
        return Transactions;
    }
    public List<CommerceOrdinanceBuy> getBuyOrdinance() {
        return buyOrdinances;
    }
    public List<CommerceOrdinanceSell> getSellOrdinances() {
        return sellOrdinances;
    }
    public TransactionDto[] getTransactionsDto() {
        TransactionDto[] transactionDtos = new TransactionDto[numTransactions];
        for (int i = 0; i < numTransactions; i++) {
            Transaction toAdd = Transactions.get(i);
            transactionDtos[i] = new TransactionDto(toAdd);
        }
        return transactionDtos;
    }
    public CommerceOrdinanceDto[] getSellDto() {
        CommerceOrdinanceDto[] sell = new CommerceOrdinanceDto[sellOrdinances.size()];
        for (int i = 0; i < sellOrdinances.size(); i++) {
            CommerceOrdinance toAdd = sellOrdinances.get(i);
            sell[i] = new CommerceOrdinanceDto(toAdd);
        }
        return sell;
    }
    public CommerceOrdinanceDto[] getBuyDto() {
        CommerceOrdinanceDto[] buy = new CommerceOrdinanceDto[buyOrdinances.size()];
        for (int i = 0; i < buyOrdinances.size(); i++) {
            CommerceOrdinance toAdd = buyOrdinances.get(i);
            buy[i] = new CommerceOrdinanceDto(toAdd);
        }
        return buy;
    }
    public void setSumTransactions(int sumTransactions) {
        this.sumTransactions += sumTransactions;
    }
}

