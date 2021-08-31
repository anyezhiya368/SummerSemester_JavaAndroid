package com.example.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsItemAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context context;
        private List<NewsBean> newsBeanList;

        public NewsItemAdapter(Context context, List<NewsBean> newsBeanList) {
            this.context = context;
            this.newsBeanList = newsBeanList;
        }

    class NewsHolder extends RecyclerView.ViewHolder{
        public TextView titletv;
        public TextView sourcetv;
        public com.makeramen.roundedimageview.RoundedImageView image;

        public NewsHolder(View newsitem){
            super(newsitem);

            titletv = newsitem.findViewById(R.id.newstitle);
            sourcetv = newsitem.findViewById(R.id.newssource);
            image = newsitem.findViewById(R.id.newsimage);

        }
    }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View newsitem = LayoutInflater.from(context).inflate(R.layout.newsitem_oneimage, parent, false);
            return new NewsHolder(newsitem);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            NewsHolder newsholder = (NewsHolder)holder;
            newsholder.titletv.setText(newsBeanList.get(position).getTitle());
            newsholder.sourcetv.setText(newsBeanList.get(position).getSource());
            Glide.with(context).load(newsBeanList.get(position).getImageurl()).into(newsholder.image);

            newsholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Hello" + holder.getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return newsBeanList.size();
        }

}
