package com.java.wangguanghan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.java.wangguanghan.newsmodel.NewsBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    Toolbar toolbar;
    XRecyclerView xRecyclerView;
    List<NewsBean> newsBeanList = new ArrayList<>();
    String title;
    String url;
    String image;
    String video;
    String publisher;
    String time;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        toolbar = findViewById(R.id.collection_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(CollectionActivity.this, "Local.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Collection", null, null, null, null, null, null);
        if(cursor.moveToLast()){
            do{
                // get the data, generate the newsbeans and add the newsbeans into the adapter
                title = cursor.getString(1);
                url = cursor.getString(2);
                image = cursor.getString(3);
                video = cursor.getString(4);
                publisher = cursor.getString(5);
                time = cursor.getString(6);
                content = cursor.getString(7);
                newsBeanList.add(new NewsBean(image, time, video, title, url, publisher, content));
            }while(cursor.moveToPrevious());
        } cursor.close();


        xRecyclerView = findViewById(R.id.collection_xrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollectionActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setAdapter(new NewsItemAdapter(CollectionActivity.this, newsBeanList));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                String titleintent = data.getStringExtra("title");
                for (NewsBean newsbean:
                     newsBeanList) {
                    if(newsbean.getTitle().equals(titleintent)){
                        newsBeanList.remove(newsbean);
                        break;
                    }
                }
            }xRecyclerView = findViewById(R.id.collection_xrecyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollectionActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xRecyclerView.setLayoutManager(linearLayoutManager);
            xRecyclerView.setAdapter(new NewsItemAdapter(CollectionActivity.this, newsBeanList));
            xRecyclerView.setPullRefreshEnabled(false);
            xRecyclerView.setLoadingMoreEnabled(false);
        }
    }
}