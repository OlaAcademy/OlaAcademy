package com.michen.olaxueyuan.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by mingge on 2016/9/2.
 */
public class SubListView extends ListView {
    public SubListView(Context context,
                       AttributeSet attrs) {
        super(context, attrs);
    }

    public SubListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SubListView(Context context) {
        super(context);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
