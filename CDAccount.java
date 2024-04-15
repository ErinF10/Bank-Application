import java.time.*;

public class CDAccount extends BankAccount{
    private LocalDate startDate;
    private Period term;

    public CDAccount(String accountNumber, MonetaryValue balance, 
    LocalDate startDate, Period term) {
        super(accountNumber, balance);
        this.startDate = startDate;
        this.term = term;
    }

    public LocalDate getTermEnd() {
        return startDate.plus(term);
    }

    public boolean withdrawalNotAllowed(MonetaryValue withdrawalAmount) {
        return super.withdrawalNotAllowed(withdrawalAmount) || 
        LocalDate.now().isBefore(getTermEnd());
    }
}
