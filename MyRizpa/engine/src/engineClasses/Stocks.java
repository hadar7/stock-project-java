package engineClasses;

import dtos.StockDto;
import scheme.genreteClasses.RseStock;
import scheme.genreteClasses.RseStocks;

import java.util.*;

public class Stocks {
    private Map<String, Stock> allStocks;
    boolean isFirstTimeUploaded=true;


    public Stocks() {
        this.allStocks = new HashMap<>();
    }

    public Stocks(Stocks other) {
        this.allStocks = new HashMap<String, Stock>();
        for (String name : other.getAllStocks().keySet()) {
            Stock toInsert = new Stock(other.getAllStocks().get(name));
            this.allStocks.put(name, toInsert);
        }
    }

    public boolean isFirstTimeUploaded() { return isFirstTimeUploaded; }
    public String isStocksInTheFileOK(RseStocks stocks) throws Exception {
        for (RseStock toAdd : stocks.getRseStock()) {
            String symbol = toAdd.getRseSymbol();
            if (symbol == null)
                throw new Exception("File error - there is an empty symbol");
            for (int i = 0; i < symbol.length(); i++) {
                char c = symbol.charAt(i);
                if ((c < 'A' || c > 'Z')) {
                    throw new Exception("File error - there is not valid symbol");
                }
            }
            if (!(allStocks.get(symbol.toUpperCase()) == null))
                throw new Exception("File error - there is two same symbols in the file");
            if (isCompanyHaveStock(toAdd.getRseCompanyName()))
                throw new Exception("File Error - there is a company with two stocks");
            if (toAdd.getRseCompanyName() == null)
                throw new Exception("File error - there is an empty company name");
            if (toAdd.getRsePrice() < 0)
                throw new Exception("File error - there is a negative price");
        }
        return "true";
    }

    public void  addStocks(RseStocks stocks) throws Exception {
        for (RseStock toAdd : stocks.getRseStock()) {
            String symbol = toAdd.getRseSymbol();
            if (symbol==null)
                throw new Exception("File error - there is an empty symbol");
            for (int i=0;i<symbol.length();i++){
                char c=symbol.charAt(i);
                if ((c <'A' || c> 'Z')) {
                    throw new Exception("File error - there is not valid symbol");
                }
            }
            if (!(allStocks.get(symbol.toUpperCase()) == null))
                throw new Exception("File error - there is two same symbols in the file");
              if (isCompanyHaveStock(toAdd.getRseCompanyName()))
                throw new Exception("File Error - there is a company with two stocks");
             if (toAdd.getRseCompanyName()==null)
                 throw new Exception("File error - there is an empty company name");
             if (toAdd.getRsePrice()<0)
                 throw new Exception("File error - there is a negative price");
             else {
                Stock myStockToAdd = new Stock(symbol, toAdd.getRseCompanyName(), toAdd.getRsePrice());
                allStocks.put(symbol.toUpperCase(), myStockToAdd);
            }
        }
    }
    public void addFileStocksToAllStocks(Stocks fromXml){
        for (Stock toAdd: fromXml.allStocks.values()) {
                Stock exist=this.getStock(toAdd.getSymbol());
                if (exist!=null) {
                    toAdd.setPrice(exist.getPrice());
                    this.allStocks.put(toAdd.getSymbol(),toAdd);
                }
                else {
                    this.allStocks.put(toAdd.getSymbol(),toAdd);
                }
        }
    }
    public Map<String, Stock> getAllStocks() {
        return allStocks;
    }
    public boolean isCompanyHaveStock(String company) {
        for (Stock s : allStocks.values()) {
            if (s.getCompany().equals(company)) {
                return true;
            }
        }
        return false;
    }
    public StockDto[] showAll() {
        StockDto[] stockDtos = new StockDto[allStocks.size()];
        int i = 0;
        for (Stock s : allStocks.values()) {
            stockDtos[i] = new StockDto(s);
            i++;
        }
        return stockDtos;
    }

    public StockDto getOneStock(String name) throws Exception {
        String theName = name.toUpperCase();
        Stock s = allStocks.get(theName);
        if (s == null) {
            throw new Exception("the stock doesnt exist");
        } else {
            StockDto sd = new StockDto(s);
            return sd;
        }
    }
    public synchronized  void addNewStock(String symbol,String company,int price) throws Exception {
        for (Stock stock:allStocks.values()) {
            if (stock.getSymbol().equals(symbol)) {
                throw new Exception("this stock is already exist");
            }
            if  (stock.getCompany().equals(company)) {
                throw new Exception("this company is already exist");
            }
        }
        Stock newStock=new Stock(symbol, company, price) ;
        this.allStocks.put(symbol,newStock);
    }
    public Stock getStock(String name) {
        Stock res = null;
        for (Stock s:allStocks.values()) {
            if (s.getSymbol().equals(name))
                res=s;
        }
        return res;
    }
    public int getCurrentPrice(String symbol) {
        String theName = symbol.toUpperCase();
        Stock s = allStocks.get(theName);
        return s.getPrice();
    }
}


