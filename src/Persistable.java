/****************
 * Name: Geovanny Pantoja
 * Date: 23 March 2026
 * Description: This interface represents the Persistable component of the Budget Tracker application.
 * It defines a contract for any class that implements it, requiring them to provide a method to retrieve a unique identifier (ID).
 * This allows for consistent handling of objects that need to be saved and loaded from storage, ensuring that each object can be uniquely identified and managed within the application's data persistence layer.
 * The Persistable interface is essential for enabling the application's ability to save and load data, as it provides a standardized way to access the unique identifiers of objects,
 * facilitating efficient data management and retrieval.
 * Classes such as Category and CategoryAllocation implement this interface, allowing them to be easily saved and loaded from storage while maintaining their unique identities within the application.
 */
public interface Persistable {
    public int getId();

}
