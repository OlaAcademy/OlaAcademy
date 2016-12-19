package com.michen.olaxueyuan.ui.circle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.circle.upload.VideoThumbnailUtil;
import com.snail.photo.util.Bimp;
import com.snail.photo.util.ImageItem;
import com.snail.photo.zoom.PhotoView;

import java.io.File;

public class ReviewMultimediaActivity extends SEBaseActivity {
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private MultiTypePagerAdapter mPagerAdapter;
    private int mCurrentIndex;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_multimedia);
        mInflater = LayoutInflater.from(this);
        mContext = this;

        setTitleText(getString(R.string.preview));
        setRightImage(R.drawable.ic_pic_del);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex >= Bimp.tempSelectBitmap.size()) {
                    mCurrentIndex = 0;
                }
                Bimp.tempSelectBitmap.remove(mCurrentIndex);
                mPagerAdapter.notifyDataSetChanged();
                if (Bimp.tempSelectBitmap.size() == 0) {
                    finish();
                }
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.review_viewPager);
        mPagerAdapter = new MultiTypePagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCurrentIndex = getIntent().getIntExtra(CommonConstant.EXTRA_INDEX_REVIEW, 0);
        mViewPager.setCurrentItem(mCurrentIndex);
    }

    class MultiTypePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Bimp.tempSelectBitmap.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            View convertView = getView(position);
            container.addView(convertView);
            return convertView;
        }

        private View getView(int position) {
            ImageItem imageItem = Bimp.tempSelectBitmap.get(position);
            String type = imageItem.tag.type;
            Bitmap bm = imageItem.getBitmap();
            final String videoPath = imageItem.tag.path;
            final String videoUrl = imageItem.tag.vid;
            View convertView = mInflater.inflate(R.layout.item_viewpager_review, null);
            if (bm != null) {
                PhotoView photoView = (PhotoView) convertView.findViewById(R.id.photoView);
                photoView.setImageDrawable(new BitmapDrawable(getResources(), bm));
            }
            View iv_play = convertView.findViewById(R.id.item_im_videoPlay);
            iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 调用系统自带的播放器来播放流媒体视频
                    Intent playIntent = new Intent(Intent.ACTION_VIEW);
                    if (!TextUtils.isEmpty(videoPath)) {
                        Uri uri = Uri.fromFile(new File(videoPath));
                        playIntent.setDataAndType(uri, "video/*");
                    } else if (!TextUtils.isEmpty(videoUrl)) {
                        String extension = MimeTypeMap.getFileExtensionFromUrl(videoUrl);
                        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                        playIntent.setDataAndType(Uri.parse(SEAPP.MEDIA_BASE_URL + "/" + videoUrl), mimeType);

                    } else {
                        ToastUtil.showToastShort(ReviewMultimediaActivity.this, "播放地址不存在");
                        return;
                    }
                    Intent finalIntent = VideoThumbnailUtil.filterIntentByType(mContext, playIntent);
                    startActivity(finalIntent);
                }
            });
            if ("3".equals(type)) {
                iv_play.setVisibility(View.VISIBLE);
            } else {
                iv_play.setVisibility(View.GONE);
            }

            return convertView;
        }

//        private View getView(int position) {
//            ImageItem imageItem = Bimp.tempSelectBitmap.get(position);
//            String type = imageItem.tag.type;
//            Bitmap bm = imageItem.getBitmap();
//
//            View convertView = null;
//            //1.图片2.音频3.视频
//            if ("1".equals(type)) { //图片
//                convertView = mInflater.inflate(R.layout.item_viewpager_image, null);
//                PhotoView photoView = (PhotoView) convertView.findViewById(R.id.photoView);
//                photoView.setImageDrawable(new BitmapDrawable(getResources(), bm));
//            } else if ("2".equals(type)) { //音频
//                convertView = mInflater.inflate(R.layout.item_viewpager_audio, null);
//            } else if ("3".equals(type)) { //视频
//                convertView = mInflater.inflate(R.layout.item_viewpager_video, null);
//                String videoPath = imageItem.tag.path;
//                String videoUrl = imageItem.tag.vid;
//                final VideoView videoView = (VideoView) convertView.findViewById(R.id.item_videoView);
//                videoView.setBackgroundDrawable(new BitmapDrawable(getResources(), bm));
//
//                final View iv_play = convertView.findViewById(R.id.item_im_videoPlay);
//                final View progressView = convertView.findViewById(R.id.item_progressBar);
////                iv_play.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        iv_play.setVisibility(View.GONE);
////                        progressView.setVisibility(View.VISIBLE);
////                        videoView.requestFocus();
////                    }
////                });
//
//
//                if (!TextUtils.isEmpty(videoPath)) { //本地地址
//                    videoView.setVideoPath(videoPath);
//                } else if (!TextUtils.isEmpty(videoUrl)) {  //网络地址
//                    videoView.setVideoURI(Uri.parse(NTConfig.getInstance().getIMAGEBaseURL() + "/" + videoUrl));
//                }
//                MediaController mediaController = new MediaController(mContext);
//                videoView.setMediaController(mediaController);
//                mediaController.setMediaPlayer(videoView);
//                videoView.requestFocus();
//
//                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                    }
//                });
//                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        showToast("播放完成");
//                    }
//                });
//            }
//
//            return convertView;
//        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);


        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);


        return bm1;
    }

}
