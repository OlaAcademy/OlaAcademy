package com.michen.olaxueyuan.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.stickview.StickyListHeadersListView;
import com.michen.olaxueyuan.common.stickview.SwipeRefreshLayout;
import com.michen.olaxueyuan.ui.SuperFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 2016/8/31.
 */
public class TeacherHomeFragment extends SuperFragment implements AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderOffsetChangedListener, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{
    View view;
    @Bind(R.id.listview)
    StickyListHeadersListView listview;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.empty)
    TextView empty;
    @Bind(R.id.teacher_view)
    RelativeLayout teacherView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
        ButterKnife.bind(this, view);
        return view;
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
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {

    }
}
