package model;
/***************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the CategoryAllocation component of the Budget Tracker application.
 * It defines the structure and properties of a category allocation,
 * including the category name and the allocated amount. The class implements the Persistable interface, allowing it to be easily saved and loaded from storage.
 * It includes methods for getting the category name and allocated amount, as well as methods to add and remove allocations.
 */
public class CategoryAllocation implements Persistable {
    private int id;
    private int user_id;
    private int category_id;    
    private double allocatedAmount;
    private String categoryName; // Add this for convenience

    public CategoryAllocation() {
    }

    public CategoryAllocation(int id, int user_id, int category_id, double allocatedAmount) {
        setId(id);
        this.user_id = user_id;
        this.category_id = category_id;
        this.allocatedAmount = allocatedAmount;
    }

    public CategoryAllocation(int user_id, int category_id, double allocatedAmount) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.allocatedAmount = allocatedAmount;
    }

    public CategoryAllocation(int user_id, int category_id, double allocatedAmount, String categoryName) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.allocatedAmount = allocatedAmount;
        this.categoryName = categoryName;
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }
    public int getCategory_id() {
        return category_id;
    }
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void addAllocation(double amount) {
        this.allocatedAmount += amount;
    }

    public void removeAllocation(double amount) {
        if(amount > allocatedAmount) {
            throw new IllegalArgumentException("Cannot remove more than allocated amount");
        }
        this.allocatedAmount -= amount;
    }

    @Override
    public String toString() {
        return String.format("Category: %s | Allocated: $%.2f", categoryName, allocatedAmount);
    }

}
