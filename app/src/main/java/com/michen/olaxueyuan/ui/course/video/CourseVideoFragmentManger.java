package com.michen.olaxueyuan.ui.course.video;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.michen.olaxueyuan.ui.course.CourseVideoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 16/3/17.
 */
public class CourseVideoFragmentManger {
    private static CourseVideoFragmentManger fragmentManger;
    private CatalogVideoFragment catalogVideoFragment;
    private HandOutVideoFragment handOutVideoFragment;
    List<Fragment> viewsCache = new ArrayList<Fragment>();
    private MainPageAdapter adapter;

    public static CourseVideoFragmentManger getInstance() {
        if (fragmentManger == null) {
            fragmentManger = new CourseVideoFragmentManger();
        }
        return fragmentManger;
    }

    CourseVideoActivity activity;

    public void initView(CourseVideoActivity activity) {
        this.activity = activity;
        catalogVideoFragment = null;
        handOutVideoFragment = null;
        viewsCache.clear();

        if (catalogVideoFragment == null) {
            catalogVideoFragment = new CatalogVideoFragment();
        }
        if (handOutVideoFragment == null) {
            handOutVideoFragment = new HandOutVideoFragment();
        }
        viewsCache.add(catalogVideoFragment);
        viewsCache.add(handOutVideoFragment);

        adapter = new MainPageAdapter(activity.getSupportFragmentManager());
        activity.mViewPager.setAdapter(adapter);
        activity.mViewPager.setCurrentItem(0);
        activity.mViewPager.setOffscreenPageLimit(3);
        activity.mViewPager.addOnPageChangeListener(new MainOnPageChangeListener());
    }

    class MainPageAdapter extends FragmentPagerAdapter {

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return viewsCache.get(arg0);
        }

        @Override
        public int getCount() {
            return viewsCache.size();
        }
    }

    class MainOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public MainOnPageChangeListener() {
            onPageSelected(0);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0) {
                selectOne();
            } else if (arg0 == 1) {
                selectTwo();
            }
        }
    }

    public void selectOne() {
        activity.cataligText.setSelected(true);
        activity.handoutText.setSelected(false);

        activity.catalogIndicator.setSelected(true);
        activity.handoutIndicator.setSelected(false);
    }

    public void selectTwo() {
        activity.cataligText.setSelected(false);
        activity.handoutText.setSelected(true);

        activity.catalogIndicator.setSelected(false);
        activity.handoutIndicator.setSelected(true);
        activity.setDownloadPdfPosition(activity.pdfPosition);
    }

}
