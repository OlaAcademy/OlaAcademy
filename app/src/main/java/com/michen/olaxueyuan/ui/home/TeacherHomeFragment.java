package com.michen.olaxueyuan.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.stickview.StickyListHeadersListView;
import com.michen.olaxueyuan.common.stickview.SwipeRefreshLayout;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.home.data.TeacherHomeListAdapter;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/8/31.
 */
public class TeacherHomeFragment extends SuperFragment implements AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderOffsetChangedListener, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    View view;
    @Bind(R.id.listview)
    StickyListHeadersListView mListView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.empty)
    TextView empty;

    TitleManager titleManager;
    private TextView mEmptyView;
    protected TeacherHomeListAdapter mConferenceListAdapter;
    private TextView mLoadMore;
    private ProgressBar mProgressBar;
    private static final String PAGE_SIZE = "20";//每次加载20条
    private String homeworkId;
    protected List<HomeworkListResult.ResultBean> mList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        initViews();
        fetchData();
        return view;
    }

    private void initView() {
        titleManager = new TitleManager("老师版", this, view, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.message_tip_icon);
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        mSwipeRefreshLayout.setRefreshing(true);
        String userId = null;
        try {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = "381";
        }
        TeacherHomeManager.getInstance().getHomeworkList(userId, "2", homeworkId, PAGE_SIZE, new Callback<HomeworkListResult>() {
            @Override
            public void success(HomeworkListResult homeworkListResult, Response response) {
                Logger.json(homeworkListResult);
                if (getActivity() != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    SVProgressHUD.dismiss(getActivity());
                    if (homeworkListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), homeworkListResult.getMessage(), 2.0f);
                    } else {
                        mList.clear();
                        List<HomeworkListResult.ResultBean> list = homeworkListResult.getResult();
                        mList.addAll(list);
                        if (mList.size() <= 0) {
                            mListFooterView.setVisibility(View.GONE);
                        } else {
                            homeworkId = mList.get(mList.size() - 1).getId() + "";
                            mListFooterView.setVisibility(View.VISIBLE);
                        }
                        mConferenceListAdapter.update(mList);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        mSwipeRefreshLayout.setColorScheme(android.R.color.white, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mListView.addFooterView(initFooterView());
        mListView.setOnItemClickListener(this);
        mListView.setOnHeaderClickListener(this);
        mListView.setOnStickyHeaderOffsetChangedListener(this);
        mEmptyView = (TextView) view.findViewById(R.id.empty);
        mListView.setEmptyView(mEmptyView);
        mListView.setDrawingListUnderStickyHeader(true);
        mListView.setAreHeadersSticky(true);
        mConferenceListAdapter = new TeacherHomeListAdapter(getActivity(), mList);
        mListView.setAdapter(mConferenceListAdapter);
    }

    private View mListFooterView;

    private View initFooterView() {
        mListFooterView = View.inflate(getActivity(), R.layout.load_more_layout, null);
        mLoadMore = (TextView) mListFooterView.findViewById(R.id.load_more);
        mProgressBar = (ProgressBar) mListFooterView.findViewById(R.id.progressbar);
        mLoadMore.setOnClickListener(this);
        return mListFooterView;
    }

    private void loadMore() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadMore.setVisibility(View.GONE);
        String userId = null;
        try {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = "381";
        }
        TeacherHomeManager.getInstance().getHomeworkList(userId, "2", homeworkId, PAGE_SIZE, new Callback<HomeworkListResult>() {
            @Override
            public void success(HomeworkListResult homeworkListResult, Response response) {
                Logger.json(homeworkListResult);
                if (getActivity() != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    SVProgressHUD.dismiss(getActivity());
                    if (homeworkListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), homeworkListResult.getMessage(), 2.0f);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        mLoadMore.setVisibility(View.VISIBLE);
                        List<HomeworkListResult.ResultBean> list = homeworkListResult.getResult();
                        mList.addAll(list);
                        mConferenceListAdapter.update(mList);
                        homeworkId = mList.get(mList.size() - 1).getId() + "";
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressBar.setVisibility(View.GONE);
                    mLoadMore.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_more:
                loadMore();
                break;
            case R.id.empty:
                onRefresh();
                break;
        }
    }


    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
        homeworkId = "";
        fetchData();
    }

    @Override
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
