package ua.dp.skillsup.tdd;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FeeService {

    private HolidayService holidayService;

    public FeeService() {
    }

    public FeeService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    public double getFee(double paymentAmount) {
        throw new NotImplementedException();
    }
}
