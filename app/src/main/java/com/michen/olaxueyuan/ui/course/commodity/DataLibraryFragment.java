package com.michen.olaxueyuan.ui.course.commodity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.DataLibraryFragmentListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataLibraryFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2 {
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
        intView();
        fetchData();
        return view;
    }

    private void intView() {
        type = getArguments().getInt("type", 1);
        adapter = new DataLibraryFragmentListAdapter(getActivity());
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        listview.setAdapter(adapter);
    }

    private void fetchData() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        HomeListManager.getInstance().getMaterailList(userId, materailId, PAGE_SIZE, String.valueOf(type), new Callback<MaterialListResult>() {
            @Override
            public void success(MaterialListResult materialListResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (materialListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), materialListResult.getMessage(), 2.0f);
                    } else {
                        SVProgressHUD.dismiss(getActivity());
                        listview.onRefreshComplete();
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
                    SVProgressHUD.dismiss(getActivity());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
}
