import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.DaysOfWeekUtil;
import utils.MoneyUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

class RentalAgreementTest {
    private final double LADDER_PRICE = 50;
    private final double CHAINSAW_PRICE = 100;
    private final double JACKHAMMER_PRICE = 150;

    private final boolean LADDER_WKD_FREE = false;
    private final boolean CHAINSAW_WKD_FREE = true;
    private final boolean JACKHAMMER_WKD_FREE = true;

    private final boolean LADDER_HOL_FREE = true;
    private final boolean CHAINSAW_HOL_FREE = false;
    private final boolean JACKHAMMER_HOL_FREE = true;

    /**
     * Test ladder rental which takes place during Independence Day
     * @throws Exception
     */
    @Test   // Specification Test 2
    void calculateLadderIndependeceDayTest() throws Exception {
        ToolType toolType = ToolType.LADDER;    // weekendFree: false   holidayFree: true
        String toolCode = "LADW";
        String brand = "Werner";
        double dailyPrice = LADDER_PRICE;
        int discount = 10;
        int daysRented = 3;
        int daysCharged = 2;    // This value changes depending on tool and date of rental period
        LocalDate checkoutDate = createDate("2020-07-02");
        double prediscountTotal = MoneyUtil.round(daysCharged * dailyPrice);
        double finalCharge = MoneyUtil.applyDiscount(prediscountTotal, discount);
        double discountAmt = MoneyUtil.round(prediscountTotal - finalCharge);

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);
        rentAgmt.calculate();
        rentAgmt.createDocument();

        // Assert that all members vars all instantiated
        assertRentalAgreementNotNull(rentAgmt);

        // Assert that values in rental agreement match expected values
        assertValuesEqual(rentAgmt, toolCode, toolType, brand, daysRented, daysCharged, checkoutDate,
                checkoutDate.plusDays(daysRented), dailyPrice, prediscountTotal, discount, discountAmt, finalCharge);

