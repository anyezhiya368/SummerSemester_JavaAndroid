package com.example.newsapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import javax.xml.xpath.XPathException;

public class SearchableActivity extends AppCompatActivity{

    Toolbar toolbar;
    NewsItemFragment newssearch;
    String text;
    String keyword;
    String category;
    String starttime;
    String endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            text = query;
            toolbar.setTitle(text);

            parser();

            newssearch = new NewsItemFragment(keyword, category, starttime, endtime);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.search_fragment_layout, newssearch);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbat_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        if (id == R.id.action_settings) {
            DrawerLayout mDrawerLayout = findViewById(R.id.search_drawer_layout);
            mDrawerLayout.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parser(){
        StringBuffer buffer = new StringBuffer(text);
        int i = 0;
        char cur = buffer.charAt(0);
        while(cur != '|'){
            i++;
            cur = buffer.charAt(i);
        }keyword = buffer.substring(0, i);
        buffer.delete(0, i + 1);
        i = 0;
        cur = buffer.charAt(0);
        while(cur != '|'){
            i++;
            cur = buffer.charAt(i);
        }category = buffer.substring(0, i);
        buffer.delete(0, i + 1);
        i = 0;
        cur = buffer.charAt(0);
        while(cur != '|'){
            i++;
            cur = buffer.charAt(i);
        }starttime = buffer.substring(0, i);
        buffer.delete(0, i + 1);
        endtime = buffer.toString();
    }
}