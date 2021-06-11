import utils.LogUtil;
import utils.MoneyUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class which holds the business logic for creating a rental agreement document
 */
public class RentalAgreement {
    private String document;
    private Order order;

    private double prediscountTotal;
    private int discountPercent;
    private double discountedAmt;
    private double finalAmount;
    List<LocalDate> holidays;
    private LocalDate dueDate;

    // Map of charge days for each tool in order
    private Map<String, Integer> toolChargeDaysMap;

    public RentalAgreement(Order order, int discountPercent, List<LocalDate> holidays) {
        this.order = order;
        this.discountPercent = discountPercent;
        this.holidays = holidays;
        toolChargeDaysMap = new HashMap<>();
    }

    /**
     * Calculate values needed for rental agreement document
     * @throws Exception
     */
    public void calculate() throws Exception {
        LogUtil.log("Rental agreement calculation started.");
        if(valid()) {
            calcPrices();
        } else {
            throw new Exception("Order invalid. Make sure rental is for at least 1 day and discount is between 0-100");
        }
    }

    /**
     * Check if rental values valid
     * @return
     */
    private boolean valid() {
        if(this.order.getDaysRented() < 1) {
            return false;
        }

        if(this.discountPercent < 0 || this.discountPercent > 100) {
            return false;
        }

        for(Tool tool : this.order.getTools()) {
            if(tool.getDailyCharge() < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculate prices of tools rented
     */
    private void calcPrices() {
        double total = 0;

        for(Tool tool : order.getTools()) {
            int days = calcDays(tool);
            toolChargeDaysMap.put(tool.getToolCode(), days);
            total += (days * tool.getDailyCharge());
        }

        this.prediscountTotal = MoneyUtil.round(total);
        this.finalAmount = MoneyUtil.applyDiscount(this.prediscountTotal, discountPercent);
        this.discountedAmt = this.prediscountTotal - this.finalAmount;

        LogUtil.log("Final total calculated.");
    }

    /**
     * Calculate number of charged days for a tool
     * @param tool
     * @return
     */
    private int calcDays(Tool tool) {
        int freeDayCount = 0;

        if(tool.isWeekendFree() || tool.isHolidayFree()) {
            freeDayCount = countFreeDays(tool.isWeekendFree(), tool.isHolidayFree());
        }

        return order.getDaysRented() - freeDayCount;
    }

    /**
     * Count amount of free days within rental period
     * @param weekendFree
     * @param holidayFree
     * @return
     */
    private int countFreeDays(boolean weekendFree, boolean holidayFree) {
        LocalDate indexDate = order.getCheckoutDay();
        LocalDate dueDate = order.getDueDate();
        int freeDayCount = 0;

        while(!indexDate.equals(dueDate)) {
            if(isHoliday(indexDate) && isWeekend(indexDate)) {
                freeDayCount = countWeekendHolidaysFree(indexDate, weekendFree, holidayFree);
            } else if(isWeekend(indexDate) && weekendFree) {
                freeDayCount++;
            } else if(isHoliday(indexDate) && holidayFree) {
                freeDayCount++;
            }

            indexDate = indexDate.plusDays(1);
        }

        return freeDayCount;
    }

    /**
     * Check how many free days should be counted for holidays that fall on weekends. Holidays
     * should be shifted to nearest weekday if possible.
     * @param dateToCheck
     * @param weekendFree
     * @param holidayFree
     * @return
     */
    private int countWeekendHolidaysFree(LocalDate dateToCheck, boolean weekendFree, boolean holidayFree) {
        int freeDayCount = 0;

        if(weekendFree && holidayFree) {
            if(doesHolidayCount(dateToCheck)) {   // Weekend day + holiday
                freeDayCount = freeDayCount + 2;
            } else {                              // Just weekend day. Holiday can't be shifted to weekday
                freeDayCount++;
            }
        } else if(holidayFree) {
            if(doesHolidayCount(dateToCheck)) {   // Just holiday
                freeDayCount++;
            }
        }
        else if(weekendFree) {                    // Just weekend day
            freeDayCount++;
        }

        return freeDayCount;
    }

    /**
     * Check if day a weekend day
     * @param date
     * @return
     */
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        if( day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Check if day is holiday
     * @param date
     * @return
     */
    private boolean isHoliday(LocalDate date) {
        for(LocalDate holiday : holidays) {
            int dateMonth = date.getMonthValue();
            int dateDay = date.getDayOfMonth();
            int holidayMonth = holiday.getMonthValue();
            int holidayDay = holiday.getDayOfMonth();

            if(dateMonth == holidayMonth && dateDay == holidayDay) {
                return true;
            }
        }
        return false;
    }

    /**
     * For weekend holidays, check if we can shift holiday discount to nearest weekday
     * @param holiday
     * @return
     */
    private boolean doesHolidayCount(LocalDate holiday) {
        if(holiday.getDayOfWeek() == DayOfWeek.SATURDAY) {
            if(holiday != order.getCheckoutDay()) {   // Yesterday was rented. Count as holiday
                return true;
            }
        } else if(holiday.getDayOfWeek() == DayOfWeek.SUNDAY) {  // Tomorrow was rented. Count as holiday
            if(holiday != order.getDueDate()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Create rental agreement document
     */
    public void createDocument() {
        StringBuilder sb = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        if(order.getTools().size() > 0) {
            for (Tool tool : order.getTools()) {
                sb.append(newLine);
                sb.append(newLine);
                sb.append("Rental Agreement:" + newLine);
                sb.append("Tool code: " + tool.getToolCode() + newLine);
                String toolTypeUprCse =
                        tool.getToolType().toString().substring(0, 1) + tool.getToolType().toString().substring(1).toLowerCase(Locale.ROOT);
                sb.append("Tool type: " + toolTypeUprCse + newLine);
                sb.append("Tool brand: " + tool.getBrand() + newLine);
                sb.append("Daily rental charge: " + MoneyUtil.formatForDisplay(tool.getDailyCharge()) + newLine);
                sb.append("Charge days: " + toolChargeDaysMap.get(tool.getToolCode()) + newLine);
            }

            sb.append("Rental days: " + this.order.getDaysRented() + newLine);
            sb.append("Check out date: " + formatDateString(this.order.getCheckoutDay()) + newLine);
            sb.append("Due date: " + formatDateString(this.order.getDueDate()) + newLine);
            sb.append("Pre-discount: " + MoneyUtil.formatForDisplay(this.prediscountTotal) + newLine);
            sb.append("Discount percent: " + this.discountPercent + "%" + newLine);
            sb.append("Discount amount: " + MoneyUtil.formatForDisplay(this.prediscountTotal - this.discountedAmt) + newLine);
            sb.append("Final charge: " + MoneyUtil.formatForDisplay(finalAmount) + newLine);
            sb.append(newLine);
            sb.append(newLine);
        } else {
            LogUtil.logError("Can't print rental agreement. You haven't rented any tools yet.");
        }

        this.document = sb.toString();

        LogUtil.log("Rental agreement document successfully created.");
    }

    /**
     * Format date for display
     * @param date
     * @return
     */
    private String formatDateString(LocalDate date) {
        String month = Integer.toString(date.getMonthValue());
        String day = Integer.toString(date.getDayOfMonth());
        String fullYear = Integer.toString(date.getYear());
        String abrYear = fullYear.substring(2);
        String slash = "/";

        String formatedDate = month + slash + day + slash + abrYear;

        return formatedDate;
    }


    // Getters

    public String getDocument() {
        return this.document;
    }

    public Order getOrder() {
        return this.order;
    }

    public double getPrediscountTotal() {
        return this.prediscountTotal;
    }

    public double getDiscountPercent() {
        return this.discountPercent;
    }


    public double getDiscountedAmt() {
        return this.discountedAmt;
    }

    public double getFinalAmount() {
        return this.finalAmount;
    }

    public LocalDate getDueDate() {
        return order.getDueDate();
    }

    public Map<String, Integer> getToolChargeDaysMap() {
        return toolChargeDaysMap;
    }
}
