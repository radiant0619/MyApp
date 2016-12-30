package com.radiant.acsl.myworkapp.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.radiant.acsl.myworkapp.Other.FeedItem;
import com.radiant.acsl.myworkapp.R;


import java.util.List;

/**
 * Created by sakthivel on 01/12/2016.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {


    private List<FeedItem> feedItemList;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private OnItemClickListener onItemClickListener;

    public int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;


    private OnLoadMoreListener onLoadMoreListener;

    public RecycleAdapter(Context context, List<FeedItem> feedItemList, RecyclerView recycleView) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.mRecyclerView = recycleView;
        if (mRecyclerView.getLayoutManager() instanceof RecyclerView.LayoutManager) {
            final LinearLayoutManager linearLayout = (LinearLayoutManager) mRecyclerView.getLayoutManager();

            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayout.getItemCount();
                    lastVisibleItem = linearLayout.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_views, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final FeedItem feedItem = feedItemList.get(position);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getmThumbnail())) {
            Glide.with(mContext)
                    .load(feedItem.getmThumbnail())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }

        //Setting text view title
        Log.i("Html Title", feedItem.getmTitle());
        holder.textView.setText(Html.fromHtml(feedItem.getmTitle()));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(feedItem);
            }
        };
        holder.imageView.setOnClickListener(listener);
        holder.textView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public void setFeedItemList(List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListner(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onloadMore) {
        this.onLoadMoreListener = onloadMore;
    }

    public void setLoaded() {
        loading = false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imgthumbnail);
            this.textView = (TextView) itemView.findViewById(R.id.txttitle);
        }
    }
}


