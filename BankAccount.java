public class BankAccount {

    
    private final String accountNumber;
    private MonetaryValue balance;
    //private static MonetaryValue totalBalance = new MonetaryValue(0);
  
    public BankAccount(String accountNumber, MonetaryValue balance) {
      this.accountNumber = accountNumber;
      this.balance = balance;
      //totalBalance = new MonetaryValue(totalBalance.plus(balance).toString());
    }
  
    public BankAccount(String accountNumber) {
      this.accountNumber = accountNumber;
      balance = MonetaryValue.ZERO;
    }
  
    public BankAccount(BankAccount original) {
      this.accountNumber = original.accountNumber;
      this.balance = original.balance;
      //totalBalance = new MonetaryValue(totalBalance.plus(balance).toString());
    }
  
    public String getAccountNumber() {
      return accountNumber;
    }
  
    public MonetaryValue getBalance() {
      return balance;
    }
  
    public boolean deposit(MonetaryValue amount) {
      if (amount.isNegative()) {
        return false;
      } else {
        balance = balance.plus(amount);
        //totalBalance = new MonetaryValue(totalBalance.plus(amount).toString());
        return true;
      }
    }
  
    public boolean withdraw(MonetaryValue amount) {
      if (withdrawalNotAllowed(amount)) {
        return false;
    } else {
        balance = balance.minus(amount);
        return true;
    }
      // if (amount.isNegative()) {
      //   return false;
      // } else if (balance.isLessThan(amount)) {
      //   return false;
      // } else {
      //   balance = balance.minus(amount);
      //   //totalBalance = new MonetaryValue(totalBalance.minus(amount).toString());
      //   return true;
      // }
    }
  
    protected boolean withdrawalNotAllowed(MonetaryValue amount) {
      return amount.isNegative() || balance.isLessThan(amount);
    }

    public String toString() {
      return accountNumber + " " + balance.toString();
    }
    
    // public static MonetaryValue getTotal() {
    //     return totalBalance;
    // }
  }
  
