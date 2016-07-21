package com.michen.olaxueyuan.ui.manager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/7/21.
 */
public class CirclePopManager {
    private static CirclePopManager popManager;
    Context context;
    private String[] mMarkArray = SEAPP.getAppContext().getResources().getStringArray(R.array.circle_select);
    private View all_search_view;

    public static CirclePopManager getInstance() {
        if (popManager == null) {
            popManager = new CirclePopManager();
        }
        return popManager;
    }

    /**
     * 讲骨堂条件筛选
     *
     * @param context
     * @param popLine
     */
    public void showMarkPop(Context context, View popLine, final CircleClickListener circleClickListener, View all_search_view) {
        this.context = context;
        this.all_search_view = all_search_view;
        View contentView = LayoutInflater.from(context).inflate(R.layout.circle_fragment_pop_listview, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listview);
        final MarkAdapter markAdapter = new MarkAdapter();
        listView.setAdapter(markAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                circleClickListener.circlePosition(position, mMarkArray[position]);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(contentView, Utils.dip2px(context, 150), Utils.dip2px(context, 150));
        contentView.setFocusableInTouchMode(true);
        // 返回键可用
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_select));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(popLine);
        all_search_view.setVisibility(View.VISIBLE);
    }

    PopupWindow popupWindow;

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     **/
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            all_search_view.setVisibility(View.GONE);
        }
    }

    class MarkAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mMarkArray.length;
        }

        @Override
        public Object getItem(int position) {
            return mMarkArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.circle_fragment_pop_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.rankName.setText(mMarkArray[position]);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.rankName)
            TextView rankName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public interface CircleClickListener {
        void circlePosition(int type, String text);
    }
}
