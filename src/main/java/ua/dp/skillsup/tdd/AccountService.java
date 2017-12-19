package ua.dp.skillsup.tdd;

public class AccountService {
    private FeeService feeService;

    public AccountService(FeeService feeService){
        this.feeService = feeService;
    }

    public void transferMoney(BankAccount sender,
                              BankAccount recipient, double amount){
        sender.setAmount(sender.getAmount() - amount);
        double fee = feeService.getFee(0);
        recipient.setAmount(recipient.getAmount() +
                amount * (1 - fee));
    }
}
