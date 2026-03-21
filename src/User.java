public class User implements Persistable{

    private int id;
    private String username;
    private Budget budget;

    public User(String username) {
        setUsername(username);
        this.budget = new Budget(); 
    }

    @Override
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Budget getBudget() {
        return budget;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    
    

    

}
