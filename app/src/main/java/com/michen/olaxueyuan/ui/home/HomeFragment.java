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
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.common.HorizontalListView;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.course.turtor.TurtorActivity;
import com.michen.olaxueyuan.ui.home.data.DirectBroadCastAdapter;
import com.michen.olaxueyuan.ui.home.data.DirectBroadCastRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.HeaderImgeManager;
import com.michen.olaxueyuan.ui.home.data.HomeQuestionAdapter;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends SuperFragment {
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
    @Bind(R.id.goods_listview)
    SubListView goodsListview;
    @Bind(R.id.scroll)
    PullToRefreshScrollView scroll;
    @Bind(R.id.show_all_direct_broadcast)
    TextView showAllDirectBroadcast;
    @Bind(R.id.horizontalListView)
    HorizontalListView horizontalListView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    HomeQuestionAdapter homeQuestionAdapter;
    DirectBroadCastAdapter directBroadCastAdapter;
    DirectBroadCastRecyclerAdapter directBroadCastRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        fetchData();
        return view;
    }

    private void initView() {
        homeQuestionAdapter = new HomeQuestionAdapter(getActivity());
        questionListListview.setAdapter(homeQuestionAdapter);
        directBroadCastAdapter = new DirectBroadCastAdapter(getActivity());
        horizontalListView.setAdapter(directBroadCastAdapter);
    }

    private void fetchData() {
        HomeListManager.getInstance().getHomeListService().getHomeList(new Callback<HomeModule>() {
            @Override
            public void success(HomeModule result, Response response) {
//                Logger.json(result);
                if (getActivity() != null) {
                    if (result.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.getMessage(), 2.0f);
                    } else {
                        initData(result);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void initData(HomeModule result) {
        new HeaderImgeManager(getActivity(), imgViewpagerHome, pointerLayoutHome, result.getResult().getBannerList());
        homeQuestionAdapter.updateData(result.getResult().getQuestionList());
        List<HomeModule.ResultBean.GoodsListBean> goodsList = result.getResult().getGoodsList();
        goodsList.addAll(result.getResult().getGoodsList());
        directBroadCastAdapter.updateData(goodsList);
//        directBroadCastAdapter.updateData(result.getResult().getGoodsList());
        directBroadCastRecyclerAdapter=new DirectBroadCastRecyclerAdapter(getActivity(),goodsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(directBroadCastRecyclerAdapter);
//        directBroadCastRecyclerAdapter=new DirectBroadCastRecyclerAdapter(getActivity(),result.getResult().getGoodsList());
    }

    @OnClick({R.id.put_question_layout, R.id.find_teacher_layout, R.id.find_data_layout, R.id.find_data_group
            , R.id.show_all_question, R.id.show_all_direct_broadcast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.put_question_layout:
                break;
            case R.id.find_teacher_layout:
                break;
            case R.id.find_data_layout:
                break;
            case R.id.find_data_group:
                break;
            case R.id.show_all_question:
                break;
            case R.id.show_all_direct_broadcast:
                break;
        }
    }

    private void showTurtorView() {
        Intent turtorIntent = new Intent(getActivity(), TurtorActivity.class);
        startActivity(turtorIntent);
    }

    private void showCommodityView() {
        Intent commodityIntent = new Intent(getActivity(), CommodityActivity.class);
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
}
