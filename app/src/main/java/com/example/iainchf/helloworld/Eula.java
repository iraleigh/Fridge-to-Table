package com.example.iainchf.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

public class Eula extends Activity {

    private String title = "";
    private String message = "";
    private Activity activity;
    private Context context;

    public Eula(Activity activity, Context context) {
        this.title = "EULA";
        this.message = "END USER LICENSE FOR ANDROID FRIDGE TO TABLE SOFTWARE\n" +
                "PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THIS SOFTWARE. THIS LICENSE PROVIDES IMPORTANT INFORMATION CONCERNING THE SOFTWARE, PROVIDES YOU WITH A LICENSE TO USE THE SOFTWARE AND CONTAINS WARRANTY AND LIABILITY INFORMATION.\n" +
                "BY USING THE SOFTWARE, YOU ARE ACCEPTING THE SOFTWARE \"AS IS\" AND AGREEING TO BE BOUND BY THE TERMS OF THIS LICENSE AGREEMENT.\n" +
                "IN NO EVENT WILL THE AUTHORS BE HELD LIABLE FOR ANY DAMAGES ARISING FROM THE USE OF THIS SOFTWARE.\n" +
                "IF YOU DO NOT WISH TO DO SO, DO NOT USE THE SOFTWARE.";
        this.activity = activity;
        this.context = context;
    }

    public Eula(String title, String message, Activity activity, Context context) {
        this.title = title;
        this.message = message;
        this.activity = activity;
        this.context = context;
    }

    public void render() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        context.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("eulaAccepted", true).apply();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, Refrigerator.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        }, 750);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                        System.exit(0);
                    }
                });
        builder.create().show();
    }

}
