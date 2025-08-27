import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private double amount;
    private String category;
    private String description;
    private String date;

    public Expense(int id, double amount, String category, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return id + "," + amount + "," + category + "," + description + "," + date;
    }
}
