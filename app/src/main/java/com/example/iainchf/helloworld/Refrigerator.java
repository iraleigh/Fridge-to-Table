package com.example.iainchf.helloworld;

import android.content.Context;
import android.content.Intent;
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
        TextView textview = (TextView) findViewById(R.id.fridgeIsEmptyLabel);
        if(ingredientsList.size() == 0) {
            textview.setVisibility(View.VISIBLE);
        } else {
            textview.setVisibility(View.INVISIBLE);
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

    public void goToHome(View v){
        Intent in = new Intent(this,Home.class);
        startActivity(in);
    }

}