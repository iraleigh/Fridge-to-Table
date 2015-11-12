package com.example.iainchf.helloworld;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


/**
 * Created by iainchf on 9/25/15.
 *
 * The purpose of this Class is to get data from and HTTP request
 * to use: create a new HttpGetData object
 */
public class HttpGetData {
    private static final String DEBUG_TAG = "HttpExample";
    String url;
    String data;

    public HttpGetData(){
        this.url = "";
        new DownloadWebpageTask().execute(url);
    }

    public HttpGetData(String url){
        this.url = url;
        this.data = null;
        new DownloadWebpageTask().execute(this.url);
    }

    public String getData(){
        return this.data;
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 10000;

        try {
            URL url = new URL(myurl);
            String https = myurl.substring(0,5);
            if(https.equals("https")){

                try {
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    conn.setUseCaches(false);

                    // act like a browser
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    int responseCode = conn.getResponseCode();
                    is = new BufferedInputStream(conn.getInputStream());
                    // Convert the InputStream into a string
                    data = readIt(is,12000);
                    is.close();
                }catch (Exception e){
                    e.getMessage();
                }
            } else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = new BufferedInputStream(conn.getInputStream());
                // Convert the InputStream into a string
                data = readIt(is,12000);
                is.close();
            }
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
