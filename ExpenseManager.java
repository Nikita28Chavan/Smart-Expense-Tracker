public interface ExpenseManager {
    void addExpense(double amount, String category, String description);
    void viewExpenses();
    void viewCategorySummary();
    void viewMonthlyTotal();
    void deleteExpense(int id);
}
