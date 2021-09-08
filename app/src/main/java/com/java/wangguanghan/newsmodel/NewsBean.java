package com.java.wangguanghan.newsmodel;

import java.util.ArrayList;
import java.util.List;

public class NewsBean {

    private String image = "";
    private String publishTime;
    private String video;
    private String title;
    private String url;
    private String publisher;
    private String category;
    private String content;
    private List<String> imagelist = new ArrayList<>();

    public NewsBean(){}

    public NewsBean(String image, String publishTime, String video, String title, String url, String publisher
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

    public NewsBean(NewsBeanPlanB newsBeanPlanB){
        this.imagelist = newsBeanPlanB.getImage();
        this.publishTime = newsBeanPlanB.getPublishTime();
        this.video = newsBeanPlanB.getVideo();
        this.title = newsBeanPlanB.getTitle();
        this.url = newsBeanPlanB.getUrl();
        this.publisher = newsBeanPlanB.getPublisher();
        this.content = newsBeanPlanB.getContent();
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if(imagelist.size() != 0){
            for (String imagestring:
                 this.imagelist) {
                buffer.append(imagestring);
                buffer.append(',');
                buffer.append(' ');
            }buffer.deleteCharAt(buffer.length() - 1);
            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append(']');
            this.image = buffer.toString();
        }else{
            buffer.append(']');
            this.image = buffer.toString();
        }
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

    public String getImageString(){return image;}

    public List<String> getImage() {
        if(imagelist.size() != 0)
            return imagelist;
        //Log.e("TAG", image);
        List<String> resultlist = new ArrayList<>();
        resultlist.clear();
        if (image.equals("[]") || image.equals(""))
        {
            return resultlist;
        }
        else {
            StringBuffer result = new StringBuffer(1000);
            result.append(image);
            result.delete(0, 1);
            // 得到第一个逗号的位置
            while (true) {
                int i = 0;
                char cur = result.charAt(i);
                while ((cur != ',') && (cur != ']')) {
                    i++;
                    cur = result.charAt(i);
                }
                if (result.charAt(i) != ']') {
                    if(result.charAt(i - 1) != 'f') {
                        String temp = result.substring(0, i);
                        resultlist.add(temp);
                        result.delete(0, i + 2);
                    }else{
                        result.delete(0, i + 2);
                    }
                }else{
                    if(result.charAt(i - 1) != 'f'){
                        String temp = result.substring(0, i);
                        resultlist.add(temp);
                        return resultlist;
                    }else
                        return resultlist;
                }
            }
        }
    }

    public void setImage(String imagestring) {
        this.image = imagestring;
    }
}
