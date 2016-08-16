package com.michen.olaxueyuan.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.michen.olaxueyuan.ui.course.turtor.TurtorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void showTurtorView(){
        Intent turtorIntent = new Intent(getActivity(), TurtorActivity.class);
        startActivity(turtorIntent);
    }

    private void showCommodityView(){
        Intent commodityIntent = new Intent(getActivity(), CommodityActivity.class);
        startActivity(commodityIntent);
    }

}
