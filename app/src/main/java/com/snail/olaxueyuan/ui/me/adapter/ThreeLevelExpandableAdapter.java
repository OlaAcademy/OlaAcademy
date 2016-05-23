package com.snail.olaxueyuan.ui.me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.CustomExpandableListView;

/**
 * Base class that can let ExpandableListView show three level
 *
 * @author Liyanshun
 */
public abstract class ThreeLevelExpandableAdapter extends
        BaseExpandableListAdapter {

    public static final String TAG = "ThreeLevelExpandableAdapter";
    public Context mContext;
    private int mThreeLevelColumns = 2;

    public ThreeLevelExpandableAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // for the second level, every child is a single ExpandableListView
        // which has only one child
        ChildExpandableListAdapter carStyleAdapter = new ChildExpandableListAdapter(
                groupPosition, childPosition);
        CustomExpandableListView SecondLevelexplv = new CustomExpandableListView(mContext);
        SecondLevelexplv.setAdapter(carStyleAdapter);
        SecondLevelexplv.setGroupIndicator(null);
        return SecondLevelexplv;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ChildExpandableListAdapter extends BaseExpandableListAdapter {
        private int mFatherGroupPosition, mChildGroupPosition;

        public ChildExpandableListAdapter(int groupPosition, int childPosition) {
            mFatherGroupPosition = groupPosition;
            mChildGroupPosition = childPosition;
        }

        @Override
        public int getGroupCount() {
            // every second level has only one group,which will show second
            // level contents
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // every second level has only one child that is a gridview,this
            // gridview will contains three level contents
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return ThreeLevelExpandableAdapter.this.getChild(
                    mFatherGroupPosition, groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return getGrandChild(mFatherGroupPosition, groupPosition,
                    childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            return getSecondLevleView(mFatherGroupPosition,
                    mChildGroupPosition, isExpanded, convertView, parent);
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ListView listview = null;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.fragment_user_knowledge_listview, null);

                listview = (ListView) convertView.findViewById(R.id.listview);

                listview.setAdapter(new GridAdapter());
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        /**
         * The adapter for the third level gridview
         *
         * @author Liyanshun
         */
        private class GridAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return getThreeLevelCount(mFatherGroupPosition,
                        mChildGroupPosition);
            }

            @Override
            public Object getItem(int position) {
                return getGrandChild(mFatherGroupPosition, mChildGroupPosition,
                        position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return getThreeLevleView(mFatherGroupPosition,
                        mChildGroupPosition, position, convertView, parent);
            }

        }

    }

    /**
     * implement this method to get the count of the third level
     *
     * @param firstLevelPosition
     * @param secondLevelPosition
     * @return the count of the third level
     */
    public abstract int getThreeLevelCount(int firstLevelPosition,
                                           int secondLevelPosition);

    /**
     * implement this method to get the view that will show on the second level
     *
     * @param firstLevelPosition
     * @param secondLevelPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return the view that will show on the second level
     */
    public abstract View getSecondLevleView(int firstLevelPosition,
                                            int secondLevelPosition, boolean isExpanded, View convertView,
                                            ViewGroup parent);

    /**
     * implement this method to get the view that will show on the third level
     *
     * @param firstLevelPosition
     * @param secondLevelPosition
     * @param ThreeLevelPosition
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View getThreeLevleView(int firstLevelPosition,
                                           int secondLevelPosition, int ThreeLevelPosition, View convertView,
                                           ViewGroup parent);

    /**
     * implement this method to get the object on the third level
     *
     * @param groupPosition
     * @param childPosition
     * @param grandChildPosition
     * @return
     */
    public abstract Object getGrandChild(int groupPosition, int childPosition,
                                         int grandChildPosition);
}
