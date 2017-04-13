package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.firebase.Code;

/**
 * Created by Alok on 12/04/17.
 */

public interface CodeLabNavigationListener {
    void loadCompileCodeFragment(Code code);
    void loadCodeLanguagesFragment();
}
