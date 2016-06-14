package com.snail.olaxueyuan.ui.circle;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.RoundRectImageView;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.snail.olaxueyuan.protocol.result.OLaCircleModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.course.CourseVideoActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
public class CircleFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    List<OLaCircleModule.ResultEntity> list = new ArrayList<>();
    TitleManager titleManager;
    View rootView;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.listview)
    PullToRefreshListView listview;

    CircleAdapter adapter;

    public CircleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_circle, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        fetchData();
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager(R.string.ola_circle, this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.ic_sub_subject);
        adapter = new CircleAdapter();
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().getHistotyList(new Callback<OLaCircleModule>() {
            @Override
            public void success(OLaCircleModule oLaCircleModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
                listview.onRefreshComplete();
//                Logger.json(oLaCircleModule);
                if (oLaCircleModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), oLaCircleModule.getMessage(), 2.0f);
                } else {
                    list.clear();
//                    Logger.json(oLaCircleModule);
                    list.addAll(oLaCircleModule.getResult());
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    adapter.updateList(module);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    @OnClick({R.id.right_response})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_response:
                ToastUtil.showShortToast(getActivity(), "我是右上角提醒");
                break;
        }
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
                convertView = View.inflate(getActivity(), R.layout.fragment_circle_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.avatar.setRectAdius(100);
            holder.title.setText(list.get(position).getUserName());
            if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
                Picasso.with(getActivity()).load(list.get(position).getUserAvatar()).placeholder(R.drawable.ic_default_avatar)
                        .error(R.drawable.ic_default_avatar).resize(Utils.dip2px(getActivity(), 50), Utils.dip2px(getActivity(), 50)).into(holder.avatar);
            }
            holder.time.setText(getActivity().getString(R.string.study_record, list.get(position).getTime()));
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
