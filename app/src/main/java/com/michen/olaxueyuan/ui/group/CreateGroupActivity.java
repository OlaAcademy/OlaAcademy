package com.michen.olaxueyuan.ui.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.manager.UploadMediaManager;
import com.michen.olaxueyuan.protocol.result.CreateGroupResult;
import com.michen.olaxueyuan.protocol.result.UploadImageResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.imagechooser.api.ChooserType;
import com.snail.imagechooser.api.ChosenImage;
import com.snail.imagechooser.api.ImageChooserListener;
import com.snail.imagechooser.api.ImageChooserManager;
import com.snail.photo.upload.UploadResult;
import com.snail.photo.upload.UploadService;
import com.snail.photo.util.PictureUtil;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.File;
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

public class CreateGroupActivity extends SEBaseActivity implements ImageChooserListener {
    @Bind(R.id.add_group_avatar)
    RoundRectImageView addGroupAvatar;
    @Bind(R.id.group_name)
    EditText groupName;
    @Bind(R.id.group_intro)
    EditText groupIntro;
    @Bind(R.id.agreement)
    CheckBox agreement;
    @Bind(R.id.agreement_text)
    TextView agreementText;
    @Bind(R.id.service_declaration)
    TextView serviceDeclaration;
    @Bind(R.id.create_group)
    Button createGroup;
    @Bind(R.id.notice_hint_text)
    TextView noticeHintText;
    @Bind(R.id.notice_view)
    RelativeLayout noticeView;

    private View parentView;
    private Button mBt_camera;
    private Button mBt_photo;
    private Button mBt_cancel;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private Context context;

    private String _updatedAvatarFilename;

    private ImageChooserManager _imageChooserManager;
    private int _chooserType;
    private String _filePath;
    private String _imageName; //上传成功后返回的图片名

