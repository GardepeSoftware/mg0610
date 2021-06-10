package utils;

public class MoneyUtil {

    public static double roundUp(double fullNum) {
        double roundedNum = Math.round(fullNum * 100.0) / 100.0;

        return roundedNum;
    }

    public static double applyDiscount(double totalAmount, int discountPercent) {
        double discountDecimal = discountPercent / 100;
        double discountedAmount = totalAmount * discountDecimal;

        return roundUp(discountedAmount);
    }
}
