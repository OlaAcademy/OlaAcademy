package com.michen.olaxueyuan.ui.course.video;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.ui.adapter.CourseVideoListAdapter;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 2016/9/19.
 */
public class CatalogVideoFragment extends BaseFragment {
    View view;
    @Bind(R.id.listview)
    ListView listview;
    private CourseVideoListAdapter adapter;
    private List<CourseVideoResult.ResultBean.VideoListBean> videoArrayList;
    CourseVideoResult courseVideoResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.catalog_video_fragment, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    private void initView() {
        adapter = new CourseVideoListAdapter(getActivity());
        listview.setAdapter(adapter);
        adapter.updateData(videoArrayList);
    }

    private void initListViewItemClick() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (videoArrayList != null && videoArrayList.size() > 0) {
                    if (videoArrayList.get(position).getIsfree() == 0) {
                        if (!SEAuthManager.getInstance().isAuthenticated()) {
                            startActivity(new Intent(getActivity(), UserLoginActivity.class));
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("友情提示")
                                    .setMessage("购买会员后即可拥有")
                                    .setPositiveButton("去购买", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getActivity(), BuyVipActivity.class));
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .show();
                        }
                        return;
                    }
                    for (int i = 0; i < videoArrayList.size(); i++) {
                        videoArrayList.get(i).setSelected(false);
                    }
                    videoArrayList.get(position).setSelected(true);
                    adapter.updateData(videoArrayList);
                }
            }
        });
    }

    public void onEventMainThread(CourseVideoResult result){
        courseVideoResult = result;
        videoArrayList = result.getResult().getVideoList();
        if (videoArrayList != null && videoArrayList.size() > 0) {
            videoArrayList.get(0).setSelected(true);
            adapter = new CourseVideoListAdapter(getActivity());
            listview.setAdapter(adapter);
            initListViewItemClick();
            adapter.updateData(videoArrayList);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
