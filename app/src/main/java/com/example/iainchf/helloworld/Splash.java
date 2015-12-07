package com.example.iainchf.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        checkEula();
    }

    public void checkEula() {
        boolean eulaAccepted = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("eulaAccepted", false);
        if(!eulaAccepted) {
            //create Eula object and render it
            Eula eula = new Eula(this, Splash.this);
            eula.render();
        } else {
            //wait 3 seconds on splash screen, then transition to MainActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, Refrigerator.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}
