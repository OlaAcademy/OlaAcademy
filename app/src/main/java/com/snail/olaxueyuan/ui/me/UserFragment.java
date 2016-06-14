package com.snail.olaxueyuan.ui.me;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.RoundRectImageView;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.me.adapter.UserPageAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingge on 2016/5/20.
 */
public class UserFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.is_vip)
    TextView isVip;
    @Bind(R.id.remain_days)
    TextView remainDays;
    @Bind(R.id.knowledge_bottom_indicator)
    ImageView knowledgeBottomIndicator;
    @Bind(R.id.knowledge_layout)
    RelativeLayout knowledgeLayout;
    @Bind(R.id.course_collect_bottom_indicator)
    ImageView courseCollectBottomIndicator;
    @Bind(R.id.course_collect_layout)
    RelativeLayout courseCollectLayout;
    @Bind(R.id.vip_bottom_indicator)
    ImageView vipBottomIndicator;
    @Bind(R.id.vip_layout)
    RelativeLayout vipLayout;
    @Bind(R.id.download_bottom_indicator)
    ImageView downloadBottomIndicator;
    @Bind(R.id.download_layout)
    RelativeLayout downloadLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private UserPageAdapter userPageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        avatar.setRectAdius(100);
        titleTv.setText(R.string.me);
        leftIcon.setVisibility(View.VISIBLE);
        rightResponse.setVisibility(View.VISIBLE);

        userPageAdapter = new UserPageAdapter(getActivity().getFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(userPageAdapter);
        viewPager.setOnPageChangeListener(new ViewPagerListener());
        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.left_icon, R.id.right_response, R.id.knowledge_layout, R.id.course_collect_layout, R.id.vip_layout, R.id.download_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_icon:
                ToastUtil.showShortToast(getActivity(), "我是左上角icon");
                break;
            case R.id.right_response:
                ToastUtil.showShortToast(getActivity(), "我是右上角icon");
                break;
            case R.id.knowledge_layout:
                viewPager.setCurrentItem(0);
                changeTitleTab(0);
                break;
            case R.id.vip_layout:
                viewPager.setCurrentItem(1);
                changeTitleTab(1);
                break;
            case R.id.course_collect_layout:
                viewPager.setCurrentItem(2);
                changeTitleTab(2);
                break;
            case R.id.download_layout:
                viewPager.setCurrentItem(3);
                changeTitleTab(3);
                break;
        }
    }

    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            changeTitleTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private void changeTitleTab(int position) {
        knowledgeBottomIndicator.setVisibility(View.GONE);
        courseCollectBottomIndicator.setVisibility(View.GONE);
        vipBottomIndicator.setVisibility(View.GONE);
        downloadBottomIndicator.setVisibility(View.GONE);
        switch (position) {
            case 0:
                knowledgeBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 1:
                vipBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 2:
                courseCollectBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 3:
                downloadBottomIndicator.setVisibility(View.VISIBLE);
                break;
            default:
                knowledgeBottomIndicator.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
