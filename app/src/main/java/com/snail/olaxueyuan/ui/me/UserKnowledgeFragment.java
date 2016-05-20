package com.snail.olaxueyuan.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.me.adapter.ThreeLevelExpandableAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by mingge on 2016/5/20.
 */
public class UserKnowledgeFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    private ArrayList<Level1> items;

    class Level1 {
        String title;
        ArrayList<Level2> child = new ArrayList<UserKnowledgeFragment.Level2>();
    }

    class Level2 {
        String title;
        ArrayList<Level3> child = new ArrayList<UserKnowledgeFragment.Level3>();
    }

    class Level3 {
        String title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_knowledge, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        items = new ArrayList<UserKnowledgeFragment.Level1>();
        for (int i = 0; i < 6; i++) {
            Level1 level1 = new Level1();
            level1.title = "Level1-" + i;
            items.add(level1);
            for (int j = 0; j < 7; j++) {
                Level2 level2 = new Level2();
                level2.title = "  Level2-" + j;
                level1.child.add(level2);
                for (int k = 0; k < 8; k++) {
                    Level3 level3 = new Level3();
                    level3.title = "Level3-" + k;
                    level2.child.add(level3);
                }
            }
        }
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new MainAdapter(getActivity()));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MainAdapter extends ThreeLevelExpandableAdapter {

        public MainAdapter(Context context) {
            super(context);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return items.get(groupPosition).child.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).child.get(childPosition);
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
            TextView textView = new TextView(mContext);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(
                    android.R.color.darker_gray));
            Level1 level1 = (Level1) getGroup(groupPosition);
            textView.setText(level1.title);
            return textView;
        }

        @Override
        public int getThreeLevelCount(int firstLevelPosition,
                                      int secondLevelPosition) {
            return ((Level1) getGroup(firstLevelPosition)).child.get(secondLevelPosition).child
                    .size();
        }

        @Override
        public View getSecondLevleView(int firstLevelPosition,
                                       int secondLevelPosition, boolean isExpanded, View convertView,
                                       ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setWidth(600);
            textView.setTextSize(25);
            textView.setTextColor(getResources().getColor(
                    android.R.color.secondary_text_dark));
            Level2 level2 = (Level2) getChild(firstLevelPosition, secondLevelPosition);
            textView.setText(level2.title);
            return textView;
        }

        @Override
        public View getThreeLevleView(final int firstLevelPosition,
                                      final int secondLevelPosition, final int ThreeLevelPosition,
                                      View convertView, ViewGroup parent) {
            Level3 level3 = getGrandChild(firstLevelPosition,
                    secondLevelPosition, ThreeLevelPosition);
            ViewHolderThree viewHolderThree;
            if (convertView == null) {
                viewHolderThree = new ViewHolderThree();
                convertView = View.inflate(mContext, R.layout.fragment_user_knowledge_listview_item, null);
                viewHolderThree.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolderThree);
            } else {
                viewHolderThree = (ViewHolderThree) convertView.getTag();
            }
            viewHolderThree.name.setText(level3.title);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "firstLevelPosition==" + firstLevelPosition + ",secondLevelPosition=" + secondLevelPosition
                            + "ThreeLevelPosition=" + ThreeLevelPosition, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolderThree {
            TextView name;

        }

        @Override
        public Level3 getGrandChild(int groupPosition, int childPosition,
                                    int grandChildPosition) {
            return ((Level2)getChild(groupPosition, childPosition)).child
                    .get(grandChildPosition);
        }

    }
}
