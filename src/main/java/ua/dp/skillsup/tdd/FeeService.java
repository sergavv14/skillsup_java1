package ua.dp.skillsup.tdd;

import org.springframework.beans.factory.InitializingBean;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        fee = 0.01;
        this.weekendService = new WeekendService();
        this.holidayService = holidayService;
    }

    public FeeService(WeekendService weekendService) {
        fee = 0.01;
        this.weekendService = weekendService;
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
        if (weekendService.isWeekend() & holidayService.isHoliday()) {
            return fee*2;
        }if(!weekendService.isWeekend() & !holidayService.isHoliday()){
            return fee;
        }
        return fee*1.5;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("FeeService Initialised with fee " + fee);
    }
}
