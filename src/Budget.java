/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026  
 * Description: This class represents the Budget component of the Budget Tracker application. It manages the user's transactions, category allocations, and provides methods to add income and expenses,
 * move money between categories, and generate a budget summary. The class interacts with the CategoryManager to ensure that category allocations are properly managed and updated based on user actions.
 * It also includes methods to handle user input for adding transactions and managing category allocations, ensuring that the budget remains accurate and up-to-date.
 * The Budget class is a central part of the application's functionality, allowing users to effectively track and manage their finances.
 */
import java.util.ArrayList;

public class Budget {

    private ArrayList<Transaction> transactions;
    private ArrayList<CategoryAllocation> allocations = new ArrayList<CategoryAllocation>();
   

    public Budget(){
        this.transactions = new ArrayList<Transaction>();   
        allocations.add(new CategoryAllocation("Everyday Use", 0));     
    }

    public ArrayList<CategoryAllocation> getAllocations() {
        return allocations;
    }

    

    private CategoryAllocation getOrCreateAllocation(String categoryName, CategoryManager categoryManager) {
        categoryManager.getCategoryByName(categoryName);
        for (CategoryAllocation ca : allocations) {
            if (ca.getCategoryName().equalsIgnoreCase(categoryName)) {
                return ca;
            }
        }
        // If not found, create a new allocation for the category
        
        CategoryAllocation newAllocation = new CategoryAllocation(categoryName, 0);
        allocations.add(newAllocation);
        return newAllocation;
    }

    public void addIncome(double amount, String description, String date, CategoryManager catManager){
        if (amount <= 0) {
            throw new IllegalArgumentException("Income amount must be positive");
        }
        
        transactions.add(new Income(amount, description, date));
        CategoryAllocation everyDay = getOrCreateAllocation("Everyday Use", catManager);
        everyDay.addAllocation(amount);
        

    }

    public void addExpense(double amount, String description, String date, CategoryManager catManager){
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        
        
        CategoryAllocation everyDay = getOrCreateAllocation("Everyday Use", catManager);
        if (amount > everyDay.getAllocatedAmount()) {
            throw new IllegalArgumentException("Expense amount exceeds available funds in 'Everyday Use' category, consider moving funds from other categories or reducing the expense amount.");
        }
        transactions.add(new Expense(amount, description, date));
        everyDay.removeAllocation(amount);
        
    }
    

    public double getTotalIncome(){
        double total = 0;
        for(Transaction t : transactions){
            if(t.getNetAmount() > 0){
                total += t.getNetAmount();
            }
           
        }
        return total;
    }
    
    public double getTotalExpense(){
        double total = 0;
        
        for(Transaction t : transactions){
            if(t.getNetAmount() < 0){
                total += Math.abs(t.getNetAmount());
            }
            
        }
        return total;
    }   

    public double getUnallocatedFunds(){
        for (CategoryAllocation ca : allocations) {
            if (ca.getCategoryName().equalsIgnoreCase("Everyday Use")) {
                return ca.getAllocatedAmount();
            }
        }
        return 0;

    }
    
    public double getNetBalance() {
        return getTotalIncome() - getTotalExpense();
    }
    
    
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void moveMoneyBetweenCategories(
        String fromCategory,
        String toCategory,
        double amount,
        CategoryManager categoryManager) {

    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive.");
    }

    
    categoryManager.getCategoryByName(fromCategory);
    categoryManager.getCategoryByName(toCategory);

  
    CategoryAllocation fromAlloc = getOrCreateAllocation(fromCategory, categoryManager);
    CategoryAllocation toAlloc = getOrCreateAllocation(toCategory, categoryManager);

    
    if (fromAlloc.getAllocatedAmount() < amount) {
        throw new IllegalStateException("Not enough funds in " + fromCategory);
    }

    
    fromAlloc.removeAllocation(amount);
    toAlloc.addAllocation(amount);
}

   public void moveMoneyBetweenCategoriesFromInput(
        InputHelper input,
        CategoryManager categoryManager) {

        String fromCategory = input.getRequiredString("Move FROM category: ");
        String toCategory = input.getRequiredString("Move TO category: ");
        double amount = input.getPositiveDouble("Enter amount to move: ");
        moveMoneyBetweenCategories(fromCategory, toCategory, amount, categoryManager);
        System.out.println("Money moved successfully.");
    }

    public void addIncomeFromInput(InputHelper input, CategoryManager catManager) {
    double amount = input.getPositiveDouble("Enter income amount: ");
    String description = input.getRequiredString("Enter income description: ");
    String date = input.getRequiredString("Enter income date (YYYY-MM-DD): ");    
    addIncome(amount, description, date, catManager);
    System.out.println("Income added successfully.");

    }

    public void addExpenseFromInput(InputHelper input, CategoryManager catManager) {
        double amount = input.getPositiveDouble("Enter expense amount: ");
        String description = input.getRequiredString("Enter expense description: ");
        String date = input.getRequiredString("Enter expense date (YYYY-MM-DD): ");

        addExpense(amount, description, date, catManager);
        System.out.println("Expense added successfully.");
    }

    public String printBudgetSummary() {

        printTransactions();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total Income: $%.2f\n", getTotalIncome()));
        sb.append(String.format("Total Expenses: $%.2f\n", getTotalExpense()));
        sb.append(String.format("Net Balance: $%.2f\n", getNetBalance()));
        sb.append(String.format("Unallocated Funds: $%.2f\n", getUnallocatedFunds()));
        sb.append("Categories:\n");
        for (CategoryAllocation ca : allocations) {
            sb.append(ca.toString()).append("\n");
        }
        return sb.toString();
    }

    public void printTransactions() {
    for (Transaction t : transactions) {
        System.out.println(t.printSummary());
    }
}



    
    


}
