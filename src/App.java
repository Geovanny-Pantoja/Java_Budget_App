public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        

        User geo = new User("geo");

        geo.getBudget().addExpense(300, "Water Bill", "12 Mar 2024");
        geo.getBudget().addIncome(500, "earnings", "12 Mar 1981");
        geo.getBudget().addIncome(600, "gift", "mar 12 1982");
        geo.getBudget().addExpense(200, "Grocery", "24 Mar 2024");

        double totalExpenses = geo.getBudget().getTotalExpense();
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);  
        double totalIncome = geo.getBudget().getTotalIncome();
        System.out.printf("Total Income: $%.2f%n", totalIncome);
        double availMoney = geo.getBudget().getUnallocatedFunds();
        System.out.printf("Available money to allocate: $%.2f%n", availMoney);

        geo.getBudget().addCategory("Car");
        geo.getBudget().allocateMoney("Car", 300.00);
        geo.getBudget().addCategory("House");
        geo.getBudget().allocateMoney("House", 200.00);
        double housemoney = 0;
        for(Category c : geo.getBudget().getCategories()){
            if (c.getName().equalsIgnoreCase("house")){
             housemoney = c.getAllocatedAmount();   
            }
        }
        
        System.out.printf("Money in House for geo: %.2f%n", housemoney);

        
    }
}
