package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

        TableLayout IngredientsTable = (TableLayout)findViewById(R.id.IngredientTable);

        ingredientsToGiveToAPI = new String[ingredients.size() + 1];

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
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //What the check box does when it's checked
                    // All checkboxes start unchecked.
                    if (buttonView.isChecked())
                    {
                        //what it does when checked
                        String ingredientName = (String) buttonView.getText();
                        for (int l = 0; l < ingredients.size(); l++)
                        {
                            if (ingredients.get(l).getName().equals(ingredientName))
                            {
                                ingredientsToGiveToAPI[l] = ingredients.get(l).getName();
                                break;
                            }
                        }
                    }
                    else
                    {
                        //what it does when unchecked
                        String ingredientName = (String) buttonView.getText();
                        for (int l = 0; l < ingredients.size(); l++)
                        {
                            if (ingredients.get(l).getName().equals(ingredientName))
                            {
                                ingredientsToGiveToAPI[l] = null;
                                break;
                            }
                        }
                    }

                }
            });

            box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //What the check box2 does when it's checked.
                    // All checkboxes start unchecked.
                    if (buttonView.isChecked())
                    {
                        //what it does when checked
                        String ingredientName = (String)buttonView.getText();
                        for(int l = 0; l < ingredients.size(); l++)
                        {
                            if(ingredients.get(l).getName().equals(ingredientName))
                            {
                                ingredientsToGiveToAPI[l] = ingredients.get(l).getName();
                                break;
                            }
                        }
                    }
                    else
                    {
                        //what it does when unchecked
                        String ingredientName = (String) buttonView.getText();
                        for (int l = 0; l < ingredients.size(); l++)
                        {
                            if (ingredients.get(l).getName().equals(ingredientName))
                            {
                                ingredientsToGiveToAPI[l] = null;
                                break;
                            }
                        }
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
                        if (buttonView.isChecked())
                        {
                            //what it does when checked
                            String ingredientName = (String)buttonView.getText();
                            for(int l = 0; l < ingredients.size(); l++)
                            {
                                if(ingredients.get(l).getName().equals(ingredientName))
                                {
                                    ingredientsToGiveToAPI[l] = ingredients.get(l).getName();
                                    break;
                                }
                            }
                        }
                        else
                        {
                            //what it does when unchecked
                            String ingredientName = (String) buttonView.getText();
                            for (int l = 0; l < ingredients.size(); l++)
                            {
                                if (ingredients.get(l).getName().equals(ingredientName))
                                {
                                    ingredientsToGiveToAPI[l] = null;
                                    break;
                                }
                            }
                        }
                    }
                });

                IngredientsTable.addView(edgeBox);
            }
        }
    }

    public void goToRecipe(View v)
    {
        int nullCount = 0;

        for(int k = 0; k < ingredientsToGiveToAPI.length; k++)
        {
            if(ingredientsToGiveToAPI[k] == null)
            {
                nullCount++;
            }
        }

        String[] ingredientsToGiveToApiCondensed = new String[ingredientsToGiveToAPI.length - nullCount];

        for(int k = 0, z = 0; k < ingredientsToGiveToAPI.length; k++ )
        {
            if(ingredientsToGiveToAPI[k] == null)
            {
                //DO NOTHING!
            }
            else
            {
                ingredientsToGiveToApiCondensed[z] = ingredientsToGiveToAPI[k];
                z++;
            }
        }

        Bundle preferences = new Bundle();
        preferences.putStringArray( "preference" , ingredientsToGiveToApiCondensed );
        Intent prefData = new Intent(this, RecipePage.class);
        prefData.putExtras(preferences);
        startActivity(prefData);
    }

    public String[] getIngredients()
    {
        return ingredientsToGiveToAPI;
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
                startActivity(new Intent(Get_Recipes.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(Get_Recipes.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(Get_Recipes.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(Get_Recipes.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(Get_Recipes.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
