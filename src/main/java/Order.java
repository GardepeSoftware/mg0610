import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<Tool> tools;
    private int daysRented;
    private LocalDate checkoutDay;
    private LocalDate dueDate;

    public Order(List<Tool> tools, LocalDate chkoutDay, int daysRented) {
        this.tools = tools;
        this.checkoutDay = chkoutDay;
        this.daysRented = daysRented;
        this.dueDate = chkoutDay.plusDays(daysRented);
    }

    public List<Tool> getTools() {
        return tools;
    }

    public LocalDate getCheckoutDay() {
        return checkoutDay;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }
}
