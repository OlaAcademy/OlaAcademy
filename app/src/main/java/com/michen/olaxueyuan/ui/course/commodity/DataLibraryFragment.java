package com.michen.olaxueyuan.ui.course.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.DataLibraryFragmentListAdapter;
import com.michen.olaxueyuan.ui.me.activity.CoinHomePageActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataLibraryFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, DataLibraryFragmentListAdapter.UpdateBrowseCount {
    View view;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private int type = 1;//1 数学 2 英语 3 逻辑 4 写作
    private DataLibraryFragmentListAdapter adapter;
    private static final String PAGE_SIZE = "20";//每次请求20个
    private String materailId = "";//字符串	当前页最后一条的id
    private List<MaterialListResult.ResultBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_data_library, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        intView();
        fetchData();
        return view;
    }

    private void intView() {
        type = getArguments().getInt("type", 1);
        adapter = new DataLibraryFragmentListAdapter(getActivity(), type, this);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
//        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().getMaterailList(userId, materailId, PAGE_SIZE, String.valueOf(type), new Callback<MaterialListResult>() {
            @Override
            public void success(MaterialListResult materialListResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (materialListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(getActivity(), materialListResult.getMessage());
                    } else {
//                        SVProgressHUD.dismiss(getActivity());
                        listview.onRefreshComplete();
                        if (materialListResult.getResult().size() == 0) {
                            ToastUtil.showToastShort(getActivity(), R.string.to_end);
                            return;
                        }
                        if (materialListResult.getResult().size() > 0) {
                            materailId = String.valueOf(materialListResult.getResult().get(materialListResult.getResult().size() - 1).getId());
                            list.addAll(materialListResult.getResult());
                        }
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    public void onEventMainThread(final MaterialListResult.ResultBean resultBean) {
        if (resultBean.getCourseType() == type) {
            DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.yes:
                            getCheckinStatus(resultBean.getId(), resultBean.getPrice());
                            break;
                    }
                }
            }, resultBean.getTitle(), "兑换该套题将消耗您" + resultBean.getPrice() + "欧拉币", "", "");
        }
    }

    String userId = "";

    private void getCheckinStatus(final String materailId, final int price) {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
            return;
        }
//        SVProgressHUD.showInView(getActivity(), getActivity().getString(R.string.get_coin_ing), true);
        SEAPP.showCatDialog(this);
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                    if (checkinStatusResult.getApicode() == 10000) {
                        if (checkinStatusResult.getResult().getCoin() >= price) {
                            unlockMaterial(userId, materailId);
                        } else {
                            DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (v.getId()) {
                                        case R.id.yes:
                                            Utils.jumpLoginOrNot(getActivity(), CoinHomePageActivity.class);
                                            break;
                                    }
                                }
                            }, "", "您的欧拉币余额不足，赚取积分", "", "");
                        }
                    } else {
                        ToastUtil.showToastShort(getActivity(), checkinStatusResult.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(getActivity(), "获取积分失败,请重试");
                }
            }
        });
    }

    private void unlockMaterial(String userId, String materailId) {
//        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().unlockMaterial(userId, materailId, new Callback<SimpleResult>() {
            @Override
            public void success(SimpleResult simpleResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                    if (simpleResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(getActivity(), simpleResult.getMessage());
                    } else {
                        ToastUtil.showToastShort(getActivity(), "兑换成功");
                        fetchData();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), "兑换失败，请稍后再试");
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SEAPP.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        materailId = "";
        list.clear();
        fetchData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @Override
    public void updateCount(boolean isUpdate, final int position, int subjectType) {
        if (subjectType == type && isUpdate) {
            HomeListManager.getInstance().updateBrowseCount(String.valueOf(list.get(position).getId()), new Callback<SimpleResult>() {
                @Override
                public void success(SimpleResult simpleResult, Response response) {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        if (simpleResult.getApicode() == 10000) {
                            list.get(position).setCount(list.get(position).getCount() + 1);
                            adapter.updateData(list);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }
}
