package com.michen.olaxueyuan.ui.course.turtor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.MCOrgManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.MCOrgInfo;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15/12/20.
 */
public class OrgInfoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MCOrgInfo> orgList;
    private final static int USER_LOGIN = 0x1213;

    public OrgInfoAdapter(Context context, ArrayList<MCOrgInfo> orgList) {
        super();
        this.context = context;
        this.orgList = orgList;
    }

    @Override
    public int getCount() {
        return orgList.size();
    }

    @Override
    public Object getItem(int position) {
        if (orgList != null) {
            return orgList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_org_info, null);
            holder = new ViewHolder();
            holder.iv_avatar = (RoundRectImageView) convertView.findViewById(R.id.avatar_org);
            holder.tv_name = (TextView) convertView.findViewById(R.id.name_org);
            holder.tv_enroll = (TextView) convertView.findViewById(R.id.enrollCount);
            holder.tv_org = (TextView) convertView.findViewById(R.id.org);
            holder.btn_checkIn = (Button) convertView.findViewById(R.id.checkIn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MCOrgInfo orgInfo = orgList.get(position);
        holder.tv_name.setText(orgInfo.name);
        holder.tv_enroll.setText("已有" + orgInfo.checkinCount + "人报名");
        holder.iv_avatar.setRectAdius(100);
        if (!TextUtils.isEmpty(orgInfo.logo)) {
            Picasso.with(context).load(orgInfo.logo).placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar).config(Bitmap.Config.RGB_565)
                    .resize(Utils.dip2px(context, 35), Utils.dip2px(context, 35)).into(holder.iv_avatar);
        }
        holder.tv_org.setText(orgInfo.org);
        if (orgInfo.checkedIn == 1) {
            holder.btn_checkIn.setText("已报名");
        } else {
            holder.btn_checkIn.setText("立即报名");
        }
        holder.btn_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SEAuthManager am = SEAuthManager.getInstance();
                if (!am.isAuthenticated()) {
                    Intent intent = new Intent(context, UserLoginActivity.class);
                    ((TurtorActivity) context).startActivityForResult(intent, USER_LOGIN);
                    return;
                }
                enroll(orgInfo.id, am.getAccessUser().getPhone());
            }
        });
        return convertView;
    }

    private void enroll(final String orgId, String userPhone) {
        MCOrgManager orgManager = MCOrgManager.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SVProgressHUD.showInView(context, "请稍后...", true);
        orgManager.enroll(orgId, userPhone, "", formatter.format(curDate), new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                SVProgressHUD.dismiss(context);
                if (result.apicode.equals("10000")) {
                    new AlertDialog.Builder(context)
                            .setTitle("报名成功")
                            .setMessage("报名信息已发往欧拉学院")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    for (int i = 0; i < orgList.size(); i++) {
                        MCOrgInfo org = orgList.get(i);
                        if (org.id.equals(orgId)) {
                            org.checkedIn = 1;
                            orgList.set(i, org);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(context);
            }
        });

    }

    class ViewHolder {
        private RoundRectImageView iv_avatar;
        private TextView tv_name;
        private TextView tv_enroll;
        private TextView tv_org;
        private Button btn_checkIn;
    }

}
