package com.radiant.acsl.myworkapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.radiant.acsl.myworkapp.Adapters.TabPageAdapter;
import com.radiant.acsl.myworkapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentTabHost tabHost;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tabHost = (FragmentTabHost) rootView.findViewById(R.id.tabHost);

        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabContent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Tab 1"), TabHome.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Tab 2"), TabSecond.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Tab 3"), TabThird.class, null);


        return rootView;
    }


}
