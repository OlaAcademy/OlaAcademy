package com.michen.olaxueyuan.ui.home;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.AutoScrollViewPager;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.circle.DeployPostActivity;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.course.commodity.DataLibraryActivity;
import com.michen.olaxueyuan.ui.course.turtor.OrgEnrolActivity;
import com.michen.olaxueyuan.ui.group.GroupListActivity;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.home.data.CourseDatabaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.DirectBroadCastRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.HeaderImgeManager;
import com.michen.olaxueyuan.ui.home.data.HomeQuestionAdapter;
import com.michen.olaxueyuan.ui.home.data.QualityCourseRecyclerAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View view;
    @Bind(R.id.img_viewpager_home)
    AutoScrollViewPager imgViewpagerHome;
    @Bind(R.id.pointer_layout_home)
    LinearLayout pointerLayoutHome;
    @Bind(R.id.put_question_layout)
    LinearLayout putQuestionLayout;
    @Bind(R.id.find_teacher_layout)
    LinearLayout findTeacherLayout;
    @Bind(R.id.find_data_layout)
    LinearLayout findDataLayout;
    @Bind(R.id.find_data_group)
    LinearLayout findDataGroup;
    @Bind(R.id.show_all_question)
    TextView showAllQuestion;
    @Bind(R.id.questionList_listview)
    SubListView questionListListview;
    @Bind(R.id.scroll)
    PullToRefreshScrollView scroll;
    @Bind(R.id.show_all_direct_broadcast)
    TextView showAllDirectBroadcast;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.show_all_quality_course)
    TextView showAllQualityCourse;
    @Bind(R.id.recycler_view_quality_course)
    RecyclerView recyclerViewQualityCourse;
    @Bind(R.id.show_all_course_database)
    TextView showAllCourseDatabase;
    @Bind(R.id.recycler_view_course_database)
    RecyclerView recyclerViewCourseDatabase;

    HomeQuestionAdapter homeQuestionAdapter;
    DirectBroadCastRecyclerAdapter directBroadCastRecyclerAdapter;
    QualityCourseRecyclerAdapter qualityCourseRecyclerAdapter;
    CourseDatabaseRecyclerAdapter courseDatabaseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        fetchData();
        return view;
    }

    private void initView() {
        scroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scroll.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQualityCourse.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCourseDatabase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        homeQuestionAdapter = new HomeQuestionAdapter(getActivity());
        questionListListview.setAdapter(homeQuestionAdapter);
        directBroadCastRecyclerAdapter = new DirectBroadCastRecyclerAdapter(getActivity());
        recyclerView.setAdapter(directBroadCastRecyclerAdapter);
        qualityCourseRecyclerAdapter = new QualityCourseRecyclerAdapter(getActivity());
        recyclerViewQualityCourse.setAdapter(qualityCourseRecyclerAdapter);
        courseDatabaseRecyclerAdapter = new CourseDatabaseRecyclerAdapter(getActivity());
        recyclerViewCourseDatabase.setAdapter(courseDatabaseRecyclerAdapter);
    }

    private void fetchData() {
        HomeListManager.getInstance().getHomeListService().getHomeList(new Callback<HomeModule>() {
            @Override
            public void success(HomeModule result, Response response) {
//                Logger.json(result);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    scroll.onRefreshComplete();
                    if (result.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.getMessage(), 2.0f);
                    } else {
                        initData(result);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    scroll.onRefreshComplete();
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void initData(HomeModule result) {
        new HeaderImgeManager(getActivity(), imgViewpagerHome, pointerLayoutHome, result.getResult().getBannerList());
        homeQuestionAdapter.updateData(result.getResult().getQuestionList());

        directBroadCastRecyclerAdapter.updateData(result.getResult().getGoodsList());
        qualityCourseRecyclerAdapter.updateData(result.getResult().getGoodsList());
        courseDatabaseRecyclerAdapter.updateData(result.getResult().getCourseList());
    }

    @OnClick({R.id.put_question_layout, R.id.find_teacher_layout, R.id.find_data_layout, R.id.find_data_group, R.id.show_all_question
            , R.id.show_all_direct_broadcast, R.id.show_all_quality_course, R.id.show_all_course_database})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.put_question_layout:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                Intent intent = new Intent(getActivity(), DeployPostActivity.class);
                startActivity(intent);
                break;
            case R.id.find_teacher_layout:
                showTurtorView();
                break;
            case R.id.find_data_layout:
//                showCommodityView();
                startActivity(new Intent(getActivity(), DataLibraryActivity.class));
                break;
            case R.id.find_data_group:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                startActivity(new Intent(getActivity(), GroupListActivity.class));
                break;
            case R.id.show_all_question:
                CircleFragment.type = "2";
                chageIndex(3);
                break;
            case R.id.show_all_direct_broadcast:
                break;
            case R.id.show_all_quality_course:
                Intent commodityIntent = new Intent(getActivity(), CommodityActivity.class);
                commodityIntent.putExtra("title", "精品课程");
                commodityIntent.putExtra("type", "1");
                getActivity().startActivity(commodityIntent);
                break;
            case R.id.show_all_course_database:
                chageIndex(1);
                break;
        }
    }

    private void chageIndex(int position) {
        /**
         *{@link com.michen.olaxueyuan.ui.MainFragment#onEventMainThread(ChangeIndexEvent)}
         * {@link com.michen.olaxueyuan.ui.circle.CircleFragment#onEventMainThread(ChangeIndexEvent)}
         */
        EventBus.getDefault().post(new ChangeIndexEvent(position, true));
    }

    private void showTurtorView() {
        Intent turtorIntent = new Intent(getActivity(), OrgEnrolActivity.class);
//        Intent turtorIntent = new Intent(getActivity(), TurtorActivity.class);  //不跳转这个界面了
        startActivity(turtorIntent);
    }

    private void showCommodityView() {
        Intent commodityIntent = new Intent(getActivity(), CommodityActivity.class);
        commodityIntent.putExtra("title", "资料库");
        commodityIntent.putExtra("type", "2");
        startActivity(commodityIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        imgViewpagerHome.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        imgViewpagerHome.stopAutoScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }
}
