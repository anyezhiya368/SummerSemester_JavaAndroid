package com.java.wangguanghan;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.java.wangguanghan.newsmodel.NewsBean;
import com.java.wangguanghan.newsmodel.NewsBeanPlanB;
import com.java.wangguanghan.newsmodel.NewsSource;
import com.google.gson.Gson;
import com.java.wangguanghan.newsmodel.NewsSourcePlanB;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsItemFragment extends Fragment {

    private String title = "";
    private List<NewsBean> newsbeanlist = new ArrayList<>();
    private XRecyclerView mainrecyclerview;
    private String keyword = "";
    private String starttime = "1949-10-10";
    private String endtime="2021-09-02";
    int cursize = 30;

    public NewsItemFragment(String keyword, String category, String starttime, String endtime){
        this.keyword = keyword;
        this.title = category;
        this.starttime = starttime;
        this.endtime = endtime;
    }


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
        mainrecyclerview.getDefaultFootView().setLoadingHint("????????????");
        mainrecyclerview.getDefaultFootView().setNoMoreHint("???????????????");

        SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate="+starttime+"&endDate="+endtime+"&words="+keyword+"&categories="+title);
        searchThread.start();

        mainrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean connected = false;
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    connected = (exitValue == 0);
                }
                catch (IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }
                if(connected){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size=15&startDate=2021-09-02&endDate=2021-09-03&words="+keyword+"&categories="+title);
                        starttime = "2021-09-02";
                        endtime = "2021-09-03";
                        searchThread.start();
                        mainrecyclerview.refreshComplete();
                    }
                },1000);}
                else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "You are not connected to the Internet.", Toast.LENGTH_SHORT).show();
                            mainrecyclerview.refreshComplete();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onLoadMore() {
                boolean connected = false;
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    connected = (exitValue == 0);
                }
                catch (IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }
                if(connected){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SearchThread searchThread = new SearchThread("https://api2.newsminer.net/svc/news/queryNewsList?size="+cursize+"&startDate="+starttime+"&endDate="+endtime+"&words="+keyword+"&categories="+title);
                        cursize += 15;
                        searchThread.start();
                        mainrecyclerview.setLimitNumberToCallLoadMore(2);
                        mainrecyclerview.loadMoreComplete();
                    }
                },  1000);}
                else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "You are not connected to the Internet.", Toast.LENGTH_SHORT).show();
                            mainrecyclerview.loadMoreComplete();
                        }
                    }, 1000);
                }
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
                NewsSource newsSource = null;
                NewsSourcePlanB newsSourcePlanB = null;
                List<NewsBean> data = new ArrayList<>();
                try {
                    newsSource = gson.fromJson(json, NewsSource.class);
                    data = newsSource.getData();
                }catch(Exception e){
                    newsSourcePlanB = gson.fromJson(json, NewsSourcePlanB.class);
                    List<NewsBeanPlanB> data1 = newsSourcePlanB.getData();
                    for (NewsBeanPlanB newsbeanplanb:
                         data1) {
                            data.add(new NewsBean(newsbeanplanb));
                    }
                }
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
