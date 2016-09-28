package com.michen.olaxueyuan.ui.circle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.PostDetailModule;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.PostCommentAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.story.activity.ImagePagerActivity;
import com.snail.photo.util.NoScrollGridView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostDetailActivity extends SEBaseActivity implements PlatformActionListener, Handler.Callback {
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.study_name)
    TextView studyName;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.comment_comment)
    TextView commentComment;
    @Bind(R.id.comment_empty)
    TextView commentEmpty;
    @Bind(R.id.comment_list)
    SubListView listView;
    @Bind(R.id.comment_praise)
    TextView commentPraise;
    @Bind(R.id.comment)
    ImageView comment;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.bt_send)
    Button btSend;

    private SharePopupWindow shareView;

    PostCommentAdapter commentAdapter;
    private Context mContext;
    private LocationClient mLocationClient;
    private String location;
    private CommentModule.ResultBean commentResultBean;
    private int circleId;
    private PostDetailModule.ResultBean resultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        etContent.clearFocus();
        setTitleText("欧拉分享");
        initView();
        locations();
        getCommentListData();
    }

    private void initView() {
        circleId = getIntent().getIntExtra("circleId", 0);
        queryCircleDetail(String.valueOf(circleId));
        commentAdapter = new PostCommentAdapter(mContext);
        listView.setAdapter(commentAdapter);
    }

    private void getCommentListData() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().getCommentList(String.valueOf(circleId), "2", new Callback<CommentModule>() {
            @Override
            public void success(CommentModule commentModule, Response response) {
                Logger.json(commentModule);
                SVProgressHUD.dismiss(mContext);
                if (commentModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, commentModule.getMessage(), 2.0f);
                } else {
                    commentAdapter.upDateData(commentModule.getResult());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(mContext);
            }
        });
    }

    private void queryCircleDetail(String circleId) {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().queryCircleDetail(circleId, new Callback<PostDetailModule>() {
            @Override
            public void success(PostDetailModule postDetailModule, Response response) {
                SVProgressHUD.dismiss(mContext);
                if (postDetailModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, postDetailModule.getMessage(), 2.0f);
                } else {
                    resultBean=postDetailModule.getResult();
                    updateDetail(postDetailModule.getResult());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(mContext);
            }
        });
    }

    private void updateDetail(PostDetailModule.ResultBean resultBean) {
        avatar.setRectAdius(100);
        title.setText(resultBean.getUserName());
        if (!TextUtils.isEmpty(resultBean.getUserAvatar())) {
            String avatarUrl = "";
//            if (resultBean.getUserAvatar().indexOf("jpg") != -1) {
            if (resultBean.getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + resultBean.getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + resultBean.getUserAvatar();
            }
            Picasso.with(mContext).load(avatarUrl).placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(avatar);
        } else {
            avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
        }
        time.setText(resultBean.getTime());
        studyName.setText(resultBean.getContent());
        commentPraise.setText(String.valueOf(resultBean.getPraiseNumber()));
        if (!TextUtils.isEmpty(resultBean.getLocation())) {
            address.setText("@" + resultBean.getLocation());
        } else {
            address.setText("");
        }
        if (!TextUtils.isEmpty(resultBean.getImageGids())) {
            ArrayList<String> imageUrls = getListFromString(resultBean.getImageGids());
            final ArrayList<String> imageList = imageUrls;
            if (imageUrls.size() == 1) {
                gridview.setNumColumns(1);
            } else {
                gridview.setNumColumns(3);
            }
            gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
            // 点击回帖九宫格，查看大图
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageBrower(position, imageList);
                }
            });
        } else {
            gridview.setVisibility(View.GONE);
        }
    }

    private ArrayList<String> getListFromString(String images) {
        ArrayList imageUrlList = new ArrayList();
        String[] imageUrlArray = images.split(",");
        for (String imgUrl : imageUrlArray) {
            imageUrlList.add(SEAPP.PIC_BASE_URL + imgUrl);
        }
        return imageUrlList;
    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    @OnClick({R.id.comment_praise, R.id.comment, R.id.share, R.id.bt_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_praise:
                praise();
                break;
            case R.id.comment:
                Utils.showInputMethod(PostDetailActivity.this);
                this.commentResultBean = null;
                break;
            case R.id.share:
                share();
                break;
            case R.id.bt_send:
                addComment();
                break;
            default:
                break;
        }
    }

    private void praise() {
        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        MCCircleManager.getInstance().praiseCirclePost(String.valueOf(circleId), new Callback<PraiseCirclePostResult>() {
            @Override
            public void success(PraiseCirclePostResult mcCommonResult, Response response) {
                SVProgressHUD.dismiss(mContext);
                if (mcCommonResult.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, mcCommonResult.getMessage(), 2.0f);
                } else {
                    resultBean.setPraiseNumber(resultBean.getPraiseNumber() + 1);
                    commentPraise.setText(String.valueOf(resultBean.getPraiseNumber()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(mContext);
                ToastUtil.showToastShort(mContext, R.string.data_request_fail);
            }
        });
    }

    private void share() {
        PostDetailModule.ResultBean circle = resultBean;
        shareView = new SharePopupWindow(this);
        shareView.setPlatformActionListener(this);
        ShareModel model = new ShareModel();
//        if (circle.getUserAvatar().indexOf("jpg") != -1) {
        if (circle.getUserAvatar().contains(".")) {
            model.setImageUrl("http://api.olaxueyuan.com/upload/" + circle.getUserAvatar());
        } else {
            model.setImageUrl(SEAPP.PIC_BASE_URL + circle.getUserAvatar());
        }
        model.setText(circle.getContent());
        model.setTitle("欧拉学院");
        model.setUrl(SEConfig.getInstance().getAPIBaseURL() + "/circlepost.html?circleId=" + circle.getId());
        shareView.initShareParams(model);
        shareView.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        shareView.showAtLocation(findViewById(R.id.circle_detail),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    /**
     * {@link com.michen.olaxueyuan.ui.adapter.PostCommentAdapter#getView(int, View, ViewGroup)}
     */
    public void onEventMainThread(CommentModule.ResultBean comments) {
        Utils.showInputMethod(PostDetailActivity.this);
        this.commentResultBean = comments;
        etContent.setFocusable(true);
    }

    private void addComment() {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user != null) {
            String postId = String.valueOf(circleId);
            String toUserId;
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToastShort(mContext, R.string.fill_comment_content);
                return;
            }
            if (commentResultBean != null) {
                Logger.json(commentResultBean);
                toUserId = String.valueOf(commentResultBean.getUserId());
            } else {
                toUserId = "";
            }
            SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
            QuestionCourseManager.getInstance().addComment(user.getId(), postId, toUserId
                    , content, location, "2", new Callback<CommentSucessResult>() {
                        @Override
                        public void success(CommentSucessResult commentSuccess, Response response) {
                            SVProgressHUD.dismiss(mContext);
                            etContent.setText("");
                            etContent.clearComposingText();
                            getCommentListData();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            SVProgressHUD.dismiss(mContext);
                        }
                    });
            this.commentResultBean = null;
        } else {
            startActivity(new Intent(mContext, UserLoginActivity.class));
        }
    }

    private void locations() {
        mLocationClient = new LocationClient(mContext);
        initLocation();
        //开始定位 精确到市
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mLocationClient.stop();
                if (bdLocation == null) {
                    return;
                }
//                Log.e("Test", bdLocation.getAddrStr());
                try {
                    location = bdLocation.getCity() + bdLocation.getDistrict();
                } catch (Exception e) {
                    e.printStackTrace();
                    location="定位失败";
                }
            }

        });
        mLocationClient.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /////////////////////// 分享相关  ///////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        if (shareView != null) {
            shareView.dismiss();
        }
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform plat, int action,
                           HashMap<String, Object> res) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        Message msg = new Message();
        msg.what = 1;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (shareView != null) {
            shareView.dismiss();
        }
        return false;
    }
}
