package com.example.iainchf.helloworld;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecipeDetail extends AppCompatActivity {

    private ImageView iv;
    private Bitmap bitmap;
    private TextView recipeName;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        String description = b.getString("description");
        String instructions = b.getString("instructions");
        String videoURL = b.getString("videoURL");
        Boolean dietFood = b.getBoolean("dietFood");
        Boolean hasCaffeine = b.getBoolean("hasCaffeine");
        Boolean glutenFree = b.getBoolean("glutenFree");
        int calories = b.getInt("calories");
        String nameOfAPI = b.getString("nameOfAPI");
        ArrayList<String> ingredientList = b.getStringArrayList("ingredients");
        String idFromAPI = b.getString("idFromAPI");
        String imageUrl = b.getString("imageUrl");

        recipeName = (TextView) findViewById(R.id.recipeName);
        recipeName.setText(name);

        lv = (ListView) findViewById(R.id.listIngredientsView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientList);
        lv.setAdapter(arrayAdapter);

        iv = (ImageView) findViewById(R.id.bcgImage);
        try {
            bitmap = new GetBitmapFromURL().execute(imageUrl).get();
            iv.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
