package com.example.iainchf.helloworld;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by iainchf on 11/30/15.
 */
public class RecipeList extends ArrayAdapter<String> {
    public Activity context;
    public String[] recipeNames;
    public String[] recipeImageURL;

    public RecipeList(Activity context, String[] recipeNames, String[] recipeImageURL){
        super(context, R.layout.recipe_list, recipeNames);
        this.context = context;
        this.recipeNames = recipeNames;
        this.recipeImageURL = recipeImageURL;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.recipe_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.recipe_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.recipe_image);

        txtTitle.setText(recipeNames[position]);

        Bitmap bitmap = null;
        try {
            bitmap = new GetBitmapFromURL().execute(recipeImageURL[position]).get();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return rowView;
    }

}
