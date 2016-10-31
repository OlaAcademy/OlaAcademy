package com.michen.olaxueyuan.ui.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.clansfab.FloatingActionMenu;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.QuestionHomeWorkListAdapter;
import com.michen.olaxueyuan.ui.group.CreateGroupActivity;
import com.michen.olaxueyuan.ui.group.TGroupListActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.michen.olaxueyuan.R.id.listview;

/**
 * Created by mingge on 2016/8/31.
 */
public class TeacherHomeFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2 {
    View view;
    @Bind(listview)
    PullToRefreshListView mListView;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.menu_view)
    FloatingActionMenu menuView;
    @Bind(R.id.notice_title_text)
    TextView noticeTitleText;
    @Bind(R.id.notice_hint_text)
    TextView noticeHintText;
    @Bind(R.id.create_group)
    Button createGroup;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;

    TitleManager titleManager;
    protected QuestionHomeWorkListAdapter adapter;
    private static final String PAGE_SIZE = "20";//每次加载20条
    private String homeworkId;
    protected List<HomeworkListResult.ResultBean> mList = new ArrayList<>();
    private boolean isHasGroup;//是否有群，false默认没有

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        } else {
            view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
            initView();
            getTeacherGroupList();//先判断是否有群，有的话再请求作业列表
            return view;
        }
    }

    private void initView() {
        menuView.setClosedOnTouchOutside(true);
        titleManager = new TitleManager("老师版", this, view, false);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(this);
        mListView.getRefreshableView().setDivider(null);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.message_tip_icon);
        adapter = new QuestionHomeWorkListAdapter(getActivity(),2);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int lastIndex = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 滚动之前,手还在屏幕上 记录滚动前的下标
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        lastIndex = view.getLastVisiblePosition();
                        break;
                    // 滚动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 记录滚动停止后 记录当前item的位置
                        int scrolled = view.getLastVisiblePosition();
                        // 滚动后下标大于滚动前 向下滚动了
                        if (scrolled > lastIndex) {
                            menuView.hideMenuButton(true);
                        } else {// 向上滚动了
                            menuView.showMenuButton(true);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        String userId = null;
        try {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
//            userId = "381";
        }
        TeacherHomeManager.getInstance().getHomeworkList(userId, "2", homeworkId, PAGE_SIZE, new Callback<HomeworkListResult>() {
            @Override
            public void success(HomeworkListResult homeworkListResult, Response response) {
                Logger.json(homeworkListResult);
                if (getActivity() != null) {
                    mListView.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    if (homeworkListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(getActivity(), homeworkListResult.getMessage());
                        if (isHasGroup) {
                            showNoHomeWorkView();
                        }
                    } else {
                        List<HomeworkListResult.ResultBean> list = homeworkListResult.getResult();
                        if (list.size() == 0) {
                            if (isHasGroup) {
                                showNoHomeWorkView();
                            }
                            return;
                        } else {
                            emptyLayout.setVisibility(View.GONE);
                        }
                        mList.addAll(list);
                        if (mList.size() > 0) {
                            homeworkId = mList.get(mList.size() - 1).getId() + "";
                        }
                        adapter.updateData(mList);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    if (isHasGroup) {
                        showNoHomeWorkView();
                    }
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), "获取作业列表失败");
                    mListView.onRefreshComplete();
                }
            }
        });
    }

    @OnClick({R.id.right_response, R.id.fab_math, R.id.fab_english, R.id.fab_logic
            , R.id.fab_write, R.id.create_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_response:
                Utils.jumpLoginOrNot(getActivity(), TGroupListActivity.class);
                break;
            case R.id.fab_math:
                publishSubject("1");
                break;
            case R.id.fab_english:
                publishSubject("2");
                break;
            case R.id.fab_logic:
                publishSubject("3");
                break;
            case R.id.fab_write:
                publishSubject("4");
                break;
            case R.id.create_group:
                Utils.jumpLoginOrNot(getActivity(), CreateGroupActivity.class);
                break;
        }
    }

    private void publishSubject(String pid) {
        //pid 1 数学 2 英语 3 逻辑 4 写作
        if (SEAuthManager.getInstance().isAuthenticated()) {
            startActivity(new Intent(getActivity(), TGetSubjectListActivity.class).putExtra("pid", pid));
            menuView.close(true);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        homeworkId = "";
        mList.clear();
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    /**
     * {@link TSubjectDeployActivity#publishHomeWork()}
     * {@link CreateGroupActivity#saveGroupInfo(String, String)}
     *
     * @param successEvent
     */
    public void onEventMainThread(PublishHomeWorkSuccessEvent successEvent) {
        if (successEvent.isSuccess) {
            getTeacherGroupList();
        }
    }

    private void getTeacherGroupList() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            startActivity(new Intent(getActivity(), UserLoginActivity.class));
            return;
        }
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        TeacherHomeManager.getInstance().getTeacherGroupList(userId, new Callback<TeacherGroupListResult>() {
            @Override
            public void success(TeacherGroupListResult teacherGroupListResult, Response response) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    if (teacherGroupListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(getActivity(), teacherGroupListResult.getMessage());
                        showNoGroupView();
                    } else {
                        if (teacherGroupListResult.getResult().size() > 0) {
                            isHasGroup = true;//size大于0就是有群
                            fetchData();
                        } else {
                            showNoGroupView();
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    showNoGroupView();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.request_group_fail);
                }
            }
        });
    }

    private void showNoGroupView() {
        emptyLayout.setVisibility(View.VISIBLE);
        noticeTitleText.setText(R.string.no_create_group);
        noticeHintText.setText(R.string.no_group_to_create);
        createGroup.setVisibility(View.VISIBLE);
        menuView.setVisibility(View.GONE);
    }

    private void showNoHomeWorkView() {
        emptyLayout.setVisibility(View.VISIBLE);
        noticeTitleText.setText(R.string.no_homework);
        noticeHintText.setText(R.string.watch_library_to_publish_homework);
        createGroup.setVisibility(View.GONE);
        menuView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }
}
