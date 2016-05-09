package baristamatic;

import java.util.HashMap;

public class Drink implements Comparable<Drink> {

    private String name;
    private HashMap<Ingredient,Integer> ingredients;
    
    public Drink(String name, HashMap<Ingredient,Integer> ingredients) {
        
        this.setName(name);
        this.setIngredients(ingredients);
        
    }
    
    @Override
    public int compareTo(Drink d) {
        return this.getName().compareTo(d.getName());
    }
    
    public String getName() {
        return this.name;
    }
    
    public HashMap<Ingredient,Integer> getIngredients() {
        return this.ingredients;
    }
    
    private void setName(String name) {
        this.name = name;
    }
    
    private void setIngredients(HashMap<Ingredient,Integer> ingredients) {
        this.ingredients = ingredients;
    }
    
}