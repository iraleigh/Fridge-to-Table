package com.example.iainchf.helloworld;

//Imports


import java.util.ArrayList;
import java.util.List;

/**
 * Created by iainchf on 9/25/15.
 *
 */
public class RecipesProviderAPI {
    private List<Recipe> recipeList;
    private String [] ingredients;
    private String url;

    /**
     * Default Constructor
     * initialized url and ingredients to empty variables
     */
    public RecipesProviderAPI() {
        url = "";
        ingredients = new String[0];
    }

    /**
     * Overloaded Constructor
     * initialized url
     * @param ingredients
     */
    public RecipesProviderAPI(String[] ingredients){
        this.url = "";
    }

    /**
     * getRecipes()
     * Takes in a url to call REST API, parse the return data, and fill a recipe list.
     * @return recipeList
     */
    public List<Recipe> getRecipes(){
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe());
        return recipeList;
    }
}
