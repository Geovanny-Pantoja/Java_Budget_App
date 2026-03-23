/***************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the CategoryAllocation component of the Budget Tracker application.
 * It defines the structure and properties of a category allocation,
 * including the category name and the allocated amount. The class implements the Persistable interface, allowing it to be easily saved and loaded from storage.
 * It includes methods for getting the category name and allocated amount, as well as methods to add and remove allocations.
 */
public class CategoryAllocation implements Persistable {
    private int id;
    private String categoryName;
    private double allocatedAmount;

    public CategoryAllocation(String categoryName, double allocatedAmount) {
        this.categoryName = categoryName;
        this.allocatedAmount = allocatedAmount;
    }
    @Override
    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
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
