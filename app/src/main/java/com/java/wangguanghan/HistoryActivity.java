package com.java.wangguanghan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.java.wangguanghan.newsmodel.NewsBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(HistoryActivity.this, "Local.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("History", null, null, null, null, null, null);
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

        xRecyclerView = findViewById(R.id.history_xrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setAdapter(new NewsItemAdapter(HistoryActivity.this, newsBeanList));
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
}