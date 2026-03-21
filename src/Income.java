public class Income extends Transaction {

    public Income(double amount, String description, String date){
        super(amount, description, date);
    }
    @Override
    public double getNetAmount(){
        return getAmount(); 
    }
    

}
