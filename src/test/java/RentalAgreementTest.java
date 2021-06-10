import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.DaysOfWeekUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

class RentalAgreementTest {
    private final double LADDER_PRICE = 2.15;
    private final double CHAINSAW_PRICE = 5.20;
    private final double JACKHAMMER_PRICE = 8.45;

    private final boolean LADDER_WKD_FREE = false;
    private final boolean CHAINSAW_WKD_FREE = true;
    private final boolean JACKHAMMER_WKD_FREE = true;

    private final boolean LADDER_HOL_FREE = true;
    private final boolean CHAINSAW_HOL_FREE = false;
    private final boolean JACKHAMMER_HOL_FREE = true;

    private LocalDate fourth_of_july;
    private LocalDate labor_day;

    public RentalAgreementTest() throws Exception {
       fourth_of_july = LocalDate.parse("2021-07-04");
       YearMonth laborDayYearMonth = YearMonth.parse("2021-09");
       labor_day = DaysOfWeekUtil.findFirstOfMonth(laborDayYearMonth, DayOfWeek.MONDAY);
    }

    @Test
    void calculateLadderTest() {
        Tool ladder = createTool(ToolType.LADDER, "LADW", "Werner");

        List<Tool> tools = Arrays.asList(ladder);
        LocalDate checkoutDate = createDate("2021-07-01");
        Order order = new Order(tools, checkoutDate, 1);

        RentalAgreement rentAgree = createRentalAgreement(order, 20);

        rentAgree.calculate();
        assertRentalAgreementNotNull(rentAgree);

//        Assertions.assertEquals(LocalDate.parse("2021-09-01"), rentAgree.getDueDate());
        Assertions.assertEquals(2.15, rentAgree.getTotalCost());
    }

    @Test
    void calculateChainsawTest() {
        Tool chainsaw = createTool(ToolType.CHAINSAW, "CHNS", "Stihl");

        List<Tool> tools = Arrays.asList(chainsaw);
        LocalDate date = createDate("2021-06-09");
        Order order = new Order(tools, date, 13);
        RentalAgreement rentAgree = createRentalAgreement(order, 10);

        rentAgree.calculate();
        assertRentalAgreementNotNull(rentAgree);
    }

    @Test
    void calculateJackhammerRTest() {
        Tool jackhammer = createTool(ToolType.JACKHAMMER, "JAKR", "Ridgid");

        List<Tool> tools = Arrays.asList(jackhammer);
        LocalDate date = createDate("2021-06-09");
        Order order = new Order(tools, date, 4);
        RentalAgreement rentAgree = createRentalAgreement(order, 15);

        rentAgree.calculate();
        assertRentalAgreementNotNull(rentAgree);
    }

    @Test
    void calculateJackhammerDTest() {
        Tool jackhammer = createTool(ToolType.JACKHAMMER, "JAKD", "DeWalt");

        List<Tool> tools = Arrays.asList(jackhammer);
        LocalDate date = createDate("2021-06-09");
        Order order = new Order(tools, date, 6);
        RentalAgreement rentAgree = createRentalAgreement(order, 25);

        rentAgree.calculate();
        assertRentalAgreementNotNull(rentAgree);
    }

    private void assertRentalAgreementNotNull(RentalAgreement rentAgree) {
//        Assertions.assertNotNull(rentAgree.getDocument());
        Assertions.assertNotNull(rentAgree.getDiscount());
        Assertions.assertNotNull(rentAgree.getFinalAmount());
        Assertions.assertNotNull(rentAgree.getDiscountedCost());
        Assertions.assertNotNull(rentAgree.getOrder());
        Assertions.assertNotNull(rentAgree.getDueDate());
        Assertions.assertNotNull(rentAgree.getTotalCost());
    }

    private Tool createTool(ToolType toolType, String toolCode, String brand) {
        double dailyPrice;
        boolean wkdChg;
        boolean holChg;

        if(toolType == ToolType.LADDER) {
            dailyPrice = LADDER_PRICE;
            wkdChg = LADDER_WKD_FREE;
            holChg = LADDER_HOL_FREE;
        } else if(toolType == ToolType.CHAINSAW) {
            dailyPrice = CHAINSAW_PRICE;
            wkdChg = CHAINSAW_WKD_FREE;
            holChg = CHAINSAW_HOL_FREE;
        } else {
            dailyPrice = JACKHAMMER_PRICE;
            wkdChg = JACKHAMMER_WKD_FREE;
            holChg = JACKHAMMER_HOL_FREE;
        }

        Tool tool = new Tool(toolType, brand, toolCode, dailyPrice, wkdChg, holChg);

        return tool;
    }

    private RentalAgreement createRentalAgreement(Order order, int discountPercentage) {
        List<LocalDate> holidays = Arrays.asList(fourth_of_july, labor_day);
        System.out.println("labor day is " + labor_day.toString());
        RentalAgreement rentAgree = new RentalAgreement(order, discountPercentage, holidays);

        return  rentAgree;
    }

    private LocalDate createDate(String date) {
        LocalDate localDate = LocalDate.parse(date);

        return localDate;
    }
}