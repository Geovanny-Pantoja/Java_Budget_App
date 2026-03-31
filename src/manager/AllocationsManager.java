/**************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the AllocationsManager component of the Budget Tracker application.
 * It defines the structure and properties of a category allocation.
 */
package manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.CategoryAllocationDAO;
import model.CategoryAllocation;
import util.InputHelper;

public class AllocationsManager {

    public ArrayList<CategoryAllocation> getAllocationsForUser(Connection conn, int userId) {
        try {
            return CategoryAllocationDAO.getCategoryAllocationsByUserId(conn, userId);
        } catch (Exception e) {
            System.out.println("Error retrieving allocations: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    public CategoryAllocation getAllocationByUserAndCategory(Connection conn, int userId, int categoryId) {
        try {
            return CategoryAllocationDAO.getCategoryAllocationByUserIdAndCategoryId(conn, userId, categoryId);
        } catch (SQLException e) {
            System.out.println("Error retrieving allocation: " + e.getMessage());
            return null;
        }
    }

    public void moveMoneyBetweenCategoriesFromInput(
            Connection conn,
            InputHelper input,
            int userId,
            CategoryManager categoryManager,
            Budget budget) {

        String fromCategory = input.getRequiredString("Move FROM category: ");
        String toCategory = input.getRequiredString("Move TO category: ");
        double amount = input.getPositiveDouble("Enter amount to move: ");

        try {
            boolean success = budget.transferAllocations(
                    conn,
                    userId,
                    fromCategory,
                    toCategory,
                    amount,
                    categoryManager);

            if (success) {
                System.out.println("Money moved successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    

}
