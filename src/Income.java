/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the Income component of the Budget Tracker application. It extends the Transaction class and provides a specific implementation for income transactions.
 */
public class Income extends Transaction {

    public Income(double amount, String description, String date){
        super(amount, description, date);
    }
    @Override
    public double getNetAmount(){
        return getAmount(); 
    }
    

}
