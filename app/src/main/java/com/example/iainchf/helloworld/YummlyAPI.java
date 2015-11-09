package com.example.iainchf.helloworld;

/**
 * Created by alvinlu on 10/14/15.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvinlu on 9/25/15.
 * a student at SFSU
 */
public class YummlyAPI {

    private List<Recipe> recipeList;
    private List<String> ingredients;
    private String url;
    private static final String PARSEJSON_TAG = "parse JSON";
    private String []ids = new String [5];
    private List messages = new ArrayList();

    //default constructor
    public YummlyAPI(){

        url = "http://api.yummly.com/v1/api/recipes?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";

    }

    public YummlyAPI(List<String> ingredients){
        this.ingredients = ingredients;
        url = createURL();
        HttpGetData yummlyGetter = new HttpGetData(url);

        while(yummlyGetter.getData() == null);

        String json = yummlyGetter.getData();
        recipeList = parseJSON(json);
    }

    //get Recipe
    private void getRecipeByID(){
        //TODO create proper url
        //String URL = "";
        for (String id: ids) {
            String idURL = "http://api.yummly.com/v1/api/recipe/" + id + "?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";
            HttpGetData idData = new HttpGetData(idURL);
            String json = idData.getData();
            recipeList.add(parseJsonFromID(json));
        }

    }

    private String createURL(){
        String tempURL = "http://api.yummly.com/v1/api/recipes?_app_id=612599c2&_app_key=48de0f287a32bb809ebc97c99ac31f86";
        // for loop to traverse through the ingredients
        for(String i: ingredients){
            tempURL += "&allowedIngredient[]=" + i;
        }
        return tempURL;
    }

    private List<Recipe> parseJSON(String json){
        YummlyIDJsonReader yummlyReader = new YummlyIDJsonReader();

        try {
            return yummlyReader.readJsonStream(json);
        } catch (IOException e){
            return new ArrayList<>();
        }
    }
    private class YummlyIDJsonReader {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public List<Recipe> readJsonStream(String in) throws IOException{
            try (JsonReader reader = new JsonReader(new StringReader(in))) {
                return readMessagesArray(reader);
            }
        }

        @SuppressWarnings("unchecked")
        public List<Recipe> readMessagesArray(JsonReader reader) throws IOException {
            List<Recipe> recipes = new ArrayList();

            reader.beginArray();
            while (reader.hasNext()) {
                recipes.add(readRecipes(reader));
            }
            reader.endArray();
            return recipes;
        }

        public Recipe readRecipes(JsonReader reader) throws IOException{

            String id;
            int i = 0;
            //Ingredient[] ingredientList;

            reader.beginObject();

            //while (reader.hasNext() && i < 5){

            String name = reader.nextName();

            if(name.equals("matches")) {

                reader.beginArray();

                while (reader.hasNext() && i < 5) {
                    if(name.equals("imageUrlBySize")){
                        reader.skipValue();
                    } else if(name.equals("sourceDisplayName")){
                        reader.skipValue();
                    } else if(name.equals("ingredients")){
                        reader.skipValue();
                    } else if(name.equals("id")) {
                        ids[i] = reader.nextString();
                        i++;
                    } else if(name.equals("smallImageUrls")){
                        reader.skipValue();
                    } else if(name.equals("recipeName")){
                        reader.skipValue();
                    } else if(name.equals("totalTimeInSeconds")){
                        reader.skipValue();
                    } else if(name.equals("attributes")){
                        reader.skipValue();
                    } else if(name.equals("flavors")){
                        reader.skipValue();
                    } else if(name.equals("rating")){
                        reader.skipValue();
                    }
                    else{
                        reader.skipValue();
                    }
                }
            } else if(name.equals("criteria")) {
                reader.skipValue();
            } else if(name.equals("totalMatchCount")){
                reader.skipValue();
            } else if(name.equals("attribution")){
                reader.skipValue();
            } else {
                reader.skipValue();
            }
            //}

            reader.endObject();
            return new Recipe();
        }

    }


    private Recipe parseJsonFromID(String json){
        YummlyJsonReader yummlyReader = new YummlyJsonReader();

        try {
            return yummlyReader.readJsonStream(json);

        } catch (IOException e){
            return new Recipe();
        } 

    }

    //TODO customize to read Recipe from Yummly
    private class YummlyJsonReader {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public Recipe readJsonStream(String in) throws IOException{
            try (JsonReader reader = new JsonReader(new StringReader(in))) {
                return readMessagesArray(reader);
            }
        }
        @SuppressWarnings("unchecked")
        public Recipe readMessagesArray(JsonReader reader) throws IOException {
            Recipe recipe;

            reader.beginArray();
            recipe = readRecipe(reader);
            reader.endArray();
            return recipe;
        }

        public Recipe readRecipe(JsonReader reader) throws IOException{

            String recipeName;
            String sourceURL;
            String ingredients;

            List<String> ingredientList;

            ingredientList = new ArrayList<>();
            reader.beginObject();
            //TODO fill in variables from JSON
            while (reader.hasNext()){
                String name = reader.nextName();
                if (name.equals("yield")){
                    reader.skipValue();
                } else if(name.equals("nutritionEstimates")){
                    reader.skipValue();
                } else if(name.equals("totalTime")){
                    reader.skipValue();
                } else if(name.equals("images")){
                    reader.skipValue();
                } else if(name.equals("name")){
                    recipeName = reader.nextString();
                } else if(name.equals("source")){
                    if(name.equals("sourceDisplayName")){
                        reader.skipValue();
                    } else if(name.equals("SourceSiteUrl")){
                        reader.skipValue();
                    } else if(name.equals("sourceRecipeUrl")) {
                        sourceURL = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                } else if(name.equals("id")){
                    reader.skipValue();
                } else if(name.equals("ingredientLines") && reader.peek() != JsonToken.NULL){
                    reader.beginArray();
                    while(reader.hasNext()) {
                        ingredientList.add(reader.nextString());
                    }
                    reader.endArray();
                } else if(name.equals("attribution")){
                    reader.skipValue();
                } else if(name.equals("numberOfServings")){
                    reader.skipValue();
                } else if(name.equals("totalTimeInSeconds")){
                    reader.skipValue();
                } else if(name.equals("attributes")){
                    reader.skipValue();
                } else if(name.equals("flavors")){
                    reader.skipValue();
                } else if(name.equals("rating")){
                    reader.skipValue();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Recipe();
        }
    }
}