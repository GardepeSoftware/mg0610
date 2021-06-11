package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class DaysOfWeekUtil {

    /**
     * Find first occurrence of a particular day of the week
     * @param yearMonth
     * @param weekday
     * @return
     * @throws Exception
     */
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
