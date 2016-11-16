package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.WrongListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.WrongTopicListViewAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WrongTopicSetActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {
    @Bind(R.id.maths_text)
    TextView mathsText;
    @Bind(R.id.maths_indicator)
    View mathsIndicator;
    @Bind(R.id.maths_layout)
    RelativeLayout mathsLayout;
    @Bind(R.id.english_text)
    TextView englishText;
    @Bind(R.id.english_indicator)
    View englishIndicator;
    @Bind(R.id.english_layout)
    RelativeLayout englishLayout;
    @Bind(R.id.logic_text)
    TextView logicText;
    @Bind(R.id.logic_indicator)
    View logicIndicator;
    @Bind(R.id.logic_layout)
    RelativeLayout logicLayout;
    @Bind(R.id.writing_text)
    TextView writingText;
    @Bind(R.id.writing_indicator)
    View writingIndicator;
    @Bind(R.id.writing_layout)
    RelativeLayout writingLayout;
    @Bind(R.id.subject_name)
    TextView subjectName;
    @Bind(R.id.subject_layout)
    RelativeLayout subjectLayout;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private int type = 0;//三个条件1,2,3
    private String subjectType = "1";// 1 数学 2 英语 3 逻辑 4 协作
    private String[] selectArray = {};//
    private WrongTopicListViewAdapter adapter;
    private int setType = 4;//4 考点 5 模考或真题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_topic_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitleText("错题集");
        mathsText.setSelected(true);
        mathsIndicator.setSelected(true);
        selectArray = mContext.getResources().getStringArray(R.array.question_select_maths);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        listview.getRefreshableView().setDivider(null);
        adapter = new WrongTopicListViewAdapter(mContext);
        listview.setAdapter(adapter);
        getWrongList();
    }

    private void changeTab(boolean maths, boolean english, boolean logic, boolean writing, int position) {
        mathsText.setSelected(maths);
        mathsIndicator.setSelected(maths);
        englishText.setSelected(english);
        englishIndicator.setSelected(english);
        logicText.setSelected(logic);
        logicIndicator.setSelected(logic);
        writingText.setSelected(writing);
        writingIndicator.setSelected(writing);
        switch (position) {
            case 1:
                selectArray = mContext.getResources().getStringArray(R.array.question_select_english);
                break;
            case 2:
                selectArray = mContext.getResources().getStringArray(R.array.question_select_logic);
                break;
            case 3:
                selectArray = mContext.getResources().getStringArray(R.array.question_select_writing);
                break;
            case 0:
            default:
                selectArray = mContext.getResources().getStringArray(R.array.question_select_maths);
                break;
        }
        subjectName.setText(selectArray[0]);
        this.subjectType = String.valueOf(position + 1);
        type = 0;
        getWrongList();
    }

    private void getWrongList() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        String userId = "";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()) {
            userId = am.getAccessUser().getId();
        }
        SEUserManager.getInstance().getWrongList(String.valueOf(type + 1), subjectType, userId, new Callback<WrongListResult>() {
            @Override
            public void success(WrongListResult wrongListResult, Response response) {
                if (mContext != null && !WrongTopicSetActivity.this.isFinishing()) {
                    SVProgressHUD.dismiss(mContext);
                    listview.onRefreshComplete();
//                Logger.json(examModule);
                    if (wrongListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, wrongListResult.getMessage(), 2.0f);
                    } else {
                        adapter.updateData(wrongListResult.getResult(), Integer.parseInt(subjectType), setType);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !WrongTopicSetActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(mContext);
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWrongList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && !WrongTopicSetActivity.this.isFinishing()) {
            listview.onRefreshComplete();
            SVProgressHUD.dismiss(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.maths_layout, R.id.english_layout, R.id.logic_layout, R.id.writing_layout, R.id.subject_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maths_layout:
                changeTab(true, false, false, false, 0);
                break;
            case R.id.english_layout:
                changeTab(false, true, false, false, 1);
                break;
            case R.id.logic_layout:
                changeTab(false, false, true, false, 2);
                break;
            case R.id.writing_layout:
                changeTab(false, false, false, true, 3);
                break;
            case R.id.subject_layout:
                DialogUtils.showSelectQuestionDialog(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.select_one:
                                type = 0;
                                setType = 4;
                                break;
                            case R.id.select_two:
                                type = 1;
                                setType = 5;
                                break;
                            case R.id.select_three:
                                type = 2;
                                setType = 5;
                                break;
                        }
                        getWrongList();
                        subjectName.setText(selectArray[type]);
                    }
                }, type, selectArray);
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getWrongList();
    }
}
