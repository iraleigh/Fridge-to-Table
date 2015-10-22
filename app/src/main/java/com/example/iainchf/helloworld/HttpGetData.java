package com.example.iainchf.helloworld;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Created by iainchf on 9/25/15.
 *
 * The purpose of this Class is to get data from and HTTP request
 * to use: create a new HttpGetData object
 */
public class HttpGetData {
    private static final String DEBUG_TAG = "HttpExample";
    String url;
    InputStream data;

    public HttpGetData(){
        this.url = "";
        new DownloadWebpageTask().execute(url);
    }

    public HttpGetData(String url){
        this.url = url;
        this.data = null;
        new DownloadWebpageTask().execute(this.url);
    }

    public InputStream getData(){
        return data;
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 10000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            data = is;
            return "Data filled";

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {

        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) {
        try {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        } catch (IOException e){
            return "IOException caught in readIt";
        }
    }


}
