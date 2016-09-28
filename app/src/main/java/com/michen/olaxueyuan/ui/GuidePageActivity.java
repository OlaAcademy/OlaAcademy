package com.michen.olaxueyuan.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuidePageActivity extends Activity {

    @Bind(R.id.viewPager_layout)
    ViewPager mViewPager;
    @Bind(R.id.pointer_layout)
    LinearLayout mPointerLayout;
    /**
     * 标记点集合
     */
    private List<ImageView> points = new ArrayList<>();
    private int mCurrentPosterIndex = 0;
    private int mPageCount = 3;
    private int[] imageArray = {R.drawable.guide_page_one, R.drawable.guide_page_two, R.drawable.guide_page_three};
    //            ,R.drawable.bg_index_fourth};
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
        mSp = getSharedPreferences(IndexActivity.SP_FILENAME_CONFIG, MODE_PRIVATE);
        initPointers();
        initViewPager();
    }

    private void initPointers() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 25f, getResources().getDisplayMetrics());
        lp.setMargins(margin, 0, margin, 0);
        mPointerLayout.removeAllViews();
        for (int i = 0; i < mPageCount; i++) {
            // 添加标记点
            ImageView point = new ImageView(this);
            if (i == mCurrentPosterIndex) {
                point.setBackgroundResource(R.drawable.solid_icon);
            } else {
                point.setBackgroundResource(R.drawable.empty_icon);
            }
            point.setLayoutParams(lp);

            points.add(point);
            mPointerLayout.addView(point);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new PosterPagerAdapter());
        mViewPager.setCurrentItem(mCurrentPosterIndex);
        mViewPager.addOnPageChangeListener(new PosterPageChange());
    }

    class PosterPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPageCount;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(GuidePageActivity.this).inflate(R.layout.item_viewpager_guide, null);
//            View view1=new View(GuidePageActivity.this);
            View bgView = view.findViewById(R.id.rl_bg);
            bgView.setBackgroundResource(imageArray[position]);
            bgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == mPageCount - 1) {
                        // 进入主页面。
                        startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
                        GuidePageActivity.this.finish();
                        try {
                            mSp.edit().putBoolean(IndexActivity.SP_PARAMSNAME_ISGUIDE, true).apply();
                            int nowVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                            mSp.edit().putInt(IndexActivity.SP_VERSION_CODE, nowVersionCode).apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bgView.setOnTouchListener(new View.OnTouchListener() {
                private float x, ux;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (position == mPageCount - 1) {
                        // 当按下时处理
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // 设置背景为选中状态
                            // 获取按下时的x轴坐标
                            x = event.getX();
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
                            // 设置背景为未选中正常状态
                            // 获取松开时的x坐标
                            ux = event.getX();
                            // 判断当前项中按钮控件不为空时
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {// 当滑动时背景为选中状态
                            if (x - ux > 10) {
                                Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
                                startActivity(intent);
                                try {
                                    mSp.edit().putBoolean(IndexActivity.SP_PARAMSNAME_ISGUIDE, true).apply();
                                    int nowVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                                    mSp.edit().putInt(IndexActivity.SP_VERSION_CODE, nowVersionCode).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }
                        } else {// 其他模式
                            // 设置背景为未选中正常状态
                        }
                    }
                    return true;
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    class PosterPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosterIndex = position;
            for (int i = 0; i < mPageCount; i++) {
                points.get(i).setBackgroundResource(R.drawable.empty_icon);
            }
            points.get(position).setBackgroundResource(R.drawable.solid_icon);
        }

    }

}