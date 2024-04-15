public class CheckingAccount extends BankAccount {
	private MonetaryValue overdraftLimit = new MonetaryValue(0);
	
	public CheckingAccount(String accountNumber, MonetaryValue balance, MonetaryValue overdraftLimit) {
		super(accountNumber, balance);
		this.overdraftLimit = overdraftLimit;
	}
	
	public MonetaryValue getOverdraftLimit() {
		return overdraftLimit;
	}
	
	protected boolean withdrawalNotAllowed(MonetaryValue withdrawalAmount) {
		if ((getBalance().minus(withdrawalAmount)).toDouble() < -(overdraftLimit.toDouble())) {
			return true;
		}
		else if (withdrawalAmount.isNegative()) {
			return true;
		}
		else {
			return false;
		}
       // return getBalance().minus(withdrawalAmount).isLessThan(overdraftLimit) && !withdrawalAmount.isNegative();
	}
}
