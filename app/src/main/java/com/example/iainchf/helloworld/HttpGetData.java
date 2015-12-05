package com.example.iainchf.helloworld;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import javax.net.ssl.TrustManagerFactory;


/**
 * @author Iain Raleigh <iain.raleigh.dev@gmail.com>
 * The purpose of this Class is to get data from and HTTP request
 * to use: create a new HttpGetData object
 */
public class HttpGetData {
    private static final String DEBUG_TAG = "HttpExample";
    String url;
    String data;

    /**
     * Constructor for customized URL request.
     * <p>
     * This is where the Object will begin its request
     * on another thread to query the URL given.
     *
     * @param url takes in a given URL to get data from
     */
    public HttpGetData(String url){
        this.url = url;
        this.data = null;
        new DownloadWebpageTask().execute(this.url);
    }

    /**
     * Gets data that was requested for using the URL
     * <p>
     * If the the thread the request is working on is not done,
     * null will be returned.
     * @return data from the URL used in the Constructor
     */

    public String getData(){
        return this.data;
    }


    /**
     * Downloads data from a specified URL, needs to used on a separate thread.
     * @param myurl URL of the data being requested
     * @return data in the form of a string (format is determined by source e.g. JSON)
     * @throws IOException
     */
    private String downloadUrl(String myurl) throws IOException {
        InputStream is;
            URL url = new URL(myurl);
            String https = myurl.substring(0,5);
            if(https.equals("https")){
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
    }

    /**
     * @author Iain Raleigh <iain.raleigh.dev@gmail.com>
     * Extending AsyncTask with custom task.
     * Uses the downloadUrl() method to invoke request for data.
     * This allows the use of downloadUrl() to be handled on a seperate thread.
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        /**
         * Overriding the AsyncTask doInBackground() to use downloadUrl() from HttpGetData class.
         * @param urls URLs to be used to get data.
         * @return data from the respective urls being passed to it.
         */
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

    /**
     * Used to convert an InputStream object to an String of specified length.
     * @param stream
     * @param len Specified length of the string to return.
     * @return String that represents the passed InputStream.
     */
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
