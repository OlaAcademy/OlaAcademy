package com.michen.olaxueyuan.ui.circle.upload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.photo.adapter.AlbumGridViewAdapter;
import com.snail.photo.upload.Constants;
import com.snail.photo.util.AlbumHelper;
import com.snail.photo.util.Bimp;
import com.snail.photo.util.ImageItem;
import com.snail.photo.util.PicInfo;
import com.snail.photo.util.PublicWay;
import com.snail.photo.util.Res;

import java.util.ArrayList;

/**
 * 进入相册显示所有图片的界面
 */
public class AlbumActivity extends SEBaseActivity {
    //显示手机里的所有图片的列表控件
    private GridView gridView;
    //当手机里没有图片时，提示用户没有图片的控件
    private TextView tv;
    //gridView的adapter
    private AlbumGridViewAdapter gridImageAdapter;
    //完成按钮
    private Button okButton;
    private Intent intent;
    // 预览按钮
    private Button preview;
    private Context mContext;
    private ArrayList<ImageItem> dataList;
    private AlbumHelper helper;
    public static Bitmap bitmap;

    private boolean isReg = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_album"));
        setTitleText("选取图片");
        PublicWay.activityList.add(this);
        mContext = this;
        register();
        bitmap = BitmapFactory.decodeResource(getResources(), Res.getDrawableID("plugin_camera_no_pictures"));
        init();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
        isShowOkBt();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mContext.unregisterReceiver(this);
            // TODO Auto-generated method stub  
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    // 预览按钮的监听
    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "1");
                intent.setClass(AlbumActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        }

    }

    // 完成按钮的监听
    private class AlbumSendListener implements OnClickListener {
        public void onClick(View v) {
            overridePendingTransition(com.snail.photo.R.anim.activity_translate_in, com.snail.photo.R.anim.activity_translate_out);
//            intent.setClass(mContext, DeployActivity.class);
//            startActivity(intent);
            finish();
        }

    }

    // 相册监听
    private class PhotoListener implements OnClickListener {
        public void onClick(View v) {
            intent.setClass(AlbumActivity.this, ImageFile.class);
            startActivity(intent);
            finish();
        }
    }

    // 取消按钮的监听
    private class CancelListener implements OnClickListener {
        public void onClick(View v) {
            Bimp.tempSelectBitmap.clear();
//            intent.setClass(mContext, DeployActivity.class);
//            startActivity(intent);
            finish();
        }
    }


    // 初始化，给一些对象赋值
    private void init() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        Constants.contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<ImageItem>();
        for (int i = 0; i < Constants.contentList.size(); i++) {
            dataList.addAll(Constants.contentList.get(i).imageList);
        }

        setRightText("相册");
        setRightTextListener(new PhotoListener());
        setLeftImageListener(new CancelListener());
        preview = (Button) findViewById(Res.getWidgetID("preview"));
        preview.setOnClickListener(new PreviewListener());
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        gridView = (GridView) findViewById(Res.getWidgetID("myGrid"));
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(Res.getWidgetID("myText"));
        gridView.setEmptyView(tv);
        okButton = (Button) findViewById(Res.getWidgetID("ok_button"));
        okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size()
                + "/" + PublicWay.num + ")");
    }

    private void initListener() {

        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final ToggleButton toggleButton, int position, boolean isChecked, CheckBox chooseBt) {
                if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
                    toggleButton.setChecked(false);
                    if (!removeOneData(dataList.get(position))) {
                        Toast.makeText(AlbumActivity.this, Res.getString("only_choose_num"),Toast.LENGTH_SHORT).show();
                    }else{ //移除成功
                        chooseBt.setChecked(false);
                    }
                    return;
                }//原有代码
//                if (isChecked) {
//                    chooseBt.setChecked(true);
//                    Bimp.tempSelectBitmap.add(dataList.get(position));
//                    okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
               //病例假中做出的修改
                if (isChecked) {
                    chooseBt.setChecked(true);
                    ImageItem ima = dataList.get(position);
                    PicInfo pi = new PicInfo();
                    pi.type = "1";
                    pi.isNew = true;
                    ima.tag = pi;
                    Bimp.tempSelectBitmap.add(ima);
                    okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                } else {
                    chooseBt.setChecked(false);
                    Bimp.tempSelectBitmap.remove(dataList.get(position));
                    okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
                }
                isShowOkBt();
            }
        });

        okButton.setOnClickListener(new AlbumSendListener());

    }

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            return true;
        }
        return false;
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            okButton.setTextColor(Color.WHITE);
            preview.setTextColor(Color.WHITE);
        } else {
            okButton.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
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
            //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
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
