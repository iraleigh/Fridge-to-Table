package com.example.iainchf.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Refrigerator extends AppCompatActivity {

    //TODO: Are ingredients just a list of Strings? Do we need Ingredient object? If so, what other fields will ingredient contain?
    private List<String> myIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerator);
        setTitle("My Refrigerator");

        //TODO: This needs to be populated from the database, rather than hardcoded
        myIngredients.add("Carrot");
        myIngredients.add("Milk");
        myIngredients.add("Rice");
        myIngredients.add("Peanut Butter");
        myIngredients.add("Canned Tuna");
        myIngredients.add("Bread");
        myIngredients.add("Eggs");
        myIngredients.add("Bottled water");

        populateRefrigeratorTable();
    }

    private void populateRefrigeratorTable() {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(Refrigerator.this, R.layout.row_refrigerator, myIngredients);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.row_refrigerator, parent, false);
            }
            String ingredient = myIngredients.get(position);
            TextView textView = (TextView)itemView.findViewById(R.id.ingredientName);
            textView.setText(ingredient);
            ImageButton delete = (ImageButton)itemView.findViewById(R.id.deleteButton);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myIngredients.remove(position);
                    populateRefrigeratorTable();
                }
            });
            return itemView;
        }
    }
}