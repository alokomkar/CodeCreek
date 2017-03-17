package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.Algorithm;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;

/**
 * Created by Alok Omkar on 2017-03-17.
 */
public interface AlgorithmNavigationListener {
    void loadAlgoritmsListFragment();
    void loadAlgorithmFragment(AlgorithmsIndex algorithm);

}
