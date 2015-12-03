package com.example.iainchf.helloworld;


import java.util.List;

/**
 * Created by Nwilley91 on 10/1/2015.
 */
public class Recipe {
    private String name;
    private String description;
    private String instructions;
    private String videoURL;
    private boolean dietFood;
    private boolean hasCaffeine;
    private boolean glutenFree;
    private int calories;
    private String nameOfAPI;
    private List<String> ingredientList;
    private String idFromAPI;
    private String imageUrl;

    //Sets all strings to null, boolean to false, and int to 0.
    public Recipe(){
    }

    public Recipe(String n, String d, String inst, String vURL, boolean diet, boolean caffe,
                  boolean gluten, int cal, List<String> iList,String imageURL ,String api, String id){
        name = n;
        description = d;
        instructions = inst;
        videoURL = vURL;
        dietFood = diet;
        hasCaffeine = caffe;
        glutenFree = gluten;
        calories = cal;
        ingredientList = iList;
        nameOfAPI = api;
        idFromAPI = id;
        imageUrl = imageURL;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getInstructions(){
        return instructions;
    }

    public String getVideoURL(){
        return videoURL;
    }

    public boolean isDiet(){
        return dietFood;
    }

    public boolean isCaffeinated(){
        return hasCaffeine;
    }

    public boolean isGlutenFree(){
        return glutenFree;
    }

    public int getCalorieCount(){
        return calories;
    }

    public List<String> getIngredientList(){
        return ingredientList;
    }

    public String toString(){
        String recipe = "Name: " + this.getName() + "\n" +
                        "Description: " + this.getDescription() + "\n" +
                        "Instructions: " + this.getInstructions() + "\n" +
                        "Ingredient List"+ this.listIngredients() + "\n" +
                        "**********************************************" + "\n\n";
        return recipe;
    }

    private String listIngredients(){
        String listOfIngredients = "";
        for (String ingredient : ingredientList){
            listOfIngredients = listOfIngredients + ingredient + "\n";
        }
        return listOfIngredients;
    }
    public String getNameOfAPI() {return nameOfAPI;}
    public String getIdFromAPI() {return idFromAPI;}
    public String getImageUrl() {return imageUrl;}

}
