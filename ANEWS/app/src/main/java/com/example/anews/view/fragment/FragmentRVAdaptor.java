package com.example.anews.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.anews.R;

import java.util.List;

//fragment中recycler view的adaptor
public class FragmentRVAdaptor extends RecyclerView.Adapter<RVHolder>{
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private Context mContext;
    private List<NewsItem> mNewsItemList;

    public FragmentRVAdaptor(Context context, List<NewsItem> newsItems){
        this.mContext = context;
        this.mNewsItemList = newsItems;
    }

    @NonNull
    @Override
    public RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RVHolder rvHolder = new RVHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item, null));
        return rvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVHolder holder, final int position) {
        String imgURL = mNewsItemList.get(position).getmPicURL();
        Glide.with(mContext)
                .load(imgURL)
                .into(holder.getmImageView());
        holder.getmTitleTV().setText(mNewsItemList.get(position).getmTitle());
        holder.getmTimeTV().setText(mNewsItemList.get(position).getmTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onItemLongClick(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsItemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
