package com.michen.olaxueyuan.ui.home.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.stickview.StickyListHeadersAdapter;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingge on 2016/9/2.
 */
public class TeacherHomeListAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {

    private final Context mContext;

    private String[] mCountries;

    private int[] mSectionIndices;

    private String[] mSectionLetters;

    private LayoutInflater mInflater;

    private List<HomeworkListResult.ResultBean> mConferenceList = new ArrayList<>();

    private ProgressDialog dialog;

    private int currentPos = -1;

    private final Handler handler = new Handler();

    public void update(List<HomeworkListResult.ResultBean> conferenceList) {
        initValues(conferenceList);
        this.notifyDataSetChanged();
    }


    private final int index = 8;

    public TeacherHomeListAdapter(Context context, List<HomeworkListResult.ResultBean> orderList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initValues(orderList);
    }

    private void initValues(List<HomeworkListResult.ResultBean> orderList) {
        mConferenceList = orderList;
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

    private static final int DEFAULT_INDEX = 6;

    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        // char lastFirstChar = mCountries[0].charAt(0);
        if (mConferenceList.size() <= 0) {
            return null;
        }
        char lastFirstChar = mConferenceList.get(0).getTime().charAt(DEFAULT_INDEX);
        sectionIndices.add(0);
        for (int i = 1; i < mConferenceList.size(); i++) {
            if (lastFirstChar != mConferenceList.get(i).getTime().charAt(DEFAULT_INDEX)) {
                lastFirstChar = mConferenceList.get(i).getTime().charAt(DEFAULT_INDEX);
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private String[] getSectionLetters() {
        if (mConferenceList.size() <= 0) {
            return null;
        }
        String[] letters = new String[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = mConferenceList.get(mSectionIndices[i]).getTime()
                    .substring(DEFAULT_INDEX, DEFAULT_INDEX + 1);
        }
        return letters;
    }

    @Override
    public int getCount() {
        if (mConferenceList != null && mConferenceList.size() > 0) {
            return mConferenceList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mConferenceList != null && mConferenceList.size() > 0) {
            mConferenceList.size();
        }
        return mConferenceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.conference_item_layout, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.sponor = (TextView) convertView.findViewById(R.id.spontor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mConferenceList == null || mConferenceList.size() <= 0
                || position > mConferenceList.size()) {
            return convertView;
        }
        final HomeworkListResult.ResultBean conferenceItemModel = mConferenceList.get(position);

//        holder.title.setText(conferenceItemModel.getTitle());
//        holder.address.setText(mContext.getString(R.string.address, conferenceItemModel.getLocal()));
//        holder.sponor.setText(mContext.getString(R.string.sponsor, conferenceItemModel.getSponsor()));
        holder.time.setText(conferenceItemModel.getTime().substring(8, 11));

        final ViewHolder viewHolder = holder;
        return convertView;
    }

    @Override
    public View getHeaderView(final int position, View convertView,
                              ViewGroup parent) {
        HeaderViewHolder holder;
        convertView = mInflater
                .inflate(R.layout.conference_header_item_layout, parent, false);
        TextView text = (TextView) convertView.findViewById(R.id.time);

        if (mConferenceList == null || mConferenceList.size() <= 0
                || position > mConferenceList.size()) {
            return convertView;
        }
        String month = mConferenceList.get(position).getTime().substring(DEFAULT_INDEX - 1, DEFAULT_INDEX + 1);
        if (month.startsWith("0")) {
            month = month.substring(1, 2);
        }
        text.setText(mContext.getString(R.string.month, month));


        return convertView;
    }


    /**
     * Remember that these have to be static, postion=1 should always return the
     * same Id that is.
     */
    @Override
    public long getHeaderId(int position) {
        // return the first character of the country as ID because this is what
        // headers are based upon
        // return mCountries[position].subSequence(0, 1).charAt(0);
        // return commodityList.get(position).getTime().substring(0,
        // index).charAt(0);
        // return strToDate(commodityList.get(position).getCtime());
        return strToLong(mConferenceList.get(position).getTime().substring(0, DEFAULT_INDEX + 1));
    }

    private long strToLong(String str) {
        return Long.valueOf(str.hashCode());
    }

    @Override
    public int getPositionForSection(int section) {
        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    TextView payStatus;

    public void clear() {
        mCountries = new String[0];
        mSectionIndices = new int[0];
        mSectionLetters = new String[0];
        notifyDataSetChanged();
    }

    public void restore() {
        // mCountries =
        // mContext.getResources().getStringArray(R.array.countries);
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {

        public TextView title;

        public TextView address;

        public TextView sponor;

        public TextView time;
    }

}