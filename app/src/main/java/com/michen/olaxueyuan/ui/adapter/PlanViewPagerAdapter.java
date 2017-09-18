package com.michen.olaxueyuan.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.plan.PlanFragment;
import com.michen.olaxueyuan.ui.question.QuestionFragment;

/**
 * Created by mingge on 17/9/2.
 */
public class PlanViewPagerAdapter extends FragmentStatePagerAdapter {
	private QuestionFragment questionFragment;
	private PlanFragment planFragment;

	public PlanViewPagerAdapter(FragmentManager fm) {
		super(fm);
		constrct();
	}

	private void constrct() {
		questionFragment = new QuestionFragment();
		planFragment = new PlanFragment();
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return planFragment;
			case 1:
				return questionFragment;
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
