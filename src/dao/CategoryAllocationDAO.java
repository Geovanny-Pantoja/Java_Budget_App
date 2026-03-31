/**************
 * Name: Geovanny Pantoja
 * Date: 29  March 2026
 * Description: This class represents the CategoryAllocationDAO component of the Budget Tracker application.
 *  It is responsible for managing the persistence of category allocations in the database.
 * The class provides methods for creating the category allocation table, adding new category allocations, and retrieving category allocations for a specific user.
 *  It interacts with the database using SQL queries and handles any potential SQL exceptions that may arise during these operations.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.CategoryAllocation;

public class CategoryAllocationDAO {

    public static boolean createCategoryAllocationTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS CategoryAllocations ("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "UserId INTEGER NOT NULL,"
                + "CategoryId INTEGER NOT NULL,"
                + "Amount DOUBLE NOT NULL,"
                + "FOREIGN KEY(UserId) REFERENCES Users(Id),"
                + "FOREIGN KEY(CategoryId) REFERENCES Categories(Id)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        }
    }

    public static void addCategoryAllocation(Connection conn, CategoryAllocation ca) throws SQLException {
        String sql = "INSERT INTO CategoryAllocations(UserId, CategoryId, Amount) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ca.getUser_id());
            pstmt.setInt(2, ca.getCategory_id());
            pstmt.setDouble(3, ca.getAllocatedAmount());
            pstmt.executeUpdate();
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()) {
                ca.setId(keys.getInt(1));
            }

        }
    }

    public static ArrayList<CategoryAllocation> getCategoryAllocationsByUserId(Connection conn, int userId) throws SQLException {
        String sql = "SELECT * FROM CategoryAllocations WHERE UserId = ?";
        ArrayList<CategoryAllocation> allocations = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CategoryAllocation ca = new CategoryAllocation(
                    rs.getInt("Id"),
                    rs.getInt("UserId"),
                    rs.getInt("CategoryId"),
                    rs.getDouble("Amount")
                );
                allocations.add(ca);
            }
        }
        return allocations;
    }

    public static void updateCategoryAllocation(Connection conn, CategoryAllocation ca) throws SQLException {
        String sql = "UPDATE CategoryAllocations SET Amount = ? WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, ca.getAllocatedAmount());
            pstmt.setInt(2, ca.getId());
            pstmt.executeUpdate();
        }
    }

    public static void deleteCategoryAllocationById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM CategoryAllocations WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteAllocationsByUserId(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM CategoryAllocations WHERE UserId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    public static CategoryAllocation getCategoryAllocationById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM CategoryAllocations WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return new CategoryAllocation(
                    rs.getInt("Id"),
                    rs.getInt("UserId"),
                    rs.getInt("CategoryId"),
                    rs.getDouble("Amount")
                );
            }
        }
        return null;
    }

    public static CategoryAllocation getCategoryAllocationByUserIdAndCategoryId(Connection conn, int userId, int categoryId) throws SQLException {
        String sql = "SELECT * FROM CategoryAllocations WHERE UserId = ? AND CategoryId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return new CategoryAllocation(
                    rs.getInt("Id"),
                    rs.getInt("UserId"),
                    rs.getInt("CategoryId"),
                    rs.getDouble("Amount")
                );
            }
        }
        return null;
    }

}
