package com.example.iainchf.helloworld;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

public class Get_Recipes extends AppCompatActivity {

    private static String[] ingredientsToGiveToAPI;
    //Is this the correct way to do this? I have no idea.
    //If I don't do it this way I can't access i and j in the onChangeListener method.
    private static int i;
    private static int j;
    private static int halfListSize;
    private static double checkEndOfList;
    private static List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__recipes);

        SQLiteAPISingletonHandler ingredientListSQL = SQLiteAPISingletonHandler.getInstance(this);

        ingredients = ingredientListSQL.getIngredients();

        List<Recipe> recipes = ingredientListSQL.getCookbook();

        TableLayout IngredientsTable = (TableLayout)findViewById(R.id.IngredientTable);

        ingredientsToGiveToAPI = new String[ingredients.size()];

        //half list size because we want two columns. If we want more than two columns you have to split the list
        //size by the number of columns you have.
        halfListSize = (ingredients.size()/2);
        checkEndOfList = (double)ingredients.size()/2.0;

        for(i = 0, j = halfListSize; i < halfListSize; i++, j++)
        {
            CheckBox box = new CheckBox(this);
            CheckBox box2 = new CheckBox(this);
            CheckBox edgeBox = new CheckBox(this);
            TableRow newRow = new TableRow(this);

            newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            box.setText(ingredients.get(i).getName());
            box2.setText(ingredients.get(j).getName());

            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    //What the check box does when it's checked
                    // All checkboxes start unchecked.
                    if(buttonView.isChecked())
                    {
                        //what it does when checked
                        ingredientsToGiveToAPI[i] = ingredients.get(i).getName();
                    }
                    else
                    {
                        //what it does when unchecked
                        ingredientsToGiveToAPI[i] = "";
                    }

                }
            });

            box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //What the check box2 does when it's checked.
                    // All checkboxes start unchecked.
                    if(buttonView.isChecked())
                    {
                        //what it does when checked
                        ingredientsToGiveToAPI[j] = ingredients.get(j).getName();
                    }
                    else
                    {
                        //what it does when unchecked
                        ingredientsToGiveToAPI[j] = "";
                    }
                }
            });

            newRow.addView(box);
            newRow.addView(box2);

            IngredientsTable.addView(newRow);

            if(i == ( halfListSize - 1 ) && halfListSize - checkEndOfList != 0)
            {
                edgeBox.setText(ingredients.get(ingredients.size() - 1).getName());

                edgeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //What the check box does when it's checked
                        // All checkboxes start unchecked.
                        if (buttonView.isChecked()) {
                            //what it does when checked
                            ingredientsToGiveToAPI[ingredients.size() - 1] = ingredients.get(ingredients.size() - 1).getName();
                        } else {
                            //what it does when unchecked
                            ingredientsToGiveToAPI[ingredients.size() - 1] = "";
                        }
                     }
                });

                IngredientsTable.addView(edgeBox);
            }
        }


        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.FILTER , android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void goToRecipe(View v)
    {
        startActivity(new Intent(Get_Recipes.this, RecipePage.class));
    }

    public String[] getIngredients()
    {
        return ingredientsToGiveToAPI;
    }
}
