package com.example.iainchf.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddIngredient extends AppCompatActivity {
    ArrayList<Ingredient> items;
    ArrayList<Ingredient> listItems;
    ArrayAdapter<Ingredient> adapter;
    ListView listView;
    EditText editText;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        setTitle("Add Ingredient");

        this.context = getApplicationContext();

        listView = (ListView)findViewById(R.id.listview);
        editText = (EditText)findViewById(R.id.txtsearch);
        initList();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    initList();
                } else {
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                SQLiteAPISingletonHandler.getInstance(context).addIngredient(listItems.get(position));
                Intent in = new Intent(AddIngredient.this, Refrigerator.class);
                startActivity(in);
            }
        });
    }

    public void searchItem(String textToSearch) {
        listItems.clear();
        for(Ingredient item:items) {
            if(item.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                listItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList() {
        items = SQLiteAPISingletonHandler.getInstance(context).getAllIngredients();
        listItems = SQLiteAPISingletonHandler.getInstance(context).getAllIngredients();
        adapter = new MyListAdapter();
        listView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Ingredient> {
        public MyListAdapter() {
            super(AddIngredient.this, R.layout.ingredient_search_list_item, listItems);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.ingredient_search_list_item, parent, false);
            }
            Ingredient ingredient = listItems.get(position);
            TextView textView = (TextView)itemView.findViewById(R.id.txtitem);
            textView.setText(ingredient.getName());
            return itemView;
        }
    }

}
