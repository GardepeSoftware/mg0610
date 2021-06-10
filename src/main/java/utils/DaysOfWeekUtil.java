package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class DaysOfWeekUtil {

    public static LocalDate findFirstOfMonth(YearMonth yearMonth, DayOfWeek weekday) throws Exception {
        LocalDate indexDate = yearMonth.atDay(1);
        LocalDate endOfFirstWeek = indexDate.plusDays(7);

        while(indexDate != endOfFirstWeek) {
            if(indexDate.getDayOfWeek().equals(weekday)) {
                return indexDate;
            }

            indexDate = indexDate.plusDays(1);
        }
        throw new Exception("Could not find first " + weekday + " in " + yearMonth.getMonthValue() + " of " + yearMonth.getYear());
    }
}
