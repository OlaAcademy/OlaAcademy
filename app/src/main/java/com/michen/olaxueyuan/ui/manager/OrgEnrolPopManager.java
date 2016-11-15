package com.michen.olaxueyuan.ui.manager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/5/11.
 */
public class OrgEnrolPopManager {
    private static OrgEnrolPopManager popManager;
    Context context;
    private List<OrganizationInfoResult.ResultBean.OptionListBean> list = new ArrayList<>();
    private List<OrganizationInfoResult.ResultBean> menuList = new ArrayList<>();
    public EnrolClickListener enrolClickListener;

    public static OrgEnrolPopManager getInstance() {
        if (popManager == null) {
            popManager = new OrgEnrolPopManager();
        }
        return popManager;
    }

    PopupWindow popupWindow;
    OrgAdapter orgAdapter = new OrgAdapter();

    public void showOrgPop(Context context, View popLine, final EnrolClickListener enrolClickListener
            , final int type, final List<OrganizationInfoResult.ResultBean> menuList) {
        this.menuList = menuList;
        this.context = context;
        this.enrolClickListener = enrolClickListener;
        View contentView = LayoutInflater.from(context).inflate(R.layout.org_enrol_pop, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listview);
        listView.setAdapter(orgAdapter);
        popupWindow = new PopupWindow(contentView, (Utils.getScreenWidth(context) - Utils.dip2px(context, 30)), LinearLayout.LayoutParams.WRAP_CONTENT);
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
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_rectangle));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(popLine);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orgAdapter.notifyDataSetChanged();
                enrolClickListener.enrolPosition(type, position, 0, menuList.get(position).getOptionName(), "");
                popupWindow.dismiss();
            }
        });
    }

    class OrgAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public Object getItem(int position) {
            return menuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.org_enrol_pop_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(menuList.get(position).getOptionName());
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.name)
            TextView name;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    ChildAdapter childAdapter = new ChildAdapter();

    public void showChildPop(Context context, View popLine, final EnrolClickListener enrolClickListener
            , final int type, final int orgType, final List<OrganizationInfoResult.ResultBean.OptionListBean> list) {
        this.list = list;
        this.context = context;
        this.enrolClickListener = enrolClickListener;
        View contentView = LayoutInflater.from(context).inflate(R.layout.org_enrol_pop, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listview);
        listView.setAdapter(childAdapter);
        popupWindow = new PopupWindow(contentView, (Utils.getScreenWidth(context) - Utils.dip2px(context, 30)), LinearLayout.LayoutParams.WRAP_CONTENT);
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
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_gray_rectangle));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(popLine);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                childAdapter.notifyDataSetChanged();
                enrolClickListener.enrolPosition(type, orgType, position, list.get(position).getName(), list.get(position).getId());
                popupWindow.dismiss();
            }
        });
    }

    class ChildAdapter extends BaseAdapter {
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
                convertView = View.inflate(context, R.layout.org_enrol_pop_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(list.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.name)
            TextView name;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public interface EnrolClickListener {
        /**
         * @param type      :1、第一级机构2、第二级名称
         * @param orgType   第一个选项选中的位置
         * @param childType 第二个选项选中的位置
         * @param name
         * @param id
         */
        void enrolPosition(int type, int orgType, int childType, String name, String id);
    }

}
