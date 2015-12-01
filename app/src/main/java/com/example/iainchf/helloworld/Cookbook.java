package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        SQLiteAPISingletonHandler insta = SQLiteAPISingletonHandler.getInstance(this);
        savedRecipes.addAll(insta.getCookbook());
        //Recipe sample = new Recipe("Lasagna", "Meat dish", "Put in oven", "vURL", false, false, false, 0,new ArrayList<String>(),"","api", "apiId");

        //for (int i = 0; i < numSavedRecipes; i++) {
          //  savedRecipes.add(i, sample);
        //}
    }

        /*public void delete (View view) {
            savedRecipes.remove(numSavedRecipes);
            numSavedRecipes = numSavedRecipes - 1;
        }*/

    public void goToGetRecipes(View v){
        Intent in = new Intent(this,Get_Recipes.class);
        startActivity(in);
    }

    public void goToHome(View v){
        Intent in = new Intent(this,Home.class);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.get_recipes:
                startActivity(new Intent(Cookbook.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(Cookbook.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(Cookbook.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(Cookbook.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(Cookbook.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
