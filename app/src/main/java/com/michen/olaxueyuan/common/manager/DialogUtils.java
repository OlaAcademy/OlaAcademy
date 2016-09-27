package com.michen.olaxueyuan.common.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;

import butterknife.Bind;


/**
 * Created by mingge on 16/3/22.
 */
public class DialogUtils {
    @Bind(R.id.name_one)
    TextView nameOne;
    @Bind(R.id.img_one)
    ImageView imgOne;
    @Bind(R.id.select_one)
    RelativeLayout selectOne;
    @Bind(R.id.name_two)
    TextView nameTwo;
    @Bind(R.id.img_two)
    ImageView imgTwo;
    @Bind(R.id.select_two)
    RelativeLayout selectTwo;
    @Bind(R.id.name_three)
    TextView nameThree;
    @Bind(R.id.img_three)
    ImageView imgThree;
    @Bind(R.id.select_three)
    RelativeLayout selectThree;
    @Bind(R.id.close)
    TextView close;

    public static void showDialog(Context context, final View.OnClickListener listener, String content, String sure, String cancle) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirm_collect_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int screenWidth = Utils.getScreenMetrics(context).x;
        params.width = screenWidth - 160;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        TextView text = (TextView) view.findViewById(R.id.content);
        TextView noView = (TextView) view.findViewById(R.id.no);
        TextView yesView = (TextView) view.findViewById(R.id.yes);
        text.setText(content);
        if (!TextUtils.isEmpty(cancle)) {
            noView.setText(cancle);
        }
        if (!TextUtils.isEmpty(sure)) {
            yesView.setText(sure);
        }
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
    }

    public static void showSelectQuestionDialog(Context context, final View.OnClickListener listener, int selectType, String[] selectArray) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_select_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int screenWidth = Utils.getScreenMetrics(context).x;
        params.width = screenWidth - 230;
//        params.width = Utils.dip2px(context, 260);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        final ImageView imgOne = (ImageView) view.findViewById(R.id.img_one);
        final ImageView imgTwo = (ImageView) view.findViewById(R.id.img_two);
        final ImageView imgThree = (ImageView) view.findViewById(R.id.img_three);
        RelativeLayout selectOne = (RelativeLayout) view.findViewById(R.id.select_one);
        RelativeLayout selectTwo = (RelativeLayout) view.findViewById(R.id.select_two);
        RelativeLayout selectThree = (RelativeLayout) view.findViewById(R.id.select_three);
        TextView nameOne = (TextView) view.findViewById(R.id.name_one);
        TextView nameTwo = (TextView) view.findViewById(R.id.name_two);
        TextView nameThree = (TextView) view.findViewById(R.id.name_three);
        nameOne.setText(selectArray[0]);
        nameTwo.setText(selectArray[1]);
        nameThree.setText(selectArray[2]);

        TextView closeView = (TextView) view.findViewById(R.id.close);
        switch (selectType) {
            case 1:
                imgTwo.setVisibility(View.VISIBLE);
                break;
            case 2:
                imgThree.setVisibility(View.VISIBLE);
                break;
            case 0:
            default:
                imgOne.setVisibility(View.VISIBLE);
                break;
        }

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        selectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
                imgOne.setVisibility(View.VISIBLE);
                imgTwo.setVisibility(View.GONE);
                imgThree.setVisibility(View.GONE);
            }
        });
        selectTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
                imgOne.setVisibility(View.GONE);
                imgTwo.setVisibility(View.VISIBLE);
                imgThree.setVisibility(View.GONE);
            }
        });
        selectThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
                imgOne.setVisibility(View.GONE);
                imgTwo.setVisibility(View.GONE);
                imgThree.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 签到
     *
     * @param context
     * @param listener
     * @param dayNum
     * @param dayScore
     * @param subjectNum
     */
    public static void showSignDialog(Context context, final View.OnClickListener listener, String dayNum, String dayScore, String subjectNum) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sign_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int screenWidth = Utils.getScreenMetrics(context).x;
        params.width = screenWidth - 160;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        ImageView closeIcon = (ImageView) view.findViewById(R.id.close_icon);

        TextView signDayNum = (TextView) view.findViewById(R.id.sign_day_num);
        TextView signDayScore = (TextView) view.findViewById(R.id.sign_day_score);
        TextView signSubjectNum = (TextView) view.findViewById(R.id.sign_subject_num);

        RadioButton wechatSign = (RadioButton) view.findViewById(R.id.wechat_sign);
        RadioButton wechatCircleSign = (RadioButton) view.findViewById(R.id.wechat_circle_sign);
        RadioButton qqFriendSign = (RadioButton) view.findViewById(R.id.qq_friend_sign);
        RadioButton qqFriend_spaceSign = (RadioButton) view.findViewById(R.id.qq_friend_space_sign);
        RadioButton sinaSign = (RadioButton) view.findViewById(R.id.sina_sign);

        if (!TextUtils.isEmpty(dayNum)) {
            signDayNum.setText(dayNum);
        }
        if (!TextUtils.isEmpty(dayScore)) {
            signDayScore.setText(dayScore);
        }
        if (!TextUtils.isEmpty(subjectNum)) {
            signSubjectNum.setText(subjectNum);
        }
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        wechatSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        wechatCircleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        qqFriendSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        qqFriend_spaceSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
        sinaSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClick(view);
            }
        });
    }
}
