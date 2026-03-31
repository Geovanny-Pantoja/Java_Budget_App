package model;
/**************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the Category component of the Budget Tracker application. It defines the structure and properties of a category,
 * including its name and description. The class implements the Persistable interface, allowing it to be easily saved and loaded from storage.
 * It includes methods for getting and setting the category's name and description, as well as a toString method for displaying category information in a user-friendly format.
 * The Category class is essential for organizing transactions into meaningful groups, enabling users to better manage their budgets and track their spending habits.
 */
public class Category implements Persistable{

    private int id;
    private String name;
    private String description;

    public Category() {
    }

    public Category(int id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    public Category(String name, String description) {
           setName(name);
           setDescription(description);
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

   

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return String.format("Category: %s - %s", name, description);
    }
    

}
