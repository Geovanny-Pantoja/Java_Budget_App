import java.util.ArrayList;

public class Budget {

    ArrayList<Transaction> transactions;
    ArrayList<Category> categories;

    public Budget(){
        this.transactions = new ArrayList<Transaction>();
        this.categories = new ArrayList<Category>();
    }

    public void addIncome(double amount, String description, String date){
        Income income = new Income(amount, description, date);
        transactions.add(income);
    }

    public void addExpense(double amount, String description, String date){
        Expense expense = new Expense(amount, description, date);
        transactions.add(expense);    
    }

    public void addCategory(String name){
        for(Category c : categories){
            if(c.getName().trim().equalsIgnoreCase(name)){
                throw new IllegalArgumentException("Category Already exists: " + name);
            }
        }

        Category category = new Category(name);
        categories.add(category);
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

    public double getTotalAllocated(){
        double total = 0;
        for (Category c : categories){
            total += c.getAllocatedAmount();
        }
        return total;
    }

    public double getUnallocatedFunds(){

         double avilFunds = getTotalIncome() - getTotalExpense() - getTotalAllocated();
         return avilFunds;

    }

    public void allocateMoney(String catName, double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be postive");
        }

        Category target = null; 
        for (Category c : categories){
            if(c.getName().trim().equalsIgnoreCase(catName)){
                target = c;
                break;
            }
        }
        if (target == null){
            throw new IllegalArgumentException("Category not found: " + catName);
        }

        if (amount > getUnallocatedFunds()){
            throw new IllegalStateException("Not enough unallocated funds available.");
            
        }

        double newAmnt = target.getAllocatedAmount() + amount;
        target.setAllocatedAmount(newAmnt);


    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


}
