package com.example.iainchf.helloworld;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iainchf on 9/30/15.
 */
public class Food2ForkAPI extends RecipesProviderAPI {
    private final String API_KEY = "61501f7532d789ba822260444db1efed";
    private List<Recipe> recipeList;
    private String [] ingredients;
    private String url;
    private List<String> ids;
    private int placeInIdList;
    public static final String PARSE_JSON_FOR_ID_TAG = "Inside ";
    public static final String PARSE_JSON_FOR_RECIPE_TAG = "Inside ";
    public boolean noRecipesFound = false;

    /**
     * Default Constructor
     * initialized url and ingredients to empty variables
     */
    public Food2ForkAPI() {
        url = "http://food2fork.com/api/search?key="+ API_KEY +"&q=";
        ingredients = new String[0];
    }

    /**
     * Overloaded Constructor
     * Initializes url;
     * Takes in a url to call REST API, parse the return data, and fill a recipe list.
     * @param ingredients
     */
    public Food2ForkAPI(String[] ingredients){
        this.ingredients = ingredients;
        this.recipeList = new ArrayList<>();
        this.url = createSearchURL();
        HttpGetData food2ForkGetter = new HttpGetData(url);

        while(food2ForkGetter.getData() == null);

        String json = food2ForkGetter.getData();

        ids = parseJsonForID(json);

        if(ids.size() < 1){
            url = "http://food2fork.com/api/search?key=" + API_KEY + "&q=";
            noRecipesFound = true;

            food2ForkGetter = new HttpGetData(url);

            while(food2ForkGetter.getData() == null);

            json = food2ForkGetter.getData();

            ids = parseJsonForID(json);
        }

        placeInIdList = 0;



    }

    /**
     * getRecipes()
     * Takes in a url to call REST API, parse the return data, and fill a recipe list.
     * @return recipeList
     */
    public List<Recipe> getRecipes(){
        if (placeInIdList < ids.size()) {
            for (int i = placeInIdList; i < ids.size(); i++) {
                HttpGetData food2ForkRecipeGetter = new HttpGetData(createGetURL(ids.get(i)));
                while(food2ForkRecipeGetter.getData() == null);
                String json = food2ForkRecipeGetter.getData();
                recipeList.add(parseJsonForRecipes(json, ids.get(i)));
            }
            return recipeList;
        } else {
            return new ArrayList<>();
        }
    }


    private List<String> parseJsonForID(String json){
        Food2ForkIdJsonReader idReader = new Food2ForkIdJsonReader();
        List<String> ids = new ArrayList<>();
        try {
            ids = idReader.readJsonStream(json);
        } catch (IOException ioe){
            Log.e("In parseJsonForID",ioe.getMessage());
        } finally {
            return ids;
        }
    }
    public Recipe parseJsonForRecipes(String json, String id){
        Food2ForkRecipeJsonReader recipeReader = new Food2ForkRecipeJsonReader();
        Recipe recipe = new Recipe();
        try {

            recipe = recipeReader.readJsonStream(json, id);
        } catch (IOException e){
            return recipe;
        } finally {
            return recipe;
        }
    }

    public String createSearchURL() {
        String tempUrl = "http://food2fork.com/api/search?key=" + API_KEY + "&q=";
        for(String i: ingredients){
            tempUrl += i + "%20";
        }
        return tempUrl;
    }

    public String createGetURL(String id) {
        return "http://food2fork.com/api/get?key=" + API_KEY + "&rId=" + id;
    }


    private class Food2ForkIdJsonReader {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public List<String> readJsonStream(String in) throws IOException{
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(new StringReader(in));
            try{
                return readMessagesArray(jsonReader);
            }finally{
                try {
                    jsonReader.close();
                } catch(Exception e){}
            }
        }

        @SuppressWarnings("unchecked")
        public List<String> readMessagesArray(com.google.gson.stream.JsonReader reader) throws IOException {
            Gson gson = new Gson();
            List<String> ids = new ArrayList();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = gson.fromJson(reader.nextName(),String.class);

                if (name.equals("recipes")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        try {
                            ids.add(getID(reader));
                        } catch (Exception e){
                            return ids;
                        }
                    }
                    reader.endArray();
                } else if (name.equals("count")) {
                    if(reader.nextInt() == 0){
                        return ids;
                    }
                } else if(name.equals("error")){
                    return ids;
                }
            }
            reader.endObject();
            return ids;
        }

        public String getID(com.google.gson.stream.JsonReader reader) throws IOException{
            String tempId = null;
            Gson gson = new Gson();
            reader.beginObject();
            while (reader.hasNext()){
                String name = gson.fromJson(reader.nextName(), String.class);
                if(name.equals("publisher")){
                    reader.skipValue();
                } else if(name.equals("f2f_url")){
                    reader.skipValue();
                } else if(name.equals("title")){
                    reader.skipValue();
                } else if(name.equals("source_url")){
                    reader.skipValue();
                } else if(name.equals("recipe_id")){
                    tempId = reader.nextString();
                } else if(name.equals("image_url")){
                    reader.skipValue();
                } else if(name.equals("social_rank")){
                    reader.skipValue();
                } else if(name.equals("publisher_url")){
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new String(tempId);
        }


    }

    private class Food2ForkRecipeJsonReader {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public Recipe readJsonStream(String in, String id) throws IOException{
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(new StringReader(in));
            try{
                return readMessagesArray(jsonReader, id);
            }finally{
                try {
                    jsonReader.close();
                } catch(Exception e){}
            }
        }


        @SuppressWarnings("unchecked")
        public Recipe readMessagesArray(com.google.gson.stream.JsonReader reader, String id) throws IOException {
            Recipe recipe = new Recipe();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("recipe")){
                    try {
                        recipe = getRecipe(reader, id);
                    } catch (Exception e){
                        return recipe;
                    }
                }
            }
            reader.endObject();
            return recipe;
        }

        private Recipe getRecipe(com.google.gson.stream.JsonReader reader, String id) throws IOException{
            String recipeName = "";
            String description = "";
            String instructions = "";
            String videoURL = "";
            boolean dietFood = false;
            boolean hasCaffeine = false;
            boolean glutenFree = false;
            int calories = 0;
            List<String> ingredientList = new ArrayList<>();
            String imageURL = "";




            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("title")){
                    recipeName = reader.nextString();
                } else if(name.equals("source_url")){
                    instructions = reader.nextString();
                } else if(name.equals("ingredients")){
                    ingredientList = getIngredientList(reader);
                } else if(name.equals("publisher")) {
                    reader.skipValue();
                }  else if(name.equals("f2f_url")) {
                    reader.skipValue();
                } else if(name.equals("recipe_id")) {
                    reader.skipValue();
                } else if(name.equals("image_url")) {
                    imageURL= reader.nextString();
                } else if(name.equals("social_rank")) {
                    reader.skipValue();
                } else if(name.equals("publisher_url")) {
                    reader.skipValue();
                }
            }

            reader.endObject();
            return new Recipe(recipeName, description, instructions, videoURL, dietFood, hasCaffeine,
            glutenFree, calories, ingredientList, imageURL,SQLiteTablesContract.NamesOfAPIs.FOOD2FORK, id);
        }
        private List<String> getIngredientList(com.google.gson.stream.JsonReader reader) throws IOException{
            List<String> ingredientList = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()){
                ingredientList.add(reader.nextString());
            }
            reader.endArray();
            return ingredientList;
        }
    }
}
