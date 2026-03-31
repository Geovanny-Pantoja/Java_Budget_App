/*********************************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the UserController component of the Budget Tracker application. 
 * It serves as the main controller for user interactions, managing the flow of the application and coordinating between the UserManager, CategoryManager, TransactionManager, and AllocationsManager.
 * The UserController handles the main menu and user dashboard, allowing users to view their budget summary, add income and expenses, manage category allocations, and view transactions. 
 * It also provides methods for managing categories and handling user input for various actions within the application.
 * The class is responsible for ensuring that the user interface is responsive and that user actions are properly
 */
package Controllers;

import manager.AllocationsManager;
import manager.Budget;
import manager.CategoryManager;
import manager.TransactionManager;
import manager.UserManager;
import model.CategoryAllocation;
import model.User;
import model.Category;
import util.InputHelper;
import util.OutputHelper;
import java.sql.Connection;
import java.sql.SQLException;

public class UserController {

    private final UserManager userManager;
    private final CategoryManager catManager;
    private final TransactionManager transactionManager;

    private final Category category;
    private final AllocationsManager allocMan;

    public UserController(UserManager userManager, CategoryManager catManager, TransactionManager transactionManager,
            Category category, AllocationsManager allocMan) {
        this.userManager = userManager;
        this.catManager = catManager;
        this.transactionManager = transactionManager;

        this.category = category;
        this.allocMan = allocMan;

    }

    public void runMainMenu(InputHelper input, OutputHelper output, Connection conn) throws SQLException {
        boolean running = true;

        while (running) {
            try {
                output.clearScreen();
                output.printHeader();
                output.printMainMenuInstructions();
                output.printDivider();
                output.showMainMenu();
                String choice = input.getRequiredString("Enter your choice: ");
                switch (choice) {
                    case "1":
                        output.clearScreen();
                        userManager.ListUsers(conn);
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "2":
                        output.clearScreen();
                        try {
                            userManager.createUserFromInput(input, conn);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error creating user: " + e.getMessage());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "3":
                        try {
                            User currentUser = userManager.selectUserFromInput(input, conn);
                            Budget userBudget = new Budget(currentUser.getId(), conn, catManager);
                            runUserDashboard(conn, input, output, catManager, currentUser, transactionManager,
                                    userBudget,
                                    category);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error selecting user: " + e.getMessage());
                            output.pause();
                            input.waitForEnter();
                        }
                        break;
                    case "4":
                        output.clearScreen();
                        userManager.updateUserFromInput(input, conn);
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "5":
                        output.clearScreen();
                        userManager.deleteUserFromInput(input, conn);
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "6":
                        running = false;
                        output.clearScreen();
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        output.pause();
                        input.waitForEnter();
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                output.pause();
                input.waitForEnter();
            }
        }
    }

    private void runUserDashboard(Connection conn, InputHelper input, OutputHelper output,
            CategoryManager catManager,
            User currentUser, TransactionManager transactionManager, Budget budget, Category category) {
        boolean userMenuRunning = true;
        while (userMenuRunning) {
            try {
                output.clearScreen();
                output.showUserDashboardMenu(currentUser.getUsername());
                String choice = input.getRequiredString("Enter your choice: ");
                switch (choice) {
                    case "1":
                        // View budget summary
                        output.clearScreen();

                        output.clearScreen();
                        try {
                            double totalIncome = transactionManager.getTotalIncome(conn, currentUser.getId());
                            double totalExpense = transactionManager.getTotalExpense(conn, currentUser.getId());
                            double netBalance = transactionManager.getNetBalance(conn, currentUser.getId());

                            System.out.println(
                                    currentUser.getBudget().printBudgetSummary(totalIncome, totalExpense, netBalance));

                        } catch (SQLException e) {
                            System.out.println("Error loading budget summary: " + e.getMessage());
                        }

                        output.pause();
                        input.waitForEnter();
                        break;
                    case "2":
                        // Add income
                        output.clearScreen();
                        try {
                            transactionManager.addIncomeFromInput(conn, input, currentUser, catManager, budget);
                        } catch (Exception e) {
                            System.out.println("Error adding income: " + e.getMessage());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "3":
                        // Add expense
                        output.clearScreen();
                        try {
                            transactionManager.addExpenseFromInput(conn, input, currentUser, catManager, budget);
                        } catch (Exception e) {
                            System.out.println("Error adding expense: " + e.getMessage());

                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "4":
                        // Manage category allocations
                        RunmanageAllocationsMenu(input, output, catManager, currentUser, conn, allocMan);
                        break;
                    case "5":
                        // Manage categories
                        runCategoryMenu(input, output, catManager, category, conn);
                        break;
                    case "6":
                        // View transactions
                        output.clearScreen();
                        output.printSection(currentUser.getUsername() + "'s Transactions");

                        try {
                            transactionManager.printTransactions(conn, currentUser.getId());
                        } catch (SQLException e) {
                            System.out.println("Error loading transactions: " + e.getMessage());
                        }

                        output.pause();
                        input.waitForEnter();

                        break;
                    case "7":
                        userMenuRunning = false; // Back to main menu
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        output.pause();
                        input.waitForEnter();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                output.pause();
                input.waitForEnter();
            }
        }

    }

    private void runCategoryMenu(InputHelper input, OutputHelper output, CategoryManager catManager, Category category,
            Connection conn) {
        boolean categoryMenuRunning = true;
        while (categoryMenuRunning) {
            try {
                output.clearScreen();
                output.showCategoryMenu();
                String choice = input.getRequiredString("Enter your choice: ");
                switch (choice) {
                    case "1":
                        // List categories
                        output.clearScreen();
                        output.printSection("Categories");
                        for (Category c : catManager.getCategories(conn)) {
                            System.out.println(c.toString());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "2":
                        // Add category
                        output.clearScreen();
                        catManager.addCategoryFromInput(conn, input);
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "3":
                        // Update category - you would implement this method in CategoryManager
                        output.clearScreen();
                        try {
                            catManager.updateCategoryFromInput(input, conn);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error updating category: " + e.getMessage());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "4":
                        categoryMenuRunning = false; // Back to user menu
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        output.pause();
                        input.waitForEnter();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                output.pause();
                input.waitForEnter();
            }
        }
    }

    private void RunmanageAllocationsMenu(InputHelper input, OutputHelper output, CategoryManager catManager,
            User currentUser, Connection conn, AllocationsManager allocMan) {
        boolean allocationMenuRunning = true;
        while (allocationMenuRunning) {
            try {
                output.clearScreen();
                output.printAllocationMenu();
                String choice = input.getRequiredString("Enter your choice: ");
                switch (choice) {
                    case "1":
                        // View allocations
                        output.clearScreen();
                        output.printSection("Current Allocations");
                        System.out.println("Current User: " + currentUser.getUsername());                               
                        System.out.println(
                                "NUmber of Allocations: " + currentUser.getBudget().getAllocations().size());
                        System.out.println("--------------------------------------------");

                        for (CategoryAllocation ca : currentUser.getBudget().getAllocations()) {
                            System.out.println(ca.toString());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;

                    case "2":
                        // Move money between categories
                        output.clearScreen();
                        allocMan.moveMoneyBetweenCategoriesFromInput(conn, input, currentUser.getId(), catManager,
                                currentUser.getBudget());
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "3":
                        allocationMenuRunning = false; // Back to user menu
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        output.pause();
                        input.waitForEnter();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                output.pause();
                input.waitForEnter();
            }
        }
    }

}
