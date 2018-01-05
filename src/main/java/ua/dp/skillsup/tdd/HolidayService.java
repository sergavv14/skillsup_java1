package ua.dp.skillsup.tdd;


import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;

public class HolidayService {
    private LocalDate localDate;
    private HashSet<LocalDate> collectionOfHoliday;

    public HolidayService() {this.localDate = LocalDate.now(); autoGenerateCollectionOfHoliday();}

    public HolidayService(LocalDate localDate) {this.localDate = localDate; autoGenerateCollectionOfHoliday();}

    public LocalDate getLocalDate() {return localDate;}

    public HashSet<LocalDate> getCollectionOfHoliday() {return collectionOfHoliday;}

    public void setCollectionOfHoliday(HashSet<LocalDate> collectionOfHoliday) {this.collectionOfHoliday = collectionOfHoliday;}

    public void autoGenerateCollectionOfHoliday(){
        collectionOfHoliday = new HashSet<LocalDate>();
        collectionOfHoliday.add(LocalDate.of(2018, Month.JANUARY, 1));
        collectionOfHoliday.add(LocalDate.of(2018, Month.JANUARY, 7));
        collectionOfHoliday.add(LocalDate.of(2018, Month.JANUARY, 8));
        collectionOfHoliday.add(LocalDate.of(2018, Month.MARCH, 8));
        collectionOfHoliday.add(LocalDate.of(2018, Month.APRIL, 8));
        collectionOfHoliday.add(LocalDate.of(2018, Month.APRIL, 9));
        collectionOfHoliday.add(LocalDate.of(2018, Month.MAY, 1));
        collectionOfHoliday.add(LocalDate.of(2018, Month.MAY, 8));
        collectionOfHoliday.add(LocalDate.of(2018, Month.MAY, 27));
        collectionOfHoliday.add(LocalDate.of(2018, Month.MAY, 28));
        collectionOfHoliday.add(LocalDate.of(2018, Month.JUNE, 28));
        collectionOfHoliday.add(LocalDate.of(2018, Month.AUGUST, 24));
        collectionOfHoliday.add(LocalDate.of(2018, Month.OCTOBER, 14));
        collectionOfHoliday.add(LocalDate.of(2018, Month.OCTOBER, 15));
        collectionOfHoliday.add(LocalDate.of(2018, Month.DECEMBER, 25));
    }

    public boolean addDayInHoliday(LocalDate localDate){
        collectionOfHoliday.remove(localDate);
        return collectionOfHoliday.add(localDate);}

    public boolean delDayInHoliday(LocalDate localDate){return  collectionOfHoliday.remove(localDate);}


    public boolean isHoliday(){
        for (LocalDate dateHoliday: collectionOfHoliday){
            if (dateHoliday==localDate) return true;
        }
        return false;
    }
}
