package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipePage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.mainListView);

        // Create and populate a List of nutrition names.
        String[] nutrition = new String[]
                {"      Recipe       ", "Flour 4 cups", "Sugar 1 tsp", "Salt 2 tsp", "Water 1 1/2 cups",
                        "       Nutrition      ", "Calories", "Total Fat", "Saturated Fat", "Sodium", "Total Carbs",
                        "Sugars", "Protein"};
        ArrayList<String> nutritionList = new ArrayList<>();
        nutritionList.addAll( Arrays.asList(nutrition) );

        // Create ArrayAdapter using the nutrition list.
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, nutritionList);

        // Add more Nutrition information. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.
        listAdapter.add( "Dietary Fiber" );
        listAdapter.add( "Vitamin A" );
        listAdapter.add( "Vitamin C" );
        listAdapter.add("Calcium");

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openHomePage(View v)
    {
        startActivity(new Intent(RecipePage.this, HomeDummyActivity.class));
    }

    public void openCookBookPage(View v)
    {
        startActivity(new Intent(RecipePage.this, CookBookDummyActivity.class));
    }

    public void openFridgePage(View v) {
        startActivity(new Intent(RecipePage.this, FridgeDummyActivity.class));
    }
    public void toastMessage(View v)
    {
        Toast.makeText(getApplicationContext(), "No data base to save to yet ... ",
                Toast.LENGTH_LONG).show();
    }
}
