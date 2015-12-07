package com.example.iainchf.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Cookbook extends AppCompatActivity {

    private ArrayList<Recipe> savedRecipes= new ArrayList<>();

    private ListView list;
    private RecipeList adapter;
    private String [] recipeNames;
    private String [] recipeImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook);

        list = (ListView) findViewById(R.id.listView2);
        list.setHeaderDividersEnabled(true);

        setTitle("Cookbook");

        populateCookbook();

        adapter = new RecipeList(Cookbook.this, recipeNames, recipeImageURL);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(Cookbook.this, RecipeDetail.class);
                in.putExtra("name", savedRecipes.get(position).getName());
                in.putExtra("description", savedRecipes.get(position).getDescription());
                in.putExtra("instructions", savedRecipes.get(position).getInstructions());
                in.putExtra("videoURL", savedRecipes.get(position).getVideoURL());
                in.putExtra("dietFood", savedRecipes.get(position).isDiet());
                in.putExtra("hasCaffeine", savedRecipes.get(position).isCaffeinated());
                in.putExtra("glutenFree", savedRecipes.get(position).isGlutenFree());
                in.putExtra("calories", savedRecipes.get(position).getCalorieCount());
                in.putExtra("nameOfAPI", savedRecipes.get(position).getNameOfAPI());
                in.putStringArrayListExtra("ingredients", new ArrayList<String>(savedRecipes.get(position).getIngredientList()));
                in.putExtra("idFromAPI", savedRecipes.get(position).getIdFromAPI());
                in.putExtra("imageUrl", savedRecipes.get(position).getImageUrl());
                startActivity(in);
            }

        });

    }

    private void populateCookbook() {
        SQLiteAPISingletonHandler instance = SQLiteAPISingletonHandler.getInstance(this);
        savedRecipes.clear();
        savedRecipes.addAll(instance.getCookbook());
        recipeNames = new String[savedRecipes.size()];
        recipeImageURL = new String[savedRecipes.size()];
        for(int i = 0; i < savedRecipes.size(); i++){
            recipeNames[i] = savedRecipes.get(i).getName();
            recipeImageURL[i] = savedRecipes.get(i).getImageUrl();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateCookbook();
        RecipeList adapter1 = new RecipeList(Cookbook.this, recipeNames, recipeImageURL);
        list.setAdapter(adapter1);
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
