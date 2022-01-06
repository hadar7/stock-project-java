package engineClasses;

public class Move {
   private int type;//1-buy , 2-sell ,3 -load
   private String symbol;
   private String date;
   private int amount;
   private int balanceBefore;
   private int balanceAfter;

   public Move(int type,String symbol,String date,int amount,int balanceBefore,int balanceAfter) {
      this.amount=amount;
      this.balanceAfter=balanceAfter;
      this.symbol=symbol;
      this.balanceBefore=balanceBefore;
      this.date=date;
      this.type=type;
   }
   public Move(int type,String date) {
      this.amount=0;
      this.balanceAfter=0;
      this.symbol=symbol;
      this.balanceBefore=0;
      this.date=date;
      this.type=type;
   }



}
