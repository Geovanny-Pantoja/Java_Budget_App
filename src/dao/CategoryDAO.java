/**************
 * Name: Geovanny Pantoja
 * Date: 29  March 2026
 * Description: This class represents the CategoryDAO component of the Budget Tracker application. 
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations on category data in the database.
 * The CategoryDAO class interacts with the database using JDBC (Java Database Connectivity) to execute SQL queries and manage category records. 
 * It includes methods for creating the category table, adding a new category, updating existing category information, retrieving all categories, and deleting a category by their ID. 
 * The CategoryDAO class is essential for managing category data and ensuring that the application can persist category information effectively.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Category;

public class CategoryDAO {

    public static boolean createCategoryTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Categories ("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Name VARCHAR(40) NOT NULL UNIQUE,"
                + "Description VARCHAR(100)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        }
    }

    public static void ensureDefaultCategories(Connection conn) throws SQLException {
        ensureCategory(conn, 1, "House", "Save for a new home or fixes in home");
        ensureCategory(conn, 2, "Car", "Save for a new car or repairs");
        ensureCategory(conn, 3, "Everyday Use",
                "Unallocated funds; default category for transactions without a specified category");
    }

    private static void ensureCategory(Connection conn, int id,String name, String description) throws SQLException {
        String sql = "INSERT OR IGNORE INTO Categories (id, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.executeUpdate();
        }
    }

    public static void addCategory(Connection conn, Category c) throws SQLException {
        String sql = "INSERT INTO Categories(Name, Description) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getDescription());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                c.setId(keys.getInt(1));
            }

        } 
    }

    public static void updateCategory(Connection conn, Category c) throws SQLException {
        String sql = "UPDATE Categories SET Name = ?, Description = ? WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getDescription());
            pstmt.setInt(3, c.getId());
            pstmt.executeUpdate();
        } 
    }

    public static ArrayList<Category> getAllCategories(Connection conn) throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description")));
            }
        } 
        return categories;
    }

    public static void deleteCategoryById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM Categories WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } 
    }

    public static Category getCategoryById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description"));
            }
        } 
        return null;
    }

    public static Category getCategoryByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM Categories WHERE Name = ? COLLATE NOCASE";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description"));
            }
        } 
        return null;
    }

}
