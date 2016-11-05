package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.AttendGroupResult;
import com.michen.olaxueyuan.protocol.result.UserGroupListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.group.data.GroupListAdapter;
import com.michen.olaxueyuan.ui.group.data.JoinGroupEvent;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/9/6.
 */
public class GroupListFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View view;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private int type = 1;//1 数学 2 英语 3 逻辑 4 写作
    private GroupListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.grouplist_fragment, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        intView();
        fetchData();
        return view;
    }

    private void intView() {
        type = getArguments().getInt("type", 1);
        adapter = new GroupListAdapter(getActivity(),type);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        TeacherHomeManager.getInstance().getUserGroupList(userId, String.valueOf(type), new Callback<UserGroupListResult>() {
            @Override
            public void success(UserGroupListResult userGroupListResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (userGroupListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), userGroupListResult.getMessage(), 2.0f);
                    } else {
                        SVProgressHUD.dismiss(getActivity());
                        listview.onRefreshComplete();
                        adapter.updateData(userGroupListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                }
            }
        });
    }

    /**
     * {@link GroupListAdapter#getView(int, View, ViewGroup)}
     * @param joinGroupEvent
     */
    public void onEventMainThread(JoinGroupEvent joinGroupEvent) {
        if (joinGroupEvent.isValid&&type==joinGroupEvent.subjectType) {
            attendGroup(joinGroupEvent.groupId, String.valueOf(joinGroupEvent.type));
        }
    }

    private void attendGroup(String groupId, String type) {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        TeacherHomeManager.getInstance().attendGroup(userId, groupId, type, new Callback<AttendGroupResult>() {
            @Override
            public void success(AttendGroupResult attendGroupResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (attendGroupResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), attendGroupResult.getMessage(), 2.0f);
                    } else {
                        SVProgressHUD.dismiss(getActivity());
                        fetchData();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SVProgressHUD.dismiss(getActivity());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
