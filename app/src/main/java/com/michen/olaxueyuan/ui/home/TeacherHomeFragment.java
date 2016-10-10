package com.michen.olaxueyuan.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.clansfab.FloatingActionMenu;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.QuestionHomeWorkListAdapter;
import com.michen.olaxueyuan.ui.group.CreateGroupActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    TitleManager titleManager;
    protected QuestionHomeWorkListAdapter adapter;
    private static final String PAGE_SIZE = "20";//每次加载20条
    private String homeworkId;
    protected List<HomeworkListResult.ResultBean> mList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        } else {
            view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
            ButterKnife.bind(this, view);
            initView();
            fetchData();
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
        adapter = new QuestionHomeWorkListAdapter(getActivity());
        mListView.setAdapter(adapter);
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
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), homeworkListResult.getMessage(), 2.0f);
                    } else {
                        List<HomeworkListResult.ResultBean> list = homeworkListResult.getResult();
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
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                    mListView.onRefreshComplete();
                }
            }
        });
    }

    @OnClick({R.id.right_response, R.id.fab_math, R.id.fab_english, R.id.fab_logic, R.id.fab_write})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_response:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
//                startActivity(new Intent(getActivity(), GroupDetailActivity.class));
                startActivity(new Intent(getActivity(), CreateGroupActivity.class));
                break;
            case R.id.fab_math:
                menuView.close(true);
                break;
            case R.id.fab_english:
                menuView.close(true);
                break;
            case R.id.fab_logic:
                menuView.close(true);
                break;
            case R.id.fab_write:
                menuView.close(true);
                break;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
