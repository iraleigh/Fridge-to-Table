package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Get_Recipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__recipes);

        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.FILTER , android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void goToRecipe(View v)
    {
        startActivity(new Intent(Get_Recipes.this, Home.class));
    }

}
