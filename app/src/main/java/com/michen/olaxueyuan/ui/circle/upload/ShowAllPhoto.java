package com.michen.olaxueyuan.ui.circle.upload;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.michen.olaxueyuan.R;
import com.snail.photo.adapter.AlbumGridViewAdapter;
import com.snail.photo.util.Bimp;
import com.snail.photo.util.ImageItem;
import com.snail.photo.util.PicInfo;
import com.snail.photo.util.PublicWay;

import java.util.ArrayList;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 *
 * @author king
 * @version 2014年10月18日  下午11:49:10
 * @QQ:595163260
 */
public class ShowAllPhoto extends Activity {
    private GridView gridView;
    private ProgressBar progressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    // 完成按钮
    private Button okButton;
    // 预览按钮
    private Button preview;
    // 返回按钮
    private Button back;
    // 取消按钮
    private Button cancel;
    // 标题
    private TextView headTitle;
    private Intent intent;
    private Context mContext;
    public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();

    private boolean isReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.plugin_camera_show_all_photo);
        PublicWay.activityList.add(this);
        mContext = this;
        back = (Button) findViewById(R.id.showallphoto_back);
        cancel = (Button) findViewById(R.id.showallphoto_cancel);
        preview = (Button) findViewById(R.id.showallphoto_preview);
        okButton = (Button) findViewById(R.id.showallphoto_ok_button);
        headTitle = (TextView) findViewById(R.id.showallphoto_headtitle);
        this.intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        headTitle.setText(folderName);
        cancel.setOnClickListener(new CancelListener());
        back.setOnClickListener(new BackListener(intent));
        preview.setOnClickListener(new PreviewListener());
        init();
        initListener();
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub  
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "2");
                intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
                startActivity(intent);
            }
        }

    }

    private class BackListener implements OnClickListener {// 返回按钮监听
        Intent intent;

        public BackListener(Intent intent) {
            this.intent = intent;
        }

        public void onClick(View v) {
            intent.setClass(ShowAllPhoto.this, ImageFile.class);
            startActivity(intent);
        }

    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.tempSelectBitmap.clear();
//            intent.setClass(mContext, DeployActivity.class);
//            startActivity(intent);
            finish();
        }
    }

    private void init() {
        register();
        progressBar = (ProgressBar) findViewById(R.id.showallphoto_progressbar);
        progressBar.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.showallphoto_myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        okButton = (Button) findViewById(R.id.showallphoto_ok_button);
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
                    public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, CheckBox button) {
                        if (Bimp.tempSelectBitmap.size() >= PublicWay.num && isChecked) {
                            toggleButton.setChecked(false);
//                            button.setChecked(false);
                            Toast.makeText(ShowAllPhoto.this, ShowAllPhoto.this.getString(R.string.only_choose_num), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (isChecked) {
                            button.setChecked(true);
//                            Bimp.tempSelectBitmap.add(dataList.get(position));
                            ImageItem ima = dataList.get(position);
                            PicInfo pi = new PicInfo();
                            pi.type = "1";
                            pi.isNew = true;
                            ima.tag = pi;
                            Bimp.tempSelectBitmap.add(ima);
                            okButton.setText(getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                        } else {
                            button.setChecked(false);
                            Bimp.tempSelectBitmap.remove(dataList.get(position));
                            okButton.setText(getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                        }
                        isShowOkBt();
                    }
                });

        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                okButton.setClickable(false);
//				if (PublicWay.photoService != null) {
//					PublicWay.selectedDataList.addAll(Bimp.tempSelectBitmap);
//					Bimp.tempSelectBitmap.clear();
//					PublicWay.photoService.onActivityResult(0, -2,
//							intent);
//				}
//                intent.setClass(mContext, DeployActivity.class);
//                startActivity(intent);
                // Intent intent = new Intent();
                // Bundle bundle = new Bundle();
                // bundle.putStringArrayList("selectedDataList",
                // selectedDataList);
                // intent.putExtras(bundle);
                // intent.setClass(ShowAllPhoto.this, UploadPhoto.class);
                // startActivity(intent);
                finish();

            }
        });

    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            okButton.setText(getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText(getString(R.string.finish) + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            okButton.setTextColor(Color.parseColor("#E1E0DE"));
            preview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    private void register() {
        if (!isReg) {
            IntentFilter filter = new IntentFilter("data.broadcast.action");
            registerReceiver(broadcastReceiver, filter);
            isReg = true;
        }
    }

    private void unregister() {
        if (isReg) {
            //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
            unregisterReceiver(broadcastReceiver);
            isReg = false;
        }
    }

}
