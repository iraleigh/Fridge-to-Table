package com.example.iainchf.helloworld;

import android.provider.BaseColumns;

/**
 * Created by iainchf on 11/9/15.
 */
public final class SQLiteTablesContract {

    public SQLiteTablesContract(){}

    public static abstract class FridgeOfIngredientsEntry implements BaseColumns{
        public static final String TABLE_NAME = "ingredientList";
        public static final String COLUMN_NAME_INGREDIENT_NAME = "ingredientName";
        public static final String COLUMN_NAME_INGREDIENT_TYPE = "ingredientType";
        public static final String COLUMN_NAME_INGREDIENT_LIQUID = "ingredintLiquid";
    }
}
