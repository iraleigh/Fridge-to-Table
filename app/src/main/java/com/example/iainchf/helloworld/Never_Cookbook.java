package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Never_Cookbook extends AppCompatActivity {
    ArrayList<Recipe> rejectedRecipes= new ArrayList<>();
    int numRejectedRecipes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_never__cookbook);
        ArrayAdapter<Recipe> adapt = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_1, rejectedRecipes);
        ListView list = (ListView) findViewById(R.id.listView3);
        list.setAdapter(adapt);
        //SQLiteAPISingletonHandler insta = SQLiteAPISingletonHandler.getInstance(this);
        //savedRecipes.addAll(insta.getCookbook());
        Recipe sample = new Recipe("Casorole", "Vegetable dish", "Put in oven", "vURL", false, false, false, 0, new ArrayList<String>(),"","api", "apiId");

        for (int i = 0; i < numRejectedRecipes; i++) {
            rejectedRecipes.add(i, sample);
        }
    }

    public void goToHome(View v){
        //Log.d("In startA","Starting Recipe Activity" );
        Intent in = new Intent(this,Home.class);
        startActivity(in);
    }

    public void goToGetRecipes(View v){
        Intent in = new Intent(this,Get_Recipes.class);
        startActivity(in);
    }
}
