package com.michen.olaxueyuan.ui.course;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.model.MCSubCourse;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

import java.util.List;


public class CourseListActivity extends SEBaseActivity {

    private ListView courseListView;
    private CourseListAdapter adapter;


    private List<CourseVideoResult.ResultBean.VideoListBean> videoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        setTitleText("课程");

        List<MCSubCourse> courseList = (List<MCSubCourse>) getIntent().getSerializableExtra("courseList");

        courseListView = (ListView) findViewById(R.id.lv_point);

        adapter = new CourseListAdapter(CourseListActivity.this, courseList);
        courseListView.setAdapter(adapter);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

}
