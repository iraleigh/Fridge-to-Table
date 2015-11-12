package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class Cookbook extends AppCompatActivity {
    int numSavedRecipes = 1;
    ArrayList<Recipe> savedRecipes= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook);
        ArrayAdapter<Recipe> adapt = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_1, savedRecipes);
        ListView list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapt);
        Recipe sample = new Recipe("Lasagna", "Meat dish", "Put in oven", "vURL", false, false, false, 0, new ArrayList<String>(),"api", "apiId");

        for (int i = 0; i < numSavedRecipes; i++) {
            savedRecipes.add(i, sample);
        }
    }

        /*public void delete (View view) {
            savedRecipes.remove(numSavedRecipes);
            numSavedRecipes = numSavedRecipes - 1;
        }*/

    public void goToGetRecipes(View v){
        //Log.d("In startA","Starting Recipe Activity" );
        Intent in = new Intent(this,Get_Recipes.class);
        startActivity(in);
    }

    public void goToHome(View v){
        //Log.d("In startA","Starting Recipe Activity" );
        Intent in = new Intent(this,Home.class);
        startActivity(in);
    }
}
