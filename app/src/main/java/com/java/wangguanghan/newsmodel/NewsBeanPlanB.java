package com.java.wangguanghan.newsmodel;

import java.util.ArrayList;
import java.util.List;

public class NewsBeanPlanB {

    private String publishTime;
    private String video;
    private String title;
    private String url;
    private String publisher;
    private String category;
    private String content;
    private List<String> image = new ArrayList<>();

    public NewsBeanPlanB(){}

    public NewsBeanPlanB(List<String> image, String publishTime, String video, String title, String url, String publisher
            , String content)
    {
        this.image = image;
        this.publishTime = publishTime;
        this.video = video;
        this.title = title;
        this.url = url;
        this.publisher = publisher;
        this.content =  content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisherstring) {
        this.publisher = publisherstring;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titlestring) {
        this.title = titlestring;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTimestring) {
        this.publishTime = publishTimestring;
    }

    public List<String> getImage() {
        return image;
    }

}

