package util;
/*****************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the OutputHelper component of the Budget Tracker application. 
 * It provides methods for displaying various menus and prompts to the user, as well as a method for clearing the console screen. 
 * The OutputHelper class is responsible for ensuring that the user interface is clear and easy to navigate, allowing users to interact with the application effectively.
 * It includes methods for showing the main menu, user dashboard menu, category management menu, and allocation management menu, 
 * as well as methods for printing dividers and pausing the output until the user is ready to continue.
 * 
 */
public class OutputHelper {

    public void printDivider(){
        System.out.println("*".repeat(70));
    }

    public void printHeader(){
        printDivider();
        System.out.println("Welcome Budget Tracker");
        System.out.println("Created By Geovanny Pantoja");
        printDivider();
    }

    public void printSection(String title){
        System.out.println("\n=== " + title + " ===\n");
    }

    public void showMainMenu(){
        printSection("Main Menu");
        System.out.println("1. List users");
        System.out.println("2. Create user");
        System.out.println("3. Select user");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. exit");        
    }
    public void showUserDashboardMenu(String username) {
        printSection("Budget management for " + username);  
        printBudgetMenuInstructions(); 
        printDivider();
        System.out.println();
        System.out.println("1. View budget summary");
        System.out.println("2. Add income");
        System.out.println("3. Add expense");
        System.out.println("4. Manage Category allocations");
        System.out.println("5. Manage categories");
        System.out.println("6. View transactions");        
        System.out.println("7. Back to main menu");
        System.out.print("Choose an option: ");
    }
    public void showCategoryMenu() {
        System.out.println();
        printSection("Manage Categories");
        printCategoryMenuInstructions();
        printDivider();
        System.out.println("1. List categories");
        System.out.println("2. Add category"); 
        System.out.println("3. Update category");       
        System.out.println("4. Back");
        System.out.print("Choose an option: ");
    }

    public void printAllocationMenu() {
    printSection("Manage Allocations");
    printAllocationMenuInstructions();
    printDivider();
    System.out.println("1. View Allocations");   
    System.out.println("2. Move Money Between Categories");
    System.out.println("3. Back");
}


    public void pause(){
        System.out.print("\nPress Enter to continue....");
    }
   

    public void printMainMenuInstructions(){
        System.out.println("First lets create or select a user. Plese choose an option from the menu below:");
        
    }

    public void printBudgetMenuInstructions(){
        System.out.println("What would you like to do today? Please choose an option from the menu below:");
    }

    public void printAllocationMenuInstructions() {
        System.out.println("In this section you can view and move money between categories.\nIf you want to see the different category options, please go back to the previous menu and select \"Manage Categories\".\nPlease choose an option from the menu below:");
    }
    public void printCategoryMenuInstructions() {
        System.out.println("In this section you can view current categories and add new ones.\nPlease choose an option from the menu below:");
    }

    public void clearScreen() {
         System.out.print("\033[H\033[2J");
         System.out.flush();
    }

}
