package com.snail.olaxueyuan.ui.question;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.adapter.QuestionAdapter;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends SuperFragment {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.question_name)
    TextView questionName;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.pop_line)
    View popLine;
    QuestionAdapter adapter;
    QuestionCourseModule module;
    PopupWindow popupWindow;

    public QuestionFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        fetchData();
        return rootView;
    }


    private void initView() {
        new TitleManager("数学", this, rootView, false);
        Button temp = (Button) rootView.findViewById(R.id.temp);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuestionWebActivity.class);
                getActivity().startActivity(intent);
            }
        });
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        adapter = new QuestionAdapter(getActivity());
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (module != null) {
                    module.getResult().getChild().get(groupPosition).setIsExpanded(false);
                    adapter.updateList(module);
                }
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (module != null) {
                    module.getResult().getChild().get(groupPosition).setIsExpanded(true);
                    adapter.updateList(module);
                }
            }
        });
    }

    private void fetchData() {
        QuestionCourseManager.getInstance().fetchHomeCourseList("1", "1", new Callback<QuestionCourseModule>() {
            @Override
            public void success(QuestionCourseModule questionCourseModule, Response response) {
                if (questionCourseModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), questionCourseModule.getMessage(), 2.0f);
                } else {
                    module = questionCourseModule;
                    adapter.updateList(module);
                    expandableListView.setFocusable(false);
//                    for (int i = 0; i < module.getResult().getChild().size(); i++) {
//                        expandableListView.expandGroup(i);
//                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv:
                showPop();
                break;
        }
    }

    public void showPop() {
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_question_pop, null);
        GridView gridView = (GridView) contentView.findViewById(R.id.gridview);
        GridAdapter adapter = new GridAdapter();
        gridView.setAdapter(adapter);
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
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.white));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(popLine);
    }

    class GridAdapter extends BaseAdapter {
        String[] arrays = getActivity().getResources().getStringArray(R.array.courses);

        @Override
        public int getCount() {
            return arrays.length;
        }

        @Override
        public Object getItem(int position) {
            return arrays[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.fragment_question_pop_gridview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