    private boolean needToUpdate = false;
    private int sex = 1;
    private String[] selectArray = {};//
    private int groupType = -1;// 1 数学 2 英语 3 逻辑 4 协作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.activity_create_group, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        context = this;
        initView();
        initPop();
    }

    private void initView() {
        setTitleText("创建群");
        addGroupAvatar.setRectAdius(100);
        agreement.setChecked(true);
        agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createGroup.setEnabled(true);
                } else {
                    createGroup.setEnabled(false);
                }
            }
        });
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://upload.olaxueyuan.com").build();
        uploadService = restAdapter.create(UploadService.class);
        selectArray = getResources().getStringArray(R.array.courses);
    }

    public void initPop() {
        pop = new PopupWindow(context);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        /************************************************************/
//        pop = new PopupWindow(DeployActivity.this);
        /************************************************************/
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        mBt_camera = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        mBt_photo = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        mBt_cancel = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(context, AlbumActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                chooseImage();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mBt_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    private void createGroup() {
        String name = groupName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToastShort(context, R.string.group_name_not_null);
            return;
        }
        if (groupType == -1) {
            ToastUtil.showToastShort(context, "请选择群类型");
            return;
        }
        String userId = "";
        try {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToastShort(context, R.string.login_time_out);
            return;
//            userId = "381";
        }
        String profile = groupIntro.getText().toString().trim();//群简介

        if (needToUpdate) {
            uploadAvatar(userId, name, profile);
        } else {
            saveGroupInfo(userId, name, profile);
        }
    }

    private void saveGroupInfo(String userId, String name, String profile) {
//        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        TeacherHomeManager.getInstance().createGroup(userId, String.valueOf(groupType), name, profile, _imageName, new Callback<CreateGroupResult>() {
            @Override
            public void success(CreateGroupResult createGroupResult, Response response) {
                if (context != null && !CreateGroupActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (createGroupResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, createGroupResult.getMessage());
                    } else {
                        ToastUtil.showToastShort(context, "创建群成功");
//                    startActivity(new Intent(context, GroupDetailActivity.class));
                        EventBus.getDefault().post(new PublishHomeWorkSuccessEvent(true));
                        finish();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null && !CreateGroupActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    @OnClick({R.id.service_declaration, R.id.create_group, R.id.add_group_avatar, R.id.notice_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service_declaration:
                break;
            case R.id.create_group:
                createGroup();
                break;
            case R.id.add_group_avatar:
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.notice_view:
                selectGroupType();
                break;
        }
    }

    private void selectGroupType() {
        DialogUtils.showSelectListDialog(this, groupType - 1, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groupType = position + 1;
                noticeHintText.setText(selectArray[position]);
            }
        }, selectArray,true);
    }

    private void chooseImage() {
        _chooserType = ChooserType.REQUEST_PICK_PICTURE;
        _imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE, "tmp", true);
        _imageChooserManager.setImageChooserListener(this);
        try {
            _filePath = _imageChooserManager.choose();
            needToUpdate = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        _chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        _imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_CAPTURE_PICTURE, "tmp", true);
        _imageChooserManager.setImageChooserListener(this);
        try {
            _filePath = _imageChooserManager.choose();
            needToUpdate = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (_imageChooserManager == null) {
                reinitializeImageChooser();
            }
            _imageChooserManager.submit(requestCode, data);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (image != null) {
                    _updatedAvatarFilename = image.getFileThumbnail();
                    updateAvatarImageView();
                }
            }
        });
    }

    private void reinitializeImageChooser() {
        _imageChooserManager = new ImageChooserManager(this, _chooserType, "tmp", true);
        _imageChooserManager.setImageChooserListener(this);
        _imageChooserManager.reinitialize(_filePath);
    }

    @Override
    public void onError(String reason) {
        SVProgressHUD.showInViewWithoutIndicator(this, "无法选择照片，请重试。", 3.f);
    }

    private void updateAvatarImageView() {
        if (_updatedAvatarFilename != null) {
            File imageFile = new File(_updatedAvatarFilename);
            int width = addGroupAvatar.getWidth();
            if (width <= 0) {
                width = 150;
            }
            Picasso.with(this)
                    .load(imageFile)
                    .resize(width, width)
                    .centerCrop()
                    .into(addGroupAvatar);
        }
    }

    private void uploadAvatar(String userId, String name, String profile) {
        // 判断照片是否有旋转
        int degree = PictureUtil.readPictureDegree(_updatedAvatarFilename);
        File imageFile = new File(_updatedAvatarFilename);
        uploadImagesByExecutors(new TypedFile("application/octet-stream", imageFile), degree, userId, name, profile);
        needToUpdate = false;
    }

    private ExecutorService service = Executors.newFixedThreadPool(5);
    private UploadService uploadService;

    private void uploadImagesByExecutors(final TypedFile photo, final int angle, final String userId, final String name, final String profile) {
        service.submit(new Runnable() {
            @Override
            public void run() {

                UploadMediaManager.getInstance().uploadImage(photo,
//                        angle, 480, 320,
                        "jpg", new Callback<UploadImageResult>() {
                    @Override
                    public void success(UploadImageResult result, Response response) {
                        if (context != null && !CreateGroupActivity.this.isFinishing()) {
                            if (result.getApicode() != 10000) {
                                SVProgressHUD.showInViewWithoutIndicator(context, result.getMessage(), 2.0f);
                                return;
                            }
                            SVProgressHUD.showInViewWithoutIndicator(context, getString(R.string.upload_avatar_success), 2);
                            _imageName = result.getResult();
                            saveGroupInfo(userId, name, profile);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (context != null && !CreateGroupActivity.this.isFinishing()) {
                            SVProgressHUD.showInViewWithoutIndicator(context, getString(R.string.upload_avatar_fail), 2.0f);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (context != null && !CreateGroupActivity.this.isFinishing()) {
            SVProgressHUD.dismiss(context);
            SEAPP.dismissAllowingStateLoss();
        }
    }
}
