public class Category implements Persistable{

    private int id;
    private String name;
    private double allocatedAmount;

    public Category(String name){
        allocatedAmount = 0;
        setName(name);
    }
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllocatedAmount(double allocateAmount) {
        this.allocatedAmount = allocateAmount;
    }

    

}
