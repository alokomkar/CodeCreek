package com.sortedqueue.programmercreek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.sortedqueue.programmercreek.database.QuickReference;
import com.sortedqueue.programmercreek.fragments.ReferenceFragment;

import java.util.ArrayList;

/**
 * Created by Alok on 11/08/17.
 */

public class QuickReferencePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<ReferenceFragment> referenceFragments = new ArrayList<>();

    public QuickReferencePagerAdapter(FragmentManager childFragmentManager, ArrayList<QuickReference> quickReferences, String language) {
        super(childFragmentManager);
        for( QuickReference quickReference : quickReferences ) {
            referenceFragments.add(ReferenceFragment.newInstance(quickReference, language));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return referenceFragments.get(position);
    }

    @Override
    public int getCount() {
        return referenceFragments.size();
    }
}
