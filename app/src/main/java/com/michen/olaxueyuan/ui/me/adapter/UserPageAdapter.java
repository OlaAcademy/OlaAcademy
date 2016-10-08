package com.michen.olaxueyuan.ui.me.adapter;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.me.subfragment.UserBuyGoodsFragment;
import com.michen.olaxueyuan.ui.me.subfragment.UserCourseCollectFragment;
import com.michen.olaxueyuan.ui.me.subfragment.UserKnowledgeFragment;
import com.michen.olaxueyuan.ui.me.subfragment.UserVipFragment;

/**
 * Created by mingge on 2016/5/20.
 */
@Deprecated
public class UserPageAdapter extends FragmentStatePagerAdapter {
    private UserKnowledgeFragment userKnowledgeFragment;
    private UserVipFragment userVipFragment;
    private UserCourseCollectFragment userCourseCollectFragment;
    private UserBuyGoodsFragment userBuyGoodsFragment;

    public UserPageAdapter(FragmentManager fm) {
        super(fm);
        construct();
    }

    @Override
    public android.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return userKnowledgeFragment;
            case 1:
                return userVipFragment;
            case 2:
                return userCourseCollectFragment;
            case 3:
                return userBuyGoodsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    private void construct() {
        userKnowledgeFragment = new UserKnowledgeFragment();
        userCourseCollectFragment = new UserCourseCollectFragment();
        userVipFragment = new UserVipFragment();
        userBuyGoodsFragment = new UserBuyGoodsFragment();
    }
}
