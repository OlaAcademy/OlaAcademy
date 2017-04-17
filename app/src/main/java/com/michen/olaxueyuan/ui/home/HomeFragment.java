package com.michen.olaxueyuan.ui.home;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.AutoScrollViewPager;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.download.DownloadService;
import com.michen.olaxueyuan.protocol.event.ChatNewMessageEvent;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.MainFragment;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.circle.DeployPostActivity;
import com.michen.olaxueyuan.ui.circle.StudyHistoryActivity;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.course.commodity.DataLibraryActivity;
import com.michen.olaxueyuan.ui.course.turtor.OrgEnrolActivity;
import com.michen.olaxueyuan.ui.home.data.AskQuestionRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.home.data.CourseDatabaseRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.DirectBroadCastRecyclerAdapter;
import com.michen.olaxueyuan.ui.home.data.HeaderImgeManager;
import com.michen.olaxueyuan.ui.home.data.HomeQuestionAdapter;
import com.michen.olaxueyuan.ui.home.data.QualityCourseRecyclerAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.question.LenCloudMessageActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.cache.LCIMConversationItemCache;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;

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
    @Bind(R.id.study_time_length_text)
    TextView studyTimeLengthText;
    @Bind(R.id.complete_num_subject_text)
    TextView completeNumSubjectText;
    @Bind(R.id.persist_text)
    TextView persistText;
    @Bind(R.id.defeat_text)
    TextView defeatText;
    @Bind(R.id.user_avatar)
    RoundRectImageView userAvatar;
    @Bind(R.id.recycler_view_ask_question)
    RecyclerView recyclerViewAskQuestion;
    @Bind(R.id.chat_message_dot)
    TextView chatMessageDot;
    @Bind(R.id.study_progress_layout)
    LinearLayout studyProgressLayout;


    HomeQuestionAdapter homeQuestionAdapter;
    DirectBroadCastRecyclerAdapter directBroadCastRecyclerAdapter;
    QualityCourseRecyclerAdapter qualityCourseRecyclerAdapter;
    CourseDatabaseRecyclerAdapter courseDatabaseRecyclerAdapter;
    AskQuestionRecyclerAdapter askQuestionRecyclerAdapter;
    public int unReadNum = 0;//未读消息数

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        fetchData();
        return view;
    }

    private void initView() {
        DownloadService.startTimer(getActivity());
        scroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scroll.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQualityCourse.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCourseDatabase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAskQuestion.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        userAvatar.setRectAdius(100);

        homeQuestionAdapter = new HomeQuestionAdapter(getActivity());
        questionListListview.setAdapter(homeQuestionAdapter);
        directBroadCastRecyclerAdapter = new DirectBroadCastRecyclerAdapter(getActivity());
        recyclerView.setAdapter(directBroadCastRecyclerAdapter);
        qualityCourseRecyclerAdapter = new QualityCourseRecyclerAdapter(getActivity());
        recyclerViewQualityCourse.setAdapter(qualityCourseRecyclerAdapter);
        courseDatabaseRecyclerAdapter = new CourseDatabaseRecyclerAdapter(getActivity());
        recyclerViewCourseDatabase.setAdapter(courseDatabaseRecyclerAdapter);
        askQuestionRecyclerAdapter = new AskQuestionRecyclerAdapter(getActivity());
        recyclerViewAskQuestion.setAdapter(askQuestionRecyclerAdapter);
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().fetchHomeCourseList(SEUserManager.getInstance().getUserId(), new Callback<HomeModule>() {
            @Override
            public void success(HomeModule result, Response response) {
//                Logger.json(result);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
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
                    SEAPP.dismissAllowingStateLoss();
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
        askQuestionRecyclerAdapter.updateData(result.getResult().getUserList());
        studyTimeLengthText.setText(String.valueOf(getActivity().getSharedPreferences(CommonConstant.DAY_STUDY_PREFERENCE, MODE_PRIVATE)
                .getInt(CommonConstant.DAY_STUDY_TIME_LENGTH, 0)));
        completeNumSubjectText.setText(result.getResult().getFinishCount());
        persistText.setText(result.getResult().getStudyDay());
        defeatText.setText(result.getResult().getDefeatPercent());
        if (SEAuthManager.getInstance().getAccessUser() != null && !TextUtils.isEmpty(SEAuthManager.getInstance().getAccessUser().getAvator())) {
            String avatarUrl;
            if (SEAuthManager.getInstance().getAccessUser().getAvator().contains("http://")) {
                avatarUrl = SEAuthManager.getInstance().getAccessUser().getAvator();
            } else if (SEAuthManager.getInstance().getAccessUser().getAvator().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + SEAuthManager.getInstance().getAccessUser().getAvator();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + SEAuthManager.getInstance().getAccessUser().getAvator();
            }
            Picasso.with(getActivity()).load(avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                    .resize(60, 60).into(userAvatar);
        }
    }

    @OnClick({R.id.put_question_layout, R.id.find_teacher_layout, R.id.find_data_layout, R.id.find_data_group, R.id.show_all_question
            , R.id.show_all_direct_broadcast, R.id.show_all_quality_course, R.id.show_all_course_database, R.id.show_all_ask_question
            , R.id.study_progress_layout})
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
//                startActivity(new Intent(getActivity(), GroupListActivity.class));
                startActivity(new Intent(getActivity(), LenCloudMessageActivity.class));
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
            case R.id.show_all_ask_question:
                break;
            case R.id.study_progress_layout:
                startActivity(new Intent(getActivity(), StudyHistoryActivity.class));
                break;
        }
    }

    private void chageIndex(int position) {
        /**
         *{@link MainFragment#onEventMainThread(ChangeIndexEvent)}
         * {@link CircleFragment#onEventMainThread(ChangeIndexEvent)}
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
        onChange();
        updateUnReadMsg();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            onChange();
        }
    }

    private void onChange() {
        imgViewpagerHome.startAutoScroll();
        studyTimeLengthText.setText(String.valueOf(CommonConstant.DAY_STUDY_TIME
                + getActivity().getSharedPreferences(CommonConstant.DAY_STUDY_PREFERENCE, MODE_PRIVATE)
                .getInt(CommonConstant.DAY_STUDY_TIME_LENGTH, 0)));
    }

    @Override
    public void onPause() {
        super.onPause();
        imgViewpagerHome.stopAutoScroll();
        SEAPP.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DownloadService.destroyTimer(getActivity());
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
        updateUnReadMsg();
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (module.isLogin) {

        } else {

        }
    }

    /**
     * 收到聊天消息事件
     *
     * @param event
     */
    public void onEvent(LCIMIMTypeMessageEvent event) {
        updateUnReadMsg();
    }

    private void updateUnReadMsg() {
        unReadNum = 0;
        List<String> convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
        for (String convId : convIdList) {
            AVIMConversation conversation = LCChatKit.getInstance().getClient().getConversation(convId);
            unReadNum = unReadNum + LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
        }
        handler.sendEmptyMessage(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    chatMessageDot.setText(String.valueOf(unReadNum));
                    EventBus.getDefault().post(new ChatNewMessageEvent(unReadNum));
                    if (unReadNum > 0) {
                        chatMessageDot.setVisibility(View.VISIBLE);
                    } else {
                        chatMessageDot.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
}
