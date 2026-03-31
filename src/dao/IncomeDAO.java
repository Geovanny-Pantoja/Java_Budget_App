/**************
 * Name: Geovanny Pantoja
 * Date: 29  March 2026
 * Description: This class represents the IncomeDAO component of the Budget Tracker application. 
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations on income data in the database.
 * The IncomeDAO class interacts with the database using JDBC (Java Database Connectivity) to execute SQL queries and manage income records. 
 * It includes methods for creating the income table, adding a new income record, updating existing income information, retrieving all income records, and deleting an income record by their ID. 
 * The IncomeDAO class is essential for managing income data and ensuring that the application can persist income information effectively.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Income;

public class IncomeDAO {

    public static boolean createIncomeTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Incomes ("
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

    public static void addIncome(Connection conn, Income i) throws SQLException {
        String sql = "INSERT INTO Incomes(UserId, Amount, Description, Date) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, i.getUser_id());
            pstmt.setDouble(2, i.getAmount());
            pstmt.setString(3, i.getDescription());
            pstmt.setString(4, i.getDate());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                i.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Income> getIncomesByUserId(Connection conn, int userId) throws SQLException {
        String sql = "SELECT * FROM Incomes WHERE UserId = ?";
        ArrayList<Income> incomes = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Income i = new Income(
                        rs.getInt("Id"),
                        rs.getInt("UserId"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getString("Date"));
                incomes.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return incomes;
    }

    public static Income getIncomeById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Incomes WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Income i = new Income(
                        rs.getInt("Id"),
                        rs.getInt("UserId"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getString("Date"));                
                return i;
            } else {
                System.out.println("Income with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void updateIncome(Connection conn, Income i) throws SQLException {
        String sql = "UPDATE Incomes SET Amount = ?, Description = ?, Date = ? WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, i.getAmount());
            pstmt.setString(2, i.getDescription());
            pstmt.setString(3, i.getDate());
            pstmt.setInt(4, i.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteIncomeById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM Incomes WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();  
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteIncomesByUserId(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM Incomes WHERE UserId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
