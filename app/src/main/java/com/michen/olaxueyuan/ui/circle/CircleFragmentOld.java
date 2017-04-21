package com.michen.olaxueyuan.ui.circle;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CircleFragmentOld extends SuperFragment implements PullToRefreshBase.OnRefreshListener2 {
    List<OLaCircleOldModule.ResultEntity> list = new ArrayList<>();
    TitleManager titleManager;
    View rootView;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.listview)
    PullToRefreshListView listview;

    CircleAdapter adapter;

    public CircleFragmentOld() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_circle_old, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        fetchData("", "10");
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager("学习记录", this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.ic_circle_add);
        titleManager.HideImageViews(TitleManager.RIGHT_INDEX_RESPONSE);
        adapter = new CircleAdapter();
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.getRefreshableView().setDivider(null);
        listview.setOnRefreshListener(this);
    }

    private void fetchData(final String videoId, String pageSize) {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().getHistotyList(videoId, pageSize, new Callback<OLaCircleOldModule>() {
            @Override
            public void success(OLaCircleOldModule oLaCircleModule, Response response) {
                SEAPP.dismissAllowingStateLoss();
                listview.onRefreshComplete();
//                Logger.json(oLaCircleModule);
                if (oLaCircleModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), oLaCircleModule.getMessage(), 2.0f);
                } else {
//                    Logger.json(oLaCircleModule);
                    if (videoId.equals("")) {
                        list.clear();
                        listview.setAdapter(adapter);
                    }
                    list.addAll(oLaCircleModule.getResult());
                    adapter.notifyDataSetChanged();
//                    adapter.updateList(module);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    listview.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    @OnClick({R.id.right_response})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_response:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                Intent intent = new Intent(getActivity(), DeployPostActivity.class);
                startActivity(intent);
                break;
        }
    }

    // EventBus 回调
    public void onEventMainThread(Boolean addSuccess) {
        if (addSuccess) {
            fetchData("", "10");
        }
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
        fetchData("", "10");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        String videoId = "";
        if (list.size() > 0) {
        }
        videoId = list.get(list.size() - 1).getLogId() + "";
        fetchData(videoId, "10");
    }

    class CircleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.fragment_circle_old_listview_item_two, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.avatar.setRectAdius(100);
            holder.title.setText(list.get(position).getUserName());

            if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
                String avatarUrl = "";
                if (list.get(position).getUserAvatar().contains("http://")) {
                    avatarUrl = list.get(position).getUserAvatar();
                } else if (list.get(position).getUserAvatar().contains(".")) {
                    avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
                } else {
                    avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
                }
                Picasso.with(getActivity()).load(avatarUrl)
                        .placeholder(R.drawable.ic_default_avatar)
                        .error(R.drawable.ic_default_avatar)
                        .resize(Utils.dip2px(getActivity(), 50), Utils.dip2px(getActivity(), 50))
                        .into(holder.avatar);
            } else {
                holder.avatar.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_default_avatar));
            }
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureUtils.viewPictures(getActivity(), list.get(position).getUserAvatar());
                }
            });
//            holder.time.setText(getActivity().getString(R.string.study_record, list.get(position).getTime()));
            holder.time.setText(DateUtils.formatTime(list.get(position).getTime()));
            holder.studyName.setText(list.get(position).getVideoName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CourseVideoActivity.class);
                    intent.putExtra("pid", list.get(position).getCourseId());
                    getActivity().startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.avatar)
            RoundRectImageView avatar;
            @Bind(R.id.title)
            TextView title;
            @Bind(R.id.time)
            TextView time;
            @Bind(R.id.study)
            TextView study;
            @Bind(R.id.study_name)
            TextView studyName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
