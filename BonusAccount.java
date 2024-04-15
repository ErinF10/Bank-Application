public class BonusAccount extends BankAccount {
	private MonetaryValue bonus;
	
	public BonusAccount(String accountNumber, MonetaryValue balance, MonetaryValue bonus) {
		super(accountNumber, balance);
		this.bonus = bonus;
	}
	
	public boolean deposit(MonetaryValue depositAmount) {
		if (!depositAmount.isNegative()) {
			super.deposit(depositAmount);
			if (depositAmount.isGreaterThan(new MonetaryValue(100, 0))) {
				super.deposit(bonus);
			}
			return true;
		}
		return false;
	}
			
}
