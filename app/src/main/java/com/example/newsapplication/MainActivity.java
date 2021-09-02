package com.example.newsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapplication.newsmodel.NewsBean;
import com.example.newsapplication.newsmodel.NewsSource;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<NewsBean> newsbeanlist = new ArrayList<>();
    private XRecyclerView mainrecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbarmain = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbarmain);

        mainrecyclerview = findViewById(R.id.newsitem_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainrecyclerview.setLayoutManager(linearLayoutManager);
        mainrecyclerview.setAdapter(new NewsItemAdapter(MainActivity.this, newsbeanlist));

        mainrecyclerview.setPullRefreshEnabled(true);
        mainrecyclerview.setLoadingMoreEnabled(true);
        mainrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mainrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mainrecyclerview.getDefaultFootView().setLoadingHint("Loading more data");
        mainrecyclerview.getDefaultFootView().setNoMoreHint("Finish loading");

        SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-08-20&endDate=2021-08-30&words=拜登&categories=科技");
        searchThread.start();

        mainrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-08-20&endDate=2021-08-30&words=清华&categories=娱乐");
                searchThread.start();
                mainrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                // load more data here
                SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=30&startDate=2021-08-20&endDate=2021-08-30&words=拜登&categories=娱乐");
                searchThread.start();
                mainrecyclerview.setLimitNumberToCallLoadMore(2);
                mainrecyclerview.loadMoreComplete();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmain, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbarmain_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            DrawerLayout mDrawerLayout = findViewById(R.id.maindrawerlayout);
            mDrawerLayout.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SearchThread extends Thread{
        private String url;

        public SearchThread(String urlstring){
            this.url = urlstring;
        }

        @Override
        public void run() {
            synchronized (MainActivity.class) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String json = null;
                try {
                    json = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Log.e("TAG", json);
                Gson gson = new Gson();
                NewsSource newsSource = gson.fromJson(json, NewsSource.class);
                List<NewsBean> data = newsSource.getData();
                newsbeanlist.clear();
                newsbeanlist.addAll(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainrecyclerview.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mainrecyclerview != null){
            mainrecyclerview.destroy(); // this will totally release XR's memory
            mainrecyclerview = null;
        }
    }
}




