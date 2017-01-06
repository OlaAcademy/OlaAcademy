package com.michen.olaxueyuan.ui.home.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.AutoScrollViewPager;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.course.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 16/3/4.
 * 首页头部图片
 */
public class HeaderImgeManager {
    Context context;
    AutoScrollViewPager imgViewpager;
    LinearLayout pointerLayout;
    int count = 0;
    int index = 0;
    List<HomeModule.ResultBean.BannerListBean> mBanners = new ArrayList<>();
    int mCurrentPosterIndex = 0;
    List<ImageView> points = new ArrayList<>();// 标记点集合
    private static final int DELAY_TIME = 4000;//循环间隔

    public HeaderImgeManager() {
    }

    public HeaderImgeManager(Context context, AutoScrollViewPager imgViewpager, LinearLayout pointerLayout, List<HomeModule.ResultBean.BannerListBean> mBanners) {
        this.context = context;
        this.imgViewpager = imgViewpager;
        this.pointerLayout = pointerLayout;
        initHeaderImgView();
        if (this.mBanners != null) {
            this.mBanners.clear();
        }
        this.mBanners = mBanners;
        mCurrentPosterIndex = 0;
        initPoints();
        initPoster();
    }

    public void initHeaderImgView() {
        int width = Utils.getScreenWidth(context);
        int height = width * 300 / 750;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imgViewpager.getLayoutParams();
        layoutParams.height = height;
        imgViewpager.setLayoutParams(layoutParams);
    }

    public void setData(List<HomeModule.ResultBean.BannerListBean> mBanners) {
        if (this.mBanners != null) {
            this.mBanners.clear();
        }
        this.mBanners = mBanners;
        mCurrentPosterIndex = 0;
        initPoints();
        initPoster();
    }

    private void initPoints() {
        count = mBanners.size();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 0, 8, 0);
        pointerLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            // 添加标记点
            ImageView point = new ImageView(context);
            if (i == index % count) {
                point.setBackgroundResource(R.drawable.feature_point_cur);
            } else {
                point.setBackgroundResource(R.drawable.feature_point);
            }
            point.setLayoutParams(lp);
            points.add(point);
            pointerLayout.addView(point);
        }
    }

    private void initPoster() {
        imgViewpager.setAdapter(new PosterPagerAdapter(context));
        imgViewpager.setCurrentItem(mCurrentPosterIndex);
        imgViewpager.setInterval(DELAY_TIME);
        imgViewpager.addOnPageChangeListener(new PosterPageChange());
        imgViewpager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
        imgViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        imgViewpager.stopAutoScroll();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        imgViewpager.startAutoScroll();
                        break;
                    case MotionEvent.ACTION_UP:
                        imgViewpager.startAutoScroll();
                        break;
                    default:
                        break;
                }
                return false;
            }

        });
    }

    private class PosterPagerAdapter extends PagerAdapter {
        Context context;

        public PosterPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mBanners.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            // TODO 调整图片大小
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            Picasso.with(context).load(mBanners.get(position % count).getPic()).config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_index).error(R.drawable.default_index).into(imageView);

            ((ViewPager) container).addView(imageView);

            imageView.setOnClickListener(new PosterClickListener(position % count));

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private class PosterClickListener implements View.OnClickListener {

        private int position;
        private final String detail = "#p=detail";

        public PosterClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            imgViewpager.stopAutoScroll();
            HomeModule.ResultBean.BannerListBean banner = mBanners.get(position);
            switch (banner.getType()) {
                default:
                case 1://web页
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("textUrl", banner.getUrl());
                    context.startActivity(intent);
                    break;
                case 2://课程页面
                    Intent intent2 = new Intent(context, CourseVideoActivity.class);
                    intent2.putExtra("pid", String.valueOf(banner.getObjectId()));
                    context.startActivity(intent2);
                    break;
                case 3://精品课
                    break;
                case 4://报名
                    break;
            }
        }
    }

    private class PosterPageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosterIndex = position;
            for (int i = 0; i < count; i++) {
                points.get(i).setBackgroundResource(R.drawable.feature_point);
            }
            points.get(position % count).setBackgroundResource(R.drawable.feature_point_cur);
        }
    }
}
