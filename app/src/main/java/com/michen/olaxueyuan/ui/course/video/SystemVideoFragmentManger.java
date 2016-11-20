package com.michen.olaxueyuan.ui.course.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResultEntity;
import com.michen.olaxueyuan.ui.course.SystemVideoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 16/3/17.
 */
public class SystemVideoFragmentManger {
    private static SystemVideoFragmentManger fragmentManger;
    private SystemCatalogFragment systemCatalogFragment;
    private SystemDetailFragment systemDetailFragment;
    private HandOutVideoFragment handOutVideoFragment;
    List<Fragment> viewsCache = new ArrayList<>();
    private MainPageAdapter adapter;

    private String courseId;
    SystemCourseResultEntity resultEntity;

    public static SystemVideoFragmentManger getInstance() {
        if (fragmentManger == null) {
            fragmentManger = new SystemVideoFragmentManger();
        }
        return fragmentManger;
    }

    SystemVideoActivity activity;

    public void initView(SystemVideoActivity activity, String courseId, SystemCourseResultEntity resultEntity) {
        this.activity = activity;
        this.courseId = courseId;
        this.resultEntity = resultEntity;
        systemCatalogFragment = null;
        systemDetailFragment = null;
        handOutVideoFragment = null;
        viewsCache.clear();
        if (systemCatalogFragment == null) {
            systemCatalogFragment = new SystemCatalogFragment();
        }
        if (systemDetailFragment == null) {
            systemDetailFragment = new SystemDetailFragment();
        }
        if (handOutVideoFragment == null) {
            handOutVideoFragment = new HandOutVideoFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        bundle.putSerializable("resultEntity", resultEntity);
        systemCatalogFragment.setArguments(bundle);
        systemDetailFragment.setArguments(bundle);

        viewsCache.add(systemCatalogFragment);
        viewsCache.add(systemDetailFragment);
        viewsCache.add(handOutVideoFragment);

        adapter = new MainPageAdapter(activity.getSupportFragmentManager());
        activity.mViewPager.setAdapter(adapter);
        activity.mViewPager.setCurrentItem(0);
        activity.mViewPager.setOffscreenPageLimit(3);
        activity.mViewPager.addOnPageChangeListener(new MainOnPageChangeListener());
        selectOne();
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
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    selectOne();
                    break;
                case 1:
                    selectTwo();
                    break;
                case 2:
                    selectThree();
                    break;
            }
        }
    }

    public void selectOne() {
        activity.cataligText.setSelected(true);
        activity.detailText.setSelected(false);
        activity.handoutText.setSelected(false);

        activity.catalogIndicator.setSelected(true);
        activity.detailIndicator.setSelected(false);
        activity.handoutIndicator.setSelected(false);
    }

    public void selectTwo() {
        activity.cataligText.setSelected(false);
        activity.detailText.setSelected(true);
        activity.handoutText.setSelected(false);

        activity.catalogIndicator.setSelected(false);
        activity.detailIndicator.setSelected(true);
        activity.handoutIndicator.setSelected(false);
    }

    public void selectThree() {
        activity.cataligText.setSelected(false);
        activity.detailText.setSelected(false);
        activity.handoutText.setSelected(true);

        activity.catalogIndicator.setSelected(false);
        activity.detailIndicator.setSelected(false);
        activity.handoutIndicator.setSelected(true);
        activity.setDownloadPdfPosition(activity.pdfPosition);
    }

}
