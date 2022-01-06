package engineClasses;

import scheme.genreteClasses.*;

import java.util.*;

public class Users {

   // List<User> users;
    Map<String,User> users;
    boolean isAdminExist=false;

    public  Users() { this.users=new HashMap<>(); }
    public void addUser(String name, boolean isAdmin) {
        User toAdd=new User(name, isAdmin);
        users.put(name,toAdd);
        if(isAdmin && !isAdminExist) {
            isAdminExist=true;
        }
    }
  /*  public void addUsers(RseUsers users, RseStocks stocks) throws Exception {
        for(RseUser toAdd: users.getRseUser()) {
            String name=toAdd.getName();

            if (name==null)
                throw new Exception("there is user with no name ");
            if (isUserExist(name))
                throw  new Exception("there are two same users");
            for (int i=0;i<name.length();i++) {
                char c = name.charAt(i);
                if (!((c >= 'a' && c <= 'z') || c == ' ')) {
                    throw new Exception(" there is not valid name");
                }
            }
            for (RseItem item:toAdd.getRseHoldings().getRseItem()) {
                if (item.getQuantity() <= 0) {
                    throw new Exception("there is quantity less from 0 or 0");
                }
                if (!checkIfStockExist(stocks,item)) {
                    throw new Exception("the holding of the user "+ name +" are not exist");
                }
            }
            User newUser=new User(name,toAdd.getRseHoldings());
            this.users.put(newUser.getName(), newUser);
        }
    }*/

    public boolean isAdminExist() {
        return isAdminExist;
    }

    public boolean isUserExist(String name) {
        return users.get(name) != null;
    }
    public List<Notification> updateUsers(int total,String time,String userToDec, String userToInc, String symbol,int amount, int price) {
        User toDec=getUserByName(userToDec);
        User toInc=getUserByName(userToInc);
        Item itemToDec= toDec.getItemByName(symbol);
        Item itemToInc=toInc.getItemByName(symbol);
        itemToDec.changeQuantity(amount);
        if (itemToDec.getQuantity()==0) {
            toDec.getUserStocks().remove(itemToDec);
        }
        if (itemToInc==null){
            Item toAdd=new Item(symbol,amount);
            toInc.getUserStocks().add(toAdd);
        }
        else {
            itemToInc.changeQuantity(amount);
        }
        int Bbefore1=toDec.getBalance();
        toDec.setBalance(Bbefore1+total);
        Move sell=new Move(2,symbol,time,amount,Bbefore1,toDec.getBalance());
        toDec.addMove(sell);
        Notification not1=new Notification(amount+" stocks of  "+symbol+" sold to "+userToInc+" in rate "+price+" , total: "+total,toDec);
        int Bbefore2= toInc.getBalance();
        toInc.setBalance(Bbefore2-total);
        Move buy=new Move(1,symbol,time,amount,Bbefore2,toInc.getBalance());
        toInc.addMove(buy);
        Notification not2=new Notification(amount+" stocks of  "+symbol+" bought from "+userToDec+" in rate "+price+" , total: "+total,toInc);
        List<Notification> res=new ArrayList<>();
        res.add(not1);
        res.add(not2);
        return res;
    }
    public User getUserByName(String userName){
        return users.get(userName);
    }

    /*public boolean checkIfStockExist(RseStocks stocks,RseItem item) {
        for (RseStock stock : stocks.getRseStock()) {
            if (stock.getRseSymbol().equals(item.getSymbol())) {
                return true;
            }
        }
        return false;
    }*/

    public Map<String,User> getUsers() {
        return users;
    }
}
