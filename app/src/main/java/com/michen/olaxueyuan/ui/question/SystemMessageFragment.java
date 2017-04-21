package com.michen.olaxueyuan.ui.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.MessageReadEvent;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.michen.olaxueyuan.protocol.result.MessageRecordResult;
import com.michen.olaxueyuan.ui.adapter.MessageListAdapter;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/12/23.
 */

public class SystemMessageFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    Context mContext;
    private String messageId = "";
    private static final String pageSize = "20";
    private List<MessageListResult.ResultEntity> list = new ArrayList<>();
    private MessageListAdapter adapter;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.common_refresh_listview, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        mContext = getActivity();
        initView();
        fetchData();
        return view;
    }

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        adapter = new MessageListAdapter(mContext);
        listview.getRefreshableView().setDivider(null);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getMessageList(userId, messageId, pageSize, new Callback<MessageListResult>() {
            @Override
            public void success(MessageListResult messageListResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (messageListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, messageListResult.getMessage(), 2.0f);
                    } else {
                        if (messageListResult.getResult().size() == 0) {
                            ToastUtil.showToastShort(mContext, R.string.to_end);
                            return;
                        }
                        if (TextUtils.isEmpty(messageId)) {
                            list.clear();
                        }
                        list.addAll(messageListResult.getResult());
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        messageId = "";
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (list.size() > 0) {
            messageId = list.get(list.size() - 1).getId() + "";
            fetchData();
        } else {
            listview.onRefreshComplete();
        }
    }

    /**
     * {@link com.michen.olaxueyuan.ui.adapter.MessageListAdapter#getView(int, View, ViewGroup)}
     *
     * @param event
     */
    public void onEventMainThread(final MessageReadEvent event) {
        if (event.isRefresh) {
            return;
        }
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().addMessageRecord(userId, event.messageIds, new Callback<MessageRecordResult>() {
            @Override
            public void success(MessageRecordResult messageRecordResult, Response response) {
                /**
                 * {@link QuestionFragment#onEventMainThread(MessageReadEvent)}
                 */
                EventBus.getDefault().post(new MessageReadEvent(true));
                switch (event.type) {//type区分跳转的界面
                    case 1:

                        break;
                    case 2://视频
                        Intent intent2 = new Intent();
                        intent2.setClass(mContext, CourseVideoActivity.class);
                        intent2.putExtra("pid", list.get(event.position).getOtherId() + "");
                        startActivity(intent2);
                        break;
                    case 3://网页,先用视频代替
                        Intent intent3 = new Intent();
                        intent3.setClass(mContext, WebViewActivity.class);
                        intent3.putExtra("mUrl", list.get(event.position).getUrl());
                        startActivity(intent3);
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
