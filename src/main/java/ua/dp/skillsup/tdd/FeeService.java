package ua.dp.skillsup.tdd;

import org.springframework.beans.factory.InitializingBean;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

public class FeeService implements InitializingBean{

    private HolidayService holidayService;
    private WeekendService weekendService;
    private double fee;

    public FeeService() {
        fee = 0.01;
        this.weekendService = new WeekendService();
        this.holidayService = new HolidayService();
    }

    public FeeService(HolidayService holidayService) {
        this();
        this.holidayService = holidayService;
    }

    public FeeService(WeekendService weekendService) {
        this();
        this.holidayService = new HolidayService();
    }

    public FeeService(WeekendService weekendService, HolidayService holidayService){
        fee = 0.01;
        this.weekendService = weekendService;
        this.holidayService = holidayService;
    }

    public void setHolidayService(HolidayService holidayService) {this.holidayService = holidayService;}

    public void setWeekendService(WeekendService weekendService) {this.weekendService = weekendService;}


    public HolidayService getHolidayService() {return holidayService;}

    public WeekendService getWeekendService() {return weekendService;}

    public double getFee(double paymentAmount) {
        if(!weekendService.isWeekend() & !holidayService.isHoliday(new Date())) {
            return fee;
        }if ((weekendService.isWeekend() & !holidayService.isHoliday(new Date())) ||
        (!weekendService.isWeekend() & holidayService.isHoliday(new Date()))){
            return fee*1.5;
        }if (weekendService.isWeekend() & holidayService.isHoliday(new Date())) {
            return fee*2;
        }
        return fee;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("FeeService Initialised with fee " + fee);
    }
}
