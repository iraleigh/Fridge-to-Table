package com.example.iainchf.helloworld;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class Get_Recipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__recipes);

        SQLiteAPISingletonHandler ingredientListSQL = SQLiteAPISingletonHandler.getInstance(this);

        List<Ingredient> ingredients = ingredientListSQL.getIngredients();

        List<Recipe> recipes = ingredientListSQL.getCookbook();

        TableLayout IngredientsTable = (TableLayout)findViewById(R.id.IngredientTable);

        int halfListSize = (ingredients.size()/2);
        double checkEndOfList = (double)ingredients.size()/2.0;

        for(int i = 0, j = halfListSize; i < halfListSize; i++, j++)
        {
            CheckBox box = new CheckBox(this);
            CheckBox box2 = new CheckBox(this);
            CheckBox edgeBox = new CheckBox(this);
            TableRow newRow = new TableRow(this);

            newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                                             TableRow.LayoutParams.WRAP_CONTENT));

            box.setText(ingredients.get(i).getName());
            box2.setText(ingredients.get(j).getName());

            newRow.addView(box);
            newRow.addView(box2);

            IngredientsTable.addView(newRow);

            if(i == 7 && halfListSize - checkEndOfList != 0)
            {
                edgeBox.setText(ingredients.get(ingredients.size() - 1).getName());
                IngredientsTable.addView(edgeBox);
            }
        }

//        box.setChecked(true);
//        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //What the check box does when it's unchecked or checked.
//            }
//        });

        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.FILTER , android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void goToRecipe(View v)
    {
        startActivity(new Intent(Get_Recipes.this, RecipePage.class));
    }

}
