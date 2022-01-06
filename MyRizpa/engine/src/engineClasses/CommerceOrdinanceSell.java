package engineClasses;

import dtos.AddDto;
import dtos.TransactionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class CommerceOrdinanceSell  extends CommerceOrdinance {

    public CommerceOrdinanceSell(int amount, int limit, String symbol, String time, String user, int type) {
        super(amount, limit, symbol, time, user, type);
    }

    public AddDto findOrdinance(Stock stock, int type, Users users) {
        List<Notification> res = null;
        boolean isOk = true;
        if (type == 3 && !(isFOKok(stock, users))) {
            isOk = false;
        }
        int newAmount = 0;
        boolean allSell = false;
        String time;
        AddDto success = new AddDto();
        Iterator<CommerceOrdinanceBuy> iterator = stock.getBuyOrdinance().iterator();
        while (iterator.hasNext() && !allSell && isOk) {
            CommerceOrdinanceBuy next = iterator.next();
            if ((next.getLimitPrice() >= this.getLimitPrice() && type == 0 || type == 2 || type == 3) || (type == 1)) {
                if (next.getAmount() == this.getAmount()) {
                    newAmount = this.getAmount();
                    iterator.remove();
                    allSell = true;
                } else if (next.getAmount() > this.getAmount()) {
                    newAmount = this.getAmount();
                    next.setAmount(next.getAmount() - this.getAmount());
                    allSell = true;
                } else if (next.getAmount() < this.getAmount()) {
                    this.setAmount(this.getAmount() - next.getAmount());
                    newAmount = next.getAmount();
                    iterator.remove();
                }
                time = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
                Transaction newTransaction = new Transaction(time, newAmount, next.getLimitPrice(), next.getLimitPrice() * newAmount, this.getUserName(), next.getUserName());
                stock.getTransactions().add(newTransaction);
                stock.increaseNumTransactions();
                stock.setPrice(next.getLimitPrice());
                success.getTransactionDtoList().add(new TransactionDto(newTransaction));
                stock.setSumTransactions(next.getLimitPrice() * newAmount);
                res = users.updateUsers(next.getLimitPrice() * newAmount, time, this.getUserName(), next.getUserName(), stock.getSymbol(), newAmount, next.getLimitPrice());

            }
        }
        return addToCommerceOrdinanceList(stock, success, allSell, type, res);
    }

    public boolean isFOKok(Stock stock, Users users) {
        boolean allsell = false;
        int amount = this.getAmount();
        for (CommerceOrdinance c : stock.getSellOrdinances()) {
            if (c.getLimitPrice() >= this.getLimitPrice()) {
                if (c.getAmount() == amount) {
                    allsell = true;
                    break;
                } else if (c.getAmount() > amount) {
                    allsell = true;
                    break;
                } else if (c.getAmount() < amount) {
                    amount = amount - c.getAmount();
                }
                if (amount <= 0) {
                    allsell = true;
                    break;
                }
            }
        }
        return allsell;
    }

    public AddDto addToCommerceOrdinanceList(Stock stock, AddDto success, boolean allSell, int type, List<Notification> res) {
        if (!allSell && (type == 0 || type == 1)) {
            if (this.getLimitPrice() == -1)
                this.setLimitPrice(stock.getPrice());
            success.setIfAdd(true);
            int index = 0;
            if (!stock.getSellOrdinances().isEmpty()) {
                for (CommerceOrdinanceSell checkForInsert : stock.getSellOrdinances()) {
                    int res1 = this.compareTo(checkForInsert);
                    if (res1 == 1) {
                        stock.getSellOrdinances().add(index, this);
                        return success;
                    } else index++;
                }
            }
            if ((index == stock.getSellOrdinances().size() || stock.getSellOrdinances().isEmpty()))
                stock.getSellOrdinances().add(this);
        }

            Notification not0 = null;
            Notification not1 = null;
            if (res != null) {
                not0 = res.get(0);
                not1 = res.get(1);
                String message;
                if (success.isIfAdd()) {
                    message = ".A new command wes made";
                    not0.setMessage(not0.getMessage() + message);
                    not1.setMessage(not1.getMessage() + message);
                } else {
                    message = ".The all command was made successfully";
                    not0.setMessage(not0.getMessage() + message);
                    not1.setMessage(not1.getMessage() + message);
                }
            }
        return success;
    }
}
