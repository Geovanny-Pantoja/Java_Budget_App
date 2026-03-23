/*************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This class represents the InputHelper component of the Budget Tracker application.
 * It provides methods for handling user input, including validation for required strings, positive doubles, and integers.
 * The class uses a Scanner to read input from the console and includes error handling to ensure that the input is valid before returning it to the caller.
 * The InputHelper class is essential for ensuring that user input is properly validated and processed, contributing to a smooth and user-friendly experience in the application.
 */
import java.util.Scanner;
public class InputHelper {

    private Scanner scanner;

    public InputHelper() {
        scanner = new Scanner(System.in);
    }

    public String getRequiredString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        return input;
    }

    public double getPositiveDouble(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            double value = Double.parseDouble(input);
            if (value <= 0) {
                throw new IllegalArgumentException("Value must be positive.");
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid number format.");
        }
    }

    public int getInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid integer.");
        }
    }

    public void waitForEnter() {
        scanner.nextLine(); 
    }

}
