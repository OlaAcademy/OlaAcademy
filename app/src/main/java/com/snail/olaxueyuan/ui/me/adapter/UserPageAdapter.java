package com.snail.olaxueyuan.ui.me.adapter;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.snail.olaxueyuan.ui.me.UserDownloadFragment;
import com.snail.olaxueyuan.ui.me.UserCourseCollectFragment;
import com.snail.olaxueyuan.ui.me.UserKnowledgeFragment;
import com.snail.olaxueyuan.ui.me.UserVipFragment;

/**
 * Created by mingge on 2016/5/20.
 */
public class UserPageAdapter extends FragmentStatePagerAdapter {
    private UserKnowledgeFragment userKnowledgeFragment;
    private UserVipFragment userVipFragment;
    private UserCourseCollectFragment userCourseCollectFragment;
    private UserDownloadFragment userdownloadFragment;

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
                return userdownloadFragment;
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
        userdownloadFragment = new UserDownloadFragment();
    }
}
