package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sortedqueue.programmercreek.database.TutorialModel;
import com.sortedqueue.programmercreek.fragments.TutorialModelFragment;

import java.util.ArrayList;

/**
 * Created by Alok on 15/05/17.
 */

public class TutorialSlidesPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TutorialModel> tutorialModels;
    private ArrayList<Fragment> fragmentArrayList;


    public TutorialSlidesPagerAdapter(FragmentManager fm, ArrayList<TutorialModel> tutorialModels) {
        super(fm);
        this.fragmentArrayList = new ArrayList<>();
        this.tutorialModels = tutorialModels;
        int index = 1;
        for( TutorialModel tutorialModel : tutorialModels ) {
            TutorialModelFragment tutorialModelFragment = new TutorialModelFragment();
            tutorialModelFragment.setParameter( tutorialModel, index++ );
            fragmentArrayList.add(tutorialModelFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return tutorialModels.size();
    }
}
