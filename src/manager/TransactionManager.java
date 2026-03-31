/**************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the TransactionManager component of the Budget Tracker application.
 * It defines the structure and properties of a transaction.
 */
package manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ExpenseDAO;
import dao.IncomeDAO;
import model.Expense;
import model.Income;
import model.Transaction;
import model.User;
import util.InputHelper;

public class TransactionManager {

    public void addIncomeFromInput(Connection conn, InputHelper input, User user, CategoryManager catManager, Budget budget) throws SQLException {
        double amount = input.getPositiveDouble("Enter income amount: ");
        String description = input.getRequiredString("Enter income description: ");
        String date = input.getValidDate("Enter income date (YYYY-MM-DD): ");
        budget.addIncome(conn, user.getId(), amount, description, date, catManager);
        user.setBudget(new Budget(user.getId(), conn, catManager));
        System.out.println("Income added successfully.");

    }

    public void addExpenseFromInput(Connection conn, InputHelper input, User user, CategoryManager catManager, Budget budget)
            throws SQLException {
        double amount = input.getPositiveDouble("Enter expense amount: ");
        String description = input.getRequiredString("Enter expense description: ");
        String date = input.getValidDate("Enter expense date (YYYY-MM-DD): ");
        budget.addExpense(conn, user.getId(), amount, description, date, catManager);
        user.setBudget(new Budget(user.getId(), conn, catManager));
        System.out.println("Expense added successfully.");
    }

    public void listTransactionsForUser(Connection conn, int userId) throws SQLException {
    List<Income> incomes = IncomeDAO.getIncomesByUserId(conn, userId);
    List<Expense> expenses = ExpenseDAO.getExpensesByUserId(conn, userId);

    System.out.println("=== Incomes ===");
    if (incomes.isEmpty()) {
        System.out.println("No incomes found.");
    } else {
        for (Income income : incomes) {
            System.out.println(income);
        }
    }

    System.out.println("\n=== Expenses ===");
    if (expenses.isEmpty()) {
        System.out.println("No expenses found.");
    } else {
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }
}


public Object getTransactionById(Connection conn, int id) throws SQLException {
    Income income = IncomeDAO.getIncomeById(conn, id);
    if (income != null) return income;

    Expense expense = ExpenseDAO.getExpenseById(conn, id);
    if (expense != null) return expense;

    return null;
}

public void printTransactions(Connection conn, int userId) throws SQLException {
        List<Income> incomes = IncomeDAO.getIncomesByUserId(conn, userId);
        List<Expense> expenses = ExpenseDAO.getExpensesByUserId(conn, userId);

        System.out.println("=== Incomes ===");
        if (incomes.isEmpty()) {
            System.out.println("No incomes found.");
        } else {
            for (Income income : incomes) {
                System.out.println(income.toString());
            }
        }

        System.out.println("\n=== Expenses ===");
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense.toString());
            }
        }
    }

    public List<Income> getIncomesForUser(Connection conn, int userId) throws SQLException {
        return IncomeDAO.getIncomesByUserId(conn, userId);
    }

    public List<Expense> getExpensesForUser(Connection conn, int userId) throws SQLException {
        return ExpenseDAO.getExpensesByUserId(conn, userId);
    }

    public List<Transaction> getAllTransactionsForUser(Connection conn, int userId) throws SQLException {
        List<Transaction> all = new ArrayList<>();

        all.addAll(IncomeDAO.getIncomesByUserId(conn, userId));
        all.addAll(ExpenseDAO.getExpensesByUserId(conn, userId));

        return all;
    }

    public Transaction getTransactionById(Connection conn, int userId, int id) throws SQLException {
        // Check incomes first
        List<Income> incomes = getIncomesForUser(conn, userId);
        for (Income inc : incomes) {
            if (inc.getId() == id) {
                return inc;
            }
        }

        // Check expenses
        List<Expense> expenses = getExpensesForUser(conn, userId);
        for (Expense exp : expenses) {
            if (exp.getId() == id) {
                return exp;
            }
        }

        return null;
    }

    public double getTotalIncome(Connection conn, int userId) throws SQLException {
        return getIncomesForUser(conn, userId)
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }

    public double getTotalExpense(Connection conn, int userId) throws SQLException {
        return getExpensesForUser(conn, userId)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getNetBalance(Connection conn, int userId) throws SQLException {
        return getTotalIncome(conn, userId) - getTotalExpense(conn, userId);
    }



}
