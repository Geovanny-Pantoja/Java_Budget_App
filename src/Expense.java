public class Expense extends Transaction {

    public Expense(double amount, String description, String date ) {
        super(amount, description, date);
        
    }

    @Override
    public double getNetAmount(){
        return -getAmount();
    }

    

}
