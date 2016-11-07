package com.michen.olaxueyuan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.michen.olaxueyuan.common.manager.Logger;
import com.umeng.analytics.MobclickAgent;

import java.io.InputStream;
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
    private int mPageCount = 4;
    private String[] imageNameArray = {"guide_page_one.jpg", "guide_page_two.jpg", "guide_page_three.jpg", "guide_page_four.jpg"};
    private SharedPreferences mSp;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        mContext = this;
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

    private void jumpToMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
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

    class PosterPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPageCount;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_guide, null);
//            View view1=new View(mContext);
            View bgView = view.findViewById(R.id.rl_bg);
            try {
                InputStream inputStream = mContext.getAssets().open(imageNameArray[position]);
                if (Build.VERSION.SDK_INT >= 16) {
                    bgView.setBackground(new BitmapDrawable(null, BitmapFactory.decodeStream(inputStream)));
                } else {
                    bgView.setBackgroundDrawable(new BitmapDrawable(null, BitmapFactory.decodeStream(inputStream)));
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                jumpToMainActivity();
            }

            bgView.setOnTouchListener(new View.OnTouchListener() {
                private float x, ux;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (position == mPageCount - 1) {
                        // 当按下时处理
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // 获取按下时的x轴坐标
                            x = event.getX();
                            Logger.e("x==" + x);
                            jumpToMainActivity();
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
                            // 获取松开时的x坐标
                            ux = event.getX();
                            Logger.e("ux==" + ux);
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            Logger.e("x " + x + "- ux===" + (x - ux));
//                            if (x - ux > 1) {
//                            jumpToMainActivity();
//                            }
                        } else {// 其他模式
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
