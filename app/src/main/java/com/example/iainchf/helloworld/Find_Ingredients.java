package com.example.iainchf.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Find_Ingredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__ingredients);
    }
    public void goToGoogleMaps(View v){
        Intent in = new Intent(this, Google_Store_Find.class);
        startActivity(in);
    }

    public void goToAmazonOrdering(View v){
        Intent in = new Intent(this, Amazon_Ordering.class);
        startActivity(in);
    }
}
