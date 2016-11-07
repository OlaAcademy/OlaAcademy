package com.michen.olaxueyuan.ui.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.circle.upload.AlbumActivity;
import com.michen.olaxueyuan.ui.circle.upload.GalleryActivity;
import com.snail.photo.upload.UploadResult;
import com.snail.photo.upload.UploadService;
import com.snail.photo.util.Bimp;
import com.snail.photo.util.FileUtils;
import com.snail.photo.util.ImageItem;
import com.snail.photo.util.PictureUtil;
import com.snail.photo.util.PublicWay;
import com.snail.photo.util.Res;
import com.snail.svprogresshud.SVProgressHUD;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * @author 田晓鹏
 * @version 2014年10月18日  下午11:48:34
 */
public class DeployPostActivity extends SEBaseActivity {

    /****************************************************/
    private Button bt1;
    private Button bt2;
    private Button bt3;
    /****************************************************/
    private GridView noScrollgridview;
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    private TextView mTv_location;
    private String mLocation = "";

    private EditText msgET;
    private RelativeLayout /*markRL,*/ rl_switch_orignal;
    //    private TextView markText;
    private ImageView iv_switch_open_orignal, iv_switch_close_orignal;

    private boolean isOrignalImage = false;

    private UploadService uploadService;
    private int uploadNum = 0;
    private String imageGids = "";

    private EditText mEt_title;
    private String mTitle;

