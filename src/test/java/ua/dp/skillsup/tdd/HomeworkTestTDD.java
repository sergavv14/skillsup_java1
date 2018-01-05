package ua.dp.skillsup.tdd;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class HomeworkTestTDD {
    @Test
    public void transferMoneyOnWorkingdays(){
        WeekendService mockWeekendService = Mockito.mock(WeekendService.class);
        Mockito.when(mockWeekendService.isWeekend()).thenReturn(false);
        FeeService feeService = new FeeService(mockWeekendService);

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(99, recipient.getAmount(), 0.00001);
    }
}
