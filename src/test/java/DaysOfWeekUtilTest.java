import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.DaysOfWeekUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

class DaysOfWeekUtilTest {

    @Test
    public void findFirstOfMonthTest() throws Exception {
        LocalDate correctDate = LocalDate.parse("2021-09-06");

        YearMonth yearMonth = YearMonth.parse("2021-09");
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalDate calculatedDate = DaysOfWeekUtil.findFirstOfMonth(yearMonth, dayOfWeek);

        Assertions.assertEquals(correctDate, calculatedDate);
    }
}