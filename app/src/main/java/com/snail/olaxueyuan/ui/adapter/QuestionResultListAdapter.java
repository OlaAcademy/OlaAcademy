package com.snail.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snail.olaxueyuan.R;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/6/14.
 */
public class QuestionResultListAdapter extends BaseAdapter {
    private JSONArray array;
    private Context context;

    public QuestionResultListAdapter(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return array.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_question_result_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.videoName.setText("");
//        holder.videoLength.setText("");

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.video_name)
        TextView videoName;
        @Bind(R.id.video_length)
        TextView videoLength;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
