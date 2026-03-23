/********************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the UserManager component of the Budget Tracker application.
 */
import java.util.ArrayList;
import java.util.List;


public class UserManager {
    private List<User> users = new ArrayList<>();

    public void createUser(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        users.add(new User(username));
    }

    public User selectUser(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        return users.stream()
                .filter(u -> u.getUsername().trim().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public User selectUserFromInput(InputHelper input) {
        String username = input.getRequiredString("Enter username to select: ");
        return selectUser(username); 
    }



    public void createUserFromInput(InputHelper input) {
    String username = input.getRequiredString("Enter new username: ");

    // Business rule: username must be unique
    if (users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
        throw new IllegalArgumentException("A user with that name already exists.");
    }

    createUser(username); // your existing method
    System.out.println("User created successfully: " + username);
   }

    public List<User> getUsers() {
       if(users.isEmpty()) {
            System.out.println("No users found. Please create a user first.");
        }
        return users;
    }

    public void ListUsers() {
        if(users.isEmpty()) {
            System.out.println("No users found. Please create a user first.");
        } else {
            for (User user : users) {
                System.out.println(user.toString());
            }
        }
    }

}
