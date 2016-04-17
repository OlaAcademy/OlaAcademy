package com.snail.olaxueyuan.ui.course.turtor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.manager.MCOrgManager;
import com.snail.olaxueyuan.protocol.model.MCOrgInfo;
import com.snail.olaxueyuan.protocol.result.MCCommonResult;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tree.love.animtabsview.AnimTabsView;

public class OrganizationInfoActivity extends SEBaseActivity {

    private AnimTabsView mTabsView;
    private ImageView orgAvatar;

    private MCOrgInfo orgInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_info);

        mTabsView = (AnimTabsView) findViewById(R.id.publiclisten_tab);
        mTabsView.addItem("简介");
        mTabsView.addItem("名师");
        mTabsView.addItem("报名");
        orgAvatar = (ImageView) findViewById(R.id.orgAvatar);

        orgInfo = (MCOrgInfo)getIntent().getSerializableExtra(TurtorActivity.ORG_KEY);
        setTitleText(orgInfo.name);
        updateAttendCount();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(orgInfo.address, orgAvatar, options);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, OrgInfoFragment.newInstance(orgInfo)).commit();
        }

        mTabsView.setOnAnimTabsItemViewChangeListener(new AnimTabsView.IAnimTabsItemViewChangeListener() {
            @Override
            public void onChange(AnimTabsView tabsView, int targetPosition, int currentPosition) {
                switch (targetPosition) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, OrgInfoFragment.newInstance(orgInfo)).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, OrgTeacherFragment.newInstance(orgInfo)).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, OrgEnrollFragment.newInstance(orgInfo)).commit();
                        break;
                }
            }
        });
    }

    // 更新机构关注人数
    private void updateAttendCount(){
        MCOrgManager om = MCOrgManager.getInstance();
        om.updateAttendCount(orgInfo.id, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
