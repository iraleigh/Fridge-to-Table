package com.example.iainchf.helloworld;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class RecipeDetail extends AppCompatActivity {

    private ImageView iv;
    private Bitmap bitmap;

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
        String ingredientList = b.getString("ingredientList");
        String idFromAPI = b.getString("idFromAPI");
        String imageUrl = b.getString("imageUrl");

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText(imageUrl);

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
