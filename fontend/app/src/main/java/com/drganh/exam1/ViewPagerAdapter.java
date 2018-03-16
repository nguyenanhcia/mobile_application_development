package com.drganh.exam1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by NguyenAnh on 3/17/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new FragmentGetAdress();
                break;
            case 1:
                frag = new FragmentGetKm();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Get Address";
                break;
            case 1:
                title = "Get Km";
                break;
        }
        return title;
    }
}
