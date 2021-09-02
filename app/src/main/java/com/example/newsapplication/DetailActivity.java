package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.newsmodel.NewsBean;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private NewsBean newsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        newsBean = new NewsBean();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String video = intent.getStringExtra("video");
        String imagestring = intent.getStringExtra("image");
        String title = intent.getStringExtra("title");
        String publisher = intent.getStringExtra("publisher");
        String time = intent.getStringExtra("time");
        String source = publisher + '\t' + time;
        String content = intent.getStringExtra("content");

        newsBean.setImage(imagestring);
        List<String> stringList = newsBean.getImage();

        LinearLayout videolayout = findViewById(R.id.detail_video_layout);
        LinearLayout imagelayout = findViewById(R.id.detail_image_layout);
        VideoView videoView = findViewById(R.id.detail_video);
        ImageView imageView = findViewById(R.id.detail_image);
        TextView titletv = findViewById(R.id.detail_title);
        TextView sourcetv = findViewById(R.id.detail_soruce);
        TextView contenttv = findViewById(R.id.detail_content);

        if(video.length() != 0){
            videolayout.setVisibility(View.VISIBLE);
            imagelayout.setVisibility(View.GONE);
        }else{
            videolayout.setVisibility(View.GONE);
            if(stringList.size() == 0){
                imagelayout.setVisibility(View.GONE);
            }else{
                Glide.with(DetailActivity.this).load(stringList.get(0)).into(imageView);
            }
        }

        titletv.setText(title);
        sourcetv.setText(source);
        contenttv.setText(content);
    }
}