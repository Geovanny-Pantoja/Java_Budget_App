/**************
 * Name: Geovanny Pantoja
 * Date: 29  March 2026
 * Description: This class represents the ExpenseDAO component of the Budget Tracker application. 
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations on expense data in the database.
 * The ExpenseDAO class interacts with the database using JDBC (Java Database Connectivity) to execute SQL queries and manage expense records. 
 * It includes methods for creating the expense table, adding a new expense record, updating existing expense information, retrieving all expense records, and deleting an expense record by their ID. 
 * The ExpenseDAO class is essential for managing expense data and ensuring that the application can persist expense information effectively.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Expense;
public class ExpenseDAO {

    public static boolean createExpenseTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Expenses ("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "UserId INTEGER NOT NULL,"                
                + "Amount REAL NOT NULL,"
                + "Description VARCHAR(100),"
                + "Date VARCHAR(20),"
                + "FOREIGN KEY(UserId) REFERENCES Users(Id)"                
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void addExpense(Connection conn, Expense e) throws SQLException {
        String sql = "INSERT INTO Expenses(UserId, Amount, Description, Date) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, e.getUser_id());
            pstmt.setDouble(2, e.getAmount());
            pstmt.setString(3, e.getDescription());
            pstmt.setString(4, e.getDate());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                e.setId(keys.getInt(1));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

   public static ArrayList<Expense> getExpensesByUserId(Connection conn, int userId) throws SQLException {
        String sql = "SELECT * FROM Expenses WHERE UserId = ?";
        ArrayList<Expense> expenses = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Expense e = new Expense(
                        rs.getInt("Id"),
                        rs.getInt("UserId"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getString("Date"));
                expenses.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }

    public static Expense getExpenseById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Expenses WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Expense(
                        rs.getInt("Id"),
                        rs.getInt("UserId"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getString("Date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }   

    public static void updateExpense(Connection conn, Expense e) throws SQLException {
        String sql = "UPDATE Expenses SET Amount = ?, Description = ?, Date = ? WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {            
            pstmt.setDouble(1, e.getAmount());
            pstmt.setString(2, e.getDescription());
            pstmt.setString(3, e.getDate());
            pstmt.setInt(4, e.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteExpenseById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM Expenses WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteExpensesByUserId(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM Expenses WHERE UserId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
