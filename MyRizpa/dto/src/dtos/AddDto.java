package dtos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AddDto {
     private List<TransactionDto> transactionDtoList;
     private boolean ifAdd;

    public AddDto() {
        this.transactionDtoList = new ArrayList<>();
        ifAdd = false;
    }
    public List<TransactionDto> getTransactionDtoList() {
        return transactionDtoList;
    }
    public boolean isIfAdd() {
        return ifAdd;
    }
    public void setIfAdd(boolean ifAdd) {
        this.ifAdd = ifAdd;
    }
}
