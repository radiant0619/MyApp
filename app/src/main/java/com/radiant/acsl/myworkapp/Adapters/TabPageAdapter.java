package com.radiant.acsl.myworkapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.radiant.acsl.myworkapp.Fragments.TabHome;
import com.radiant.acsl.myworkapp.Fragments.TabSecond;
import com.radiant.acsl.myworkapp.Fragments.TabThird;

/**
 * Created by sakthivel on 30/11/2016.
 */

public class TabPageAdapter extends FragmentPagerAdapter {
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Top Rated fragment activity
                return new TabHome();
            case 1:
                // Games fragment activity
                return new TabSecond();
            case 2:
                // Movies fragment activity
                return new TabThird();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