    private LocationClient mLocationClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        Bimp.tempSelectBitmap.clear();
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_deploy_post, null);
        setContentView(parentView);

        Init();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://upload.olaxueyuan.com").build();
        uploadService = restAdapter.create(UploadService.class);
        setTitleText(getResources().getString(R.string.title_activity_deploy_post));
        setRightText(getResources().getString(R.string.publish));
        setLeftImageListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < PublicWay.activityList.size(); i++) {
                    if (null != PublicWay.activityList.get(i)) {
                        PublicWay.activityList.get(i).finish();
                    }
                }
                Bimp.tempSelectBitmap.clear();
            }
        });
        setRightTextListener(new OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     doUpload();
                                 }
                             }
        );
    }

    public void Init() {
        mTv_location = (TextView) findViewById(R.id.tv_location);
        mLocationClient = new LocationClient(this);
        initLocation();
        //开始定位 精确到市
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mLocationClient.stop();
                if (bdLocation == null) {
                    mTv_location.setText("定位失败");
                    return;
                }
//                Log.e("Test", bdLocation.getAddrStr());
                try {
                    String city = bdLocation.getCity();
                    String district = bdLocation.getDistrict();
                    if (city == null) {
                        city = "";
                    }
                    if (district == null) {
                        district = "";
                    }
                    mLocation = city + district;
                } catch (Exception e) {
                    e.printStackTrace();
                    mLocation = "";
                }
                mTv_location.setText(mLocation);
            }

        });
        mLocationClient.start();
        initPop();

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(DeployPostActivity.this, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

        msgET = (EditText) findViewById(R.id.et_content);

        rl_switch_orignal = (RelativeLayout) findViewById(R.id.rl_orignal);
        iv_switch_open_orignal = (ImageView) findViewById(R.id.iv_switch_open_orignal);
        iv_switch_close_orignal = (ImageView) findViewById(R.id.iv_switch_close_orignal);

        rl_switch_orignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_orignal.getVisibility() == View.VISIBLE) {
                    iv_switch_open_orignal.setVisibility(View.INVISIBLE);
                    iv_switch_close_orignal.setVisibility(View.VISIBLE);
                    isOrignalImage = false;
                } else {
                    iv_switch_open_orignal.setVisibility(View.VISIBLE);
                    iv_switch_close_orignal.setVisibility(View.INVISIBLE);
                    isOrignalImage = true;
                }
            }
        });

        mEt_title = (EditText) findViewById(R.id.et_title);
    }

    /**
     * 设置相关参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    public void doUpload() {
        //标题
        mTitle = mEt_title.getText().toString().trim();
        String content = msgET.getText().toString().trim();
        //内容
        int imageCount = Bimp.tempSelectBitmap.size();
        if (TextUtils.isEmpty(content) || content.length() < 2) {
            Toast.makeText(this, "请输入不少于两个字的帖子内容", Toast.LENGTH_SHORT).show();
            return;
        }
        SVProgressHUD.showInView(this, "正在发布中，请稍后...", true);
        if (imageCount == 0) { //没有图片直接上传
            saveInfo("");
        } else {
            for (int i = 0; i < imageCount; i++) {
                String tempPath;
                // 判断照片是否有旋转
                int degree = PictureUtil.readPictureDegree(Bimp.tempSelectBitmap.get(i).imagePath);
                File imageFile;
                if (isOrignalImage) {  //原图
                    imageFile = new File(Bimp.tempSelectBitmap.get(i).getImagePath());
                } else {
                    Bitmap bitmap = PictureUtil.getSmallBitmap(Bimp.tempSelectBitmap.get(i).imagePath);
                    tempPath = FileUtils.saveBitmap(bitmap, "snail_temp" + i);
                    imageFile = new File(tempPath);
                }
                Logger.e("imageFile==" + imageFile);
                uploadImagesByExecutors(new TypedFile("application/octet-stream", imageFile), degree);
            }
        }
    }

    private ExecutorService service = Executors.newFixedThreadPool(5);

    private void uploadImagesByExecutors(final TypedFile photo, final int angle) {
        service.submit(new Runnable() {

            @Override
            public void run() {

                uploadService.uploadImage(photo, angle, 480, 320, "jpg", new Callback<UploadResult>() {
                    @Override
                    public void success(UploadResult result, Response response) {
                        uploadNum++;
                        if (result.code != 1) {
                            SVProgressHUD.showInViewWithoutIndicator(DeployPostActivity.this, result.message, 2.0f);
                            return;
                        }
                        imageGids = imageGids + result.imgGid + ",";
                        if (uploadNum == Bimp.tempSelectBitmap.size()) {
                            Bimp.tempSelectBitmap.clear();
                            saveInfo(imageGids);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        uploadNum++;
                        if (uploadNum == Bimp.tempSelectBitmap.size()) {
                            Bimp.tempSelectBitmap.clear();
                            if (imageGids.equals("")) {
                                SVProgressHUD.showInViewWithoutIndicator(DeployPostActivity.this, "图片上传失败", 2.0f);
                            } else {
                                saveInfo(imageGids);
                            }
                        }
                    }
                });
            }

        });
    }


    private void saveInfo(String imageGids) {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        String userId = user.getId();
        Log.e("DeployActivity", "imageGids: " + imageGids);
        final MCCircleManager circleManager = MCCircleManager.getInstance();
        circleManager.deployPost(userId, mTitle, msgET.getText().toString(), imageGids, mLocation, "2", new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (!DeployPostActivity.this.isFinishing()) {
                    if (!result.apicode.equals("10000")) {
                        SVProgressHUD.showInViewWithoutIndicator(DeployPostActivity.this, result.message, 2.0f);
                        return;
                    }
                    EventBus.getDefault().post(true);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        SVProgressHUD.dismiss(this);
    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            notifyDataSetChanged();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                        String fileName = String.valueOf(System.currentTimeMillis());
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setImagePath(FileUtils.saveBitmap(bm, fileName));
                        takePhoto.setBitmap(bm);
                        Bimp.tempSelectBitmap.add(takePhoto);
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        SVProgressHUD.dismiss(DeployPostActivity.this);
        for (int i = 0; i < PublicWay.activityList.size(); i++) {
            if (null != PublicWay.activityList.get(i)) {
                PublicWay.activityList.get(i).finish();
            }
        }
        Bimp.tempSelectBitmap.clear();
        super.onBackPressed();
    }

    private void initPop() {
        pop = new PopupWindow(DeployPostActivity.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(LayoutParams.MATCH_PARENT);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        /************************************************************/
//        pop = new PopupWindow(DeployActivity.this);
        /************************************************************/
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DeployPostActivity.this, AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }
}


