package baristamatic;

public class Ingredient implements Comparable<Ingredient> {

    private String name;
    private Float cost;
    
    public Ingredient(String name, Float cost) {
        
        this.setName(name);
        this.setCost(cost);
        
    }
    
    @Override
    public int compareTo(Ingredient i) {
        return this.getName().compareTo(i.getName());
    }
    
    public String getName() {
        return this.name;
    }
    
    public Float getCost() {
        return this.cost;
    }
    
    private void setName(String name) {
        this.name = name;
    }
    
    private void setCost(Float cost) {
        this.cost = cost;
    }
    
}