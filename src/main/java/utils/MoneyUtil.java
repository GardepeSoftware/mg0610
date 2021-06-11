package utils;

import java.text.DecimalFormat;

public class MoneyUtil {

    /**
     * Round value to two decimal places
     * @param fullNum
     * @return
     */
    public static double round(double fullNum) {
        double roundedNum = Math.round(fullNum * 100.0) / 100.0;

        return roundedNum;
    }

    /**
     * Apply provided discount to provided totalAmount
     * @param totalAmount
     * @param discountPercent
     * @return
     */
    public static double applyDiscount(double totalAmount, int discountPercent) {
        double discountDecimal = discountPercent / 100;
        double discountedAmount = totalAmount * discountDecimal;
        double finalTotal = totalAmount - discountedAmount;

        return round(finalTotal);
    }

    /**
     * Format money values for display
     * @param amount
     * @return
     */
    public static String formatForDisplay(double amount) {
        DecimalFormat decFormat = new DecimalFormat("#,###.00");
        String formatAmt = decFormat.format(amount);

        String finalFormat = "$" + formatAmt;

        return finalFormat;
    }
}
