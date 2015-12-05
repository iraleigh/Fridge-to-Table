package com.example.iainchf.helloworld;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecipePage extends AppCompatActivity {
    /*
    Recipe mCurrentRecipe;
    ArrayList<ImageView> mCurrentImageToLoad;
    int mCurrentImageIterator = 0;
    */
    List<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        mRecipes = new ArrayList<>();

    }
    public void onStart(){
        super.onStart();


        //Get ingredients from the database
        SQLiteAPISingletonHandler ingredientsFromDatabaseGetter
                = SQLiteAPISingletonHandler.getInstance(this);

        List<Ingredient> ingredientsFromDatabase
                = ingredientsFromDatabaseGetter.getIngredients();

        String [] ingredientsToGiveToAPIRequest = new String[ingredientsFromDatabase.size()];

        for (int i = 0; i < ingredientsFromDatabase.size(); i++){
            ingredientsToGiveToAPIRequest[i] = ingredientsFromDatabase.get(i).getName();
        }

        //Get recipes from the api
        Food2ForkAPI apiToHandleRequest = new Food2ForkAPI(ingredientsToGiveToAPIRequest);

        List<Recipe> listOfFiveSampleRecipes = apiToHandleRequest.getFiveRecipes();
        mRecipes.addAll(listOfFiveSampleRecipes);

        //Get the names of the recipes for the list adapter
        final String [] recipeNames = new String[listOfFiveSampleRecipes.size()];
        String [] recipeImageURL = new String[listOfFiveSampleRecipes.size()];
        for(int i = 0; i < listOfFiveSampleRecipes.size(); i++) {
            recipeNames[i] = listOfFiveSampleRecipes.get(i).getName();
            recipeImageURL[i] = listOfFiveSampleRecipes.get(i).getImageUrl();
        }


        //Create a list to display recipe names and images
        ListView recipeList = (ListView) findViewById(R.id.mainListView);

        //Create an adapter from the names and image URLs
        RecipeList adapter = new RecipeList(RecipePage.this, recipeNames,recipeImageURL);

        //Populate the list with Recipes from the adapter
        recipeList.setAdapter(adapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent in = new Intent(RecipePage.this, RecipeDetail.class);
            in.putExtra("name", mRecipes.get(position).getName());
            in.putExtra("description", mRecipes.get(position).getDescription());
            in.putExtra("instructions", mRecipes.get(position).getInstructions());
            in.putExtra("videoURL", mRecipes.get(position).getVideoURL());
            in.putExtra("dietFood", mRecipes.get(position).isDiet());
            in.putExtra("hasCaffeine", mRecipes.get(position).isCaffeinated());
            in.putExtra("glutenFree", mRecipes.get(position).isGlutenFree());
            in.putExtra("calories", mRecipes.get(position).getCalorieCount());
            in.putExtra("nameOfAPI", mRecipes.get(position).getNameOfAPI());
            in.putStringArrayListExtra("ingredients", new ArrayList<String>(mRecipes.get(position).getIngredientList()));
            in.putExtra("idFromAPI", mRecipes.get(position).getIdFromAPI());
            in.putExtra("imageUrl", mRecipes.get(position).getImageUrl());
            startActivity(in);
            }

        });
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
            /*
            Log.e("Downloading Image:", "#"+mCurrentImageIterator);
            mCurrentImageToLoad.get(mCurrentImageIterator).setImageDrawable(d);
            mCurrentImageIterator++;
            */
        }
    }
}
