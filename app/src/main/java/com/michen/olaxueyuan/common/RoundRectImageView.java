package com.michen.olaxueyuan.common;

/**
 * Created by mingge on 16/3/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by kevin on 15/6/8.
 */
public class RoundRectImageView extends ImageView {
	public RoundRectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundRectImageView(Context context) {
		super(context);
		init();
	}

	private final RectF roundRect = new RectF();
	private float rect_adius = 5;
	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();

	private void init() {
		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		//
		zonePaint.setAntiAlias(true);
		zonePaint.setColor(Color.WHITE);
		//
		float density = getResources().getDisplayMetrics().density;
		rect_adius = rect_adius * density;
	}

	public void setRectAdius(float adius) {
		rect_adius = adius;
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		roundRect.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
		//
		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.restore();
	}

	@Override
	public void setLayoutParams(ViewGroup.LayoutParams params) {
		super.setLayoutParams(params);
		invalidate();
	}
}
