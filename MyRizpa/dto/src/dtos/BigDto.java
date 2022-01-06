package dtos;

import engineClasses.Stock;
import engineClasses.Stocks;

import java.util.ArrayList;
import java.util.List;

public class BigDto
{
    private List<StockDto> all;
    private int size;


    public BigDto(int size) {
        this.all=new ArrayList<>(size);
        this.size=size;
    }
   public void makeDto(Stocks stocks) {
        int i = 0;
        for (Stock s : stocks.getAllStocks().values()) {
            StockDto toAdd=new StockDto(s);
            toAdd.setTotals();
            all.add(i,toAdd);
            i++;
        }
    }
    public int getSize() {
        return size;
    }
    public List<StockDto> getAll() {
        return all;
    }


}
