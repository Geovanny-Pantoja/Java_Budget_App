package model;
/**********************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the Transaction component of the Budget Tracker application. It is an abstract class that defines the common properties and methods for both Income and Expense transactions.
 * The Transaction class implements the Persistable interface, allowing it to be easily saved and loaded from storage.
 * It includes properties for the transaction amount, description, and date, as well as an abstract method getNetAmount() that must be implemented by subclasses to calculate the net amount of the transaction.
 * The class also provides a printSummary() method to generate a summary of the transaction and a toString() method for displaying transaction information in a user-friendly format.
 * The Transaction class serves as a base for the Income and Expense classes, which provide specific implementations for their respective transaction types. 
 */
public abstract class Transaction implements Persistable{

    private int id;
    private int user_id;    
    private double amount;
    private String description;
    private String date;

    public Transaction() {
    }

    public Transaction(int id, int user_id,  double amount, String description, String date) {
        setId(id);
        this.user_id = user_id;        
        this.amount = amount;   
        this.description = description;
        this.date = date;
    }

    
    public Transaction(int user_id, double amount, String description, String date) {
        this.user_id = user_id;        
        setAmount(amount);        
        setDescription(description);
        setDate(date);
    }

    @Override
    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
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

    public void setId(int id) {
        this.id = id;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public abstract double getNetAmount();    

    public String printSummary() {
        return String.format("%s: $%.2f%n", 
            this instanceof Income ? "Income" : "Expense", 
            Math.abs(getNetAmount())); 
           
    }
    @Override
    public String toString() {
        return String.format("%s | %s | $%.2f | %s%n", 
            date, 
            description, 
            Math.abs(getNetAmount()), 
            this instanceof Income ? "Income" : "Expense");
    }

}
