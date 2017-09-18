package com.michen.olaxueyuan.ui.plan;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.PlanViewPagerAdapter;
import com.michen.olaxueyuan.ui.group.GroupListActivity;
import com.michen.olaxueyuan.ui.question.QuestionFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangfangming on 2017/8/12.
 * 计划和题库的父fragment
 */
public class PlanHomeFragment extends SuperFragment {
	View rootView;
	@Bind(R.id.plan_text)
	TextView planText;
	@Bind(R.id.plan_indicator)
	View planIndicator;
	@Bind(R.id.plan_layout)
	RelativeLayout planLayout;
	@Bind(R.id.question_text)
	TextView questionText;
	@Bind(R.id.question_indicator)
	View questionIndicator;
	@Bind(R.id.question_layout)
	RelativeLayout questionLayout;
	@Bind(R.id.view_pager)
	ViewPager viewPager;

	private PlanViewPagerAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		} else {
			rootView = View.inflate(getActivity(), R.layout.fragment_plan_home, null);
			ButterKnife.bind(this, rootView);
			initView();
			return rootView;
		}
	}

	private void initView() {
		changeTab(true, false, 0);
		FragmentManager fragmentManager;
		if (Build.VERSION.SDK_INT >= 17) {
			fragmentManager = getChildFragmentManager();
		} else {
			fragmentManager = getFragmentManager();
		}
		adapter = new PlanViewPagerAdapter(fragmentManager);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
					case 0:
						changeTab(true, false, 0);
						break;
					case 1:
						changeTab(false, true, 1);
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.plan_layout, R.id.question_layout, R.id.btn_group})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.plan_layout:
				changeTab(true, false, 0);
				break;
			case R.id.question_layout:
				changeTab(false, true, 1);
				break;
			case R.id.btn_group:
				Utils.jumpLoginOrNot(getActivity(), GroupListActivity.class);
				break;
		}
	}

	private void changeTab(boolean plan, boolean question, int position) {
		viewPager.setCurrentItem(position);
		planText.setSelected(plan);
		planIndicator.setSelected(plan);
		questionText.setSelected(question);
		questionIndicator.setSelected(question);
	}
}
