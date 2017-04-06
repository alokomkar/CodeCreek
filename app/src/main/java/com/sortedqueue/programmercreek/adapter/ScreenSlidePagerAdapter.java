package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sortedqueue.programmercreek.fragments.SlideFragment;

import java.util.ArrayList;

/**
 * Created by Alok on 06/04/17.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;
    public ScreenSlidePagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> fragmentArrayList) {
        super(supportFragmentManager);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addNewSlideFragment(SlideFragment slideFragment) {
        fragmentArrayList.add(slideFragment);
    }

    public void removeCurrentFragment(int currentPosition) {
        fragmentArrayList.remove(currentPosition);
    }

    @Override
    public int getItemPosition(Object object) {
        int index = fragmentArrayList.indexOf(object);

        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }
}
