import java.sql.Connection;

import Controllers.UserController;
import dao.CategoryAllocationDAO;
import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.IncomeDAO;
import dao.UserDAO;
import manager.AllocationsManager;
import manager.CategoryManager;
import manager.TransactionManager;
import manager.UserManager;
import model.Category;
import util.InputHelper;
import util.OutputHelper;

/********
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This is the main class of the Budget Tracker application. It serves as the entry point and manages the overall flow of the program. It interacts with the UserManager,
 * CategoryManager, InputHelper, and OutputHelper classes to provide a seamless user experience for managing budgets, categories, and transactions.
 * The main method initializes the necessary components and enters a loop to display the main menu, allowing users to list users, create new users, select existing users, and exit the application.
 * When a user is selected, it transitions to a user-specific menu where they can manage their budget, add income and expenses, manage category allocations, and view transactions.
 * The class also contains helper methods to run the user menu, category menu, and allocation menu, ensuring that the user interface remains organized and intuitive.
 */

public class App {   
    
    
    public static void main(String[] args) throws Exception {
        final String DB_NAME = "budget_tracker.db";
        Connection conn = SQLiteDatabase.connect(DB_NAME);
        InputHelper input = new InputHelper();
        OutputHelper output = new OutputHelper();
        UserManager userManager = new UserManager();
        CategoryManager catManager = new CategoryManager();
        AllocationsManager allocMan = new AllocationsManager();        
        Category category = new Category();
        TransactionManager transactionManager = new TransactionManager();
        UserController controller = new UserController(userManager, catManager, transactionManager, category, allocMan);

        if (CategoryDAO.createCategoryTable(conn) && CategoryAllocationDAO.createCategoryAllocationTable(conn) && UserDAO.createUserTable(conn) && IncomeDAO.createIncomeTable(conn) && ExpenseDAO.createExpenseTable(conn)) {
            CategoryDAO.ensureDefaultCategories(conn);    
            controller.runMainMenu(input, output, conn);        
        } else {
            System.out.println("Failed to create database tables.");        
        }       
       
    }    
   
   
}
