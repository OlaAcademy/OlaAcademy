package com.michen.olaxueyuan.ui.circle.upload;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.snail.photo.adapter.FolderAdapter;
import com.snail.photo.upload.Constants;
import com.snail.photo.util.Bimp;
import com.snail.photo.util.ImageItem;
import com.snail.photo.util.PublicWay;

import java.util.ArrayList;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @version 2014年10月18日  下午11:48:06
 * @QQ:595163260
 */
public class ImageFile extends Activity {

    private FolderAdapter folderAdapter;
    private Button bt_cancel;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(Res.getLayoutID("plugin_camera_image_file"));
        setContentView(R.layout.plugin_camera_image_file);
        PublicWay.activityList.add(this);
        mContext = this;
        bt_cancel = (Button) findViewById(R.id.cancel);
        bt_cancel.setOnClickListener(new CancelListener());
        GridView gridView = (GridView) findViewById(R.id.fileGridView);
        TextView textView = (TextView) findViewById(R.id.headerTitle);
//        textView.setText(Res.getString("photo"));
        textView.setText(getString(R.string.photo));
        folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowAllPhoto.dataList = (ArrayList<ImageItem>) Constants.contentList.get(position).imageList;
                Intent intent = new Intent();
                String folderName = Constants.contentList.get(position).bucketName;
                intent.putExtra("folderName", folderName);
                intent.setClass(mContext, ShowAllPhoto.class);
                mContext.startActivity(intent);
                finish();
            }
        });
    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.tempSelectBitmap.clear();
            finish();
//			Intent intent = new Intent();
//			intent.setClass(mContext, DeployActivity.class);
//			startActivity(intent);
        }
    }

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(mContext, DeployActivity.class);
//			startActivity(intent);
//		}
//
//		return true;
//	}

}
