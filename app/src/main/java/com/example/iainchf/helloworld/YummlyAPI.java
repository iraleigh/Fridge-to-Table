package com.example.iainchf.helloworld;

/**
 * Created by alvinlu on 10/14/15.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvinlu on 9/25/15.
 * a student at SFSU
 */
public class YummlyAPI {

    private List<Recipe> recipeList;
    private String [] ingredients;
    private String url;
    private List<String> ids;
    private int placeInIdList;
    public static final String PARSE_JSON_FOR_ID_TAG = "Inside ";
    public static final String PARSE_JSON_FOR_RECIPE_TAG = "Inside ";

    //Default Constructor
    public YummlyAPI(){

        url = "http://api.yummly.com/v1/api/recipes?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";
        ingredients = new String[0];
    } //done

    //Overload Constructor
    public YummlyAPI(String[] ingredients){
        this.ingredients = ingredients;
        this.url = createURL();
        HttpGetData yummlyGetter = new HttpGetData(url);

        while(yummlyGetter.getData() == null);

        String json = yummlyGetter.getData();

        ids = parseJsonForID(json);

        placeInIdList = 0;
        recipeList = new ArrayList<>();
    } //done

    /**
     * getRecipes()
     * Takes in a url to call REST API, parse the return data, and fill a recipe list.
     * @return recipeList
     */
    public List<Recipe> getFiveRecipes(){
        if (placeInIdList < ids.size()) {
            for (int i = placeInIdList; i < placeInIdList + 5 && i < ids.size(); i++) {
                HttpGetData food2ForkRecipeGetter = new HttpGetData(createGetURL(ids.get(i)));
                while(food2ForkRecipeGetter.getData() == null);
                String json = food2ForkRecipeGetter.getData();
                recipeList.add(parseJsonForRecipes(json));
            }
            int oldPlaceInList = placeInIdList;
            if(recipeList.size() < (placeInIdList + 5)) {
                placeInIdList += recipeList.size();
            } else {
                placeInIdList +=5;
            }
            return recipeList.subList(oldPlaceInList,placeInIdList);
        } else {
            return new ArrayList<>();
        }
    }


    public List<String> parseJsonForID(String json){
        YummlyIdJsonReader idReader = new YummlyIdJsonReader();
        List<String> ids = new ArrayList<>();

        try{
            ids = idReader.readJsonStream(json);
        } catch (IOException ioe){
            Log.e("In parseJsonForID", ioe.getMessage());
        } finally{
            return ids;
        }
    } //done
    public Recipe parseJsonForRecipes(String json){
        YummlyRecipeJsonReader recipeReader = new YummlyRecipeJsonReader();
        Recipe recipe = new Recipe();
        try{
            recipe = recipeReader.readJsonStream(json);
        }catch(IOException e){
            return recipe;
        }finally{
            return recipe;
        }
    } //done

    //Creates URL
    private String createURL(){
        String tempURL = "http://api.yummly.com/v1/api/recipes?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";
        // for loop to traverse through the ingredients
        for(String i: ingredients){
            i = i.toLowerCase().replaceAll("\\s","+");

            tempURL += "&allowedIngredient[]=" + i;
        }
        return tempURL;
    } //done

    private String createGetURL(String id){
        return  "http://api.yummly.com/v1/api/recipe/"+ id +"?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";
    }


    private class YummlyIdJsonReader {


        @TargetApi(Build.VERSION_CODES.KITKAT)
        public List<String> readJsonStream(String in) throws IOException {
            JsonReader jsonReader = new JsonReader(new StringReader(in));
            try {
                return readMessagesArray(jsonReader);
            } finally {
                try {
                    jsonReader.close();
                } catch (Exception e) {
                }
            }
        }

