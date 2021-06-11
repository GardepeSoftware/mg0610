public class Tool {
    private String toolCode;
    private ToolType toolType;
    private String brand;
    private double dailyCharge;
    private boolean weekendFree;
    private boolean holidayFree;

    public Tool(ToolType toolType, String toolCode, String brand, double dailyCharge, boolean weekendFree,
                boolean holidayFree) {
        this.toolType = toolType;
        this.toolCode = toolCode;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.weekendFree = weekendFree;
        this.holidayFree = holidayFree;
    }

    public String getToolCode() {
        return toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public String getBrand() {
        return brand;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekendFree() {
        return weekendFree;
    }

    public boolean isHolidayFree() {
        return holidayFree;
    }
}
