package com.michen.olaxueyuan.ui.circle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.AndUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalHomePageActivityTwo extends AppCompatActivity {

    @Bind(R.id.head_bg)
    RoundRectImageView headBg;
    @Bind(R.id.head_image)
    RoundRectImageView headImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collect_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.scrollView)
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home_page_two);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headBg.setRectAdius(AndUtil.dip2px(this, 40));
        headImage.setRectAdius(AndUtil.dip2px(this, 37));
        setSupportActionBar(toolbar);
        setTheme(R.style.ToolbarTheme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle("");
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }
}
