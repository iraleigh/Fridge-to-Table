package com.example.iainchf.helloworld;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecipePage extends AppCompatActivity
{   Recipe currentRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);


    }
    public void onStart(){
        super.onStart();
        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.mainListView);

        SQLiteAPISingletonHandler ingredientsFromDatabaseGetter
                = SQLiteAPISingletonHandler.getInstance(this);

        List<Ingredient> ingredientsFromDatabase
                = ingredientsFromDatabaseGetter.getIngredients();

        String [] ingredientsToGiveToAPIRequest = new String[ingredientsFromDatabase.size()];

        for (int i = 0; i < ingredientsFromDatabase.size(); i++){
            ingredientsToGiveToAPIRequest[i] = ingredientsFromDatabase.get(i).getName();
        }

        Food2ForkAPI apiToHandleRequest = new Food2ForkAPI(ingredientsToGiveToAPIRequest);

        List<Recipe> listOfFiveSampleRecipes = apiToHandleRequest.getFiveRecipes();
        Recipe sampleRecipe = listOfFiveSampleRecipes.get(0);


        ArrayList<String> nutritionList = new ArrayList<>();
        nutritionList.add(sampleRecipe.getName());
        nutritionList.addAll(sampleRecipe.getIngredientList());



        // Create ArrayAdapter using the nutrition list.
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, nutritionList);


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);

        currentRecipe = sampleRecipe;

        new GetImageFromURL().execute(sampleRecipe.getImageUrl());



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
                startActivity(new Intent(RecipePage.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(RecipePage.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(RecipePage.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(RecipePage.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(RecipePage.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openHomePage(View v)
    {
        startActivity(new Intent(RecipePage.this, Home.class));
    }

    public void openCookBookPage(View v)
    {

    }

    public void openFridgePage(View v) {
        startActivity(new Intent(RecipePage.this, Refrigerator.class));
    }
    public void toastMessage(View v)
    {
        Toast.makeText(getApplicationContext(), "Saved to Cookbook",
                Toast.LENGTH_LONG).show();
        SQLiteAPISingletonHandler instanceToAddRecipeToCookbook =
                SQLiteAPISingletonHandler.getInstance(this);
        instanceToAddRecipeToCookbook.addRecipeToCookbook(currentRecipe);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private class GetImageFromURL extends AsyncTask<String,Integer,Drawable>{
        protected Drawable doInBackground(String... strings){
            Drawable d = null;
            for(int i =0; i< strings.length;i++) {
                try {
                    InputStream is = (InputStream) new URL(strings[i]).getContent();
                    d = Drawable.createFromStream(is, "src name");
                    // Escape early if cancel() is called
                    if (isCancelled()) break;
                } catch (Exception e) {
                    return null;
                }
            }
            return d;
        }
        protected void onPostExecute(Drawable d){
            ImageView recipeImage = (ImageView) findViewById(R.id.imageView);
            recipeImage.setImageDrawable(d);
        }
    }
}
