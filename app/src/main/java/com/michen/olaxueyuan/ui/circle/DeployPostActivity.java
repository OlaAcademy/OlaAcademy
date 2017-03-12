package com.michen.olaxueyuan.ui.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.NoScrollGridView;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Bind(R.id.question_type_hint_text)
    TextView questionTypeHintText;
    @Bind(R.id.question_type_view)
    RelativeLayout questionTypeView;
    @Bind(R.id.et_title)
    EditText mEt_title;
    @Bind(R.id.et_content)
    EditText msgET;
    @Bind(R.id.iv_switch_open_orignal)
    ImageView iv_switch_open_orignal;
    @Bind(R.id.iv_switch_close_orignal)
    ImageView iv_switch_close_orignal;
    @Bind(R.id.rl_orignal)
    RelativeLayout rl_switch_orignal;
    @Bind(R.id.iv_video)
    ImageView ivVideo;
    @Bind(R.id.noScrollgridview)
    NoScrollGridView noScrollgridview;
    @Bind(R.id.img_switch_open_appoint)
    ImageView imgSwitchOpenAppoint;
    @Bind(R.id.iv_switch_close_appoint)
    ImageView ivSwitchCloseAppoint;
    @Bind(R.id.appoint_answer_view)
    RelativeLayout appointAnswerView;
    @Bind(R.id.invite_hint_text)
    TextView inviteHintText;
    @Bind(R.id.invite_answer_view)
    RelativeLayout inviteAnswerView;
    @Bind(R.id.is_public_hint_text)
    TextView isPublicHintText;
    @Bind(R.id.is_public_view)
    RelativeLayout isPublicView;
    /****************************************************/
    private Button bt1;
    private Button bt2;
    private Button bt3;
    /****************************************************/
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;

    private boolean isOrignalImage = true;
    private UploadService uploadService;
    private int uploadNum = 0;
    private String imageGids = "";
    private String mTitle;
    private String phoneInfo;//手机信息
    private OptionsPickerView pvOptions;
    private ArrayList<String> courses = new ArrayList<>();//类型
    private ArrayList<String> publics = new ArrayList<>();//是否公开
    private int optionType = 1;//区分选择器返回的类型；1、类型；2、是否公开
    private int isPublic = 0;//是否公开
    private int assignUser;//指定回答者Id
    private String postType;//

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        Bimp.tempSelectBitmap.clear();
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_deploy_post, null);
        setContentView(parentView);
        ButterKnife.bind(this);

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
        getPhoneInfo();
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SVProgressHUD.dismiss(this);
        SEAPP.dismissAllowingStateLoss();
    }

    public void Init() {
        initPop();

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
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        String[] coursesArray = getResources().getStringArray(R.array.deploy_post_course);
        for (int i = 0; i < coursesArray.length; i++) {
            courses.add(coursesArray[i]);
        }
        String[] publicArray = getResources().getStringArray(R.array.public_select);
        for (int i = 0; i < publicArray.length; i++) {
            publics.add(publicArray[i]);
        }
        pvOptions.setPicker(courses);
        pvOptions.setCyclic(false);
        //监听确定选择按钮
        pvOptions.setSelectOptions(0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                switch (optionType) {
                    case 1:
                        questionTypeHintText.setText(courses.get(options1));
                        break;
                    case 2:
                        isPublicHintText.setText(publics.get(options1));
                        isPublic = options1;
                        break;
                }
            }
        });
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
        postType = questionTypeHintText.getText().toString().trim();
        if (TextUtils.isEmpty(postType)) {
            ToastUtil.showToastShort(mContext, "请选择类型");
            return;
        }
