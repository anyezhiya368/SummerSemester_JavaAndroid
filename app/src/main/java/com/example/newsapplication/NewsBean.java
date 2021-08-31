package com.example.newsapplication;

public class NewsBean  {

    private String title;
    private String source;
    private String imageurl;

    public  NewsBean(String titlestring, String source, String imageurl){
        this.title = titlestring;
        this.source = source;
        this.imageurl = imageurl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
