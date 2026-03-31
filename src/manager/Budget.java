package manager;

import java.sql.Connection;
import java.sql.SQLException;
/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026  
 * Description: This class represents the Budget component of the Budget Tracker application. It manages the user's transactions, category allocations, and provides methods to add income and expenses,
 * move money between categories, and generate a budget summary. The class interacts with the CategoryManager to ensure that category allocations are properly managed and updated based on user actions.
 * It also includes methods to handle user input for adding transactions and managing category allocations, ensuring that the budget remains accurate and up-to-date.
 * The Budget class is a central part of the application's functionality, allowing users to effectively track and manage their finances.
 */
import java.util.ArrayList;

import dao.CategoryAllocationDAO;
import dao.ExpenseDAO;
import dao.IncomeDAO;
import model.Category;
import model.CategoryAllocation;
import model.Expense;
import model.Income;

public class Budget {
    private int userId;

    private ArrayList<CategoryAllocation> allocations = new ArrayList<CategoryAllocation>();

    public Budget(int userId, Connection conn, CategoryManager catManager) throws SQLException {
        this.userId = userId;
        loadAllocations(conn, catManager);

    }

    private void loadAllocations(Connection conn, CategoryManager catManager) throws SQLException {
        this.allocations = CategoryAllocationDAO.getCategoryAllocationsByUserId(conn, userId);
        for (CategoryAllocation ca : allocations) {
            Category cat = catManager.getCategories(conn).stream().filter(c -> c.getId() == ca.getCategory_id())
                    .findFirst()
                    .orElse(null);
            if (cat != null) {
                ca.setCategoryName(cat.getName());
            }
        }
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<CategoryAllocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(ArrayList<CategoryAllocation> allocations) {
        this.allocations = allocations;
    }

    public void addIncome(Connection conn, int userId, double amount, String description, String date,
            CategoryManager catManager) throws SQLException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Income amount must be positive");
        }

        Income income = new Income(userId, amount, description, date);
        IncomeDAO.addIncome(conn, income);

        CategoryAllocation everyDay = getOrCreateAllocation("Everyday Use", catManager, conn);
        everyDay.addAllocation(amount);
        CategoryAllocationDAO.updateCategoryAllocation(conn, everyDay);

       
    }

    public void addExpense(Connection conn, int userId, double amount, String description, String date,
            CategoryManager catManager) throws SQLException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }

        CategoryAllocation everyDay = getOrCreateAllocation("Everyday Use", catManager, conn);
        if (amount > everyDay.getAllocatedAmount()) {
            throw new IllegalArgumentException(
                    "Expense amount exceeds available funds in 'Everyday Use' category, " +
                            "consider moving funds from other categories or reducing the expense amount.");
        }

        Expense expense = new Expense(userId, amount, description, date);
        ExpenseDAO.addExpense(conn, expense);

        everyDay.removeAllocation(amount);
        CategoryAllocationDAO.updateCategoryAllocation(conn, everyDay);

        
    }

    private CategoryAllocation getOrCreateAllocation(String categoryName, CategoryManager catManager, Connection conn)
            throws SQLException {
        Category category = catManager.getCategoryByName(conn, categoryName);
        int categoryId = category.getId();

        for (CategoryAllocation ca : allocations) {
            if (ca.getCategory_id() == categoryId) {
                return ca;
            }
        }

        // Check DB
        CategoryAllocation ca = CategoryAllocationDAO.getCategoryAllocationByUserIdAndCategoryId(conn, userId,
                categoryId);
        if (ca != null) {
            allocations.add(ca);
            return ca;
        }

        // If not found, create a new allocation with 0
        CategoryAllocation newAlloc = new CategoryAllocation(userId, categoryId, 0, categoryName);
        CategoryAllocationDAO.addCategoryAllocation(conn, newAlloc);
        allocations.add(newAlloc);
        return newAlloc;
    }

    public double getUnallocatedFunds() {
        for (CategoryAllocation ca : allocations) {
            if (ca.getCategoryName().equalsIgnoreCase("Everyday Use")) {
                return ca.getAllocatedAmount();
            }
        }
        return 0;

    }

    // good
    public boolean transferAllocations(
            Connection conn,
            int userId,
            String fromCategory,
            String toCategory,
            double amount,
            CategoryManager categoryManager) throws SQLException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        Category fromCat = categoryManager.getCategoryByName(conn, fromCategory);
        if (fromCat == null) {
            throw new IllegalStateException("Category does not exist: " + fromCategory);
        }

        Category toCat = categoryManager.getCategoryByName(conn, toCategory);
        if (toCat == null) {
            throw new IllegalStateException("Category does not exist: " + toCategory);
        }

        CategoryAllocation fromAlloc = getOrCreateAllocation(fromCategory, categoryManager, conn);

        if (fromAlloc == null) {
            throw new IllegalStateException("No allocation found for category: " + fromCategory);
        }
        CategoryAllocation toAlloc = getOrCreateAllocation(toCategory, categoryManager, conn);
        if (toAlloc == null) {
            throw new IllegalStateException("No allocation found for category: " + toCategory);
        }

        if (fromAlloc.getAllocatedAmount() < amount) {
            throw new IllegalStateException("Not enough funds in " + fromCategory);
        }

        fromAlloc.removeAllocation(amount);
        CategoryAllocationDAO.updateCategoryAllocation(conn, fromAlloc);

        toAlloc.addAllocation(amount);
        CategoryAllocationDAO.updateCategoryAllocation(conn, toAlloc);
        return true;
    }

    

    public String printBudgetSummary(double totalIncome, double totalExpense, double netBalance) {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total Income: $%.2f\n", totalIncome));
        sb.append(String.format("Total Expenses: $%.2f\n", totalExpense));
        sb.append(String.format("Net Balance: $%.2f\n", netBalance));
        sb.append(String.format("Unallocated Funds: $%.2f\n", getUnallocatedFunds()));
        sb.append("Categories:\n");
        for (CategoryAllocation ca : allocations) {
            sb.append(ca.toString()).append("\n");
        }
        return sb.toString();
    }

}
