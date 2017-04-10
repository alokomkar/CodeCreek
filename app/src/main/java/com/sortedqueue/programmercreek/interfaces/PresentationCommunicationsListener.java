package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.SlideModel;

/**
 * Created by Alok on 10/04/17.
 */

public interface PresentationCommunicationsListener {
    void onPresentationCreation(String presentationId, SlideModel slideModel);
    void onPresentationComplete();
}
