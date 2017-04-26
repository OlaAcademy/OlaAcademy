package com.michen.olaxueyuan.common.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;

/**
 * 标题管理类
 *
 * @author Administrator
 */
public class TitleManager {
    public static final int LEFT_INDEX_TEXT = 0;
    public static final int LEFT_INDEX_IMAGE = 1;
    public static final int RIGHT_INDEX_SHARE = 2;
    public static final int RIGHT_INDEX_RESPONSE = 3;
    public static final int RIGHT_INDEX_TEXT = 4;
    public static final int TITLE = 5;
    public TextView left_text;
    public TextView left_return;
    public TextView title_tv;
    public ImageView right_response;
    public ImageView right_share;
    public TextView right_text;

    public TitleManager(Activity activity, CharSequence title,
                        View.OnClickListener listener, boolean showLeftIcon) {
        title_tv = (TextView) activity.findViewById(R.id.title_tv);
        title_tv.setText(title);
        title_tv.setOnClickListener(listener);
        left_text = (TextView) activity.findViewById(R.id.left_text);
        left_text.setOnClickListener(listener);
        left_return = (TextView) activity.findViewById(R.id.left_return);
        left_return.setOnClickListener(listener);
        right_response = (ImageView) activity.findViewById(R.id.right_response);
        right_response.setOnClickListener(listener);
        right_share = (ImageView) activity.findViewById(R.id.right_share);
        right_share.setOnClickListener(listener);
        right_text = (TextView) activity.findViewById(R.id.right_texts);
        right_text.setOnClickListener(listener);
        if (showLeftIcon) {
            left_return.setVisibility(View.VISIBLE);
        }
    }

    public TitleManager(Activity activity, int title,
                        View.OnClickListener listener, boolean showLeftIcon) {
        title_tv = (TextView) activity.findViewById(R.id.title_tv);
        title_tv.setText(title);
        title_tv.setOnClickListener(listener);
        left_text = (TextView) activity.findViewById(R.id.left_text);
        left_text.setOnClickListener(listener);
        left_return = (TextView) activity.findViewById(R.id.left_return);
        left_return.setOnClickListener(listener);
        right_response = (ImageView) activity.findViewById(R.id.right_response);
        right_response.setOnClickListener(listener);
        right_share = (ImageView) activity.findViewById(R.id.right_share);
        right_share.setOnClickListener(listener);
        right_text = (TextView) activity.findViewById(R.id.right_texts);
        right_text.setOnClickListener(listener);
        if (showLeftIcon) {
            left_return.setVisibility(View.VISIBLE);
        }
    }

    public TitleManager(CharSequence title, View.OnClickListener listener, View view, boolean showLeftIcon) {
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        title_tv.setText(title);
        title_tv.setOnClickListener(listener);
        left_text = (TextView) view.findViewById(R.id.left_text);
        left_text.setOnClickListener(listener);
        left_return = (TextView) view.findViewById(R.id.left_return);
        left_return.setOnClickListener(listener);
        right_response = (ImageView) view.findViewById(R.id.right_response);
        right_response.setOnClickListener(listener);
        right_share = (ImageView) view.findViewById(R.id.right_share);
        right_share.setOnClickListener(listener);
        right_text = (TextView) view.findViewById(R.id.right_texts);
        right_text.setOnClickListener(listener);
        if (showLeftIcon) {
            left_return.setVisibility(View.VISIBLE);
        }
    }