        @SuppressWarnings("unchecked")
        public List<String> readMessagesArray(JsonReader reader) throws IOException {
            //List<Recipe> recipes = new ArrayList();
            List<String> ids = new ArrayList<>();
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();

                if(name.equals("matches")){
                    reader.beginArray();

                    while(reader.hasNext()){
                        try{
                            ids.add(getID(reader));
                        }catch(Exception e){
                            return ids;
                        }
                    }
                    reader.endArray();
                }else if(name.equals("criteria")){
                    reader.skipValue();
                }else if(name.equals("facetCounts")){
                    reader.skipValue();
                }else if(name.equals("totalMatchCount")){
                    reader.skipValue();
                }else if(name.equals("attribution")){
                    reader.skipValue();
                }
            }
            reader.endObject();
            return ids;
        }
        public String getID(JsonReader reader) throws IOException{
            String tempId = null;
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("imageUrlsBySize")){
                    reader.skipValue();
                }else if(name.equals("sourceDisplayName")){
                    reader.skipValue();
                }else if(name.equals("ingredients")){
                    reader.skipValue();
                }else if(name.equals("id")){
                    tempId = reader.nextString();
                }else if(name.equals("smallImageUrls")){
                    reader.skipValue();
                }else if(name.equals("recipeName")){
                    reader.skipValue();
                }else if(name.equals("totalTimeInSeconds")){
                    reader.skipValue();
                }else if(name.equals("attributes")){
                    reader.skipValue();
                }else if(name.equals("flavors")){
                    reader.skipValue();
                }else if(name.equals("rating")){
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new String(tempId);
        }


    } //done


    //TODO customize to read Recipe from Yummly
    private class YummlyRecipeJsonReader {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public Recipe readJsonStream(String in) throws IOException{
            JsonReader jsonReader = new JsonReader(new StringReader(in));
            try {
                return readMessagesArray(jsonReader);
            }finally{
                try{
                    jsonReader.close();
                }catch(Exception e){

                }
            }
        } //done

        @SuppressWarnings("unchecked")
        public Recipe readMessagesArray(JsonReader reader) throws IOException {
            Recipe recipe = new Recipe();

            recipe = getRecipe(reader);
            return recipe;
        } //done

        public Recipe getRecipe(JsonReader reader) throws IOException{

            String recipeName = "";
            String description = "";
            String instructions = "";
            String videoURL = "";
            String foodID = "";
            boolean dietFood = false;
            boolean hasCaffeine = false;
            boolean glutenFree = false;
            int calories = 0;
            List<String> ingredientList = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if(name.equals("yield")){
                    reader.skipValue();
                }else if(name.equals("nutritionEstimates")){
                    reader.skipValue();
                }else if(name.equals("prepTimeInSeconds")){
                    reader.skipValue();
                }else if(name.equals("totalTime")){
                    reader.skipValue();
                }else if(name.equals("images")){
                    reader.skipValue();
                }else if(name.equals("name")){
                    recipeName = reader.nextString();
                }else if(name.equals("source")){
                    reader.beginObject();
                    if(name.equals("sourceDisplayName")){
                        reader.skipValue();
                    }else if(name.equals("sourceSiteUrl")){
                        reader.skipValue();
                    }else if(name.equals("sourceRecipeUrl")){
                        instructions = reader.nextString();
                    }
                    reader.endObject();
                }else if(name.equals("prepTime")){
                    reader.skipValue();
                }else if(name.equals("id")){
                    foodID = reader.nextString();
                }else if(name.equals("ingredientLines")){
                    ingredientList = getIngredientList(reader);
                }else if(name.equals("cookTime")){
                    reader.skipValue();
                }else if(name.equals("attribution")){
                    reader.skipValue();
                }else if(name.equals("numberOfServings")){
                    reader.skipValue();
                }else if(name.equals("totalTimeInSeconds")){
                    reader.skipValue();
                }else if(name.equals("attributes")){
                    reader.skipValue();
                }else if(name.equals("cookTimeInSeconds")){
                    reader.skipValue();
                }else if(name.equals("flavors")){
                    reader.skipValue();
                }else if(name.equals("rating")){
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Recipe(recipeName, description, instructions, videoURL, dietFood, hasCaffeine,
                    glutenFree, calories, ingredientList,"",SQLiteTablesContract.NamesOfAPIs.YUMMLY,foodID);
        } //done
        public List<String> getIngredientList(JsonReader reader) throws IOException{
            List<String> ingredientList = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()){
                ingredientList.add(reader.nextString());
            }
            reader.endArray();
            return ingredientList;
        } //done
    } //done


}