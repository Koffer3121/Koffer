package com.example.koffer;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPageAdapter extends FragmentStatePagerAdapter {

    String[] tabarray = new String[]{"One","Two","Three"};
    Integer tabnumber = 3;

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabarray[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                TabOne tabOne = new TabOne();
                return tabOne;
            case 1:
                TabTwo tabTwo = new TabTwo();
                return tabTwo;
            case 2:
                TabThree tabThree = new TabThree();
                return tabThree;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}
