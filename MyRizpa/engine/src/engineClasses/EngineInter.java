package engineClasses;

import dtos.AddDto;
import dtos.BigDto;
import dtos.StockDto;
import dtos.TransactionDto;
import scheme.genreteClasses.RizpaStockExchangeDescriptor;
import scheme.genreteClasses.RseHoldings;
import scheme.genreteClasses.RseStocks;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.List;

public interface EngineInter
{

    public StockDto[] showAllStocks();
    public Users getUsers() ;
    public Stocks getStocks();
    public StockDto showStockDto(String name) throws Exception ;
    public TransactionDto[] showStockTransDto(String name) throws Exception ;
    public Stocks addAllStocksFromXml(RseStocks stocksFromXml) throws Exception;
    public RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException ;
    public AddDto addNewOrdinance(int direction, String symbol, int amount, int limit, int type,String userName) throws Exception;
    public void loadDataFromXml(InputStream inputStream,String username) throws Exception;
    public BigDto getAllCommands();
    public List<Item> addHoldingsforUser(RseHoldings holdings, User user, RseStocks rseStocks) throws Exception;



}