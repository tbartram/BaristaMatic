package baristamatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BaristaMatic {

    public static void main(String[] args) {
            
        Machine baristaMatic = new Machine();
        
        // generate all the ingredient data
        HashMap<String,Float> ingredients = generateIngredientData();
        
        // add each ingredient into the machine with default inventory level
        for ( HashMap.Entry<String,Float> ingredientEntry : ingredients.entrySet() ) {
            baristaMatic.addNewIngredient(new Ingredient(ingredientEntry.getKey(),ingredientEntry.getValue()), baristaMatic.getDefaultInventoryCount());
        }
        
        // generate all the drink data
        HashMap<String,HashMap<Ingredient,Integer>> drinks = generateDrinkData(baristaMatic);
        
        // add each drink option into the machine
        for ( HashMap.Entry<String,HashMap<Ingredient,Integer>> drinkEntry : drinks.entrySet() ) {
            baristaMatic.addNewDrink(new Drink(drinkEntry.getKey(), drinkEntry.getValue()));
        }
        
        // start up the input scanning loop (ie/ fire up the baristaMatic!!)
        powerUpBaristaMatic(baristaMatic);     
        
    }
    
    private static void powerUpBaristaMatic(Machine baristaMatic) {
        
        // ArrayList of valid string inputs
        // as-per-spec, right now only characters (or integers...dealt with later) are permitted
        // but this approach allows for extension to other (potentially non-single-character)
        // string inputs
        ArrayList<String> validInputStrings = new ArrayList();
        validInputStrings.add("R");
        validInputStrings.add("r");
        validInputStrings.add("Q");
        validInputStrings.add("q");
        //samples of how to add other valid non-integer inputs (even non-single-character)
        /*
        validInputStrings.add("I");
        validInputStrings.add("help");
        */
       
        Scanner in = new Scanner(System.in);
        
        while (true) {
            
            System.out.print(baristaMatic.getInventoryOutput());
            System.out.print(baristaMatic.getMenuOutput());
            
            // looking at an incoming integer
            if ( in.hasNextInt() ) {
                
                int intInput = in.nextInt();
                
                // a positive interger not greater than the number of drinks on the menu
                if ( (intInput <= 0) || (intInput > baristaMatic.getDrinks().size()) ) {
                    System.out.println("Invalid selection: " + intInput);
                }
                else {
                    //dispense the drink if you can
                    if ( baristaMatic.dispenseDrink(baristaMatic.getDrinks().get(intInput-1)) ) {
                        System.out.println("Dispensing: " + baristaMatic.getDrinks().get(intInput-1).getName());
                    }
                    else {
                        System.out.println("Out of stock: " + baristaMatic.getDrinks().get(intInput-1).getName());
                    }
                }
                
            } 
            
            // something other than integer incoming
            else {
                
                String textInput = in.next();
                
                if ( !validInputStrings.contains(textInput) ) {
                    System.out.println("Invalid selection: " + textInput);
                } 
                else {
                    
                    //run appropriate command
                    switch ( textInput ) {
                        case "R":
                        case "r":
                            baristaMatic.restockInventory();
                            break;
                        case "Q":
                        case "q":
                            return; //want to break the switch but also break the intput loop
                        // samples of adding support to handle additional commands
                        /*
                        case "I":
                            baristaMatic.getInventoryOutput();
                            break;
                        case "HELP":
                        case "help":
                            System.out.println(getHelpOutput());
                            break;
                        */
                        default:
                            System.out.println("Invalid selection: " + textInput);
                            break;
                    }
                    
                }
            }
        }
        
    }
    
    private static HashMap<String,Float> generateIngredientData() {
        
        // define each ingredient as a name and cost
        HashMap<String,Float> ingredients = new HashMap<>();
        ingredients.put("Coffee", new Float(0.75));
        ingredients.put("Decaf Coffee", new Float(0.75));
        ingredients.put("Sugar", new Float(0.25));
        ingredients.put("Cream", new Float(0.25));
        ingredients.put("Steamed Milk", new Float(0.35));
        ingredients.put("Foamed Milk", new Float(0.35));
        ingredients.put("Espresso", new Float(1.10));
        ingredients.put("Cocoa", new Float(0.90));
        ingredients.put("Whipped Cream", new Float(1.00));
        // Sample of how to add an additional ingredient for use in drinks
        /*
        ingredients.put("Caramel", new Float(0.50));
        */
        
        return ingredients;
        
    }
    
    private static HashMap<String,HashMap<Ingredient,Integer>> generateDrinkData(Machine baristaMatic) {
        
        // define each drink by first defining its recipe separately then associating it with the drink name
        // must load the ingredient inventory into the machine first...this lets us check if we can
        // actually make the drink we're trying to add to the menu
        HashMap<String,HashMap<Ingredient,Integer>> drinks = new HashMap<>();
        
        // build up the recipe (ingredient and amount of ingredient)
        // by getting the ingredient object from the machine's inventory
        // if we have an ingredient in a recipe that isn't in inventory
        // then we don't add the drink to the menu
        HashMap<Ingredient,Integer> recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Coffee"), 3);
        recipe.put(baristaMatic.getIngredientByName("Sugar"), 1);
        recipe.put(baristaMatic.getIngredientByName("Cream"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Coffee", recipe);
        }
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Decaf Coffee"), 3);
        recipe.put(baristaMatic.getIngredientByName("Sugar"), 1);
        recipe.put(baristaMatic.getIngredientByName("Cream"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Decaf Coffee", recipe);
        }
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Espresso"), 2);
        recipe.put(baristaMatic.getIngredientByName("Steamed Milk"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Caffe Latte", recipe);
        }
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Espresso"), 3);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Caffe Americano", recipe);
        }
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Espresso"), 1);
        recipe.put(baristaMatic.getIngredientByName("Cocoa"), 1);
        recipe.put(baristaMatic.getIngredientByName("Steamed Milk"), 1);
        recipe.put(baristaMatic.getIngredientByName("Whipped Cream"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Caffe Mocha", recipe);
        }
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Espresso"), 2);
        recipe.put(baristaMatic.getIngredientByName("Steamed Milk"), 1);
        recipe.put(baristaMatic.getIngredientByName("Foamed Milk"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Cappuccino", recipe);
        }
        // Sample of how to add an additional drink
        /*
        recipe = new HashMap<>();
        recipe.put(baristaMatic.getIngredientByName("Espresso"), 2);
        recipe.put(baristaMatic.getIngredientByName("Steamed Milk"), 1);
        recipe.put(baristaMatic.getIngredientByName("Caramel"), 1);
        if  ( !recipe.containsKey(null) ) {
            drinks.put("Caramel Latte", recipe);
        }
        */
        
        return drinks;
        
    }
    
}
