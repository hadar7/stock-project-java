package engineClasses;

import dtos.AddDto;
import dtos.CommerceOrdinanceDto;
import dtos.StockDto;
import scheme.genreteClasses.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {

   private String name;
   private List<Item> userStocks;
   private boolean isAdmin;
   private List<Move> moves;
   private int balance;
   private List<Notification> notificationList;


   public User(String name, boolean isAdmin) {
      this.userStocks = new ArrayList<>();
      this.moves=new ArrayList<>();
      this.name = name;
     this.isAdmin=isAdmin;
     this.balance=0;
     this.notificationList=new ArrayList<>();
   }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    public void addNotification(Notification toAdd) {
       this.notificationList.add(toAdd);
    }
    public List<Move> getMoves() { return moves; }
    public void addMove(Move move) {
        moves.add(move);
    }
    public void addMove(int type,int amount ,String symbol) {
        String time= DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        int balanceAfter=this.balance+amount;
        Move newMove=new Move(type,null,time,amount,this.balance,balanceAfter);
        moves.add(newMove);
        this.balance=this.balance+amount;

    }
    public  synchronized  void addNewHolding(String symbol ,int amount){
       Item item = new Item(symbol,amount);
       userStocks.add(item);
    }
    public List<Item> addHoldings(RseHoldings holdings, RseStocks rseStocks) throws Exception {
    List<Item> toAddList = new ArrayList<>();
        for (RseItem toAdd : holdings.getRseItem()) {
            String symbol = toAdd.getSymbol();
            int amount= toAdd.getQuantity();
            if (symbol == null)
                throw new Exception("File error - there is an empty symbol in the holdings");
            for (int i = 0; i < symbol.length(); i++) {
                char c = symbol.charAt(i);
                if ((c < 'A' || c > 'Z')) {
                    throw new Exception("File error - there is not valid symbol in the holdings");
                }
            }
            if ((isHoldingExistInFile(symbol.toUpperCase(),toAddList)))
                throw new Exception("File error - there is two same symbols in the file");
            if (amount < 0)
                throw new Exception("File error - there is a negative amount");
            if (!checkIfExistInRseStocks(rseStocks, toAdd)) {
                throw new Exception("one of your holdings doesnt exist in the file");
            }
            Item ItemtoAdd=new Item(symbol,amount);
            toAddList.add(ItemtoAdd);
        }
        return toAddList;

   }
    public boolean isHoldingExistInFile(String symbol,List<Item> list) {
        boolean isExist=false;
        for (Item i : list) {
            if (i.getSymbol().equals(symbol)) {
                isExist=true;
                break;
            }
        }
        return isExist;
    }
   public boolean checkIfExistInRseStocks(RseStocks rseStocks,RseItem item) {
       boolean isExist=false;
       for (RseStock stock : rseStocks.getRseStock()) {
           if (stock.getRseSymbol().equals(item.getSymbol())) {
               isExist=true;
               break;
           }
       }
       return isExist;
   }
    public int howMuchAvaliableForSell(StockDto toCheck) {
        int amountOnSell = 0;
        for (CommerceOrdinanceDto s : toCheck.getSell()) {
            if (this.name.equals(s.getUserName())) {
                amountOnSell += s.getAmount();
            }
        }
        Item item=getItemByName(toCheck.getSymbol());
        return item.getQuantity()-amountOnSell;
    }

    public Item getItemByName(String name){
      Item res=null;
      for (Item i: userStocks){
         if (i.getSymbol().equals(name)) {
            res=i;
         }
      }
      return res;
   }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getBalance() { return balance; }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Item> getUserStocks() {
      return userStocks;
   }

    public void setUserStocks(List<Item> userStocks) {
        this.userStocks = userStocks;
    }

    public String getName() {
      return name;
   }

    public List<Notification> getNotificationList() {
        return notificationList;
    }
     public void cleanNotificationList() {
         this.notificationList.clear();
     }
}

