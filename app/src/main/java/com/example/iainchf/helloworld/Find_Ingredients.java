package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Find_Ingredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__ingredients);
    }
    public void goToGoogleMaps(View v){
        Intent in = new Intent(this, Google_Store_Find.class);
        startActivity(in);
    }

    public void goToAmazonOrdering(View v){
        Intent in = new Intent(this, Amazon_Ordering.class);
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
                startActivity(new Intent(Find_Ingredients.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(Find_Ingredients.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(Find_Ingredients.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(Find_Ingredients.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(Find_Ingredients.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
