package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        Snackbar.make(btnShowSnackBar, "吐司", Snackbar.LENGTH_SHORT).setAction("你点我啊", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(btnShowSnackBar, "轻点点，疼", Snackbar.LENGTH_SHORT).show();
            }
        }).show();
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