//        SVProgressHUD.showInView(this, "正在发布中，请稍后...", true);
        SEAPP.showCatDialog(this);
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
        circleManager.deployPost(userId, mTitle, msgET.getText().toString(), imageGids, "", "2", phoneInfo
                , String.valueOf(assignUser), String.valueOf(isPublic), new Callback<MCCommonResult>() {
                    @Override
                    public void success(MCCommonResult result, Response response) {
                        if (!DeployPostActivity.this.isFinishing()) {
                            SEAPP.dismissAllowingStateLoss();
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
                        if (!DeployPostActivity.this.isFinishing()) {
                            SEAPP.dismissAllowingStateLoss();
                        }
                    }
                });
    }


    @OnClick({R.id.question_type_view, R.id.rl_orignal, R.id.appoint_answer_view
            , R.id.invite_answer_view, R.id.is_public_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.question_type_view:
                optionType = 1;
                pvOptions.setTitle("选择类型");
                pvOptions.setPicker(courses);
                pvOptions.show();
                break;
            case R.id.rl_orignal:
                if (iv_switch_open_orignal.getVisibility() == View.VISIBLE) {
                    iv_switch_open_orignal.setVisibility(View.INVISIBLE);
                    iv_switch_close_orignal.setVisibility(View.VISIBLE);
                    isOrignalImage = false;
                } else {
                    iv_switch_open_orignal.setVisibility(View.VISIBLE);
                    iv_switch_close_orignal.setVisibility(View.INVISIBLE);
                    isOrignalImage = true;
                }
                break;
            case R.id.appoint_answer_view:
                if (imgSwitchOpenAppoint.getVisibility() == View.VISIBLE) {
                    imgSwitchOpenAppoint.setVisibility(View.INVISIBLE);
                    ivSwitchCloseAppoint.setVisibility(View.VISIBLE);
                    inviteAnswerView.setVisibility(View.INVISIBLE);
                    isPublicView.setVisibility(View.INVISIBLE);
                } else {
                    imgSwitchOpenAppoint.setVisibility(View.VISIBLE);
                    ivSwitchCloseAppoint.setVisibility(View.INVISIBLE);
                    inviteAnswerView.setVisibility(View.VISIBLE);
                    isPublicView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.invite_answer_view:
                startActivityForResult(new Intent(this, AppointTeacherListActivity.class), CommonConstant.DEPLOY_POST_APPOINT_TEACHER_FOR_RESULT);
                break;
            case R.id.is_public_view:
                optionType = 2;
                pvOptions.setTitle("是否公开");
                pvOptions.setPicker(publics);
                pvOptions.show();
                break;
        }
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

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (Bimp.tempSelectBitmap.size() < 9) {
                        String fileName = String.valueOf(System.currentTimeMillis());
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setImagePath(FileUtils.saveBitmap(bm, fileName));
                        takePhoto.setBitmap(bm);
                        Bimp.tempSelectBitmap.add(takePhoto);
                    }
                    break;
                case CommonConstant.DEPLOY_POST_APPOINT_TEACHER_FOR_RESULT:
                    assignUser = data.getExtras().getInt("id");
                    String name = data.getExtras().getString("name");
                    inviteHintText.setText(name);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        SEAPP.dismissAllowingStateLoss();
//        SVProgressHUD.dismiss(DeployPostActivity.this);
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

    private void getPhoneInfo() {
        String userPhone = "";
        try {
            if (SEAuthManager.getInstance().getAccessUser() != null) {
                userPhone = SEAuthManager.getInstance().getAccessUser().getPhone();
            }
            phoneInfo = "安卓手机厂商: " + Build.MANUFACTURER
                    + ";安卓手机型号: " + Build.MODEL
                    + ";安卓sdk版本code:" + Build.VERSION.SDK_INT
                    + ";安卓sdk版本名称:" + Build.VERSION.RELEASE
                    + ";欧拉MBA版本信息:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName
                    + ";欧拉MBA版本code:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode
                    + ";手机号码:" + userPhone;
            Logger.e(phoneInfo);
        } catch (Exception e) {
            e.printStackTrace();
            phoneInfo = "安卓手机厂商: " + Build.MANUFACTURER
                    + "安卓手机型号: " + Build.MODEL
                    + ";安卓sdk版本code:" + Build.VERSION.SDK_INT
                    + ";安卓sdk版本名称:" + Build.VERSION.RELEASE;
        }
    }

}


