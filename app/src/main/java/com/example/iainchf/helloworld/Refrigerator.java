package com.example.iainchf.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Refrigerator extends AppCompatActivity {

    private List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerator);
        setTitle("My Refrigerator");

        this.context = getApplicationContext();

        ingredientsList = SQLiteAPISingletonHandler.getInstance(context).getIngredients();

        checkIfRefrigeratorIsEmpty();

        populateRefrigeratorTable();
    }

    private void checkIfRefrigeratorIsEmpty() {
        TextView fridgeIsEmptyLabel = (TextView) findViewById(R.id.fridgeIsEmptyLabel);
        TextView addIngredientsLabel = (TextView) findViewById(R.id.addIngredientsLabel);
        ImageView addIngredientsArrow = (ImageView) findViewById(R.id.addIngredientsArrow);
        ImageButton next = (ImageButton) findViewById(R.id.button_next);
        if(ingredientsList.size() == 0) {
            fridgeIsEmptyLabel.setVisibility(View.VISIBLE);
            addIngredientsLabel.setVisibility(View.VISIBLE);
            addIngredientsArrow.setVisibility(View.VISIBLE);
            next.setEnabled(false);
        } else {
            fridgeIsEmptyLabel.setVisibility(View.INVISIBLE);
            addIngredientsLabel.setVisibility(View.INVISIBLE);
            addIngredientsArrow.setVisibility(View.INVISIBLE);
            next.setEnabled(true);
        }
    }

    private void populateRefrigeratorTable() {
        ArrayAdapter<Ingredient> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Ingredient> {
        public MyListAdapter() {
            super(Refrigerator.this, R.layout.row_refrigerator, ingredientsList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.row_refrigerator, parent, false);
            }
            Ingredient ingredient = ingredientsList.get(position);
            TextView textView = (TextView)itemView.findViewById(R.id.ingredientName);
            textView.setText(ingredient.getName());
            ImageButton delete = (ImageButton)itemView.findViewById(R.id.deleteButton);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long id = ingredientsList.get(position).getId();
                    SQLiteAPISingletonHandler.getInstance(context).removeIngredient(id);
                    ingredientsList.remove(position);
                    checkIfRefrigeratorIsEmpty();
                    populateRefrigeratorTable();
                }
            });
            return itemView;
        }
    }

    public void goToAddIngredient(View v) {
        Intent in = new Intent(this, AddIngredient.class);
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
                startActivity(new Intent(Refrigerator.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(Refrigerator.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(Refrigerator.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(Refrigerator.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(Refrigerator.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToGetRecipes(View v){
        Intent in = new Intent(this,Get_Recipes.class);
        startActivity(in);
    }

}