package engineClasses;

import dtos.AddDto;
import dtos.BigDto;
import dtos.StockDto;
import dtos.TransactionDto;
import scheme.genreteClasses.RizpaStockExchangeDescriptor;
import scheme.genreteClasses.RseHoldings;
import scheme.genreteClasses.RseStocks;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Engine implements EngineInter {
    Stocks stocks;
    Users users;
    public Engine() {
        this.stocks=new Stocks();
        this.users=new Users();
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public StockDto[] showAllStocks() {
        StockDto[] res=stocks.showAll();
        return res;
    }
    @Override
    public StockDto showStockDto(String name) throws Exception {
        StockDto res = stocks.getOneStock(name);
        return res;
    }
    public TransactionDto[] showStockTransDto(String name) throws Exception {
        String theName = name.toUpperCase();
        Stock s = stocks.getAllStocks().get(theName);
        if (s == null) {
            throw new Exception("the stock doesnt exist");
        } else {
            return s.getTransactionsDto();
        }
    }
    public Stocks addAllStocksFromXml(RseStocks stocksFromXml) throws Exception {
           Stocks fromXml = new Stocks();
           fromXml.addStocks(stocksFromXml);
           return fromXml;
    }

    public List<Item> addHoldingsforUser(RseHoldings holdings, User user, RseStocks rseStocks) throws Exception {
        return user.addHoldings(holdings,rseStocks);
    }
    public void loadDataFromXml(InputStream inputStream,String userName) throws Exception {

        RizpaStockExchangeDescriptor genereteEngine=null;
        genereteEngine = deserializeFrom(inputStream);
        User user= users.getUserByName(userName);
        List<Item> items =addHoldingsforUser(genereteEngine.getRseHoldings(),user, genereteEngine.getRseStocks());
        Stocks toAdd=addAllStocksFromXml(genereteEngine.getRseStocks());
        if (!items.isEmpty() && !toAdd.getAllStocks().isEmpty()) {
            stocks.addFileStocksToAllStocks(toAdd);
            user.setUserStocks(items);
        }
    }
    public RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc=JAXBContext.newInstance("scheme.genreteClasses");
        Unmarshaller u=jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }
    public synchronized AddDto addNewOrdinance(int direction, String symbol, int amount, int limit, int type, String userName) throws Exception {
        String theSymbol=symbol.toUpperCase();
        Stock myStock=stocks.getAllStocks().get(theSymbol);
        if (limit==-1) {//if is MKT
            limit=myStock.getPrice();
        }
        String time=DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        CommerceOrdinance ordinance =null;
        if (direction==1)
            ordinance= new CommerceOrdinanceBuy(amount,limit,theSymbol,time,userName, type);
        if (direction==0)
            ordinance=new CommerceOrdinanceSell(amount,limit,theSymbol,time,userName,type);
         return ordinance.findOrdinance(myStock,type,users);
    }
    public BigDto getAllCommands() {
        BigDto res=new BigDto(stocks.getAllStocks().size());
        res.makeDto(stocks);
        return res;
    }
    public Stocks getStocks() { return stocks; }
}

