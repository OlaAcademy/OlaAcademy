package com.michen.olaxueyuan.ui.teacher;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.TGetSubjectExpandableListAdapter;
import com.michen.olaxueyuan.ui.adapter.TGetSubjectListViewAdapter;
import com.michen.olaxueyuan.ui.group.CreateGroupActivity;
import com.michen.olaxueyuan.ui.question.QuestionHomeWorkFragment;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 老是发布作业选择教材
 */
public class TGetSubjectListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.subject_name)
    TextView subjectName;
    @Bind(R.id.subject_layout)
    RelativeLayout subjectLayout;
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListViews;
    private ExpandableListView expandableListView;
    @Bind(R.id.listview_subject)
    PullToRefreshListView listview;

    TGetSubjectExpandableListAdapter adapter;
    private Context context;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作
    QuestionCourseModule module;
    private TGetSubjectListViewAdapter listViewAdapter;
    private int selectType = 0;//三个条件0,1,2
    private String[] selectArray = {};//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tget_subject_list);
        context = this;
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        fetchHomeCourseData();

    }

    private void initView() {
        setTitleText("选择教材");
        pid = getIntent().getStringExtra("pid");
        expandableListViews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expandableListViews.setOnRefreshListener(this);
        expandableListView = expandableListViews.getRefreshableView();
        expandableListView.setDivider(null);

        expandableListView.setGroupIndicator(null);
        adapter = new TGetSubjectExpandableListAdapter(context);
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
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.getRefreshableView().setDivider(null);
        listViewAdapter = new TGetSubjectListViewAdapter(context);
        listview.setAdapter(listViewAdapter);
        intArray();
    }

    private void intArray() {
        switch (Integer.parseInt(pid)) {
            case 2:
                selectArray = getResources().getStringArray(R.array.question_select_english);
                break;
            case 3:
                selectArray = getResources().getStringArray(R.array.question_select_logic);
                break;
            case 4:
                selectArray = getResources().getStringArray(R.array.question_select_writing);
                break;
            case 1:
            default:
                selectArray = getResources().getStringArray(R.array.question_select_maths);
                break;
        }
        subjectName.setText(selectArray[0]);
    }

    @OnClick(R.id.subject_layout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject_layout:
                DialogUtils.showSelectQuestionDialog(context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.select_one:
                                selectType = 0;
                                fetchHomeCourseData();
                                break;
                            case R.id.select_two:
                                selectType = 1;
                                fetchExamListData();
                                break;
                            case R.id.select_three:
                                selectType = 2;
                                fetchExamListData();
                                break;
                        }
                        subjectName.setText(selectArray[selectType]);
                    }
                }, selectType, selectArray);
                break;
        }
    }

    private void fetchHomeCourseData() {
        expandableListViews.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().fetchHomeCourseList(userId, pid, "1", new Callback<QuestionCourseModule>() {
            @Override
            public void success(QuestionCourseModule questionCourseModule, Response response) {
                if (context != null) {
                    SVProgressHUD.dismiss(context);
                    expandableListViews.onRefreshComplete();
//                Logger.json(questionCourseModule);
                    if (questionCourseModule.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(context, questionCourseModule.getMessage(), 2.0f);
                    } else {
                        module = questionCourseModule;
                        adapter.updateList(module);
                        expandableListView.setFocusable(false);
                        /**
                         * {@link QuestionHomeWorkFragment#onEventMainThread(QuestionCourseModule)}
                         */
                        EventBus.getDefault().post(questionCourseModule);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    expandableListViews.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                }
            }
        });
    }

    private void fetchExamListData() {
        expandableListViews.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        String userId = "";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()) {
            userId = am.getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getExamList(userId, pid, String.valueOf(selectType), new Callback<ExamModule>() {
            @Override
            public void success(ExamModule examModule, Response response) {
                if (context != null) {
                    SVProgressHUD.dismiss(context);
                    listview.onRefreshComplete();
//                Logger.json(examModule);
                    if (examModule.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(context, examModule.getMessage(), 2.0f);
                    } else {
                        listViewAdapter.updateData(examModule.getResult(), Integer.parseInt(pid));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(context);
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        if (refreshView == listview) {
            fetchExamListData();
        }
        if (refreshView == expandableListViews) {
            fetchHomeCourseData();
        }
    }

    /**
     * {@link TSubjectDeployActivity#publishHomeWork()}
     * {@link CreateGroupActivity#saveGroupInfo(String, String)}
     *
     * @param successEvent
     */
    public void onEventMainThread(PublishHomeWorkSuccessEvent successEvent) {
        if (successEvent.isSuccess) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
