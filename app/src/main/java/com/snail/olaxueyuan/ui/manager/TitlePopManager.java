package com.snail.olaxueyuan.ui.manager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.app.SEAPP;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.ui.question.module.CourseNameModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/11.
 */
public class TitlePopManager {
    private static TitlePopManager popManager;
    Context context;
    private static List<CourseNameModule> list = new ArrayList<>();
    public PidClickListener pidClickListener;

    public static TitlePopManager getInstance() {
        if (popManager == null) {
            popManager = new TitlePopManager();
            initTitleData();
        }
        return popManager;
    }

    private static void initTitleData() {
        list.clear();
        String[] arrays = SEAPP.getAppContext().getResources().getStringArray(R.array.courses);
        for (int i = 0; i < arrays.length; i++) {
            CourseNameModule courseName;
            if (i == 0) {
                courseName = new CourseNameModule(arrays[i], true);
            } else {
                courseName = new CourseNameModule(arrays[i], false);
            }
            list.add(courseName);
        }
    }

    PopupWindow popupWindow;
    GridAdapter gridAdapter = new GridAdapter();

    /**
     * @param context
     * @param titleManager
     * @param popLine
     * @param pidClickListener
     * @param type             type:1、考点2、题库3、课堂4、
     */
    public void showPop(Context context, final TitleManager titleManager, View popLine, final PidClickListener pidClickListener, final int type) {
        this.context = context;
        this.pidClickListener = pidClickListener;
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.fragment_question_pop, null);
        GridView gridView = (GridView) contentView.findViewById(R.id.gridview);
        gridView.setAdapter(gridAdapter);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.white));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(popLine);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(false);
                }
                list.get(position).setSelected(true);
                titleManager.title_tv.setText(list.get(position).getName());
                gridAdapter.notifyDataSetChanged();
                pidClickListener.pidPosition(type, String.valueOf(position + 1));
                popupWindow.dismiss();
            }
        });
    }

    class GridAdapter extends BaseAdapter {
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
                convertView = View.inflate(context, R.layout.fragment_question_pop_gridview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.gridview.setText(list.get(position).getName());
            if (list.get(position).isSelected()) {
                holder.gridview.setBackgroundResource(R.drawable.pop_title_bg_blue_rectangle);
                holder.gridview.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                holder.gridview.setBackgroundResource(R.drawable.pop_title_bg_rectangle);
                holder.gridview.setTextColor(context.getResources().getColor(R.color.black_one));
            }
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.gridview)
            TextView gridview;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public interface PidClickListener {
        void pidPosition(int type, String pid);//type:1、考点2、题库3、课堂4、
    }
}
