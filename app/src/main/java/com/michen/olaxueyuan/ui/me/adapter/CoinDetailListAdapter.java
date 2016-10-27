package com.michen.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.CoinHistoryResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/22.
 */
public class CoinDetailListAdapter extends BaseAdapter {
    Context context;
    List<CoinHistoryResult.ResultBean> list = new ArrayList<>();

    public CoinDetailListAdapter(Context context) {
        this.context = context;
    }

    public void updateData(CoinHistoryResult module) {
        if (module != null && module.getResult() != null) {
            this.list.clear();
            this.list = module.getResult();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.coin_detail_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getName());
        holder.time.setText(list.get(position).getDate());
        if (list.get(position).getDealNum() > 0) {
            holder.coinDetail.setText("+" + list.get(position).getDealNum());
            holder.coinDetail.setTextColor(context.getResources().getColor(R.color.blue_light));
        } else {
            holder.coinDetail.setText(String.valueOf(list.get(position).getDealNum()));
            holder.coinDetail.setTextColor(context.getResources().getColor(R.color.red_f01c06));
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.coin_detail)
        TextView coinDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
