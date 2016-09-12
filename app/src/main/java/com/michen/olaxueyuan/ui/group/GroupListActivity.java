package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.adapter.GroupListViewPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 群列表
 */
public class GroupListActivity extends SuperActivity {

    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.maths_text)
    TextView mathsText;
    @Bind(R.id.maths_indicator)
    View mathsIndicator;
    @Bind(R.id.maths_layout)
    RelativeLayout mathsLayout;
    @Bind(R.id.english_text)
    TextView englishText;
    @Bind(R.id.english_indicator)
    View englishIndicator;
    @Bind(R.id.english_layout)
    RelativeLayout englishLayout;
    @Bind(R.id.logic_text)
    TextView logicText;
    @Bind(R.id.logic_indicator)
    View logicIndicator;
    @Bind(R.id.logic_layout)
    RelativeLayout logicLayout;
    @Bind(R.id.writing_text)
    TextView writingText;
    @Bind(R.id.writing_indicator)
    View writingIndicator;
    @Bind(R.id.writing_layout)
    RelativeLayout writingLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    TitleManager titleManager;
    GroupListViewPagerAdapter groupListViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        titleManager = new TitleManager(this, "群列表", this, true);
//        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.message_tip_icon);
        groupListViewPagerAdapter = new GroupListViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(groupListViewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        mathsText.setSelected(true);
        mathsIndicator.setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeTab(true, false, false, false, false, 0);
                        break;
                    case 1:
                        changeTab(false, true, false, false, false, 1);
                        break;
                    case 2:
                        changeTab(false, false, true, false, false, 2);
                        break;
                    case 3:
                        changeTab(false, false, false, true, false, 3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.maths_layout, R.id.english_layout, R.id.logic_layout, R.id.writing_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
//            case R.id.right_response:
//                ToastUtil.showToastShort(this, "我是右上角图标");
//                break;
            case R.id.maths_layout:
                changeTab(true, false, false, false, true, 0);
                break;
            case R.id.english_layout:
                changeTab(false, true, false, false, true, 1);
                break;
            case R.id.logic_layout:
                changeTab(false, false, true, false, true, 2);
                break;
            case R.id.writing_layout:
                changeTab(false, false, false, true, true, 3);
                break;
        }
    }

    private void changeTab(boolean maths, boolean english, boolean logic, boolean writing, boolean setCurrentItem, int position) {
        mathsText.setSelected(maths);
        mathsIndicator.setSelected(maths);
        englishText.setSelected(english);
        englishIndicator.setSelected(english);
        logicText.setSelected(logic);
        logicIndicator.setSelected(logic);
        writingText.setSelected(writing);
        writingIndicator.setSelected(writing);
        if (setCurrentItem) {
            viewPager.setCurrentItem(position);
        }
    }
}
