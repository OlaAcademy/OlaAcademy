package com.michen.olaxueyuan.ui.course.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.protocol.result.SystemVideoResult;
import com.michen.olaxueyuan.ui.adapter.SystemVideoListAdapter;
import com.michen.olaxueyuan.ui.course.PaySystemVideoActivity;
import com.michen.olaxueyuan.ui.course.SystemVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/11/11.
 */

public class SystemCatalogFragment extends BaseFragment {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.rmb)
    TextView rmb;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.paynum)
    TextView paynum;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.bottom_view)
    RelativeLayout bottomView;
    private SystemVideoListAdapter adapter;
    private String courseId;
    private String userId;
    private List<SystemVideoResult.ResultBean> videoArrayList;
    SystemCourseResult.ResultEntity resultEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.catalog_video_fragment, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        courseId = getArguments().getString("courseId");
        resultEntity = (SystemCourseResult.ResultEntity) getArguments().getSerializable("resultEntity");
        adapter = new SystemVideoListAdapter(mContext);
        listview.setAdapter(adapter);
        performRefresh();
    }

    public void performRefresh() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            startActivity(new Intent(mContext, UserLoginActivity.class));
            return;
        }
        Logger.e("courseId==" + courseId);
        SECourseManager.getInstance().getVideoList(courseId, userId, new Callback<SystemVideoResult>() {
            @Override
            public void success(SystemVideoResult result, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (result.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.getMessage(), 2.0f);
                    } else {
                        videoArrayList = result.getResult();
                        if (videoArrayList != null && videoArrayList.size() > 0) {
                            videoArrayList.get(0).setSelected(true);
                            adapter = new SystemVideoListAdapter(getActivity());
                            listview.setAdapter(adapter);
                            adapter.updateData(videoArrayList);
                            initListViewItemClick();
                            ((SystemVideoActivity) getActivity()).playVideo(videoArrayList.get(0).getAddress());
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void initListViewItemClick() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (videoArrayList != null && videoArrayList.size() > 0) {
                    for (int i = 0; i < videoArrayList.size(); i++) {
                        videoArrayList.get(i).setSelected(false);
                    }
                    videoArrayList.get(position).setSelected(true);
                    if (!SEAuthManager.getInstance().isAuthenticated()) {
                        loginDialog();
                    } else {
                        if (((SystemVideoActivity) getActivity()).hasBuyGoods) {
                            ((SystemVideoActivity) getActivity()).playVideo(videoArrayList.get(position).getAddress());
                            ((SystemVideoActivity) getActivity()).pdfPosition = position;
                            adapter.updateData(videoArrayList);
                        } else {
                            jumpToPayOrder();
                        }
                    }
                }
            }
        });
    }

    private void loginDialog() {
        DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yes:
                        startActivity(new Intent(getActivity(), UserLoginActivity.class));
                        break;
                }
            }
        }, "", getString(R.string.to_login), "", "");
    }

    private void jumpToPayOrder() {
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            loginDialog();
        } else {
            if (resultEntity != null) {
                Intent intent = new Intent(getActivity(), PaySystemVideoActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("ResultEntity", resultEntity);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                jumpToPayOrder();
                break;
        }
    }
}
