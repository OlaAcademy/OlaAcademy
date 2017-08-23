package com.michen.olaxueyuan.ui.me.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.michen.olaxueyuan.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySnackBarActivity extends AppCompatActivity {

	@Bind(R.id.btnShowSnackBar)
	Button btnShowSnackBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_snack_bar);
		ButterKnife.bind(this);
	}

	private void initView() {
//        Snackbar.make(btnShowSnackBar, "吐司", Snackbar.LENGTH_SHORT).setAction("你点我啊", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(btnShowSnackBar, "轻点点，疼", Snackbar.LENGTH_SHORT).show();
//            }
//        }).show();
		Snackbar snackbar = Snackbar.make(findViewById(R.id.coord_inator_layout), "更新了20条内容", Snackbar.LENGTH_LONG);
//		Snackbar snackbar = Snackbar.make(btnShowSnackBar, "更新了20条内容", Snackbar.LENGTH_LONG);
		View snackbarView = snackbar.getView();
		snackbarView.setBackgroundColor(getResources().getColor(R.color.snackbar_blue));
//		snackbarView.setBackgroundColor(Color.BLUE);
		snackbar.setActionTextColor(R.color.white);
		TextView tvSnackbarText = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
		tvSnackbarText.setTextSize(14);
		tvSnackbarText.setGravity(Gravity.CENTER);
		snackbar.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@OnClick(R.id.btnShowSnackBar)
	public void onClick() {
		initView();
	}
}
