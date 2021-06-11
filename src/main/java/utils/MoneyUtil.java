package utils;

import java.text.DecimalFormat;

public class MoneyUtil {

    public static double round(double fullNum) {
        double roundedNum = Math.round(fullNum * 100.0) / 100.0;

        return roundedNum;
    }

    public static double applyDiscount(double totalAmount, int discountPercent) {
        double discountDecimal = discountPercent / 100;

        // TODO: this is not correct
        double discountedAmount = totalAmount * discountDecimal;
        double finalTotal = totalAmount - discountedAmount;

        return round(finalTotal);
    }

    public static String formatForDisplay(double amount) {
        DecimalFormat decFormat = new DecimalFormat("0.00");
        String formatAmt = decFormat.format(amount);

        String finalFormat = "$" + formatAmt;

        return finalFormat;
    }
}
