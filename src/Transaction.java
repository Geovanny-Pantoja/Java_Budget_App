public abstract class Transaction implements Persistable{

    private int id;
    private double amount;
    private String description;
    private String date;

    public Transaction(double amount, String descritption, String date){
        this.amount = amount;
        this.description = descritption;

    }
    @Override
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }  

    public double getAmount() {
        return amount;
    }

    public abstract double getNetAmount();    

}
