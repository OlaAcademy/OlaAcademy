package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.ui.activity.SuperActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupNoticeActivity extends SuperActivity {
    TitleManager titleManger;
    @Bind(R.id.left_text)
    TextView leftText;
    @Bind(R.id.right_texts)
    TextView rightTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notice);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        titleManger = new TitleManager(this, "群公告", this, false);
        titleManger.changeText(TitleManager.LEFT_INDEX_TEXT, "取消");
        titleManger.changeTextColor(this, TitleManager.LEFT_INDEX_TEXT, R.color.title_edge_blue);
        titleManger.changeText(TitleManager.RIGHT_INDEX_TEXT, "保存");
        titleManger.changeTextColor(this, TitleManager.RIGHT_INDEX_TEXT, R.color.title_edge_blue);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.left_text, R.id.right_texts})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_text:
                finish();
                break;
            case R.id.right_texts:
                ToastUtil.showToastShort(this, "保存");
                break;
        }
    }
}
