package com.example.newsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.newsmodel.NewsBean;

import java.util.List;

public class NewsItemAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context context;
        private List<NewsBean> newsBeanList;
        MyDatabaseHelper dbHelper;

        public NewsItemAdapter(Context context, List<NewsBean> newsBeanList) {
            this.context = context;
            this.newsBeanList = newsBeanList;
        }

        class NoImageHolder extends RecyclerView.ViewHolder{
            public TextView titletv;
            public TextView sourcetv;

            public NoImageHolder(View newsitem){
                super(newsitem);

                titletv = newsitem.findViewById(R.id.no_image_tilte);
                sourcetv = newsitem.findViewById(R.id.no_image_source);
            }

        }


        class OneImageHolder extends RecyclerView.ViewHolder{
            public TextView titletv;
            public TextView sourcetv;
            public com.makeramen.roundedimageview.RoundedImageView image;

            public OneImageHolder(View newsitem){
                super(newsitem);

                titletv = newsitem.findViewById(R.id.newstitle);
                sourcetv = newsitem.findViewById(R.id.newssource);
                image = newsitem.findViewById(R.id.newsimage);

            }
        }

        class BigImageHolder extends RecyclerView.ViewHolder{
            public TextView titletv;
            public TextView sourcetv;
            public com.makeramen.roundedimageview.RoundedImageView image;

            public BigImageHolder(View newsitem){
                super(newsitem);

                titletv = newsitem.findViewById(R.id.big_image_title);
                sourcetv = newsitem.findViewById(R.id.big_image_source);
                image = newsitem.findViewById(R.id.big_image_image);

            }
        }

        class ThreeImageHolder extends RecyclerView.ViewHolder{
            public TextView titletv;
            public TextView sourcetv;
            public com.makeramen.roundedimageview.RoundedImageView image1;
            public com.makeramen.roundedimageview.RoundedImageView image2;
            public com.makeramen.roundedimageview.RoundedImageView image3;

            public ThreeImageHolder(View newsitem){
                super(newsitem);

                titletv = newsitem.findViewById(R.id.threetitle);
                sourcetv = newsitem.findViewById(R.id.threesource);
                image1 = newsitem.findViewById(R.id.threenewsimage1);
                image2 = newsitem.findViewById(R.id.threenewsimage2);
                image3 = newsitem.findViewById(R.id.threenewsimage3);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType){
                case 0:
                    View newsitem_no = LayoutInflater.from(context).inflate(R.layout.newsitem_noimage, parent, false);
                    return new NoImageHolder(newsitem_no);
                case 1:
                    View newsitem_one = LayoutInflater.from(context).inflate(R.layout.newsitem_oneimage, parent, false);
                    return new OneImageHolder(newsitem_one);
                case 100:
                    View newsitem_big = LayoutInflater.from(context).inflate(R.layout.newsitem_bigimage, parent, false);
                    return new BigImageHolder(newsitem_big);
            }
            View newsitem_three = LayoutInflater.from(context).inflate(R.layout.newsitem_threeimages, parent, false);
            return new ThreeImageHolder(newsitem_three);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            boolean viewed = false;
            dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("History", null, null, null, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    String titledb = cursor.getString(1);
                    if(titledb.equals(newsBeanList.get(holder.getAdapterPosition() - 1).getTitle())){
                        viewed = true;
                        break;
                    }}while(cursor.moveToNext());
            }cursor.close();

            if(holder instanceof NoImageHolder){
                NoImageHolder newsholder = (NoImageHolder) holder;
                newsholder.titletv.setText(newsBeanList.get(position).getTitle());
                if(viewed)
                    newsholder.titletv.setTextColor(0xFFBFBFBF);
                newsholder.sourcetv.setText(newsBeanList.get(position).getPublisher());

                newsholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean clickviewed = false;
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("History", null, null, null, null, null, null);
                        if(cursor.moveToFirst()){
                            do{
                                String titledb = cursor.getString(1);
                                if(titledb.equals(newsBeanList.get(holder.getAdapterPosition() - 1).getTitle())){
                                    clickviewed = true;
                                    break;
                                }}while(cursor.moveToNext());
                        }cursor.close();
                        if(!clickviewed){
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("title", newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        values.put("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        values.put("image", newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        values.put("video", newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        values.put("publisher", newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        values.put("time", newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        values.put("content", newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        db1.insert("History", null, values);}
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        intent.putExtra("image", newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        intent.putExtra("video",newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        intent.putExtra("title",newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        intent.putExtra("publisher",newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        intent.putExtra("time",newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        intent.putExtra("content",newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        context.startActivity(intent);
                        newsholder.titletv.setTextColor(0xFFBFBFBF);
                    }
                });
            }else if(holder instanceof OneImageHolder) {
                OneImageHolder newsholder = (OneImageHolder) holder;
                newsholder.titletv.setText(newsBeanList.get(position).getTitle());
                if(viewed)
                    newsholder.titletv.setTextColor(0xFFBFBFBF);
                newsholder.sourcetv.setText(newsBeanList.get(position).getPublisher());
                Glide.with(context).load(newsBeanList.get(position).getImage().get(0)).into(newsholder.image);

                newsholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean clickviewed = false;
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("History", null, null, null, null, null, null);
                        if(cursor.moveToFirst()){
                            do{
                                String titledb = cursor.getString(1);
                                if(titledb.equals(newsBeanList.get(holder.getAdapterPosition() - 1).getTitle())){
                                    clickviewed = true;
                                    break;
                                }}while(cursor.moveToNext());
                        }cursor.close();
                        if(!clickviewed){
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("title", newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        values.put("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        values.put("image", newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        values.put("video", newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        values.put("publisher", newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        values.put("time", newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        values.put("content", newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        db1.insert("History", null, values);}
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("image",newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        intent.putExtra("video",newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        intent.putExtra("title",newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        intent.putExtra("publisher",newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        intent.putExtra("time",newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        intent.putExtra("content",newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        intent.putExtra("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        context.startActivity(intent);
                        newsholder.titletv.setTextColor(0xFFBFBFBF);
                    }
                });
            }else if(holder instanceof ThreeImageHolder){
                ThreeImageHolder newsholder = (ThreeImageHolder) holder;
                newsholder.titletv.setText(newsBeanList.get(position).getTitle());
                if(viewed)
                    newsholder.titletv.setTextColor(0xFFBFBFBF);
                newsholder.sourcetv.setText(newsBeanList.get(position).getPublisher());
                Glide.with(context).load(newsBeanList.get(position).getImage().get(0)).into(newsholder.image1);
                Glide.with(context).load(newsBeanList.get(position).getImage().get(1)).into(newsholder.image2);
                Glide.with(context).load(newsBeanList.get(position).getImage().get(2)).into(newsholder.image3);

                newsholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean clickviewed = false;
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("History", null, null, null, null, null, null);
                        if(cursor.moveToFirst()){
                            do{
                                String titledb = cursor.getString(1);
                                if(titledb.equals(newsBeanList.get(holder.getAdapterPosition() - 1).getTitle())){
                                    clickviewed = true;
                                    break;
                                }}while(cursor.moveToNext());
                        }cursor.close();
                        if(!clickviewed){
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("title", newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        values.put("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        values.put("image", newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        values.put("video", newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        values.put("publisher", newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        values.put("time", newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        values.put("content", newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        db1.insert("History", null, values);}
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("image",newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        intent.putExtra("video",newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        intent.putExtra("title",newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        intent.putExtra("publisher",newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        intent.putExtra("time",newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        intent.putExtra("content",newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        intent.putExtra("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        context.startActivity(intent);
                        newsholder.titletv.setTextColor(0xFFBFBFBF);
                    }
                });
            }else{
                BigImageHolder newsholder = (BigImageHolder) holder;
                newsholder.titletv.setText(newsBeanList.get(position).getTitle());
                if(viewed)
                    newsholder.titletv.setTextColor(0xFFBFBFBF);
                newsholder.sourcetv.setText(newsBeanList.get(position).getPublisher());
                Glide.with(context).load(newsBeanList.get(position).getImage().get(0)).into(newsholder.image);

                newsholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean clickviewed = false;
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.query("History", null, null, null, null, null, null);
                        if(cursor.moveToFirst()){
                            do{
                                String titledb = cursor.getString(1);
                                if(titledb.equals(newsBeanList.get(holder.getAdapterPosition() - 1).getTitle())){
                                    clickviewed = true;
                                    break;
                                }}while(cursor.moveToNext());
                        }cursor.close();
                        if(!clickviewed){
                        dbHelper = new MyDatabaseHelper(context, "Local.db", null, 1);
                        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("title", newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        values.put("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        values.put("image", newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        values.put("video", newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        values.put("publisher", newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        values.put("time", newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        values.put("content", newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        db1.insert("History", null, values);}
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("image",newsBeanList.get(holder.getAdapterPosition() - 1).getImageString());
                        intent.putExtra("video",newsBeanList.get(holder.getAdapterPosition() - 1).getVideo());
                        intent.putExtra("title",newsBeanList.get(holder.getAdapterPosition() - 1).getTitle());
                        intent.putExtra("publisher",newsBeanList.get(holder.getAdapterPosition() - 1).getPublisher());
                        intent.putExtra("time",newsBeanList.get(holder.getAdapterPosition() - 1).getPublishTime());
                        intent.putExtra("content",newsBeanList.get(holder.getAdapterPosition() - 1).getContent());
                        intent.putExtra("url", newsBeanList.get(holder.getAdapterPosition() - 1).getUrl());
                        context.startActivity(intent);
                        newsholder.titletv.setTextColor(0xFFBFBFBF);
                    }
                });
            }
        }

    @Override
    public int getItemViewType(int position) {
            //needed to be overided
        if(newsBeanList.get(position).getImage().size() == 0)
            return 0;
        if(newsBeanList.get(position).getImage().size() >= 3)
            return 3;
        if(newsBeanList.get(position).getTitle().length() % 2 == 0)
            return 100;
        else
            return 1;
    }

    @Override
        public int getItemCount() {
            return newsBeanList.size();
        }

}
