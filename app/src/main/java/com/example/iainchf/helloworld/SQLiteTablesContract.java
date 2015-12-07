package com.example.iainchf.helloworld;

import android.provider.BaseColumns;

/**
 * Contract between all of the classes that use the SQLite database,
 * to use the same Table and Column names.
 * @author Iain Raleigh <iain.raleigh.dev@gmail.com>
 */
public final class SQLiteTablesContract {

    public SQLiteTablesContract(){}

    public static abstract class FridgeOfIngredientsEntry implements BaseColumns{
        public static final String TABLE_NAME = "ingredientList";
        public static final String COLUMN_NAME_INGREDIENT_NAME = "ingredientName";
        public static final String COLUMN_NAME_INGREDIENT_TYPE = "ingredientType";
        public static final String COLUMN_NAME_INGREDIENT_LIQUID = "ingredintLiquid";
    }
    public static abstract class CookBookOfFavoriteRecipes implements BaseColumns{
        public static final String TABLE_NAME = "favoriteRecipes";
        public static final String COLUMN_NAME_API_SOURCE_NAME = "apiSourceName";
        public static final String COLUMN_NAME_API_SOURCE_ID = "apiSourceId";
    }
    public static abstract class NamesOfAPIs{
        public static final String FOOD2FORK = "Food2Fork";
        public static final String YUMMLY= "Yummly";
    }
}
