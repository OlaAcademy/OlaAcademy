package com.michen.olaxueyuan.ui.course;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.model.MCSubCourse;
import com.michen.olaxueyuan.protocol.model.SECourseCate;
import com.michen.olaxueyuan.protocol.result.MCCourseListResult;
import com.michen.olaxueyuan.ui.BaseSearchActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.manager.TitlePopManager;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseFragment extends SuperFragment implements TitlePopManager.PidClickListener {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pop_line)
    View popLine;
    private PullToRefreshListView courseListView;
    private CourseAdapter adapter;
    private ArrayList<MCSubCourse> courseArrayList;
    View mMainView;
    TitleManager titleManager;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, mMainView);

        setupNavBar();

        courseListView = (PullToRefreshListView) mMainView.findViewById(R.id.infoListView);
        adapter = new CourseAdapter(getActivity());
        courseListView.setAdapter(adapter);
        performRefresh();
        courseListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                performRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SECourseCate courseCate = (SECourseCate) adapter.getItem(position - 1);
                int cid = courseCate.getId();
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        return mMainView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(BaseSearchActivity.MENU_SEARCH).setVisible(false);
    }

    private void setupNavBar() {
        titleManager = new TitleManager("数学", this, mMainView, false);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);
    }

    private void performRefresh() {
        SECourseManager courseManager = SECourseManager.getInstance();
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        courseManager.fetchHomeCourseList(userId, pid, "2", new Callback<MCCourseListResult>() {
            @Override
            public void success(MCCourseListResult result, Response response) {
                if (!result.apicode.equals("10000")) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.message, 2.0f);
                } else {
                    courseArrayList = result.course.courseArrayList;
                    adapter.updateData(courseArrayList);
                }
                courseListView.onRefreshComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                courseListView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv:
                TitlePopManager.getInstance().showPop(getActivity(), titleManager, popLine, this, 3);
                break;
        }
    }

    @Override
    public void pidPosition(int type, String pid) {
        if (type == 3) {
            this.pid = pid;
            performRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

