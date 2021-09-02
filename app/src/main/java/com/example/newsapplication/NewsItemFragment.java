package com.example.newsapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsapplication.newsmodel.NewsBean;
import com.example.newsapplication.newsmodel.NewsSource;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsItemFragment extends Fragment {

    private String title;
    private List<NewsBean> newsbeanlist = new ArrayList<>();
    private XRecyclerView mainrecyclerview;

    public NewsItemFragment(String newstype) {
        this.title = newstype;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsitem, container, false);
        mainrecyclerview = view.findViewById(R.id.newsitem_recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainrecyclerview.setLayoutManager(linearLayoutManager);
        mainrecyclerview.setAdapter(new NewsItemAdapter(getActivity(), newsbeanlist));

        mainrecyclerview.setPullRefreshEnabled(true);
        mainrecyclerview.setLoadingMoreEnabled(true);
        mainrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mainrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mainrecyclerview.getDefaultFootView().setLoadingHint("Loading more data");
        mainrecyclerview.getDefaultFootView().setNoMoreHint("Finish loading");

        SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2020-08-20&endDate=2021-08-30&words=&categories="+title);
        searchThread.start();

        mainrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2020-08-20&endDate=2021-08-30&words=&categories="+title);
                searchThread.start();
                mainrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                // load more data here
                SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=30&startDate=2020-08-20&endDate=2021-08-30&words=&categories="+title);
                searchThread.start();
                mainrecyclerview.setLimitNumberToCallLoadMore(2);
                mainrecyclerview.loadMoreComplete();
            }
        });

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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainrecyclerview.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mainrecyclerview != null){
            mainrecyclerview.destroy(); // this will totally release XR's memory
            mainrecyclerview = null;
        }
    }
}
