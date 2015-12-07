package com.example.iainchf.helloworld;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iainchf on 11/30/15.
 */
public class RecipeList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] recipeNames;
    private final String[] recipeImageURL;
    private ArrayList<ImageView> imagesToLoad;

    public RecipeList(Activity context, String[] recipeNames, String[] recipeImageURL){
        super(context, R.layout.recipe_list, recipeNames);
        this.context = context;
        this.recipeNames = recipeNames;
        this.recipeImageURL = recipeImageURL;
        imagesToLoad = new ArrayList<>();
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.recipe_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.recipe_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.recipe_image);

        txtTitle.setText(recipeNames[position]);

        //imageView.setImageResource(R.drawable.default_food_icon);

        imagesToLoad.add(imageView);

       return rowView;
    }

    public ArrayList<ImageView> getImageViews(){
        return this.imagesToLoad;
    }
}
