package model;
/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026  
 * Description: This class represents the Expense component of 
 * the Budget Tracker application. It extends the Transaction class and provides a 
 * specific implementation for expenses.
 */
public class Expense extends Transaction {

    public Expense() {
    }

    public Expense(int id, int user_id, double amount, String description, String date) {
        super(id, user_id, amount, description, date);
    }

    public Expense(int user_id, double amount, String description, String date ) {
        super(user_id, amount, description, date);
        
    }

    @Override
    public double getNetAmount(){
        return -getAmount();
    }    

}
