package com.example.iainchf.helloworld;

/**
 * Created by alvinlu on 11/10/15.
 */
import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class gPlacesAPI {

    private List<Places> placesList;
    private String url;
    private String apiKey;

    public gPlacesAPI(double lng, double lat){
        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + ", "+ lng + "&radius=500&types=grocery_or_supermarket&key=AIzaSyB8ESdZiFJ0vjiYi1T6YE4bTm16Vsov610";

        HttpGetData gPlacesGetter = new HttpGetData(url);
        while(gPlacesGetter.getData() == null);

        String json = gPlacesGetter.getData();
        placesList = parseJSON(json);
    }

    public List<Places> getList(){return placesList;}

    private List<Places> parseJSON(String json){
        gPlacesJsonReader placesreader = new gPlacesJsonReader();
        try {
            return placesreader.readJsonStream(json);
        } catch (IOException e){
            return new ArrayList<>();
        }
    }

    private class gPlacesJsonReader {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public List<Places> readJsonStream(String in) throws IOException{
            com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(new StringReader(in));
            try{
                return getPlaces(jsonReader);
            }finally{
                try {
                    jsonReader.close();
                } catch(Exception e){}
            }
        }

        /*
        @SuppressWarnings("unchecked")
        public List<Places> readMessagesArray(com.google.gson.stream.JsonReader reader) throws IOException {
            List<Places> places = new ArrayList<>();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("recipe")){
                    try {
                        places.add(getPlaces(reader));
                    } catch (Exception e){
                        return places;
                    }
                }
            }
            reader.endObject();
            return places;
        }
        */
        private List<Places> getPlaces(com.google.gson.stream.JsonReader reader) throws IOException{
            List<Places> places = new ArrayList<>();
            String placesName = "";
            String address = "";
            double lat = 0;
            double lng = 0;
            String name;

            reader.beginObject();
            while (reader.hasNext()){
                name = reader.nextName();
                if(name.equals("html_attributions")){
                    reader.skipValue();
                } else if(name.equals("next_page_token")){
                    reader.skipValue();
                } else if(name.equals("results")){
                    reader.beginArray(); //begin result object

                    // going inside geo
                    while(reader.hasNext()){
                        reader.beginObject(); //begin result array
                        while (reader.hasNext()) {
                            name = reader.nextName();
                            if (name.equals("geometry")) {

                                reader.beginObject();
                                //going inside location
                                while (reader.hasNext()) {
                                    name = reader.nextName();
                                    if (name.equals("location")) {

                                        reader.beginObject();
                                        //going inside location
                                        while (reader.hasNext()) {
                                            name = reader.nextName();
                                            if (name.equals("lat")) {
                                                lat = reader.nextDouble();
                                            } else if (name.equals("lng")) {
                                                lng = reader.nextDouble();
                                            }
                                        }
                                        reader.endObject();

                                    }
                                }
                                reader.endObject();
                            } else if (name.equals("icon")) {
                                reader.skipValue();
                            } else if (name.equals("id")) {
                                reader.skipValue();
                            } else if (name.equals("name")) {
                                placesName = reader.nextString();
                            } else if (name.equals("photos")) {
                                reader.skipValue();
                            } else if (name.equals("place_id")) {
                                reader.skipValue();
                            } else if (name.equals("rating")) {
                                reader.skipValue();
                            } else if (name.equals("reference")) {
                                reader.skipValue();
                            } else if (name.equals("scope")) {
                                reader.skipValue();
                            } else if (name.equals("types")) {
                                reader.skipValue();
                            } else if (name.equals("vicinity")) {
                                address = reader.nextString();
                            }

                        }
                        places.add(new Places(placesName, address, lat, lng));
                        reader.endObject(); //ending result object

                    }
                    reader.endArray(); // ending result array

                } else if(name.equals("status")) {
                    reader.skipValue();
                }
            }

            reader.endObject();
            return places;
        }

    }
}
