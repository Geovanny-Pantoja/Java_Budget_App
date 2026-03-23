/********
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This is the main class of the Budget Tracker application. It serves as the entry point and manages the overall flow of the program. It interacts with the UserManager,
 * CategoryManager, InputHelper, and OutputHelper classes to provide a seamless user experience for managing budgets, categories, and transactions.
 * The main method initializes the necessary components and enters a loop to display the main menu, allowing users to list users, create new users, select existing users, and exit the application.
 * When a user is selected, it transitions to a user-specific menu where they can manage their budget, add income and expenses, manage category allocations, and view transactions.
 * The class also contains helper methods to run the user menu, category menu, and allocation menu, ensuring that the user interface remains organized and intuitive.
 */

public class App {   
    
    private static User currentUser;
    public static void main(String[] args) throws Exception {
        
        InputHelper input = new InputHelper();
        OutputHelper output = new OutputHelper();
        UserManager userManager = new UserManager();
        CategoryManager catManager = new CategoryManager();

        
        boolean running = true;
        while (running) {
            output.clearScreen();
            output.printHeader();
            output.printMainMenuInstructions(); 
            output.printDivider();           
            output.showMainMenu();
            String choice = input.getRequiredString("Enter your choice: ");
            switch (choice) {
                case "1":
                    output.clearScreen();
                    userManager.ListUsers();
                    output.pause();
                    input.waitForEnter();
                    break;
                case "2":
                    output.clearScreen();
                    try{
                        userManager.createUserFromInput(input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error creating user: " + e.getMessage());
                    }
                    output.pause();
                    input.waitForEnter();
                    break;
                case "3":
                    try{
                       currentUser = userManager.selectUserFromInput(input);
                        runUserMenu(input, output, catManager);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error selecting user: " + e.getMessage());
                        output.pause();
                        input.waitForEnter();
                    }
                    break;
                case "4":
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
        }
    }   
    
    
    private static void runUserMenu( InputHelper input, OutputHelper output, CategoryManager catManager) {
        boolean userMenuRunning = true;
        while (userMenuRunning) {
            output.clearScreen();            
            output.showUserMenu(currentUser.getUsername());
            String choice = input.getRequiredString("Enter your choice: ");
            switch (choice) {
                case "1":
                    // View budget summary
                    output.clearScreen();
                    output.printSection(currentUser.getUsername() + "'s Budget Summary");                    
                    System.out.println(currentUser.getBudget().printBudgetSummary());
                    output.pause();
                    input.waitForEnter();
                    break;
                case "2":
                    // Add income
                    output.clearScreen();
                    try{ 
                        currentUser.getBudget().addIncomeFromInput(input, catManager);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error adding income: " + e.getMessage());

                    }
                     output.pause();
                    input.waitForEnter();
                    break;
                case "3":
                    // Add expense   
                    output.clearScreen();                 
                    try{ 
                        currentUser.getBudget().addExpenseFromInput(input, catManager);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error adding expense: " + e.getMessage());

                    }  
                    output.pause();
                    input.waitForEnter();
                    break;
                case "4":
                    // Manage category allocations
                    RunmanageAllocationsMenu(input, output, catManager);
                    break;
                case "5":
                    // Manage categories
                    runCategoryMenu(input, output, catManager);
                    break;
                case "6":
                    // View transactions
                    output.clearScreen();
                    output.printSection(currentUser.getUsername() + "'s Transactions");
                    for (Transaction t : currentUser.getBudget().getTransactions()) {
                        System.out.println(t.toString());
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
        }        
        
    }    

    private static void runCategoryMenu(InputHelper input, OutputHelper output, CategoryManager catManager) { 
            boolean categoryMenuRunning = true;
            while (categoryMenuRunning) {
                output.clearScreen();
                output.showCategoryMenu();
                String choice = input.getRequiredString("Enter your choice: ");
                switch (choice) {
                    case "1":
                        // List categories
                        output.clearScreen();
                        output.printSection("Categories");
                        for (Category c : catManager.getCategories()) {
                            System.out.println(c.toString());
                        }
                        output.pause();
                        input.waitForEnter();
                        break;
                    case "2":
                        // Add category
                        output.clearScreen();
                        catManager.addCategoryFromInput(input);                       
                        output.pause();
                        input.waitForEnter();
                        break;                    
                    case "3":
                        categoryMenuRunning = false; // Back to user menu
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        output.pause();
                        input.waitForEnter();
            }
        }
    } 
    
    private static void RunmanageAllocationsMenu(InputHelper input, OutputHelper output, CategoryManager catManager) {
        boolean allocationMenuRunning = true;
        while (allocationMenuRunning) {
            output.clearScreen();
            output.printAllocationMenu();
            String choice = input.getRequiredString("Enter your choice: ");
            switch (choice) {
                case "1":
                    // View allocations
                    output.clearScreen();
                    output.printSection("Current Allocations");
                    for (CategoryAllocation ca : currentUser.getBudget().getAllocations()) {
                        System.out.println(ca.toString());
                    }
                    output.pause();
                    input.waitForEnter();
                    break;               
                    
                case "2":
                    // Move money between categories
                    output.clearScreen();
                    try{
                        currentUser.getBudget().moveMoneyBetweenCategoriesFromInput(input, catManager);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error moving money: " + e.getMessage());

                    }  
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
        }
    }
   
   
}