        // Print document to console
        System.out.println(rentAgmt.getDocument());
    }

    /**
     * Test chainsaw rental that takes place during Independence Day
     * @throws Exception
     */
    @Test   // Specification Test 3
    void calculateChainsawIndependeceDayTest() throws Exception {
        ToolType toolType = ToolType.CHAINSAW;  // weekendFree: true   holidayFree: false
        String toolCode = "CHNS";
        String brand = "Stihl";
        double dailyPrice = CHAINSAW_PRICE;
        int discount = 25;
        int daysRented = 5;
        int daysCharged = 3;    // This value changes depending on tool and date of rental period
        LocalDate checkoutDate = createDate("2015-07-02");  // TODO: verify labor day calc is correct
        double prediscountTotal = MoneyUtil.round(daysCharged * dailyPrice);
        double finalCharge = MoneyUtil.applyDiscount(prediscountTotal, discount);
        double discountAmt = MoneyUtil.round(prediscountTotal - finalCharge);

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);
        rentAgmt.calculate();
        rentAgmt.createDocument();

        // Assert that all members vars all instantiated
        assertRentalAgreementNotNull(rentAgmt);

        // Assert that values in rental agreement match expected values
        assertValuesEqual(rentAgmt, toolCode, toolType, brand, daysRented, daysCharged, checkoutDate,
                checkoutDate.plusDays(daysRented), dailyPrice, prediscountTotal, discount, discountAmt, finalCharge);

        // Print document to console
        System.out.println(rentAgmt.getDocument());
    }

    /**
     * Test jackhammer rental that takes place during Independence Day
     * @throws Exception
     */
    @Test   // Specification Test 5
    void calculateJackhammerIndependeceDayTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Ridgid";
        double dailyPrice = JACKHAMMER_PRICE;
        int discount = 0;
        int daysRented = 9;
        int daysCharged = 6;    // This value changes depending on tool and date of rental period
        LocalDate checkoutDate = createDate("2015-07-02");
        double prediscountTotal = MoneyUtil.round(daysCharged * dailyPrice);
        double finalCharge = MoneyUtil.applyDiscount(prediscountTotal, discount);
        double discountAmt = MoneyUtil.round(prediscountTotal - finalCharge);

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);
        rentAgmt.calculate();
        rentAgmt.createDocument();

        // Assert that all members vars all instantiated
        assertRentalAgreementNotNull(rentAgmt);

        // Assert that values in rental agreement match expected values
        assertValuesEqual(rentAgmt, toolCode, toolType, brand, daysRented, daysCharged, checkoutDate,
                checkoutDate.plusDays(daysRented), dailyPrice, prediscountTotal, discount, discountAmt, finalCharge);

        // Print document to console
        System.out.println(rentAgmt.getDocument());
    }

    /**
     * Test jackhammer rental that takes place during Independence day
     * @throws Exception
     */
    @Test   // Specification Test 6
    void calculateJackhammerIndependeceDayTest2() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Werner";
        double dailyPrice = JACKHAMMER_PRICE;
        int discount = 50;
        int daysRented = 4;
        int daysCharged = 1;    // This value changes depending on tool and date of rental period
        LocalDate checkoutDate = createDate("2020-07-02");
        double prediscountTotal = MoneyUtil.round(daysCharged * dailyPrice);
        double finalCharge = MoneyUtil.applyDiscount(prediscountTotal, discount);
        double discountAmt = MoneyUtil.round(prediscountTotal - finalCharge);

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);
        rentAgmt.calculate();
        rentAgmt.createDocument();

        // Assert that all members vars all instantiated
        assertRentalAgreementNotNull(rentAgmt);

        // Assert that values in rental agreement match expected values
        assertValuesEqual(rentAgmt, toolCode, toolType, brand, daysRented, daysCharged, checkoutDate,
                checkoutDate.plusDays(daysRented), dailyPrice, prediscountTotal, discount, discountAmt, finalCharge);

        // Print document to console
        System.out.println(rentAgmt.getDocument());
    }

    /**
     * Test jackhammer rental that takes place during Labor Day
     * @throws Exception
     */
    @Test   // Specification Test 4
    void calculateJackhammerLaborDayTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKD";
        String brand = "DeWalt";
        double dailyPrice = JACKHAMMER_PRICE;
        int discount = 0;
        int daysRented = 6;
        int daysCharged = 3;    // This value changes depending on tool and date of rental period
        LocalDate checkoutDate = createDate("2015-09-03");
        double prediscountTotal = MoneyUtil.round(daysCharged * dailyPrice);
        double finalCharge = MoneyUtil.applyDiscount(prediscountTotal, discount);
        double discountAmt = MoneyUtil.round(prediscountTotal - finalCharge);

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);
        rentAgmt.calculate();
        rentAgmt.createDocument();

        // Assert that all members vars all instantiated
        assertRentalAgreementNotNull(rentAgmt);

        // Assert that values in rental agreement match expected values
        assertValuesEqual(rentAgmt, toolCode, toolType, brand, daysRented, daysCharged, checkoutDate,
                checkoutDate.plusDays(daysRented), dailyPrice, prediscountTotal, discount, discountAmt, finalCharge);

        // Print document to console
        System.out.println(rentAgmt.getDocument());
    }

    /**
     * Test for rental with discount that is too high
     * @throws Exception
     */
    @Test   // Specification Test 1
    void calculateDiscountTooHighTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Werner";
        int discount = 101;
        int daysRented = 5;
        LocalDate checkoutDate = createDate("2015-09-03");

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);

        Assertions.assertThrows(Exception.class, ()->
                rentAgmt.calculate(), "Expected calculate() to throw Exception but it didn't"
        );
    }

    /**
     * Test for rental with discount that is too low
     * @throws Exception
     */
    @Test
    void calculateDiscountTooLowTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Werner";
        int discount = -10;
        int daysRented = 0;
        LocalDate checkoutDate = createDate("2015-09-03");

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);

        Assertions.assertThrows(Exception.class, ()->
                rentAgmt.calculate(), "Expected calculate() to throw Exception but it didn't"
        );
    }

    /**
     * Test for rental with too few days
     * @throws Exception
     */
    @Test
    void calculateDaysRentedTooLowTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Werner";
        int discount = 0;
        int daysRented = 0;
        LocalDate checkoutDate = createDate("2015-09-03");

        Tool tool = createTool(toolType, toolCode, brand);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);

        Assertions.assertThrows(Exception.class, ()->
                rentAgmt.calculate(), "Expected calculate() to throw Exception but it didn't"
        );
    }

    /**
     * Test for rental with negative dailyCost
     * @throws Exception
     */
    @Test
    void calculateNegativeDailyCostTest() throws Exception {
        ToolType toolType = ToolType.JACKHAMMER;    // weekendFree: true   holidayFree: true
        String toolCode = "JAKR";
        String brand = "Werner";
        int discount = 0;
        int daysRented = 5;
        LocalDate checkoutDate = createDate("2015-09-03");

        Tool tool = new Tool(toolType, toolCode, brand, -50.00, false, false);

        List<Tool> tools = Arrays.asList(tool);
        Order order = new Order(tools, checkoutDate, daysRented);

        List<LocalDate> holidays = createHolidays(checkoutDate.getYear());
        RentalAgreement rentAgmt = createRentalAgreement(order, discount, holidays);

        Assertions.assertThrows(Exception.class, ()->
                rentAgmt.calculate(), "Expected calculate() to throw Exception but it didn't"
        );
    }

    /**
     * Assert that all member variables in RentalAgreement are initialized
     * @param rentAgree
     */
    private void assertRentalAgreementNotNull(RentalAgreement rentAgree) {
        Assertions.assertNotNull(rentAgree.getDocument());
        Assertions.assertNotNull(rentAgree.getDiscountPercent());
        Assertions.assertNotNull(rentAgree.getFinalAmount());
        Assertions.assertNotNull(rentAgree.getDiscountedAmt());
        Assertions.assertNotNull(rentAgree.getOrder());
        Assertions.assertNotNull(rentAgree.getDueDate());
        Assertions.assertNotNull(rentAgree.getPrediscountTotal());
    }

    /**
     * Assert that all RentalAgreement values match expected values
     * @param rentAgmt
     * @param toolCode
     * @param toolType
     * @param brand
     * @param rentalDays
     * @param chargeDays
     * @param checkoutDate
     * @param dueDate
     * @param dailyRentalCharge
     * @param preDiscountCharge
     * @param discountPer
     * @param discountAmt
     * @param finalCharge
     */
    private void assertValuesEqual(RentalAgreement rentAgmt, String toolCode, ToolType toolType, String brand,
                                   int rentalDays, int chargeDays,  LocalDate checkoutDate, LocalDate dueDate,
                                   double dailyRentalCharge, double preDiscountCharge, int discountPer,
                                   double discountAmt, double finalCharge) {

        Order order = rentAgmt.getOrder();
        Tool tool = order.getTools().get(0);

        Assertions.assertEquals(toolCode, tool.getToolCode());
        Assertions.assertEquals(toolType, tool.getToolType());
        Assertions.assertEquals(brand, tool.getBrand());
        Assertions.assertEquals(rentalDays, order.getDaysRented());
        Assertions.assertEquals(chargeDays, rentAgmt.getToolChargeDaysMap().get(tool.getToolCode()));
        Assertions.assertEquals(checkoutDate, order.getCheckoutDay());
        Assertions.assertEquals(dueDate, order.getDueDate());
        Assertions.assertEquals(dailyRentalCharge, tool.getDailyCharge());
        Assertions.assertEquals(preDiscountCharge, rentAgmt.getPrediscountTotal());
        Assertions.assertEquals(discountPer, rentAgmt.getDiscountPercent());
        Assertions.assertEquals(discountAmt, rentAgmt.getDiscountedAmt());
        Assertions.assertEquals(finalCharge, rentAgmt.getFinalAmount());
    }

    /**
     * Create tool object for testing
     * @param toolType
     * @param toolCode
     * @param brand
     * @return
     */
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

        Tool tool = new Tool(toolType, toolCode, brand, dailyPrice, wkdChg, holChg);

        return tool;
    }

    /**
     * Create RentalAgreement test object
     * @param order
     * @param discountPercentage
     * @param holidays
     * @return
     */
    private RentalAgreement createRentalAgreement(Order order, int discountPercentage, List<LocalDate> holidays) {
        RentalAgreement rentAgree = new RentalAgreement(order, discountPercentage, holidays);

        return  rentAgree;
    }

    /**
     * Create date in LocalDate format
     * @param date
     * @return
     */
    private LocalDate createDate(String date) {
        LocalDate localDate = LocalDate.parse(date);

        return localDate;
    }

    /**
     * Create holidays for year of rental
     * @param year
     * @return
     * @throws Exception
     */
    private List<LocalDate> createHolidays(int year) throws Exception {
        LocalDate indepDay = LocalDate.parse(year + "-07-04");
        YearMonth laborDayYearMonth = YearMonth.parse(year + "-09");
        LocalDate laborDay = DaysOfWeekUtil.findFirstOfMonth(laborDayYearMonth, DayOfWeek.MONDAY);

        return Arrays.asList(indepDay, laborDay);
    }
}