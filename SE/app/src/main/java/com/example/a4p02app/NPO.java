package com.example.a4p02app;

public class NPO {
    private String uid;
    private String name;
    private boolean isFav;

    public String getName(){
        return name;
    }

    public void setName(String pName){
        this.name = pName;
    }

    public String getUID(){
        return uid;
    }

    public void setUID(String uid){
        this.uid = uid;
    }

    public boolean isFavourite(){
        return isFav;
    }

    public void setFavourite(boolean isFav){
        this.isFav = isFav;
    }

}
