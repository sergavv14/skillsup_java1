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

        Assert.assertEquals("ERROR: weekendService.isHoliday() is true (test expected false)",false, weekendService.isWeekend());

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

        Assert.assertEquals("ERROR: weekendService.isHoliday() is false (test expected true)",true, weekendService.isWeekend());

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98.5, recipient.getAmount(), 0.00001);
    }

    @Test
    public void transferMoneyOnWorkingdaysOnNationalHolidays(){
        //HolidayService mockHolidayService = Mockito.mock(HolidayService.class);
        //Mockito.when(mockHolidayService.isHoliday()).thenReturn(true);
        LocalDate localDate = LocalDate.of(2018, Month.JANUARY, 1);
        HolidayService holidayService = new HolidayService(localDate);
        holidayService.addDayInHoliday(localDate);
        WeekendService weekendService = new WeekendService(localDate);

        Assert.assertEquals( "ERROR: holidayService.isHoliday() is false (test expected true)",true, holidayService.isHoliday());
        Assert.assertEquals("ERROR: weekendService.isHoliday() is true (test expected false)",false, weekendService.isWeekend());

        FeeService feeService = new FeeService(weekendService, holidayService);

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98.5, recipient.getAmount(), 0.00001);
    }

    @Test
    public void transferMoneyOnWeekendOnNationalHolidays(){
        //HolidayService mockHolidayService = Mockito.mock(HolidayService.class);
        //Mockito.when(mockHolidayService.isHoliday()).thenReturn(true);
        LocalDate localDate = LocalDate.of(2018, Month.JANUARY, 7);
        HolidayService holidayService = new HolidayService(localDate);
        holidayService.addDayInHoliday(localDate);
        WeekendService weekendService = new WeekendService(localDate);

        Assert.assertEquals( "ERROR: holidayService.isHoliday() is false (test expected true)",true, holidayService.isHoliday());
        Assert.assertEquals("ERROR: weekendService.isHoliday() is false (test expected true)",true, weekendService.isWeekend());

        FeeService feeService = new FeeService(weekendService, holidayService);

        AccountService service = new AccountService(feeService);

        BankAccount sender = new BankAccount(100);
        BankAccount recipient = new BankAccount(0);

        service.transferMoney(sender, recipient, 100);

        Assert.assertEquals(0, sender.getAmount(), 0.00001);
        Assert.assertEquals(98, recipient.getAmount(), 0.00001);
    }
}
