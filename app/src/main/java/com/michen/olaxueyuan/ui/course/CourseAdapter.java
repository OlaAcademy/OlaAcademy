package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.SEAutoSlidingPagerView;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.model.MCSubCourse;
import com.michen.olaxueyuan.protocol.result.MCBannerResult;
import com.michen.olaxueyuan.ui.common.HorizontalListView;
import com.michen.olaxueyuan.ui.index.ImagePagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.snail.svprogresshud.SVProgressHUD;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class CourseAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<MCSubCourse> courseList = new ArrayList<>();

    //定义两个int常量标记不同的Item视图
    public static final int PIC_ITEM = 0;
    public static final int PIC_WORD_ITEM = 1;

    public CourseAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(ArrayList<MCSubCourse> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (courseList != null) {
            return courseList.size() + 1;  //顶部滚动图占据一行
        } else {
            return 1;
        }
    }

    @Override
    public Object getItem(int index) {
        if (courseList != null && index > 0) {
            return courseList.get(index - 1);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return PIC_ITEM;
        } else {
            return PIC_WORD_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        //因为有两种视图，所以返回2
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        TopViewHolder topViewHolder = null;
        if (getItemViewType(position) == PIC_ITEM) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.view_information_top, null);
                topViewHolder = new TopViewHolder();
                topViewHolder.autoSlidingPagerView = (SEAutoSlidingPagerView) convertView.findViewById(R.id.autoSlideImage);
                int width = context.getResources().getDisplayMetrics().widthPixels;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width * 300 / 750);
                topViewHolder.autoSlidingPagerView.setLayoutParams(layoutParams);
                convertView.setTag(topViewHolder);
            } else {
                topViewHolder = (TopViewHolder) convertView.getTag();
            }
            final SEAutoSlidingPagerView slidingPagerView = topViewHolder.autoSlidingPagerView;
            final SECourseManager courseManager = SECourseManager.getInstance();
            courseManager.fetchHomeBanner(new Callback<MCBannerResult>() {
                @Override
                public void success(MCBannerResult result, Response response) {
                    if (!result.apicode.equals("10000")) {
                        SVProgressHUD.showInViewWithoutIndicator(context, result.message, 2.0f);
                    } else {
                        slidingPagerView.setAdapter(new ImagePagerAdapter(context, result.bannerList));
                        slidingPagerView.setOnPageChangeListener(new MyOnPageChangeListener());
                        slidingPagerView.setInterval(4000);
                        slidingPagerView.startAutoScroll();
                        slidingPagerView.setCycle(true);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_course, null);
                holder = new ViewHolder();
                holder.tv_all = (TextView) convertView.findViewById(R.id.allTV);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.horizontalListView = (HorizontalListView) convertView.findViewById(R.id.horizontalListView);
                int width = Utils.getScreenWidth(context);
                int height = (width / 2 - 45) * 350 / 750 + Utils.dip2px(context, 55);
                ViewGroup.LayoutParams layoutParams = holder.horizontalListView.getLayoutParams();
                layoutParams.height = height;
                holder.horizontalListView.setLayoutParams(layoutParams);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MCSubCourse course = courseList.get(position - 1);
//            Logger.e("course=="+course.toString());
            holder.tv_title.setText(course.name);
            holder.tv_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseListActivity.class);
                    intent.putExtra("courseList", (Serializable) course.subCourseArrayList);
                    context.startActivity(intent);
                }
            });
            HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(course.subCourseArrayList);
            holder.horizontalListView.setAdapter(adapter);
            holder.horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, CourseVideoActivity.class);
                    intent.putExtra("pid", course.subCourseArrayList.get(position).id);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }


    class ViewHolder {
        private TextView tv_title;
        private TextView tv_all;
        private HorizontalListView horizontalListView;
    }

    class TopViewHolder {
        SEAutoSlidingPagerView autoSlidingPagerView;
    }

    class GridViewHolder {
        private TextView tv_name;
        private ImageView iv_course;
        private TextView tv_time;
        private TextView tv_browser;
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private class HorizontalListViewAdapter extends BaseAdapter {

        private ArrayList<MCSubCourse> subCourseList;

        public HorizontalListViewAdapter(ArrayList<MCSubCourse> subCourseList) {
            this.subCourseList = subCourseList;
        }

        public void updateData(ArrayList<MCSubCourse> subCourseList) {
            this.subCourseList = subCourseList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_gridview_course, null);
                viewHolder = new GridViewHolder();
                viewHolder.iv_course = (ImageView) convertView.findViewById(R.id.iv_course);
                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) viewHolder.iv_course.getLayoutParams();
                Resources resources = context.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                linearParams.width = dm.widthPixels / 2 - 45;
                linearParams.height = linearParams.width * 350 / 750;
                if (position % 2 == 0)
                    linearParams.setMargins(30, 0, 15, 0);
                else
                    linearParams.setMargins(15, 0, 30, 0);
                viewHolder.iv_course.setLayoutParams(linearParams);
                viewHolder.iv_course.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.tv_browser = (TextView) convertView.findViewById(R.id.tv_browser);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GridViewHolder) convertView.getTag();
            }
            MCSubCourse course = subCourseList.get(position);
            viewHolder.tv_name.setText(course.name);
            viewHolder.tv_time.setText(course.totalTime);
            viewHolder.tv_browser.setText(context.getString(R.string.num_watch, course.playcount));
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(course.address, viewHolder.iv_course, options);
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            if (courseList != null) {
                return courseList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
//            return subCourseList.size()<=2?subCourseList.size():2;
            return subCourseList.size();
        }
    }
}
