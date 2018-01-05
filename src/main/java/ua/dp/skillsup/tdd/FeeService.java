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
    }

    public FeeService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    public FeeService(WeekendService weekendService) {
        fee = 0.01;
        this.weekendService = weekendService;
    }


    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setHolidayService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }


    public double getFee(double paymentAmount) {
        return fee;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("FeeService Initialised with fee " + fee);
    }
}
