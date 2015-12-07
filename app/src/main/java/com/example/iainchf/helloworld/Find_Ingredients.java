package com.example.iainchf.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Locale;

public class Find_Ingredients extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__ingredients);

        //Set up configuration to connect to good places API
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }
    public void goToGoogleMaps(View v){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            Intent intent = builder.build(this);

            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        }catch (Exception e){

        }
    }

    public void goToAmazonOrdering(View v){
        WebView amazonFresh = new WebView(this);
        amazonFresh.loadUrl("https://fresh.amazon.com/");
        setContentView(amazonFresh);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.get_recipes:
                startActivity(new Intent(Find_Ingredients.this, RecipePage.class));
                break;
            case R.id.filter_recipes:
                startActivity(new Intent(Find_Ingredients.this, Get_Recipes.class));
                break;
            case R.id.fridge:
                startActivity(new Intent(Find_Ingredients.this, Refrigerator.class));
                break;
            case R.id.find_ingredients:
                startActivity(new Intent(Find_Ingredients.this, Find_Ingredients.class));
                break;
            case R.id.cookbook:
                startActivity(new Intent(Find_Ingredients.this, Cookbook.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_find__ingredients);
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                //Open Google Maps
                Place place = PlacePicker.getPlace(data, this);
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", Uri.encode(place.getAddress().toString()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        }
    }
}
