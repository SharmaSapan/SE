package com.example.a4p02app;

public class Post {

    private String name;
    private String content;
    private String date;

    public String getName(){
        return name;
    }

    public void setName(String pName){
        this.name = pName;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String pContent){
        this.content=pContent;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String pDate){
        this.date=pDate;
    }
}
