package com.example.iainchf.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("AAA", "POP");

    }

    public void goToGetRecipes(View v){
        Intent in = new Intent(this,Get_Recipes.class);
        startActivity(in);
    }

    public void goToRefrigerator (View v){
        Intent in = new Intent(this, Refrigerator.class);
        startActivity(in);
    }

    public void goToCookBook (View v) {
        Intent in = new Intent(this, Cookbook.class);
        startActivity(in);
    }

    public void goToFindIngredients (View v) {
        Intent in = new Intent(this, Find_Ingredients.class);
        startActivity(in);
    }

    public void goToNeverCookBook (View v) {
        Intent in = new Intent(this, Never_Cookbook.class);
        startActivity(in);
    }

}
