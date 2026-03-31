/********************
 * Name: Geovanny Pantoja
 * Date: 29 March 2026
 * Description: This class represents the UserManager component of the Budget Tracker application.
 */
package manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.InputHelper;
import dao.CategoryAllocationDAO;

import dao.ExpenseDAO;
import dao.IncomeDAO;
import dao.UserDAO;


public class UserManager {

    public User createUser(Connection Conn, String username) {
        User u = new User(username);
        try {
            User existingUser = selectUserByUsername(username, Conn);
            if (existingUser != null) {
                System.out.println("Username already exists. Please choose a different username.");
                return null;
            }
            UserDAO.addUser(Conn, u);
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return null;
        }
        return u;
    }

    public User selectUserByUsername(String userName, Connection conn) {
        try {
            return UserDAO.getUserByUsername(conn, userName);
        } catch (SQLException e) {
            System.out.println("Error selecting user: " + e.getMessage());
            return null;
        }
    }

    public User selectUserFromInput(InputHelper input, Connection conn) {
        String username = input.getRequiredString("Enter username to select: ");
        User user = selectUserByUsername(username, conn);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        try {
            CategoryManager catManager = new CategoryManager(); // Assuming it's created here
            Budget budget = new Budget(user.getId(), conn, catManager);
            user.setBudget(budget);

        } catch (SQLException e) {
            System.out.println("Error selecting user: " + e.getMessage());
            return null;

        }

        return user;
    }

    public void createUserFromInput(InputHelper input, Connection conn) {
        String username = input.getRequiredString("Enter new username: ");
        username = username.trim().toLowerCase();

        try {
            User existingUser = selectUserByUsername(username, conn);
            if (existingUser != null) {
                throw new IllegalArgumentException("Username already exists. Please choose a different username.");
            }

            User newUser = createUser(conn, username); // your existing method
            if (newUser == null) {
                System.out.println("Failed to create user. Please try again.");
                return;
            }
            System.out.println("User created successfully: " + username);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
    }

    public List<User> getUsers(Connection conn) {
        List<User> users = new ArrayList<>();
        try {
            users = UserDAO.getAllUsers(conn);
            if (users.isEmpty()) {
                System.out.println("No users found. Please create a user first.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }

        return users;
    }

    public void ListUsers(Connection conn) {
        List<User> users = getUsers(conn);
        if (!users.isEmpty()) {
            for (User user : users) {
                System.out.println(user.toString());
            }
        }
    }

    public void updateUserFromInput(InputHelper input, Connection conn) {
        try {
            String currentUsername = input.getRequiredString("Enter current username: ");
            User user = selectUserByUsername(currentUsername, conn);

            if (user == null) {
                throw new IllegalArgumentException("User not found: " + currentUsername);
            }

            String newUsername = input.getRequiredString("Enter new username: ");

            User existingUser = selectUserByUsername(newUsername, conn);

            // Allow same username, block only if it's a different user
            if (existingUser != null && existingUser.getId() != user.getId()) {
                throw new IllegalArgumentException("Username already exists. Please choose a different username.");
            }

            user.setUsername(newUsername);
            UserDAO.updateUser(conn, user);

            System.out.println("User updated successfully: " + newUsername);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    public void deleteUserFromInput(InputHelper input, Connection conn) {
        try {
            String username = input.getRequiredString("Enter username to delete: ");
            User user = selectUserByUsername(username, conn);

            if (user == null) {
                System.out.println("User not found: " + username);
                return;
            }

            // Confirm deletion
            String confirm = input.getRequiredString(
                    "Are you sure you want to delete user '" + username + "' and ALL related data? (yes/no): ");

            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Deletion cancelled.");
                return;
            }

            // Delete related data first
            ExpenseDAO.deleteExpensesByUserId(conn, user.getId());
            IncomeDAO.deleteIncomesByUserId(conn, user.getId());
            CategoryAllocationDAO.deleteAllocationsByUserId(conn, user.getId());

            // Delete the user
            UserDAO.deleteUserById(conn, user.getId());

            System.out.println("User '" + username + "' and all related data deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

}