    public TitleManager(int title, View.OnClickListener listener, View view, boolean showLeftIcon) {
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        title_tv.setText(title);
        title_tv.setOnClickListener(listener);
        left_text = (TextView) view.findViewById(R.id.left_text);
        left_text.setOnClickListener(listener);
        left_return = (TextView) view.findViewById(R.id.left_return);
        left_return.setOnClickListener(listener);
        right_response = (ImageView) view.findViewById(R.id.right_response);
        right_response.setOnClickListener(listener);
        right_share = (ImageView) view.findViewById(R.id.right_share);
        right_share.setOnClickListener(listener);
        right_text = (TextView) view.findViewById(R.id.right_texts);
        right_text.setOnClickListener(listener);
        if (showLeftIcon) {
            left_return.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏图标
     *
     * @param index
     */
    public TitleManager HideImageViews(int... index) {
        for (int i = 0; i < index.length; i++) {
            int x = index[i];
            switch (x) {
                case LEFT_INDEX_TEXT:
                    if (left_text != null) {
                        left_text.setVisibility(View.GONE);
                    }
                    break;
                case LEFT_INDEX_IMAGE:
                    if (left_return != null) {
                        left_return.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_SHARE:
                    if (right_share != null) {
                        right_share.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_RESPONSE:
                    if (right_response != null) {
                        right_response.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_TEXT:
                    if (right_text != null) {
                        right_text.setVisibility(View.GONE);
                    }
                    break;
                case TITLE:
                    if (title_tv != null) {
                        title_tv.setVisibility(View.GONE);
                    }
                    break;
            }
        }
        return this;
    }

    /**
     * 显示图标
     *
     * @param index
     */
    public void showImageView(int... index) {
        for (int i = 0; i < index.length; i++) {
            int x = index[i];
            switch (x) {
                case LEFT_INDEX_TEXT:
                    if (left_text != null && left_return != null) {
                        left_text.setVisibility(View.VISIBLE);
                        left_return.setVisibility(View.GONE);
                    }
                    break;
                case LEFT_INDEX_IMAGE:
                    if (left_return != null && left_text != null) {
                        left_return.setVisibility(View.VISIBLE);
                        left_text.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_SHARE:
                    if (right_share != null && right_response != null && right_text != null) {
                        right_share.setVisibility(View.VISIBLE);
                        right_response.setVisibility(View.GONE);
                        right_text.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_RESPONSE:
                    if (right_share != null && right_response != null && right_text != null) {
                        right_share.setVisibility(View.GONE);
                        right_response.setVisibility(View.VISIBLE);
                        right_text.setVisibility(View.GONE);
                    }
                    break;
                case RIGHT_INDEX_TEXT:
                    if (right_share != null && right_response != null && right_text != null) {
                        right_share.setVisibility(View.GONE);
                        right_response.setVisibility(View.GONE);
                        right_text.setVisibility(View.VISIBLE);
                    }
                    break;
                case TITLE:
                    if (title_tv != null) {
                        title_tv.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    /**
     * 改变文字
     *
     * @param index
     * @param text
     */
    public void changeText(int index, String text) {
        switch (index) {
            case LEFT_INDEX_TEXT:
                if (left_text != null) {
                    left_text.setVisibility(View.VISIBLE);
                    left_text.setText(text);
                }
                break;
            case LEFT_INDEX_IMAGE:
                if (left_return != null) {
                    left_return.setVisibility(View.VISIBLE);
                    left_return.setText(text);
                }
                break;
            case RIGHT_INDEX_SHARE:
                break;
            case RIGHT_INDEX_RESPONSE:
                break;
            case RIGHT_INDEX_TEXT:
                if (right_text != null) {
                    right_text.setVisibility(View.VISIBLE);
                    right_text.setText(text);
                }
                break;
            case TITLE:
                if (title_tv != null) {
                    title_tv.setVisibility(View.VISIBLE);
                    title_tv.setText(text);
                }
                break;
        }
    }

    /**
     * 改变图标
     *
     * @param resId
     */
    public void changeImageRes(int index, int resId) {
        switch (index) {
            case LEFT_INDEX_TEXT:
                break;
            case LEFT_INDEX_IMAGE:
                break;
            case RIGHT_INDEX_SHARE:
                if (right_share != null) {
                    right_share.setVisibility(View.VISIBLE);
                    right_share.setImageResource(resId);
                }
                break;
            case RIGHT_INDEX_RESPONSE:
                if (right_response != null) {
                    right_response.setVisibility(View.VISIBLE);
                    right_response.setImageResource(resId);
                }
                break;
            case RIGHT_INDEX_TEXT:
                break;
            case TITLE:
                break;
        }
    }

    /**
     * 改变文字颜色
     *
     * @param context
     * @param index
     * @param color
     */
    public void changeTextColor(Context context, int index, int color) {
        switch (index) {
            case LEFT_INDEX_TEXT:
                if (left_text != null) {
                    left_text.setVisibility(View.VISIBLE);
                    left_text.setTextColor(context.getResources().getColor(color));
                }
                break;
            case LEFT_INDEX_IMAGE:
                if (left_return != null) {
                    left_return.setVisibility(View.VISIBLE);
                    left_return.setTextColor(context.getResources().getColor(color));
                }
                break;
            case RIGHT_INDEX_SHARE:
                break;
            case RIGHT_INDEX_RESPONSE:
                break;
            case RIGHT_INDEX_TEXT:
                if (right_text != null) {
                    right_text.setVisibility(View.VISIBLE);
                    right_text.setTextColor(context.getResources().getColor(color));
                }
                break;
            case TITLE:
                if (title_tv != null) {
                    title_tv.setVisibility(View.VISIBLE);
                    title_tv.setTextColor(context.getResources().getColor(color));
                }
                break;
        }
    }
}
