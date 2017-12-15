package ua.dp.skillsup.tdd;

public class AccountService {
    public void transferMoney(BankAccount sender, BankAccount recipient, double amount){
        sender.setAmount(sender.getAmount() - amount);
        recipient.setAmount(recipient.getAmount() + amount);
    }
}
