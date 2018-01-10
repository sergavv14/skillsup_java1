package ua.dp.skillsup.tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

public class HomeworkTestTDD {
    BankAccount sender, recipient;
    FeeService feeService;
    AccountService service;
    WeekendService mockWeekendService;
    HolidayService mockHolidayService;

    @Before
    public void before() {
        sender = new BankAccount(100);
        recipient = new BankAccount(0);
        mockWeekendService = Mockito.mock(WeekendService.class);
        mockHolidayService = Mockito.mock(HolidayService.class);
    }

    @Test
    public void transferMoneyOnWorkingdays(){
        Mockito.when(mockWeekendService.isWeekend()).thenReturn(false);
        Mockito.when(mockHolidayService.isHoliday(Mockito.any(Date.class))).thenReturn(false);

        feeService = new FeeService(mockWeekendService, mockHolidayService);
        service = new AccountService(feeService);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(99, recipient.getAmount(), 0.00001);
    }

   @Test
    public void transferMoneyOnWeekend(){
       Mockito.when(mockWeekendService.isWeekend()).thenReturn(true);
       Mockito.when(mockHolidayService.isHoliday(Mockito.any(Date.class))).thenReturn(false);

       feeService = new FeeService(mockWeekendService, mockHolidayService);
       service = new AccountService(feeService);

       service.transferMoney(sender, recipient, 100);

       Assert.assertEquals(0, sender.getAmount(), 0.00001);
       Assert.assertEquals(98.5, recipient.getAmount(), 0.00001);
   }


    @Test
    public void transferMoneyOnWorkingdaysOnNationalHolidays(){
        Mockito.when(mockWeekendService.isWeekend()).thenReturn(false);
        Mockito.when(mockHolidayService.isHoliday(Mockito.any(Date.class))).thenReturn(true);

        feeService = new FeeService(mockWeekendService, mockHolidayService);
        service = new AccountService(feeService);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98.5, recipient.getAmount(), 0.00001);
    }

    @Test
    public void transferMoneyOnWeekendOnNationalHolidays(){
        Mockito.when(mockWeekendService.isWeekend()).thenReturn(true);
        Mockito.when(mockHolidayService.isHoliday(Mockito.any(Date.class))).thenReturn(true);

        feeService = new FeeService(mockWeekendService, mockHolidayService);
        service = new AccountService(feeService);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98, recipient.getAmount(), 0.00001);
    }
}
