package com.example.newsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapplication.newsmodel.NewsBean;
import com.example.newsapplication.newsmodel.NewsSource;
import com.google.android.material.tabs.TabLayout;
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

    List<NewsItemFragment> fragmentList = new ArrayList<>();
    List<String> newstypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbarmain = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbarmain);

        //娱乐、军事、教育、文化、健康、财经、体育、汽车、科技、社会
        newstypeList.add("娱乐");
        newstypeList.add("军事");
        newstypeList.add("财经");
        newstypeList.add("体育");
        newstypeList.add("科技");
        newstypeList.add("汽车");
        newstypeList.add("教育");
        newstypeList.add("文化");
        newstypeList.add("健康");
        newstypeList.add("社会");

        List<String> curnewslist = new ArrayList<>();
        Intent intent = getIntent();
        boolean entertainmentchecked = intent.getBooleanExtra("娱乐", true);
        if(entertainmentchecked)
            curnewslist.add("娱乐");
        boolean militarychecked = intent.getBooleanExtra("军事",true);
        if(militarychecked)
            curnewslist.add("军事");
        boolean financechecked = intent.getBooleanExtra("财经",true);
        if(financechecked)
            curnewslist.add("财经");
        boolean sportchecked = intent.getBooleanExtra("体育",true);
        if(sportchecked)
            curnewslist.add("体育");
        boolean technologychecked = intent.getBooleanExtra("科技",true);
        if(technologychecked)
            curnewslist.add("科技");
        boolean carchecked = intent.getBooleanExtra("汽车",true);
        if(carchecked)
            curnewslist.add("汽车");
        boolean educationchecked = intent.getBooleanExtra("教育",true);
        if(educationchecked)
            curnewslist.add("教育");
        boolean culturechecked = intent.getBooleanExtra("文化",true);
        if(culturechecked)
            curnewslist.add("文化");
        boolean healthchecked = intent.getBooleanExtra("健康",true);
        if(healthchecked)
            curnewslist.add("健康");
        boolean societychecked = intent.getBooleanExtra("社会",true);
        if(societychecked)
            curnewslist.add("社会");


        for (String newstype:
             curnewslist) {
            fragmentList.add(new NewsItemFragment(newstype));
        }

        TabLayout tabLayout = findViewById(R.id.main_tablayout);
        ViewPager viewPager = findViewById(R.id.main_newspager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList, curnewslist));

        tabLayout.setupWithViewPager(viewPager);

        Button tablayouticon = findViewById(R.id.tablayout_icon);
        tablayouticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_send = new Intent(MainActivity.this, SetTypeActivity.class);
                for (String newstype:
                     newstypeList) {
                    if(curnewslist.contains(newstype)){
                        intent_send.putExtra(newstype, true);
                        //Log.e("TAG",newstype);
                    }else
                        intent_send.putExtra(newstype,false);
                }
                startActivity(intent_send);
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

}




