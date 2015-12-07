package com.example.iainchf.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iainchf on 11/9/15.
 */
public class SQLiteAPIHelper extends SQLiteOpenHelper{
    //List of constants are SQL queries to be
    //applied to the database.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FridgeToTable.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String COMMA_SEP = ",";



    private static final String SQL_CREATE_INGREDIENTLIST =
            "CREATE TABLE " + SQLiteTablesContract.FridgeOfIngredientsEntry.TABLE_NAME + " (" +
                    SQLiteTablesContract.FridgeOfIngredientsEntry._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_NAME +
                    TEXT_TYPE + COMMA_SEP +
                    SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_TYPE +
                    TEXT_TYPE + COMMA_SEP +
                    SQLiteTablesContract.FridgeOfIngredientsEntry.COLUMN_NAME_INGREDIENT_LIQUID +
                    BOOLEAN_TYPE +
                    " )";
    private static final String SQL_DELETE_INGREDIENTLIST =
            "DROP TABLE IF EXISTS " + SQLiteTablesContract.FridgeOfIngredientsEntry.TABLE_NAME;


    private static final String SQL_CREATE_COOKBOOK =
            "CREATE TABLE " + SQLiteTablesContract.CookBookOfFavoriteRecipes.TABLE_NAME + " (" +
                    SQLiteTablesContract.CookBookOfFavoriteRecipes._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_NAME +
                    TEXT_TYPE + COMMA_SEP +
                    SQLiteTablesContract.CookBookOfFavoriteRecipes.COLUMN_NAME_API_SOURCE_ID +
                    TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_COOKBOOK =
            "DROP TABLE IF EXISTS " + SQLiteTablesContract.CookBookOfFavoriteRecipes.TABLE_NAME;



    public SQLiteAPIHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Methods to Overwrite

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_INGREDIENTLIST);
        db.execSQL(SQL_CREATE_COOKBOOK);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_INGREDIENTLIST);
        db.execSQL(SQL_DELETE_COOKBOOK);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
