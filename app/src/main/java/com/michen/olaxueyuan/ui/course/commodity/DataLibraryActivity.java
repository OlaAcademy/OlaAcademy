package com.michen.olaxueyuan.ui.course.commodity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.DataLibraryViewPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataLibraryActivity extends SEBaseActivity {
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
    DataLibraryViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_library);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitleText("我找资料");
        setRightImage(R.drawable.data_library_icon);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommodityView();
            }
        });
        viewPagerAdapter = new DataLibraryViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
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

    @OnClick({R.id.maths_layout, R.id.english_layout, R.id.logic_layout, R.id.writing_layout})
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void showCommodityView() {
        Intent commodityIntent = new Intent(DataLibraryActivity.this, CommodityActivity.class);
        commodityIntent.putExtra("title", "资料库");
        commodityIntent.putExtra("type", "2");
        startActivity(commodityIntent);
    }
}
