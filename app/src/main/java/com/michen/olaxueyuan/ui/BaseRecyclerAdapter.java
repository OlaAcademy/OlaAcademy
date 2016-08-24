package com.michen.olaxueyuan.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 16/8/24.
 */
public class BaseRecyclerAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    protected LayoutInflater layoutInflater;
    public Context context;
    protected List<T> list = new ArrayList<>();
    public View.OnClickListener onClickListener = null;

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(K holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
