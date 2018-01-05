package ua.dp.skillsup.tdd;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

public class HomeworkTestTDD {
    @Test
    public void transferMoneyOnWorkingdays(){
        //WeekendService mockWeekendService = Mockito.mock(WeekendService.class);
        //Mockito.when(mockWeekendService.isWeekend()).thenReturn(false);

        LocalDate localDate = LocalDate.of(2018, Month.JANUARY, 5);
        WeekendService weekendService = new WeekendService(localDate);
        FeeService feeService = new FeeService(weekendService);

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(99, recipient.getAmount(), 0.00001);
    }

    @Test
    public void transferMoneyOnWeekend(){
        //WeekendService mockWeekendService = Mockito.mock(WeekendService.class);
        //Mockito.when(mockWeekendService.isWeekend()).thenReturn(true);

        LocalDate localDate = LocalDate.of(2018, Month.JANUARY, 6);
        WeekendService weekendService = new WeekendService(localDate);
        FeeService feeService = new FeeService(weekendService);

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98.5, recipient.getAmount(), 0.00001);
    }
}
