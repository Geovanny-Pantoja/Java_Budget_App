package model;

import manager.Budget;
/*****************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026  
 * Description: This class represents the User component of the Budget Tracker application. It defines the structure and properties of a user, including their username and associated budget.
 * The class implements the Persistable interface, allowing it to be easily saved and loaded from storage.
 * It includes methods for getting and setting the username, as well as a method to retrieve the user's budget.
 * The User class is essential for managing individual users within the application, allowing each user to have their own budget and associated transactions, categories, and allocations.
 * It serves as the primary entity for user management and data organization within the Budget Tracker application.
 */
public class User implements Persistable{

    private int id;
    private String username;
    private Budget budget; // in memory representation of the user's budget    

    public User() {
        
    }

    public User(int id, String username) {
        setId(id);
        setUsername(username);
       
    }

    public User(String username) {
        setUsername(username);
       
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public Budget getBudget() {
        return budget;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
   
    @Override
    public String toString() {
        
        return "User: " + username;
    }

    
    

    

}
