/**************
 * Name: Geovanny Pantoja
 * Date: 29  March 2026
 * Description: This class represents the UserDAO component of the Budget Tracker application. 
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations on user data in the database.
 * The UserDAO class interacts with the database using JDBC (Java Database Connectivity) to execute SQL queries and manage user records. 
 * It includes methods for creating the user table, adding a new user, updating existing user information, retrieving all users, and deleting a user by their ID. 
 * The UserDAO class is essential for managing user data and ensuring that the application can persist user information effectively.
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;
public class UserDAO {

        public static boolean createUserTable(Connection conn) throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS Users ("
                    + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Username VARCHAR(20) NOT NULL UNIQUE"
                    + ");";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                return true;
            }
        }
    
        public static void addUser(Connection conn, User u) throws SQLException {
            String sql = "INSERT INTO Users(Username) VALUES(?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, u.getUsername());
                pstmt.executeUpdate();
                
                ResultSet keys = pstmt.getGeneratedKeys();
                if(keys.next()) {
                    u.setId(keys.getInt(1));
                }

            }
        }

        public static void updateUser(Connection conn, User u) throws SQLException {
            String sql = "UPDATE Users SET Username = ? WHERE Id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, u.getUsername());
                pstmt.setInt(2, u.getId());
                pstmt.executeUpdate();
            }
        }
    
        public static ArrayList<User> getAllUsers(Connection conn) throws SQLException {
            ArrayList<User> users = new ArrayList<>();
            String sql = "SELECT * FROM Users";
            try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {                
                while (rs.next()) {
                    User u = new User( rs.getInt("Id"), rs.getString("Username"));
                    users.add(u);
                }
            }
            return users;
        }

        public static User getUserById(Connection coonm, int id) throws SQLException {
            String sql = "SELECT * FROM Users WHERE Id = ?";
            try
                (PreparedStatement pstmt = coonm.prepareStatement(sql)){;
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                 return new User(rs.getInt("Id"), rs.getString("Username"));
                }
            } 
            return null;
        }

        public static User getUserByUsername(Connection coonm, String username) throws SQLException{
            
            String sql = "SELECT * FROM Users WHERE Username = ? COLLATE NOCASE";
            try( PreparedStatement pstmt = coonm.prepareStatement(sql)){
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                    return new User(rs.getInt("Id"), rs.getString("Username"));
                }
            } 
            return null;
        }

        public static void deleteUserById(Connection coonm, int id) throws SQLException{
            String sql = "DELETE FROM Users WHERE Id = ?";
            try( PreparedStatement pstmt = coonm.prepareStatement(sql)){
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } 
        }        

}
