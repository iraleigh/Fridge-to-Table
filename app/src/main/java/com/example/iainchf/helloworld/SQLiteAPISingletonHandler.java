package com.example.iainchf.helloworld;



import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iainchf on 11/9/15.
 *
 */
public class SQLiteAPISingletonHandler {
    private static SQLiteAPIHelper sqLitehelper;
    private static SQLiteDatabase db;

    private SQLiteAPISingletonHandler(){}

    private static SQLiteAPISingletonHandler instance = new SQLiteAPISingletonHandler();

    public static SQLiteAPISingletonHandler getInstance(Context context){
        instance.setSQLiteAPIHelper(context);
        return instance;
    }
    public static void releaseInstance(){
        db.close();
    }


    private void setSQLiteAPIHelper(Context context){
        sqLitehelper = new SQLiteAPIHelper(context);
    }
    /*
     * This is a list of requested methods and there respective
     * functionality.
     */

    //gets list of all ingredients in the DB
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredientsList = new ArrayList<>();

        db = sqLitehelper.getReadableDatabase();
        String [] columnsToReadFrom = {
                SQLiteTablesContract.FridgeOfIngredientsEntry._ID,
                SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_NAME,
                SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_TYPE,
                SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_LIQUID
        };
        class Columns {
            public static final int ID = 0;
            public static final int NAME = 1;
            public static final int TYPE = 2;
            public static final int LIQUID = 3;
        }

        String sortOrder = SQLiteTablesContract.FridgeOfIngredientsEntry._ID + " ASC";

        Cursor c = db.query(
                SQLiteTablesContract.FridgeOfIngredientsEntry.TABLE_NAME, // The table to query
                columnsToReadFrom, // The columns to return
                null,              // The columns for the WHERE clause
                null,              // The values for the WHERE clause
                null,              // don't group the rows
                null,              // don't filter by row groups
                sortOrder
        );

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(Columns.ID);
                String name = c.getString(Columns.NAME);
                String type = c.getString(Columns.TYPE);
                String pictureURL = null;
                boolean liquid = false;
                if (c.getInt(Columns.LIQUID) == 1) {
                    liquid = true;
                }
                ingredientsList.add(new Ingredient(id, name, type, pictureURL, liquid));
            } while (c.moveToNext());
        }
        c.close();
        return ingredientsList;
    }

    //adds one ingredient to the DB
    public void addIngredient(Ingredient ingredient) {
        // Gets the data repository in write mode
        db = sqLitehelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(
                SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_NAME,
                ingredient.getName());
        values.put(SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_TYPE,
                ingredient.getType());
        values.put(SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_LIQUID,
                ingredient.isLquid());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SQLiteTablesContract.FridgeOfIngredientsEntry.TABLE_NAME,"null",values);
        ingredient.setId(newRowId);
    }

    //removes an ingredient with a given id from the DB
    public void removeIngredient(int id) {
        db = sqLitehelper.getWritableDatabase();
        db.delete(
                SQLiteTablesContract.FridgeOfIngredientsEntry.TABLE_NAME,
                SQLiteTablesContract.FridgeOfIngredientsEntry._ID + " LIKE ?",
                new String[]{String.valueOf(id)}
        );
    }

    //returns a list of ingredients that match the search string
    public List<Ingredient> searchIngredients(String search) {
        return new ArrayList<>();
    }


    public List<Recipe> getCookbook(){
        List<Recipe> favoriteRecipes = new ArrayList<>();

        db = sqLitehelper.getReadableDatabase();
        String [] columnsToReadFrom = {
                SQLiteTablesContract.CookBookOfFavoriteRecipes._ID,
                SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_NAME,
                SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_ID
        };
        class Columns {
            public static final int ID = 0;
            public static final int API_NAME = 1;
            public static final int API_ID = 2;
        }

        String sortOrder = SQLiteTablesContract.CookBookOfFavoriteRecipes._ID + " ASC";

        Cursor c = db.query(
                SQLiteTablesContract.CookBookOfFavoriteRecipes.TABLE_NAME, // The table to query
                columnsToReadFrom, // The columns to return
                null,              // The columns for the WHERE clause
                null,              // The values for the WHERE clause
                null,              // don't group the rows
                null,              // don't filter by row groups
                sortOrder
        );

        if (c.moveToFirst()) {
            do {
                long id = c.getInt(Columns.ID);
                String apiName = c.getString(Columns.API_NAME);
                String apiId = c.getString(Columns.API_ID);
                if(apiName.equals(SQLiteTablesContract.NamesOfAPIs.FOOD2FORK)){
                    Food2ForkAPI api = new Food2ForkAPI();
                    HttpGetData getDataAboutRecipe = new HttpGetData(api.createGetURL(apiId));
                    while(getDataAboutRecipe.getData() == null);

                    Recipe currentRecipe = api.parseJsonForRecipes(getDataAboutRecipe.getData(), apiId);

                    favoriteRecipes.add(currentRecipe);
                } else if (apiName.equals(SQLiteTablesContract.NamesOfAPIs.YUMMLY)){

                }
            } while (c.moveToNext());
        }
        c.close();

        return favoriteRecipes;
    }

    public void addRecipeToCookbook(Recipe recipe) {
        // Gets the data repository in write mode
        db = sqLitehelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(
                SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_NAME,
                recipe.getNameOfAPI());
        values.put(SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_ID,
                recipe.getIdFromAPI());


        db.insert(SQLiteTablesContract.CookBookOfFavoriteRecipes.TABLE_NAME,"null",values);
    }

    public void removeRecipeFromCookbook(Recipe recipe){
        db = sqLitehelper.getWritableDatabase();
        db.delete(
                SQLiteTablesContract.CookBookOfFavoriteRecipes.TABLE_NAME,
                SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_ID + " LIKE ?",
                new String[]{recipe.getIdFromAPI()}
        );
    }

}
