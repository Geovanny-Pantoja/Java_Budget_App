package model;
/*************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the Income component of the Budget Tracker application. It extends the Transaction class and provides a specific implementation for income transactions.
 */
public class Income extends Transaction {

    public Income() {
    }
    public Income(int id, int user_id, double amount, String description, String date) {
        super(id, user_id, amount, description, date);
    }

    public Income(int user_id, double amount, String description, String date){
        super(user_id, amount, description, date);
    }
    @Override
    public double getNetAmount(){
        return getAmount(); 
    }
    

}
