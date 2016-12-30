package com.michen.olaxueyuan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.model.PostDetailBottomGridBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 15/11/23.
 */
public class PostDetailBottomGridAdapter extends BaseAdapter {
    public static final int CAMERA = 0; //拍照
    public static final int PICTURE = 1;//相册
    public static final int VIDEO = 2;//视频
    public static final int VIDEO_RECORD = 3;//录屏
    public List<PostDetailBottomGridBean> listBeans = new ArrayList<>();
    Context context;

    public PostDetailBottomGridAdapter(Context context) {
        this.context = context;
        addData();
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.post_detail_grid_item, null);
            mHolder.imageItem = (ImageView) convertView.findViewById(R.id.img_item);
            mHolder.text_item = (TextView) convertView.findViewById(R.id.text_item);
            mHolder.root_view = convertView.findViewById(R.id.root_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.imageItem.setImageResource(listBeans.get(position).getImg());
        mHolder.text_item.setText(listBeans.get(position).getText());
        return convertView;
    }

    class ViewHolder {
        ImageView imageItem;
        TextView text_item;
        View root_view;
    }

    private void addData() {
        this.listBeans.clear();
        PostDetailBottomGridBean bean1 = new PostDetailBottomGridBean();
        bean1.setImg(R.drawable.camera_bottom_icon);
        bean1.setText("拍照");
        bean1.setPosition(CAMERA);
        this.listBeans.add(bean1);
        PostDetailBottomGridBean bean2 = new PostDetailBottomGridBean();
        bean2.setImg(R.drawable.album_bottom_icon);
        bean2.setText("相册");
        bean2.setPosition(PICTURE);
        this.listBeans.add(bean2);
        PostDetailBottomGridBean bean3 = new PostDetailBottomGridBean();
        bean3.setImg(R.drawable.video_bottom_icon);
        bean3.setPosition(VIDEO);
        bean3.setText("视频");
        this.listBeans.add(bean3);
        PostDetailBottomGridBean bean4 = new PostDetailBottomGridBean();
        bean4.setImg(R.drawable.video_record_bottom_icon);
        bean4.setText("录屏");
        bean4.setPosition(VIDEO_RECORD);
        this.listBeans.add(bean4);
    }
}
