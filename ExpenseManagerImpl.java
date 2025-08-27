import java.io.*;
import java.util.*;

public class ExpenseManagerImpl implements ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();
    private static final String FILE_NAME = "expenses.txt";
    private int nextId = 1;

    public ExpenseManagerImpl() {
        loadExpenses();
    }

    @Override
    public void addExpense(double amount, String category, String description) {
        Expense expense = new Expense(nextId++, amount, category, description);
        expenses.add(expense);
        saveExpenses();
        System.out.println("Expense added successfully!");
    }

    @Override
    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\n===== ALL EXPENSES =====");
        for (Expense e : expenses) {
            System.out.println("ID: " + e.getId() + " | Amount: " + e.getAmount() +
                    " | Category: " + e.getCategory() + " | Description: " + e.getDescription() +
                    " | Date: " + e.getDate());
        }
    }

    @Override
    public void viewCategorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Map<String, Double> categoryTotal = new HashMap<>();
        for (Expense e : expenses) {
            categoryTotal.put(e.getCategory(), categoryTotal.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }

        System.out.println("\n===== CATEGORY-WISE SUMMARY =====");
        for (Map.Entry<String, Double> entry : categoryTotal.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    @Override
    public void viewMonthlyTotal() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Map<String, Double> monthlyTotal = new HashMap<>();
        for (Expense e : expenses) {
            String month = e.getDate().substring(0, 7); // yyyy-MM
            monthlyTotal.put(month, monthlyTotal.getOrDefault(month, 0.0) + e.getAmount());
        }

        System.out.println("\n===== MONTHLY TOTAL =====");
        for (Map.Entry<String, Double> entry : monthlyTotal.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    @Override
    public void deleteExpense(int id) {
        Iterator<Expense> iterator = expenses.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Expense e = iterator.next();
            if (e.getId() == id) {
                iterator.remove();
                saveExpenses();
                System.out.println("Expense deleted successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Expense ID not found!");
        }
    }

    private void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    double amount = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    String desc = parts[3];
                    Expense expense = new Expense(id, amount, category, desc);
                    expenses.add(expense);
                    nextId = Math.max(nextId, id + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
