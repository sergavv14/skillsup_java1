package ua.dp.skillsup.tdd;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendService {
    private LocalDate localDate;

    public WeekendService() {this.localDate = LocalDate.now();}

    public WeekendService(LocalDate localDate) {this.localDate = localDate;}


    public LocalDate getLocalDate() {return localDate;}


    public boolean isWeekend(){
        if ((localDate.getDayOfWeek()==DayOfWeek.SATURDAY)||
                (localDate.getDayOfWeek()==DayOfWeek.SUNDAY))
            return true;

        return false;
    }
}
