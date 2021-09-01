package com.example.newsapplication.newsmodel;

import android.util.Log;

import java.util.List;

public class NewsBean {

    private String image;
    private String publishTime;
    private String video;
    private String title;
    private String url;
    private List<Puborg> puborgList;
    private String publisher;
    private String category;



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

    public List<Puborg> getPuborgList() {
        return puborgList;
    }

    public void setPuborgList(List<Puborg> puborgList) {
        this.puborgList = puborgList;
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

    public String getImage() {
        if (image.equals("[]"))
            return "https://img0.baidu.com/it/u=236392046,3444623050&fm=26&fmt=auto&gp=0.jpg";
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
            if (result.charAt(i - 1) != 'f') {
                //Log.e("TAG", String.valueOf(result.substring(0, i)));
                return result.substring(0, i);
            } else {
                result.delete(0, i + 2);
            }
        }
    }

    public void setImage(String imagestring) {
        this.image = imagestring;
    }
}
