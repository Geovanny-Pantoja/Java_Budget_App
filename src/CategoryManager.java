/**************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the CategoryManager component of the Budget Tracker application.
 * It defines the structure and properties of a category allocation.
 */
import java.util.ArrayList;

public class CategoryManager {
    private ArrayList<Category> categories = new ArrayList<Category>();
    
    
    public CategoryManager() {
        addCategory("House", "Save for a new home or fixes in home");
        addCategory("Car", "Save for a new car or repairs");    
        addCategory("Everyday Use", "Unallocated funds; default category for transactions without a specified category");
        
    }   

    private Category addCategory(String name, String description) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }   
        for(Category c : categories) {
            if(c.getName().trim().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Category already exists: " + name);
            }
        }
        Category category = new Category(name, description);
        categories.add(category);
        return category;
    }

    public void addCategoryFromInput(InputHelper input) {
    String name = input.getRequiredString("Enter new category name: ");
    String description = input.getRequiredString("Enter category description: ");

    // Check for duplicates
    for (Category c : categories) {
        if (c.getName().equalsIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists: " + name);
        }
    }

    Category newCategory = new Category(name, description);
    categories.add(newCategory);

    System.out.println("Category added successfully.");
}

    public ArrayList<Category> getCategories() {
        return categories;
    }   

    public Category getCategoryByName(String name) {
        for(Category c : categories) {
            if(c.getName().trim().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Category not found: " + name);
    }


}
