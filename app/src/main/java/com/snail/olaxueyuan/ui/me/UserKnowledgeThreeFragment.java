package com.snail.olaxueyuan.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.me.adapter.ThreeLevelExpandableAdapter;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by mingge on 2016/5/20.
 */
public class UserKnowledgeThreeFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    /*private ArrayList<Level1> module.getResult();

    class Level1 {
        String title;
        ArrayList<Level2> child = new ArrayList<UserKnowledgeFragment.Level2>();
    }

    class Level2 {
        String title;
        ArrayList<Level3> child = new ArrayList<UserKnowledgeFragment.Level3>();
    }

    class Level3 {
        String title;
    }*/

    private UserKnowledgeResult module;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_knowledge, container, false);
        ButterKnife.bind(this, rootView);
        fetchData();
        initView();
        return rootView;
    }

    private void initView() {
        /*module.getResult() = new ArrayList<UserKnowledgeFragment.Level1>();
        for (int i = 0; i < 6; i++) {
            Level1 level1 = new Level1();
            level1.title = "Level1-" + i;
            module.getResult().add(level1);
            for (int j = 0; j < 7; j++) {
                Level2 level2 = new Level2();
                level2.title = "  Level2-" + j;
                level1.child.add(level2);
                for (int k = 0; k < 8; k++) {
                    Level3 level3 = new Level3();
                    level3.title = "Level3-" + k;
                    level2.child.add(level3);
                }
            }
        }*/
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEUserManager.getInstance().getStatisticsList("1", SEAuthManager.getInstance().getAccessUser().getId(), new Callback<UserKnowledgeResult>() {
            @Override
            public void success(UserKnowledgeResult userKnowledgeResult, Response response) {
                SVProgressHUD.dismiss(getActivity());
                Logger.json(userKnowledgeResult);
                if (userKnowledgeResult.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), userKnowledgeResult.getMessage(), 2.0f);
                } else {
                    module = userKnowledgeResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initAdapter();
                    break;
            }
        }
    };

    private void initAdapter() {
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new MainAdapter(getActivity()));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MainAdapter extends ThreeLevelExpandableAdapter {

        public MainAdapter(Context context) {
            super(context);
        }

        @Override
        public int getGroupCount() {
            return module.getResult().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return module.getResult().get(groupPosition).getChild().size();
        }

        @Override
        public UserKnowledgeResult.ResultEntity getGroup(int groupPosition) {
            return module.getResult().get(groupPosition);
        }

        @Override
        public UserKnowledgeResult.ResultEntity.ChildEntity getChild(int groupPosition, int childPosition) {
            return module.getResult().get(groupPosition).getChild().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(
                    android.R.color.darker_gray));
            UserKnowledgeResult.ResultEntity resultEntity = getGroup(groupPosition);
            textView.setText(resultEntity.getName());
            return textView;
        }

        @Override
        public int getThreeLevelCount(int firstLevelPosition,
                                      int secondLevelPosition) {
            return getGroup(firstLevelPosition).getChild().size();
        }

        @Override
        public View getSecondLevleView(int firstLevelPosition,
                                       int secondLevelPosition, boolean isExpanded, View convertView,
                                       ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setWidth(600);
            textView.setTextSize(25);
            textView.setTextColor(getResources().getColor(
                    android.R.color.secondary_text_dark));
            List<UserKnowledgeResult.ResultEntity> resultEntity = (List<UserKnowledgeResult.ResultEntity>) getChild(firstLevelPosition, secondLevelPosition);
            textView.setText(resultEntity.get(secondLevelPosition).getName());
            return textView;
        }

        @Override
        public View getThreeLevleView(final int firstLevelPosition,
                                      final int secondLevelPosition, final int ThreeLevelPosition,
                                      View convertView, ViewGroup parent) {
            UserKnowledgeResult.ResultEntity.ChildEntity child = getGrandChild(firstLevelPosition,
                    secondLevelPosition, ThreeLevelPosition);
            ViewHolderThree viewHolderThree;
            if (convertView == null) {
                viewHolderThree = new ViewHolderThree();
                convertView = View.inflate(mContext, R.layout.fragment_user_knowledge_listview_item, null);
                viewHolderThree.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolderThree);
            } else {
                viewHolderThree = (ViewHolderThree) convertView.getTag();
            }
            viewHolderThree.name.setText(child.getName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "firstLevelPosition==" + firstLevelPosition + ",secondLevelPosition=" + secondLevelPosition
                            + "ThreeLevelPosition=" + ThreeLevelPosition, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolderThree {
            TextView name;

        }

        @Override
        public UserKnowledgeResult.ResultEntity.ChildEntity getGrandChild(int groupPosition, int childPosition,
                                                                          int grandChildPosition) {
            return getChild(groupPosition, childPosition);
        }

    }
}
