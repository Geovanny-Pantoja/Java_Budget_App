/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026  
 * Description: This class represents the Expense component of 
 * the Budget Tracker application. It extends the Transaction class and provides a 
 * specific implementation for expenses.
 */
public class Expense extends Transaction {

    public Expense(double amount, String description, String date ) {
        super(amount, description, date);
        
    }

    @Override
    public double getNetAmount(){
        return -getAmount();
    }    

}
