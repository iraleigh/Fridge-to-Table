package com.example.iainchf.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeDetail extends AppCompatActivity {

    private ImageView iv;
    private Bitmap bitmap;
    private TextView recipeName;
    private ListView lv;
    private ImageButton cookbookButton;
    private ImageView recipeView;

    private Context context;

    private String name;
    private String description;
    private String instructions;
    private String videoURL;
    private Boolean dietFood;
    private Boolean hasCaffeine;
    private Boolean glutenFree;
    private int calories;
    private String nameOfAPI;
    private ArrayList<String> ingredientList;
    private String idFromAPI;
    private String imageUrl;

    private Boolean addToCookbook;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        this.context = getApplicationContext();

        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        description = b.getString("description");
        instructions = b.getString("instructions");
        videoURL = b.getString("videoURL");
        dietFood = b.getBoolean("dietFood");
        hasCaffeine = b.getBoolean("hasCaffeine");
        glutenFree = b.getBoolean("glutenFree");
        calories = b.getInt("calories");
        nameOfAPI = b.getString("nameOfAPI");
        ingredientList = b.getStringArrayList("ingredients");
        idFromAPI = b.getString("idFromAPI");
        imageUrl = b.getString("imageUrl");

        recipeName = (TextView) findViewById(R.id.recipeName);
        recipeName.setText(name);
        recipeView = (ImageView) findViewById(R.id.recipeImageView);
        cookbookButton = (ImageButton) findViewById(R.id.addToCookbook);

        if(isRecipeInCookbook(idFromAPI)) {
            this.addToCookbook = false;
            this.cookbookButton.setBackground(getResources().getDrawable(R.drawable.cookbook_button_active));
        } else {
            this.addToCookbook = true;
            this.cookbookButton.setBackground(getResources().getDrawable(R.drawable.cookbook_button_inactive));
        }

        iv = (ImageView) findViewById(R.id.bcgImage);
        try {
            bitmap = new GetBitmapFromURL().execute(imageUrl).get();
            iv.setImageBitmap(bitmap);
            recipeView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        lv = (ListView) findViewById(R.id.listIngredientsView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientList);
        lv.setAdapter(arrayAdapter);
        setHeightOfListView(lv, recipeView, bitmap);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean isRecipeInCookbook(String idFromAPI) {
        List<Recipe> cookbook = SQLiteAPISingletonHandler.getInstance(context).getCookbook();
        for(int i=0; i<cookbook.size(); i++) {
            if(cookbook.get(i).getIdFromAPI().equals(idFromAPI)) {
                return true;
            }
        }
        return false;
    }

    public void goToInstructions (View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.instructions));
        startActivity(browserIntent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void cookbookButtonTapped(View v) {
        Recipe recipe = new Recipe(name, description, instructions, videoURL, dietFood, hasCaffeine, glutenFree, calories, ingredientList, imageUrl, nameOfAPI, idFromAPI);
        if(this.addToCookbook) {
            // add to cookbook
            SQLiteAPISingletonHandler.getInstance(context).addRecipeToCookbook(recipe);
            this.cookbookButton.setBackground(getResources().getDrawable(R.drawable.cookbook_button_active));
            Toast toast = Toast.makeText(context, "Recipe added to cookbook", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
            this.addToCookbook = false;
        } else {
            // remove from cookbook
            SQLiteAPISingletonHandler.getInstance(context).removeRecipeFromCookbook(recipe);
            this.cookbookButton.setBackground(getResources().getDrawable(R.drawable.cookbook_button_inactive));
            Toast toast = Toast.makeText(context, "Recipe removed from cookbook", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
            this.addToCookbook = true;
        }
    }

    public static void setHeightOfListView(ListView listView, ImageView recipeView, Bitmap bitmap) {

        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = bitmap.getHeight() + 450;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

        recipeView.setY(totalHeight - bitmap.getHeight() - 200);

    }

}
