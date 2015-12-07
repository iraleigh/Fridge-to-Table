package com.example.iainchf.helloworld;

import java.util.*;

/**
 * Created by alvinlu on 11/9/15.
 */
public class Places {
    private String name;
    private String address;
    private double lat;
    private double lng;

    public Places(){
    }
    public Places(String n, String a, double la, double ln){
        name = n;
        address = a;
        lat = la;
        lng = ln;
    }

    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }
    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }

}
