package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.fragments.ViewSlideFragment;

import java.util.ArrayList;

/**
 * Created by Alok on 11/04/17.
 */

public class ViewSlidesPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<SlideModel> slideModelArrayList;
    private ArrayList<ViewSlideFragment> fragmentArrayList;


    public ViewSlidesPagerAdapter(FragmentManager fm, ArrayList<SlideModel> slideModelArrayList) {
        super(fm);
        this.fragmentArrayList = new ArrayList<>();
        this.slideModelArrayList = slideModelArrayList;
        for( SlideModel slideModel : slideModelArrayList ) {
            ViewSlideFragment viewSlideFragment = new ViewSlideFragment();
            viewSlideFragment.setParameter( slideModel );
            fragmentArrayList.add(viewSlideFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return slideModelArrayList.size();
    }
}
