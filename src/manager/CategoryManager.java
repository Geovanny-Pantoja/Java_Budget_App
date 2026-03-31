/**************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the CategoryManager component of the Budget Tracker application.
 * It defines the structure and properties of a category allocation.
 */
package manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.CategoryDAO;
import model.Category;
import util.InputHelper;

public class CategoryManager {

    private Category addCategory(Connection conn, String name, String description) {
        try {
            Category newCategory = new Category(name, description);
            CategoryDAO.addCategory(conn, newCategory);
            return newCategory;
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
            throw null;

        }

    }

    public void addCategoryFromInput(Connection conn, InputHelper input) {
        String name = input.getRequiredString("Enter new category name: ");
        String description = input.getRequiredString("Enter category description: ");

        try {
            Category existing = CategoryDAO.getCategoryByName(conn, name);
            if (existing != null) {
                System.out.println("Category already exists: " + name);
                return;
            }
            Category newCategory = addCategory(conn, name, description);
            if (newCategory != null) {
                System.out.println("Category added: " + newCategory.toString());
            } else {
                System.out.println("Failed to add category.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
        }
    }

    public ArrayList<Category> getCategories(Connection conn) {
        try {
            return CategoryDAO.getAllCategories(conn);
        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Category getCategoryById(Connection conn, int id) {
        try {
            return CategoryDAO.getCategoryById(conn, id);
        } catch (SQLException e) {
            System.out.println("Error retrieving category: " + e.getMessage());
            return null;
        }
    }

    public Category getCategoryByName(Connection conn, String name) {
        try {
            return CategoryDAO.getCategoryByName(conn, name);
        } catch (SQLException e) {
            System.out.println("Error retrieving category: " + e.getMessage());
            return null;
        }
    }

    public void updateCategoryFromInput(InputHelper input, Connection conn) {
        String name = input.getRequiredString("Enter category name to update: ");

        try {
            Category category = CategoryDAO.getCategoryByName(conn, name);

            if (category == null) {
                System.out.println("Category not found with name: " + name);
                return;
            }

            String newName = input.getRequiredString("Enter new category name: ");
            String newDescription = input.getRequiredString("Enter new category description: ");

            // Check duplicate name (but allow same name for this category)
            Category existing = CategoryDAO.getCategoryByName(conn, newName);
            if (existing != null && existing.getId() != category.getId()) {
                System.out.println("A category with that name already exists.");
                return;
            }

            category.setName(newName);
            category.setDescription(newDescription);

            CategoryDAO.updateCategory(conn, category);

            System.out.println("Category updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
        }
    }

}
