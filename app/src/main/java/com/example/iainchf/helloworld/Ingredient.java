package com.example.iainchf.helloworld;

/**
 * Created by Nwilley91 on 9/30/2015.
 */
public class Ingredient {
    private String name;
    private String type;
    private String pictureURL;
    private boolean liquid;
    private long id = 0;

    //Sets all strings to null and boolean to false.
    public Ingredient(){
    }

    public Ingredient(long id,String name, String type, String url, boolean liquid){
        this.id = id;
        this.name = name;
        this.type = type;
        this.pictureURL = url;
        this.liquid = liquid;
    }
    public long getId() { return  id;}
    public void setId(long id){this.id = id;}

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String getPictureURL(){
        return pictureURL;
    }

    public boolean isLquid(){
        return liquid;
    }

    public String toString() {
        return id + " " + name + " " + type + " " + liquid + "\n";
    }
}
