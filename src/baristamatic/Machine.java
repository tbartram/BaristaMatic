package baristamatic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Machine {
 
    private ArrayList<Drink> drinks;
    private TreeMap<Ingredient,Integer> inventory;
    private Integer DEFAULT_INVENTORY_COUNT;
    
    public Machine() {
        
        this.setDrinks(new ArrayList<>());
        this.setInventory(new TreeMap<>());
        this.setDefaultInventoryCount(10);
              
    }
    
    public ArrayList<Drink> getDrinks() {
        return this.drinks;
    }
    
    public TreeMap<Ingredient,Integer> getInventory() {
        return this.inventory;
    }
    
    public Integer getDefaultInventoryCount() {
        return this.DEFAULT_INVENTORY_COUNT;
    }
    
    // use this method as a way to get an ingredient object by its name only
    // useful for knowing we have an ingredient in inventory before trying to add
    // a menu item that uses it   
    public Ingredient getIngredientByName(String name) {
        
        for ( Ingredient i : this.getInventory().keySet() ) {
            if ( i.getName().equals(name) ) {
                return i;
            }
        }
        return null;
        
    }
    
    public String getInventoryOutput() {
        
        StringBuilder output = new StringBuilder("Inventory:\n");
        
        for ( Map.Entry<Ingredient,Integer> inventoryEntry : this.getInventory().entrySet() ) {
            output.append(inventoryEntry.getKey().getName()).append(",").append(inventoryEntry.getValue()).append("\n");
        }
        
        return output.toString();
        
    }
    
    public String getMenuOutput() {
        
        Drink currDrink;
 
        StringBuilder output = new StringBuilder("Menu:\n");
        
        ArrayList<Drink> menuDrinks = this.getDrinks();
        Collections.sort(menuDrinks);
        for ( int i=0; i < menuDrinks.size(); i++ ) {

            currDrink = menuDrinks.get(i);
            
            output.append(i+1).append(",").append(currDrink.getName());
            output.append(",$").append(String.format("%.2f", this.costForDrink(currDrink)));
            output.append(",").append(this.canMakeDrink(currDrink)).append("\n");
            
        }
        
        return output.toString();

    }
    
    public Float costForDrink(Drink drink) {
        
        Float currPrice = new Float(0.00);
        
        for ( Map.Entry<Ingredient,Integer> ingredientEntry : drink.getIngredients().entrySet() ) {
            currPrice = currPrice + (ingredientEntry.getValue() * ingredientEntry.getKey().getCost());
        }
        
        return currPrice;
        
    }
    
    public boolean canMakeDrink(Drink drink) {
        
        for ( Map.Entry<Ingredient,Integer> ingredientEntry : drink.getIngredients().entrySet() ) {
            Integer currStock = this.getInventory().get(ingredientEntry.getKey());
            Integer amtNeeded = ingredientEntry.getValue();
            if ( currStock < amtNeeded ) {
                return false;
            }
        }
        
        return true;
        
    }
    
    public void restockInventory() {
        
        for ( Map.Entry<Ingredient, Integer> inventoryEntry : this.getInventory().entrySet() ) {
            inventoryEntry.setValue(this.getDefaultInventoryCount());
        }
        
    }
    
    public boolean dispenseDrink(Drink drink) {
        
        if ( !canMakeDrink(drink) ) {
            return false;
        }
        
        for ( Map.Entry<Ingredient,Integer> ingredientEntry : drink.getIngredients().entrySet() ) {
            this.getInventory().put(ingredientEntry.getKey(), this.getInventory().get(ingredientEntry.getKey()) - drink.getIngredients().get(ingredientEntry.getKey()));
        }
        
        return true;
        
    }
    
    public void addNewIngredient(Ingredient ingredient, Integer count ){
        // using putIfAbsent ensures we have no duplicates
        this.getInventory().putIfAbsent(ingredient, count);
    }
    
    public void addNewDrink(Drink drink) {
        this.getDrinks().add(drink);
    }
    
    private void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }
    
    private void setDefaultInventoryCount(Integer count) {
        this.DEFAULT_INVENTORY_COUNT = count;
    }
    
    private void setInventory(TreeMap<Ingredient,Integer> inventory) {
        this.inventory = inventory;
    }
    
}