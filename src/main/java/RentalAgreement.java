import utils.LogUtil;
import utils.MoneyUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: add logging

public class RentalAgreement {
    private String document;
    private Order order;

    private double totalCost;
    private int discount;
    private double discountedCost;
    private double finalAmount;
    List<LocalDate> holidays;

    private LocalDate dueDate;
    private Map<String, Integer> toolChargeDaysMap;

    public RentalAgreement(Order order, int discount, List<LocalDate> holidays) {
        this.order = order;
        this.discount = discount;
        this.holidays = holidays;
        toolChargeDaysMap = new HashMap<>();
    }

    public void calculate() {
        calcPrices();
    }

    // TODO: write test to calculate multiple tools
    // TODO: calc multiple tools with different rules
    // TODO: what about different amount of days? Separate rental agreement?
    private void calcPrices() {
        double total = 0;

        for(Tool tool : order.getTools()) {
            int days = calcDays(tool);
            toolChargeDaysMap.put(tool.getToolCode(), days);
            total += (days * tool.getDailyCharge());
        }

        this.totalCost = MoneyUtil.roundUp(total);
        this.discountedCost = MoneyUtil.applyDiscount(this.totalCost, this.discount);
        this.finalAmount = MoneyUtil.roundUp(this.totalCost - this.discountedCost);
    }

    private int calcDays(Tool tool) {
        int freeDayCount = 0;

        if(tool.isWeekendFree() || tool.isHolidayFree()) {
            freeDayCount = countFreeDays(tool.isWeekendFree(), tool.isHolidayFree());
            System.out.println("freeDayCount: " + freeDayCount);
        }

        return order.getDaysRented() - freeDayCount;
    }

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

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        if( day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
//            System.out.println(day.toString() + " is a weekend day");
            return true;
        }
        return false;
    }

    private boolean isHoliday(LocalDate date) {
        for(LocalDate holiday : holidays) {
            if(date.equals(holiday)) {
                System.out.println(date.toString() + " is a holiday");
                return true;
            } else {
                System.out.println("Non-holiday: " + date.toString());
            }
        }
        return false;
    }

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

    // TODO: all values need to be in their proper format
    public void printDocument() {
        StringBuilder sb = new StringBuilder();

        if(order.getTools().size() > 0) {
            for (Tool tool : order.getTools()) {
                sb.append("Tool code: " + tool.getToolCode());
                sb.append("Tool type: " + tool.getToolType().toString());
                sb.append("Tool brand: " + tool.getBrand());
                sb.append("Daily rental charge: " + tool.getDailyCharge());
                sb.append("Charge days: " + toolChargeDaysMap.get(tool.getToolCode()));
            }

            sb.append("Rental days: " + this.order.getDaysRented());
            sb.append("Check out date: " + this.order.getCheckoutDay().toString()); // TODO: this probably isn't right
                                                                                    // format
            sb.append("Due date: " + this.order.getDueDate().toString()); // TODO: this probably isn't right format
            sb.append("Pre-discount: " + this.totalCost);
            sb.append("Discount percent: %" + discount);
            sb.append("Final charge: " + finalAmount);
        } else {
            LogUtil.logError("Can't print rental agreement. You haven't rented any tools yet.");
        }
    }


    // Getters and Setters

    public String getDocument() {
        return this.document;
    }

    public Order getOrder() {
        return this.order;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getDiscountedCost() {
        return this.discountedCost;
    }

    public double getFinalAmount() {
        return this.finalAmount;
    }

    public LocalDate getDueDate() {
        return order.getDueDate();
    }
}
