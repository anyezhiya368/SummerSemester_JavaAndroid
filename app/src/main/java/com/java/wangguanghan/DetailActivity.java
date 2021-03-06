package com.java.wangguanghan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.java.wangguanghan.newsmodel.NewsBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DetailActivity extends AppCompatActivity{

    private NewsBean newsBean;
    private FloatingActionButton fab_uncollected;
    private FloatingActionButton fab_collected;
    SQLiteDatabase db;
    boolean collected;

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
        LinearLayout imagelayout1 = findViewById(R.id.detail_image_layout1);
        LinearLayout imagelayout2 = findViewById(R.id.detail_image_layout2);
        VideoView videoView = findViewById(R.id.detail_video);
        ImageView imageView = findViewById(R.id.detail_image);
        ImageView imageView1 = findViewById(R.id.detail_image1);
        ImageView imageView2 = findViewById(R.id.detail_image2);
        TextView titletv = findViewById(R.id.detail_title);
        TextView sourcetv = findViewById(R.id.detail_soruce);
        TextView contenttv = findViewById(R.id.detail_content);

        if(video.length() != 0){
            videolayout.setVisibility(View.VISIBLE);
            imagelayout.setVisibility(View.GONE);
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            videoView.requestFocus();
            videoView.setVideoPath(video);
            videoView.start();
        }else{
            videolayout.setVisibility(View.GONE);
            if(stringList.size() == 0){
                imagelayout.setVisibility(View.GONE);
                imagelayout1.setVisibility(View.GONE);
                imagelayout2.setVisibility(View.GONE);
            }else{
                int count = stringList.size();
                if(count == 1){
                    imagelayout1.setVisibility(View.GONE);
                    imagelayout2.setVisibility(View.GONE);
                    Glide.with(DetailActivity.this).load(stringList.get(0)).into(imageView);
                }else if(count == 2){
                    imagelayout2.setVisibility(View.GONE);
                    Glide.with(DetailActivity.this).load(stringList.get(0)).into(imageView);
                    Glide.with(DetailActivity.this).load(stringList.get(1)).into(imageView1);
                }else {
                    Glide.with(DetailActivity.this).load(stringList.get(0)).into(imageView);
                    Glide.with(DetailActivity.this).load(stringList.get(1)).into(imageView1);
                    Glide.with(DetailActivity.this).load(stringList.get(2)).into(imageView2);
                }
            }
        }

        titletv.setText(title);
        sourcetv.setText(source);
        contenttv.setText(content);

        fab_collected = findViewById(R.id.fab_collected);
        fab_uncollected = findViewById(R.id.fab_uncollected);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(DetailActivity.this, "Local.db", null, 1);


        //Search the collection.db database to find whether the article has been collected
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Collection", null, null, null, null,
                null, null);
        if(cursor.moveToFirst()){
            do{
                String titledb = cursor.getString(1);
                Log.e("TAG", titledb);
                if(titledb.equals(title)) {
                    collected = true;
                    break;
                }
            }while(cursor.moveToNext());
        }cursor.close();

        if(collected){
            fab_collected.setVisibility(View.VISIBLE);
            fab_uncollected.setVisibility(View.INVISIBLE);
        }else{
            fab_uncollected.setVisibility(View.VISIBLE);
            fab_collected.setVisibility(View.INVISIBLE);
        }

        fab_uncollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_uncollected.setVisibility(View.INVISIBLE);
                fab_collected.setVisibility(View.VISIBLE);
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("url", url);
                values.put("image", imagestring);
                values.put("video", video);
                values.put("publisher", publisher);
                values.put("time", time);
                values.put("content", content);
                db.insert("Collection", null, values);
            }
        });
        fab_collected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_collected.setVisibility(View.INVISIBLE);
                fab_uncollected.setVisibility(View.VISIBLE);
                db = dbHelper.getWritableDatabase();
                db.delete("Collection", "title = ?", new String[]{title});
                Intent intent2 = new Intent();
                setResult(1, intent2);
                intent2.putExtra("title", title);
            }
        });
    }
}